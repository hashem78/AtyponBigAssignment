package com.hashem.p1.visitors;

import com.hashem.p1.ConnectionFactory;
import com.hashem.p1.Response;
import com.hashem.p1.commands.BasicCommand;
import com.hashem.p1.helpers.BasicObjectVisitor;
import com.hashem.p1.queries.BasicQuery;

import java.sql.SQLException;

public class DefaultBasicObjectVisitor implements BasicObjectVisitor {
    @Override
    public Response visit(BasicQuery query) {

        try (var connection = ConnectionFactory.getConnection()) {
            var visitor = new DefaultBasicQueryVisitor(connection);
            return query.accept(visitor);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Response visit(BasicCommand command) {
        var visitor = new DefaultBasicCommandVisitor();
        return command.accept(visitor);
    }
}
