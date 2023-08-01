package com.hashem.p1.queries;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.hashem.p1.Query;
import com.hashem.p1.helpers.BasicObject;
import com.hashem.p1.helpers.BasicObjectVisitor;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = GetUsersQuery.class, name = "get_users"),
})
public abstract class BasicQuery implements Query, BasicObject {
    public BasicQuery() {
    }

    @Override
    public void accept(BasicObjectVisitor visitor) {
        visitor.visit(this);
    }

    public abstract void accept(BasicQueryVisitor visitor);
}
