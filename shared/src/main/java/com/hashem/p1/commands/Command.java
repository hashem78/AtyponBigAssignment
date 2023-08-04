package com.hashem.p1.commands;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.hashem.p1.commands.classes.*;
import com.hashem.p1.commands.role.CreateRoleCommand;
import com.hashem.p1.commands.role.DeleteRoleCommand;
import com.hashem.p1.commands.role.UpdateRoleCommand;
import com.hashem.p1.commands.user.*;
import com.hashem.p1.helpers.RootObject;
import com.hashem.p1.helpers.RootObjectVisitor;
import com.hashem.p1.responses.Response;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CreateUserCommand.class, name = "create_user"),
        @JsonSubTypes.Type(value = CreateRoleCommand.class, name = "create_role"),
        @JsonSubTypes.Type(value = UpdateUserCommand.class, name = "update_user"),
        @JsonSubTypes.Type(value = UpdateUserRolesAddCommand.class, name = "update_user_roles_add"),
        @JsonSubTypes.Type(value = UpdateUserRolesRemoveCommand.class, name = "update_user_roles_remove"),
        @JsonSubTypes.Type(value = DeleteUserCommand.class, name = "delete_user"),
        @JsonSubTypes.Type(value = UpdateRoleCommand.class, name = "update_role"),
        @JsonSubTypes.Type(value = DeleteRoleCommand.class, name = "delete_role"),
        @JsonSubTypes.Type(value = CreateClassCommand.class, name = "create_class"),
        @JsonSubTypes.Type(value = UpdateClassCommand.class, name = "update_class"),
        @JsonSubTypes.Type(value = DeleteClassCommand.class, name = "delete_class"),
        @JsonSubTypes.Type(value = UpdateClassUsersAddCommand.class, name = "update_class_users_add"),
        @JsonSubTypes.Type(value = UpdateClassUsersRemoveCommand.class, name = "update_class_users_remove")
})
public interface Command extends RootObject {

    @Override
    default Response accept(RootObjectVisitor visitor) {
        return visitor.visit(this);
    }

    Response accept(CommandVisitor visitor);
}
