package com.hashem.p1.queries;

public class GetUsersQuery extends BasicQuery {

    @Override
    public void accept(BasicQueryVisitor visitor) {
        visitor.visit(this);
    }
}
