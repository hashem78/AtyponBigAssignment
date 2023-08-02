package com.hashem.p1.visitors;

import com.hashem.p1.Response;
import com.hashem.p1.commands.*;

public class DefaultBasicCommandVisitor implements BasicCommandVisitor {
    @Override
    public Response visit(AddUserCommand command) {
        return null;
    }

    @Override
    public Response visit(AddUserToClassCommand command) {
        return null;
    }

    @Override
    public Response visit(CreateClassCommand command) {
        return null;
    }

    @Override
    public Response visit(RemoveUserCommand command) {
        return null;
    }

    @Override
    public Response visit(RemoveUserFromClassCommand command) {
        return null;
    }
}
