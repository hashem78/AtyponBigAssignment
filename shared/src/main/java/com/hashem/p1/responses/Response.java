package com.hashem.p1.responses;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.hashem.p1.responses.classes.GetClassesQueryResponse;
import com.hashem.p1.responses.roles.CreateRoleCommandResponse;
import com.hashem.p1.responses.roles.GetRolesQueryResponse;
import com.hashem.p1.responses.users.GetUsersQueryResponse;
import com.hashem.p1.responses.roles.UpdateRoleCommandResponse;
import com.hashem.p1.responses.users.CreateUserCommandResponse;
import com.hashem.p1.responses.roles.DeleteRoleCommandResponse;
import com.hashem.p1.responses.users.DeleteUserCommandResponse;
import com.hashem.p1.responses.users.UpdateUserCommandResponse;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = GetUsersQueryResponse.class, name = "get_users_response"),
        @JsonSubTypes.Type(value = GetClassesQueryResponse.class, name = "get_classes_response"),
        @JsonSubTypes.Type(value = CreateUserCommandResponse.class, name = "create_user_response"),
        @JsonSubTypes.Type(value = UpdateUserCommandResponse.class, name = "update_user_response"),
        @JsonSubTypes.Type(value = DeleteUserCommandResponse.class, name = "delete_user_response"),
        @JsonSubTypes.Type(value = CreateRoleCommandResponse.class, name = "create_role_response"),
        @JsonSubTypes.Type(value = UpdateRoleCommandResponse.class, name = "update_role_response"),
        @JsonSubTypes.Type(value = DeleteRoleCommandResponse.class, name = "delete_role_response"),
        @JsonSubTypes.Type(value = GetRolesQueryResponse.class, name = "get_roles_response"),
        @JsonSubTypes.Type(value = ErrorResponse.class, name = "error_response")
})
public interface Response {
}
