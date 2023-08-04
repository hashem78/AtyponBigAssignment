package com.hashem.p1.responses;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.hashem.p1.Response;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = GetUsersQueryResponse.class, name = "get_users_response"),
        @JsonSubTypes.Type(value = CreateUserCommandResponse.class, name = "create_user_response"),
        @JsonSubTypes.Type(value = CreateRoleCommandResponse.class, name = "create_role_response"),
        @JsonSubTypes.Type(value = GetRolesQueryResponse.class, name = "get_roles_response"),
        @JsonSubTypes.Type(value = ErrorResponse.class, name = "error_response")
})
public interface BasicResponse extends Response {
}
