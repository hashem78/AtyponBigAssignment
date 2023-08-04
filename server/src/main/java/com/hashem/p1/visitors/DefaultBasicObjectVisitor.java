package com.hashem.p1.visitors;

import com.hashem.p1.commands.Command;
import com.hashem.p1.helpers.BasicObjectVisitor;
import com.hashem.p1.queries.Query;
import com.hashem.p1.responses.Response;

public class DefaultBasicObjectVisitor implements BasicObjectVisitor {
    @Override
    public Response visit(Query query) {
        var visitor = new DefaultBasicQueryVisitor();
        return query.accept(visitor);
    }

    @Override
    public Response visit(Command command) {
        var visitor = new DefaultBasicCommandVisitor();
        return command.accept(visitor);
    }
}
