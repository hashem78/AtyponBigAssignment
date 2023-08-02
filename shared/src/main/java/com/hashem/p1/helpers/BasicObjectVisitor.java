package com.hashem.p1.helpers;

import com.hashem.p1.Response;
import com.hashem.p1.commands.BasicCommand;
import com.hashem.p1.queries.BasicQuery;

public interface BasicObjectVisitor {
    Response visit(BasicQuery query);
    Response visit(BasicCommand command);
}
