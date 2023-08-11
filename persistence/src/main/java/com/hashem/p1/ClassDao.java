package com.hashem.p1;

import com.hashem.p1.models.CClass;
import com.hashem.p1.models.ClassStatistics;
import com.hashem.p1.models.Role;
import com.hashem.p1.models.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class ClassDao implements AutoCloseable {

    final Connection db;

    public ClassDao() {
        this.db = ConnectionFactory.getDbConnection();
    }

    public Set<CClass> getClasses() throws Exception {
        var sqlQuery = """
                SELECT
                    classes.id AS class_id,
                    classes.name AS class_name,
                    users.id AS user_id,
                    users.email,
                    roles.name AS role_name,
                    roles.id as role_id
                FROM
                    classes
                        LEFT JOIN
                    user_classes ON classes.id = user_classes.class_id
                        LEFT JOIN
                    users ON user_classes.user_id = users.id
                        LEFT JOIN
                    user_roles ON users.id = user_roles.user_id
                        LEFT JOIN
                    roles ON user_roles.role_id = roles.id;
                """;
        var statement = db.prepareStatement(sqlQuery);
        var resultSet = statement.executeQuery();
        return extractclassesFromResultSet(resultSet);
    }

    public ClassStatistics getClassStatistics(int id) throws ClassDoesNotExistException, SQLException {
        if (!classExists(id)) throw new ClassDoesNotExistException();
        var sqlQuery = """
                WITH GradeRanks AS (
                    SELECT grade,
                           PERCENT_RANK() OVER (ORDER BY grade) AS percentile
                    FROM grades
                    WHERE class_id = ?
                )
                                
                SELECT
                    AVG(grade) AS AverageGrade,
                    MAX(grade) AS HighestGrade,
                    MIN(grade) AS LowestGrade,
                    (
                        SELECT AVG(grade)
                        FROM GradeRanks
                        WHERE percentile BETWEEN 0.49 AND 0.51
                    ) AS MedianGrade,
                    STDDEV(grade) AS StandardDeviation,
                    VARIANCE(grade) AS Variance,
                    (MAX(grade) - MIN(grade)) AS GradeRange,
                    (
                        SELECT grade
                        FROM (
                                 SELECT grade, COUNT(*) as frequency
                                 FROM grades
                                 WHERE class_id = ?
                                 GROUP BY grade
                                 ORDER BY frequency DESC, grade ASC
                                 LIMIT 1
                             ) as ModeSubquery
                    ) AS Mode
                FROM grades
                WHERE class_id = ?;
                """;
        var statement = db.prepareStatement(sqlQuery);
        statement.setInt(1, id);
        statement.setInt(2, id);
        statement.setInt(3, id);
        var resultSet = statement.executeQuery();
        var stats = new ArrayList<ClassStatistics>();

        if (resultSet.next()) {

            return new ClassStatistics(
                    resultSet.getFloat("AverageGrade"),
                    resultSet.getFloat("HighestGrade"),
                    resultSet.getFloat("LowestGrade"),
                    resultSet.getFloat("StandardDeviation"),
                    resultSet.getFloat("Variance"),
                    resultSet.getFloat("GradeRange"),
                    resultSet.getFloat("MedianGrade"),
                    resultSet.getFloat("Mode")

            );
        }

        return new ClassStatistics();
    }

    public Set<CClass> getClasses(int id) throws Exception {
        var sqlQuery = """
                SELECT
                    classes.id AS class_id,
                    classes.name AS class_name,
                    users.id AS user_id,
                    users.email,
                    roles.name AS role_name,
                    roles.id as role_id
                FROM
                    classes
                LEFT JOIN
                    user_classes ON classes.id = user_classes.class_id
                LEFT JOIN
                    users ON user_classes.user_id = users.id
                LEFT JOIN
                    user_roles ON users.id = user_roles.user_id
                LEFT JOIN
                    roles ON user_roles.role_id = roles.id
                WHERE
                    classes.id IN (
                        SELECT
                            class_id
                        FROM
                            user_classes
                        WHERE
                            user_id = ?
                    );
                 """;
        var statement = db.prepareStatement(sqlQuery);
        statement.setInt(1, id);
        var resultSet = statement.executeQuery();

        return extractclassesFromResultSet(resultSet);
    }

    public Set<User> getUsersForClass(int classId) throws SQLException {
        String query = """
                SELECT users.id AS user_id, users.email, roles.id as role_id ,roles.name AS role_name
                FROM users
                         JOIN user_classes ON users.id = user_classes.user_id
                         JOIN user_roles ON users.id = user_roles.user_id
                         JOIN roles ON user_roles.role_id = roles.id
                WHERE user_classes.class_id = ?
                ORDER BY users.id;
                """;
        var statement = db.prepareStatement(query);
        statement.setInt(1, classId);
        ResultSet resultSet = statement.executeQuery();

        Map<Integer, User> userMap = new HashMap<>();

        while (resultSet.next()) {
            int userId = resultSet.getInt("user_id");
            String email = resultSet.getString("email");
            String roleName = resultSet.getString("role_name");
            int roleId = resultSet.getInt("user_id");

            // Get user from map or create a new one if it doesn't exist
            var user = userMap.getOrDefault(
                    userId,
                    new User(userId, email, "", new HashSet<>())
            );
            user.roles().add(new Role(roleId, roleName));

            userMap.put(userId, user);
        }

        return new HashSet<>(userMap.values());
    }

    private Set<CClass> extractclassesFromResultSet(ResultSet resultSet) throws SQLException {
        var classesMap = new HashMap<Integer, CClass>();
        var usersMap = new HashMap<Integer, User>();
        while (resultSet.next()) {
            var classId = resultSet.getInt("class_id");
            var className = resultSet.getString("class_name");
            var clazz = classesMap.getOrDefault(classId, new CClass(classId, className, new HashSet<>()));

            var userId = resultSet.getInt("user_id");

            if (userId != 0) {
                var userEmail = resultSet.getString("email");

                var user = usersMap.getOrDefault(userId, new User(userId, userEmail, "", new HashSet<>()));
                var roleId = resultSet.getInt("role_id");
                var roleName = resultSet.getString("role_name");
                if (roleId != 0)
                    user.roles().add(new Role(roleId, roleName));

                usersMap.put(userId, user);
                clazz.users().add(user);
            }

            classesMap.put(classId, clazz);
        }
        return new HashSet<>(classesMap.values());
    }

    public boolean update(int id, String newClassName) throws SQLException, ClassDoesNotExistException {

        if (!classExists(id)) throw new ClassDoesNotExistException();

        var sqlQuery = "update classes set name = ? where id = ?";
        var statement = db.prepareStatement(sqlQuery);
        statement.setString(1, newClassName);
        statement.setInt(2, id);

        return statement.executeUpdate() > 0;
    }

    public int create(int creatorId, String name) throws SQLException, ClassAlreadyExistsException {

        db.setAutoCommit(false);
        if (classExists(name)) throw new ClassAlreadyExistsException();

        var createClassQuery = "insert into classes (id,name) value (default, ?)";
        var createClassStatement = db.prepareStatement(
                createClassQuery, Statement.RETURN_GENERATED_KEYS);
        createClassStatement.setString(1, name);
        createClassStatement.executeUpdate();
        var generatedKeys = createClassStatement.getGeneratedKeys();

        if (!generatedKeys.next())
            throw new SQLException("Creating class failed, no ID obtained.");

        var insertUserQuery = "insert into user_classes (user_id, class_id) value (?,?)";
        var insertUserQueryStatement = db.prepareStatement(insertUserQuery);
        insertUserQueryStatement.setInt(1, creatorId);
        insertUserQueryStatement.setInt(2, generatedKeys.getInt(1));
        insertUserQueryStatement.executeUpdate();


        db.commit();
        db.setAutoCommit(true);
        return generatedKeys.getInt(1);
    }

    public boolean delete(int id) throws SQLException, ClassDoesNotExistException {

        if (!classExists(id)) throw new ClassDoesNotExistException();

        var sqlQuery = "delete from classes where id = ?";
        var statement = db.prepareStatement(sqlQuery);
        statement.setInt(1, id);

        return statement.executeUpdate() > 0;
    }


    boolean classExists(String name) throws SQLException {
        String classExistsQuery = "SELECT EXISTS(SELECT 1 FROM classes WHERE name = ?)";

        var preparedStatement = db.prepareStatement(classExistsQuery);
        preparedStatement.setString(1, name);
        var resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getBoolean(1);
        }
        return false;
    }

    boolean classExists(int id) throws SQLException {
        String classExistsQuery = "SELECT EXISTS(SELECT 1 FROM classes WHERE id = ?)";

        var preparedStatement = db.prepareStatement(classExistsQuery);
        preparedStatement.setInt(1, id);
        var resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getBoolean(1);
        }
        return false;
    }

    @Override
    public void close() throws Exception {
        db.close();
    }
}

