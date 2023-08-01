package com.hashem.p1.helpers;

import com.hashem.p1.commands.BasicCommand;
import com.hashem.p1.commands.DefaultBasicCommandVisitor;
import com.hashem.p1.queries.BasicQuery;
import com.hashem.p1.queries.DefaultBasicQueryVisitor;

public class DefaultBasicObjectVisitor implements BasicObjectVisitor {
    @Override
    public void visit(BasicQuery query) {
        var visitor = new DefaultBasicQueryVisitor();
        query.accept(visitor);
    }

    @Override
    public void visit(BasicCommand command) {
        var visitor = new DefaultBasicCommandVisitor();
        command.accept(visitor);
    }
}
