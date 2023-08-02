package com.hashem.p1.commands;
import com.hashem.p1.Response;

public interface BasicCommandVisitor {
    Response visit(AddUserCommand command);

    Response visit(AddUserToClassCommand command);

    Response visit(CreateClassCommand command);

    Response visit(RemoveUserCommand command);

    Response visit(RemoveUserFromClassCommand command);
}
