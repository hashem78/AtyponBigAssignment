package com.hashem.p1.queries;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.hashem.p1.helpers.RootObject;
import com.hashem.p1.helpers.RootObjectVisitor;
import com.hashem.p1.responses.Response;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = GetUsersQuery.class, name = "get_users"),
        @JsonSubTypes.Type(value = GetRolesQuery.class, name = "get_roles"),
        @JsonSubTypes.Type(value = GetClassMembersQuery.class, name = "get_class_members"),
})
public interface Query extends RootObject {

    @Override
    default Response accept(RootObjectVisitor visitor) {
        return visitor.visit(this);
    }

    Response accept(QueryVisitor visitor);
}
