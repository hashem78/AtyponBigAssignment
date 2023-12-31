package com.hashem.p1;

import com.hashem.p1.models.Role;
import com.hashem.p1.models.User;

import java.sql.*;
import java.util.*;

public class UserDao implements AutoCloseable {

    final Connection db;

    public UserDao() {
        this.db = ConnectionFactory.getDbConnection();
    }

    public User getUser(int id) throws SQLException, UserDoesNotExistException {
        if (!userExists(id))
            throw new UserDoesNotExistException();

        var sqlQuery = """
                SELECT u.id as user_id, u.email, u.password_hash, r.id as role_id, r.name as role_name
                from users u
                         join user_roles ur ON u.id = ur.user_id
                         JOIN roles r ON ur.role_id = r.id
                where user_id = ?""";
        var statement = db.prepareStatement(sqlQuery);
        statement.setInt(1, id);
        return getUser(statement.executeQuery());
    }

    public Set<User> getUsers() throws SQLException {

        var sqlQuery = """
                SELECT u.id as user_id, u.email, u.password_hash, r.id as role_id, r.name as role_name
                from users u
                         LEFT join user_roles ur ON u.id = ur.user_id
                         LEFT JOIN roles r ON ur.role_id = r.id""";

        var resultSet = db
                .prepareStatement(sqlQuery)
                .executeQuery();

        final var userMap = new HashMap<Integer, User.UserBuilder>();
        final var userMapRoles = new HashMap<Integer, Set<Role>>();
        while (resultSet.next()) {
            int userId = resultSet.getInt("user_id");

            if (!userMap.containsKey(userId)) {
                var user = User.builder()
                        .id(userId)
                        .email(resultSet.getString("email"))
                        .passwordHash(resultSet.getString("password_hash" +
                                ""));

                userMap.put(userId, user);
            }
            if (!userMapRoles.containsKey(userId)) {
                userMapRoles.put(userId, new HashSet<>());
            }
            var roleId = resultSet.getInt("role_id");
            var roleName = resultSet.getString("role_name");

            if (roleId != 0 && roleName != null)
                userMapRoles.get(userId).add(new Role(roleId, roleName));
        }
        var users = new HashSet<User>();
        for (Integer userId : userMap.keySet()) {
            var oldUser = userMap.get(userId);
            var newUser = oldUser.roles(userMapRoles.get(userId)).build();
            users.add(newUser);
        }
        return users;
    }

    public int createUser(String email, String passwordHash, List<Role> roles) throws SQLException, UserAlreadyExistsException {

        if (userExists(email)) throw new UserAlreadyExistsException();

        db.setAutoCommit(false);
        var success = true;
        var userSql = "insert into users (id,email,password_hash) value (default, ?, ?)";
        var userStatement = db.prepareStatement(userSql, Statement.RETURN_GENERATED_KEYS);

        userStatement.setString(1, email);
        userStatement.setString(2, passwordHash);
        var affectedRows = userStatement.executeUpdate();
        success = affectedRows > 0;

        var generatedKeys = userStatement.getGeneratedKeys();
        if (!generatedKeys.next())
            throw new SQLException("Creating user failed, no ID obtained.");

        var user_id = generatedKeys.getInt(1);

        var roleSql = "insert into user_roles value (?,?)";
        var roleStatement = db.prepareStatement(roleSql);
        for (var role : roles) {
            roleStatement.setInt(1, user_id);
            roleStatement.setInt(2, role.id());
            success = success && roleStatement.executeUpdate() > 0;
        }
        db.commit();
        db.setAutoCommit(true);
        return user_id;
    }

    public boolean update(User user) throws SQLException, UserDoesNotExistException {

        if (!userExists(user.id())) throw new UserDoesNotExistException();

        var query = "UPDATE users set email = ?, password_hash = ? where id = ?";
        var statement = db.prepareStatement(query);
        statement.setString(1, user.email());
        statement.setString(2, user.passwordHash());
        statement.setInt(3, user.id());

        return statement.executeUpdate() > 0;
    }

    public boolean deleteUser(int id) throws SQLException, UserDoesNotExistException {

        if (!userExists(id)) throw new UserDoesNotExistException();

        var query = "delete from users where id = ?";
        var statement = db.prepareStatement(query);
        statement.setInt(1, id);
        return statement.executeUpdate() > 0;
    }

    boolean userExists(String email, String passwordHash) throws SQLException {
        String userExistsQuery = "SELECT EXISTS(SELECT 1 from users WHERE email = ? and password_hash = ?)";

        var preparedStatement = db.prepareStatement(userExistsQuery);
        preparedStatement.setString(1, email);
        preparedStatement.setString(2, passwordHash);
        var resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getBoolean(1);
        }
        return false;
    }

    boolean userExists(int id) throws SQLException {
        String userExistsQuery = "SELECT EXISTS(SELECT 1 from users WHERE id = ?)";

        var preparedStatement = db.prepareStatement(userExistsQuery);
        preparedStatement.setInt(1, id);
        var resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getBoolean(1);
        }
        return false;
    }

    boolean userExists(String email) throws SQLException {
        String userExistsQuery = "SELECT EXISTS(SELECT 1 from users WHERE email = ?)";

        var preparedStatement = db.prepareStatement(userExistsQuery);
        preparedStatement.setString(1, email);
        var resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getBoolean(1);
        }
        return false;
    }

    public User getByEmailAndPassword(String email, String passwordHash) throws SQLException, UserDoesNotExistException {
        if (!userExists(email, passwordHash))
            throw new UserDoesNotExistException();

        var sqlQuery = """
                SELECT u.id as user_id, u.email, u.password_hash, r.id as role_id, r.name as role_name
                from users u
                         join user_roles ur ON u.id = ur.user_id
                         JOIN roles r ON ur.role_id = r.id
                where (email = ? and password_hash = ?)""";


        var statement = db.prepareStatement(sqlQuery);
        statement.setString(1, email);
        statement.setString(2, passwordHash);

        return getUser(statement.executeQuery());
    }

    private User getUser(ResultSet resultSet) throws SQLException {

        var roleMap = new HashMap<Integer, Role>();
        var userBuilder = User.builder();
        while (resultSet.next()) {
            var roleId = resultSet.getInt("role_id");
            roleMap.putIfAbsent(
                    roleId,
                    Role.builder()
                            .id(resultSet.getInt("role_id"))
                            .name(resultSet.getString("role_name"))
                            .build()
            );

            userBuilder
                    .id(resultSet.getInt("user_id"))
                    .email(resultSet.getString("email"))
                    .passwordHash(resultSet.getString("password_hash"));
        }

        return userBuilder
                .roles(new HashSet<>(roleMap.values()))
                .build();
    }

    public boolean addUserToClass(int classId, int userId) throws ClassDoesNotExistException, RelationshipAlreadyExists, SQLException {
        if (!userExists(userId)) throw new ClassDoesNotExistException();
        if (relationshipExists(classId, userId)) throw new RelationshipAlreadyExists();

        var query = "insert into user_classes (class_id, user_id) value (?,?)";
        var statement = db.prepareStatement(query);
        statement.setInt(1, classId);
        statement.setInt(2, userId);

        return statement.executeUpdate() > 0;
    }

    public boolean removeUserFromClass(int classId, int userId) throws ClassDoesNotExistException, SQLException, RelationshipDoesNotExist {

        if (!userExists(userId)) throw new ClassDoesNotExistException();
        if (!relationshipExists(classId, userId)) throw new RelationshipDoesNotExist();

        var query = "delete from user_classes where class_id = ? and user_id = ?";
        var statement = db.prepareStatement(query);
        statement.setInt(1, classId);
        statement.setInt(2, userId);

        return statement.executeUpdate() > 0;
    }

    boolean relationshipExists(int classId, int userId) throws SQLException {

        String query = "SELECT EXISTS(SELECT 1 FROM user_classes WHERE class_id = ? and user_id = ?)";

        var preparedStatement = db.prepareStatement(query);
        preparedStatement.setInt(1, classId);
        preparedStatement.setInt(2, userId);
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

