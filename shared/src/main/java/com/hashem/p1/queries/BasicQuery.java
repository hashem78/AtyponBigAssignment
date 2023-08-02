package com.hashem.p1.queries;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.hashem.p1.Query;
import com.hashem.p1.Response;
import com.hashem.p1.helpers.BasicObject;
import com.hashem.p1.helpers.BasicObjectVisitor;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = GetClassMembersQuery.class, name = "get_class_members"),
        @JsonSubTypes.Type(value = GetUsersQuery.class, name = "get_users"),
})
public abstract class BasicQuery implements Query, BasicObject {
    public BasicQuery() {
    }

    @Override
    public Response accept(BasicObjectVisitor visitor) {
        return visitor.visit(this);
    }

    public abstract Response accept(BasicQueryVisitor visitor);
}
