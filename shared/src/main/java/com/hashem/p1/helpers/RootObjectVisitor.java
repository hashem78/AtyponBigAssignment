package com.hashem.p1.helpers;

import com.hashem.p1.commands.Command;
import com.hashem.p1.queries.Query;
import com.hashem.p1.responses.Response;

public interface RootObjectVisitor {
    Response visit(Query query);
    Response visit(Command command);
}
