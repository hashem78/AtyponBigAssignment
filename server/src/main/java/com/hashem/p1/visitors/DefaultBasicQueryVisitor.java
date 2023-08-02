package com.hashem.p1.visitors;

import com.hashem.p1.Response;
import com.hashem.p1.UserDao;
import com.hashem.p1.models.Role;
import com.hashem.p1.models.User;
import com.hashem.p1.queries.BasicQueryVisitor;
import com.hashem.p1.queries.GetClassMembersQuery;
import com.hashem.p1.queries.GetUsersQuery;
import com.hashem.p1.responses.GetUsersQueryResponse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DefaultBasicQueryVisitor implements BasicQueryVisitor {

    final Connection connection;

    DefaultBasicQueryVisitor(Connection connection) {

        this.connection = connection;
    }

    @Override
    public Response visit(GetUsersQuery query) {

        try (var dao = new UserDao()) {
            return new GetUsersQueryResponse(dao.getUsers());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Response visit(GetClassMembersQuery query) {
        return null;
    }
}
