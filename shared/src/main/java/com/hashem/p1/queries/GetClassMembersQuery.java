package com.hashem.p1.queries;

import com.hashem.p1.Response;

public class GetClassMembersQuery extends BasicQuery {

    @Override
    public Response accept(BasicQueryVisitor visitor) {
        return visitor.visit(this);
    }
}
