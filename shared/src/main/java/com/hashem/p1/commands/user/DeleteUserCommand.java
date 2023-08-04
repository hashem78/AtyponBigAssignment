package com.hashem.p1.commands.user;

import com.hashem.p1.commands.CommandVisitor;
import com.hashem.p1.commands.Command;
import com.hashem.p1.responses.Response;

public record DeleteUserCommand(int id) implements Command {
    @Override
    public Response accept(CommandVisitor visitor) {
        return visitor.visit(this);
    }
}
