package com.hashem.p1.queries;

public class GetClassMembersQuery extends BasicQuery {

    @Override
    public void accept(BasicQueryVisitor visitor) {
        visitor.visit(this);
    }
}
