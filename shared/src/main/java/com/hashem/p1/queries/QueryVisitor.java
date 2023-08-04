package com.hashem.p1.queries;


import com.hashem.p1.responses.Response;

public interface QueryVisitor {

    Response visit(GetUsersQuery query);
    Response visit(GetRolesQuery query);
    Response visit(GetClassMembersQuery query);
    Response visit(GetClassesQuery query);
}
