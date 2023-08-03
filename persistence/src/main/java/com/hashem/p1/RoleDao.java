package com.hashem.p1;

import com.hashem.p1.models.Role;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RoleDao implements AutoCloseable {
    final Connection db;

    public RoleDao() {
        this.db = ConnectionFactory.getConnection();
    }

    public Set<Role> getAllRoles() throws SQLException {

        var sqlQuery = "select * from Roles";
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

    public boolean createRole(String roleName) throws SQLException {
        var sqlQuery = "insert into Roles (id,name) value (default, ?)";
        var statement = db.prepareStatement(sqlQuery);
        statement.setString(1, roleName);

        return statement.executeUpdate() > 0;
    }

    public boolean updateRole(int roleId, String newRoleName) throws SQLException {

        var sqlQuery = "update Roles set name = ? where id = ?";
        var statement = db.prepareStatement(sqlQuery);
        statement.setString(1, newRoleName);
        statement.setInt(2, roleId);

        return statement.executeUpdate() > 0;
    }

    public boolean deleteRole(String roleName) throws SQLException {
        var sqlQuery = "delete from Roles where name = ?";
        var statement = db.prepareStatement(sqlQuery);
        statement.setString(1, roleName);

        return statement.executeUpdate() > 0;
    }

    public boolean deleteRole(int id) throws SQLException {

        var sqlQuery = "delete from Roles where id = ?";
        var statement = db.prepareStatement(sqlQuery);
        statement.setInt(1, id);

        return statement.executeUpdate() > 0;
    }

    public Role getRole(int id) throws SQLException, RoleDoesNotExistException {
        var sqlQuery = "select name from Roles where id = ?";
        var statement = db.prepareStatement(sqlQuery);
        statement.setInt(1, id);

        var resultSet = statement.executeQuery();
        if (!resultSet.next())
            throw new RoleDoesNotExistException();

        return Role.builder()
                .id(id)
                .name(resultSet.getString("name"))
                .build();
    }

    public List<Integer> getAllUsersForRole(int roleId) throws SQLException {
        var sqlQuery = """
                SELECT Users.id
                FROM UserRoles
                         JOIN Users ON UserRoles.user_id = Users.id
                WHERE UserRoles.role_id = ?;
                """;
        var statement = db.prepareStatement(sqlQuery);
        var resultSet = statement.executeQuery();
        var userIds = new ArrayList<Integer>();
        while (resultSet.next()) {
            userIds.add(resultSet.getInt("id"));
        }
        return userIds;
    }

    @Override
    public void close() throws Exception {
        db.close();
    }
}
