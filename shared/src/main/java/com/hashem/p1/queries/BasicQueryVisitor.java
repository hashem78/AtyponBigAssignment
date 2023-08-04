package com.hashem.p1.queries;

import com.hashem.p1.Response;

public interface BasicQueryVisitor {

    Response visit(GetUsersQuery query);
    Response visit(GetRolesQuery query);
    Response visit(GetClassMembersQuery query);

}
