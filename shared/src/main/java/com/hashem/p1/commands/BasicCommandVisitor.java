package com.hashem.p1.commands;

public interface BasicCommandVisitor {
    void visit(AddUserCommand command);

    void visit(AddUserToClassCommand command);

    void visit(CreateClassCommand command);

    void visit(RemoveUserCommand command);

    void visit(RemoveUserFromClassCommand command);
}
