package com.hashem.p1.commands;

import com.hashem.p1.responses.Response;

public interface BasicCommandVisitor {
    Response visit(CreateUserCommand command);
    Response visit(CreateRoleCommand command);
    Response visit(UpdateUserCommand command);
    Response visit(UpdateUserRolesAddCommand command);
    Response visit(UpdateUserRolesRemoveCommand command);
    Response visit(DeleteUserCommand command);
    Response visit(DeleteRoleCommand command);
    Response visit(AddUserToClassCommand command);
    Response visit(CreateClassCommand command);
    Response visit(RemoveUserFromClassCommand command);
}
