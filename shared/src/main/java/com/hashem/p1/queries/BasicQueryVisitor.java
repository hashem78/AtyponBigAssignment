package com.hashem.p1.queries;

public interface BasicQueryVisitor {

    void visit(GetUsersQuery query);

    void visit(GetClassMembersQuery query);
}
