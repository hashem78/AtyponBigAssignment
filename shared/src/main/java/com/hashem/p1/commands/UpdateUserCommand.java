package com.hashem.p1.commands;

import com.hashem.p1.models.User;
import com.hashem.p1.responses.Response;

public record UpdateUserCommand(User user) implements Command {
    @Override
    public Response accept(BasicCommandVisitor visitor) {
        return visitor.visit(this);
    }
}
