package com.hashem.p1;

import com.hashem.p1.models.CClass;
import com.hashem.p1.models.Role;
import com.hashem.p1.models.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ClassDao implements AutoCloseable {

    final Connection db;

    public ClassDao() {
        this.db = ConnectionFactory.getDbConnection();
    }

    public Set<CClass> getClasses() throws Exception {
        var sqlQuery = """
                SELECT
                    Classes.id AS class_id,
                    Classes.name AS class_name,
                    Users.id AS user_id,
                    Users.email,
                    Roles.name AS role_name,
                    Roles.id as role_id
                FROM
                    Classes
                        LEFT JOIN
                    UserClasses ON Classes.id = UserClasses.class_id
                        LEFT JOIN
                    Users ON UserClasses.user_id = Users.id
                        LEFT JOIN
                    UserRoles ON Users.id = UserRoles.user_id
                        LEFT JOIN
                    Roles ON UserRoles.role_id = Roles.id;
                """;
        var statement = db.prepareStatement(sqlQuery);
        var resultSet = statement.executeQuery();
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
                user.roles().add(new Role(resultSet.getInt("role_id"), resultSet.getString("role_name")));
                usersMap.put(userId, user);

                clazz.users().add(user);
            }

            classesMap.put(classId, clazz);
        }
        return new HashSet<>(classesMap.values());
    }

    public boolean update(int id, String newClassName) throws SQLException, ClassDoesNotExistException {

        if (!classExists(id)) throw new ClassDoesNotExistException();

        var sqlQuery = "update Classes set name = ? where id = ?";
        var statement = db.prepareStatement(sqlQuery);
        statement.setString(1, newClassName);
        statement.setInt(2, id);

        return statement.executeUpdate() > 0;
    }

    public int create(String name) throws SQLException, ClassAlreadyExistsException {

        if (classExists(name)) throw new ClassAlreadyExistsException();

        var sqlQuery = "insert into Classes (id,name) value (default, ?)";
        var statement = db.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, name);
        statement.executeUpdate();

        var generatedKeys = statement.getGeneratedKeys();

        if (!generatedKeys.next())
            throw new SQLException("Creating class failed, no ID obtained.");

        return generatedKeys.getInt(1);
    }

    public boolean delete(int id) throws SQLException, ClassDoesNotExistException {

        if (!classExists(id)) throw new ClassDoesNotExistException();

        var sqlQuery = "delete from Classes where id = ?";
        var statement = db.prepareStatement(sqlQuery);
        statement.setInt(1, id);

        return statement.executeUpdate() > 0;
    }


    boolean classExists(String name) throws SQLException {
        String classExistsQuery = "SELECT EXISTS(SELECT 1 FROM Classes WHERE name = ?)";

        var preparedStatement = db.prepareStatement(classExistsQuery);
        preparedStatement.setString(1, name);
        var resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getBoolean(1);
        }
        return false;
    }

    boolean classExists(int id) throws SQLException {
        String classExistsQuery = "SELECT EXISTS(SELECT 1 FROM Classes WHERE id = ?)";

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

