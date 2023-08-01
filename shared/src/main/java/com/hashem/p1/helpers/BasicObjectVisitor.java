package com.hashem.p1.helpers;

import com.hashem.p1.commands.BasicCommand;
import com.hashem.p1.queries.BasicQuery;

public interface BasicObjectVisitor {
    void visit(BasicQuery query);
    void visit(BasicCommand command);
}
