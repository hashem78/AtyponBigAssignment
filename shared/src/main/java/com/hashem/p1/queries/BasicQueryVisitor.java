package com.hashem.p1.queries;

import com.hashem.p1.Response;
import com.hashem.p1.queries.GetClassMembersQuery;
import com.hashem.p1.queries.GetUsersQuery;

public interface BasicQueryVisitor {

    Response visit(GetUsersQuery query);

    Response visit(GetClassMembersQuery query);
}
