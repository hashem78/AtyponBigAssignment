package com.hashem.p1.visitors;

import com.hashem.p1.commands.Command;
import com.hashem.p1.helpers.RootObjectVisitor;
import com.hashem.p1.queries.Query;
import com.hashem.p1.responses.Response;

public class DefaultRootObjectVisitor implements RootObjectVisitor {
    @Override
    public Response visit(Query query) {
        var visitor = new DefaultQueryVisitor();
        return query.accept(visitor);
    }

    @Override
    public Response visit(Command command) {
        var visitor = new DefaultCommandVisitor();
        return command.accept(visitor);
    }
}
