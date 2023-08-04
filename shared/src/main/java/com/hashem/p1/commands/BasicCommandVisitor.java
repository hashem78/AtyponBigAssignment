package com.hashem.p1.commands;

import com.hashem.p1.responses.Response;

public interface BasicCommandVisitor {
    Response visit(CreateUserCommand command);
    Response visit(CreateRoleCommand command);
    Response visit(UpdateUserCommand command);
    Response visit(UpdateUserRolesAddCommand command);
    Response visit(UpdateUserRolesRemoveCommand command);
    Response visit(AddUserToClassCommand command);
    Response visit(CreateClassCommand command);
    Response visit(RemoveUserCommand command);
    Response visit(RemoveUserFromClassCommand command);
}
