package com.hashem.p1.queries;

import com.hashem.p1.responses.Response;

public record GetUsersQuery() implements Query {

    @Override
    public Response accept(QueryVisitor visitor) {
        return visitor.visit(this);
    }
}
