package com.hashem.p1.queries;

import com.hashem.p1.responses.Response;

public record GetClassMembersQuery() implements Query {

    @Override
    public Response accept(BasicQueryVisitor visitor) {
        return visitor.visit(this);
    }
}
