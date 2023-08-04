package com.hashem.p1.commands.user;

import com.hashem.p1.commands.BasicCommandVisitor;
import com.hashem.p1.commands.Command;
import com.hashem.p1.models.User;
import com.hashem.p1.responses.Response;

public record UpdateUserCommand(User user) implements Command {
    @Override
    public Response accept(BasicCommandVisitor visitor) {
        return visitor.visit(this);
    }
}
