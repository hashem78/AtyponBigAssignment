package com.hashem.p1;

import com.hashem.p1.models.Role;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RoleDao implements AutoCloseable {
    final Connection db;

    public RoleDao() {
        this.db = ConnectionFactory.getDbConnection();
    }

    public Set<Role> getAllRoles() throws SQLException {

        var sqlQuery = "select * from roles";
        var statement = db.createStatement();
        var resultSet = statement.executeQuery(sqlQuery);
        var roles = new HashSet<Role>();
        while (resultSet.next()) {
            roles.add(
                    Role.builder()
                            .id(resultSet.getInt(1))
                            .name(resultSet.getString(2))
                            .build()
            );
        }
        return roles;
    }

    public int createRole(String roleName) throws SQLException, RoleAlreadyExistsException {

        if (roleExists(roleName)) throw new RoleAlreadyExistsException();

        var sqlQuery = "insert into roles (id,name) value (default, ?)";
        var statement = db.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, roleName);
        statement.executeUpdate();

        var generatedKeys = statement.getGeneratedKeys();

        if (!generatedKeys.next())
            throw new SQLException("Creating user failed, no ID obtained.");

        return generatedKeys.getInt(1);
    }

    boolean roleExists(String name) throws SQLException {
        String roleExistsQuery = "SELECT EXISTS(SELECT 1 FROM roles WHERE name = ?)";

        var preparedStatement = db.prepareStatement(roleExistsQuery);
        preparedStatement.setString(1, name);
        var resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getBoolean(1);
        }
        return false;
    }

    boolean roleExists(int id) throws SQLException {
        String roleExistsQuery = "SELECT EXISTS(SELECT 1 FROM roles WHERE id = ?)";

        var preparedStatement = db.prepareStatement(roleExistsQuery);
        preparedStatement.setInt(1, id);
        var resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getBoolean(1);
        }
        return false;
    }

    public boolean updateRole(int id, String newRoleName) throws SQLException, RoleDoesNotExistException {

        if (!roleExists(id)) throw new RoleDoesNotExistException();

        var sqlQuery = "update roles set name = ? where id = ?";
        var statement = db.prepareStatement(sqlQuery);
        statement.setString(1, newRoleName);
        statement.setInt(2, id);

        return statement.executeUpdate() > 0;
    }

    public boolean deleteRole(String roleName) throws SQLException, RoleDoesNotExistException {

        if (!roleExists(roleName)) throw new RoleDoesNotExistException();

        var sqlQuery = "delete from roles where name = ?";
        var statement = db.prepareStatement(sqlQuery);
        statement.setString(1, roleName);

        return statement.executeUpdate() > 0;
    }

    public boolean deleteRole(int id) throws SQLException, RoleDoesNotExistException {

        if (!roleExists(id)) throw new RoleDoesNotExistException();

        var sqlQuery = "delete from roles where id = ?";
        var statement = db.prepareStatement(sqlQuery);
        statement.setInt(1, id);

        return statement.executeUpdate() > 0;
    }

    public Role getRole(int id) throws SQLException, RoleDoesNotExistException {

        if (!roleExists(id)) throw new RoleDoesNotExistException();

        var sqlQuery = "select name from roles where id = ?";
        var statement = db.prepareStatement(sqlQuery);
        statement.setInt(1, id);

        var resultSet = statement.executeQuery();

        return Role.builder()
                .id(id)
                .name(resultSet.getString("name"))
                .build();
    }

    public List<Integer> getAllUsersForRole(int id) throws SQLException, RoleDoesNotExistException {

        if (!roleExists(id)) throw new RoleDoesNotExistException();

        var sqlQuery = """
                SELECT users.id
                FROM user_roles
                         JOIN users ON user_roles.user_id = users.id
                WHERE user_roles.role_id = ?;
                """;
        var statement = db.prepareStatement(sqlQuery);
        var resultSet = statement.executeQuery();
        var userIds = new ArrayList<Integer>();
        while (resultSet.next()) {
            userIds.add(resultSet.getInt("id"));
        }
        return userIds;
    }

    boolean relationshipExists(int userId, int roleId) throws SQLException {

        String query = "SELECT EXISTS(SELECT 1 FROM user_roles WHERE user_id = ? and role_id = ?)";

        var preparedStatement = db.prepareStatement(query);
        preparedStatement.setInt(1, userId);
        preparedStatement.setInt(2, roleId);
        var resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getBoolean(1);
        }
        return false;
    }

    public boolean addRoleToUser(int userId, int roleId) throws RoleDoesNotExistException, SQLException, RelationshipAlreadyExists {

        if (!roleExists(roleId)) throw new RoleDoesNotExistException();
        if (relationshipExists(userId, roleId)) throw new RelationshipAlreadyExists();

        var query = "insert into user_roles (user_id, role_id) value (?,?)";
        var statement = db.prepareStatement(query);
        statement.setInt(1, userId);
        statement.setInt(2, roleId);

        return statement.executeUpdate() > 0;
    }

    public boolean removeRoleFromUser(int userId, int roleId) throws RoleDoesNotExistException, SQLException, RelationshipDoesNotExist {

        if (!roleExists(roleId)) throw new RoleDoesNotExistException();
        if (!relationshipExists(userId, roleId)) throw new RelationshipDoesNotExist();

        var query = "delete from user_roles where user_id = ? and role_id = ?";
        var statement = db.prepareStatement(query);
        statement.setInt(1, userId);
        statement.setInt(2, roleId);

        return statement.executeUpdate() > 0;
    }

    @Override
    public void close() throws Exception {
        db.close();
    }
}
