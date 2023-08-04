package com.hashem.p1;

import com.hashem.p1.models.CClass;
import com.hashem.p1.models.Role;
import com.hashem.p1.models.User;

import java.sql.Connection;
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
                    Users.password,
                    Roles.name AS role_name,
                    Roles.id as role_id
                FROM
                    Classes
                        INNER JOIN
                    UserClasses ON Classes.id = UserClasses.class_id
                        INNER JOIN
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
            var userEmail = resultSet.getString("email");
            var userPassword = resultSet.getString("password");
            var user = usersMap.getOrDefault(userId, new User(userId, userEmail, userPassword, new HashSet<>()));
            user.roles().add(new Role(resultSet.getInt("role_id"), resultSet.getString("role_name")));
            usersMap.put(userId, user);

            clazz.users().add(user);
            classesMap.put(classId, clazz);
        }
        return new HashSet<>(classesMap.values());
    }

    @Override
    public void close() throws Exception {
        db.close();
    }
}

