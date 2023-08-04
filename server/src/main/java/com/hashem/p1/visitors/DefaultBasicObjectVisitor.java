package com.hashem.p1.visitors;

import com.hashem.p1.Response;
import com.hashem.p1.commands.BasicCommand;
import com.hashem.p1.helpers.BasicObjectVisitor;
import com.hashem.p1.queries.BasicQuery;

public class DefaultBasicObjectVisitor implements BasicObjectVisitor {
    @Override
    public Response visit(BasicQuery query) {
        var visitor = new DefaultBasicQueryVisitor();
        return query.accept(visitor);
    }

    @Override
    public Response visit(BasicCommand command) {
        var visitor = new DefaultBasicCommandVisitor();
        return command.accept(visitor);
    }
}
