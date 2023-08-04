package com.hashem.p1;

import com.hashem.p1.models.Role;
import com.hashem.p1.models.User;

import java.sql.*;
import java.util.*;

public class UserDao implements AutoCloseable {

    final Connection db;

    public UserDao() {
        this.db = ConnectionFactory.getConnection();
    }

    public User getUser(int id) throws SQLException, UserDoesNotExistException {
        if (!userExists(id))
            throw new UserDoesNotExistException();

        var sqlQuery = """
                SELECT u.id as user_id, u.email, u.password, r.id as role_id, r.name as role_name
                FROM Users u
                         JOIN UserRoles ur ON u.id = ur.user_id
                         JOIN Roles r ON ur.role_id = r.id
                where user_id = ?""";
        var statement = db.prepareStatement(sqlQuery);
        statement.setInt(1, id);
        return getUser(statement.executeQuery());
    }

    public Set<User> getUsers() throws SQLException {

        var sqlQuery = """
                SELECT u.id as user_id, u.email, u.password, r.id as role_id, r.name as role_name
                FROM Users u
                         JOIN UserRoles ur ON u.id = ur.user_id
                         JOIN Roles r ON ur.role_id = r.id""";

        var resultSet = db
                .prepareStatement(sqlQuery)
                .executeQuery();

        final var userMap = new HashMap<Integer, User.UserBuilder>();
        final var userMapRoles = new HashMap<Integer, List<Role>>();
        while (resultSet.next()) {
            int userId = resultSet.getInt("user_id");

            if (!userMap.containsKey(userId)) {
                var user = User.builder()
                        .id(userId)
                        .email(resultSet.getString("email"))
                        .password(resultSet.getString("password"));

                userMap.put(userId, user);
            }
            if (!userMapRoles.containsKey(userId)) {
                userMapRoles.put(userId, new ArrayList<>());
            }
            userMapRoles.get(userId).add(
                    Role.builder()
                            .id(resultSet.getInt("role_id"))
                            .name(resultSet.getString("role_name"))
                            .build());
        }
        var users = new HashSet<User>();
        for (Integer userId : userMap.keySet()) {
            var oldUser = userMap.get(userId);
            var newUser = oldUser.roles(userMapRoles.get(userId)).build();
            users.add(newUser);
        }
        return users;
    }

    public int createUser(String email, String password, List<Role> roles) throws SQLException, UserAlreadyExistsException {

        if (userExists(email)) throw new UserAlreadyExistsException();

        db.setAutoCommit(false);
        var success = true;
        var userSql = "insert into Users (id,email,password) value (default, ?, ?)";
        var userStatement = db.prepareStatement(userSql, Statement.RETURN_GENERATED_KEYS);

        userStatement.setString(1, email);
        userStatement.setString(2, password);
        var affectedRows = userStatement.executeUpdate();
        success = affectedRows > 0;

        var generatedKeys = userStatement.getGeneratedKeys();
        if (!generatedKeys.next())
            throw new SQLException("Creating user failed, no ID obtained.");

        var user_id = generatedKeys.getInt(1);

        var roleSql = "insert into UserRoles value (?,?)";
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

    boolean update(int user_id, String field, Object value) throws SQLException {
        var query = "UPDATE Users set " + field + " = ? where id = ?";
        var statement = db.prepareStatement(query);
        statement.setObject(1, value);
        statement.setInt(2, user_id);
        return statement.executeUpdate() > 0;
    }

    boolean deleteUser(int id) throws SQLException {
        var query = "delete from Users where id = ?";
        var statement = db.prepareStatement(query);
        statement.setInt(1, id);
        return statement.executeUpdate() > 0;
    }

    boolean userExists(String email, String password) throws SQLException {
        String userExistsQuery = "SELECT EXISTS(SELECT 1 FROM Users WHERE email = ? and password = ?)";

        var preparedStatement = db.prepareStatement(userExistsQuery);
        preparedStatement.setString(1, email);
        preparedStatement.setString(2, password);
        var resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getBoolean(1);
        }
        return false;
    }

    boolean userExists(int id) throws SQLException {
        String userExistsQuery = "SELECT EXISTS(SELECT 1 FROM Users WHERE id = ?)";

        var preparedStatement = db.prepareStatement(userExistsQuery);
        preparedStatement.setInt(1, id);
        var resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getBoolean(1);
        }
        return false;
    }

    boolean userExists(String email) throws SQLException {
        String userExistsQuery = "SELECT EXISTS(SELECT 1 FROM Users WHERE email = ?)";

        var preparedStatement = db.prepareStatement(userExistsQuery);
        preparedStatement.setString(1, email);
        var resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getBoolean(1);
        }
        return false;
    }

    public User getByEmailAndPassword(String email, String password) throws SQLException, UserDoesNotExistException {
        if (!userExists(email, password))
            throw new UserDoesNotExistException();

        var sqlQuery = """
                SELECT u.id as user_id, u.email, u.password, r.id as role_id, r.name as role_name
                FROM Users u
                         JOIN UserRoles ur ON u.id = ur.user_id
                         JOIN Roles r ON ur.role_id = r.id
                where (email = ? and password = ?)""";


        var statement = db.prepareStatement(sqlQuery);
        statement.setString(1, email);
        statement.setString(2, password);

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
                    .password(resultSet.getString("password"));
        }

        return userBuilder
                .roles(roleMap.values().stream().toList())
                .build();
    }

    @Override
    public void close() throws Exception {
        db.close();
    }
}

