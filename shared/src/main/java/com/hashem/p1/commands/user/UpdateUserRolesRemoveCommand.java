package com.hashem.p1.commands.user;

import com.hashem.p1.commands.CommandVisitor;
import com.hashem.p1.commands.Command;
import com.hashem.p1.responses.Response;

public record UpdateUserRolesRemoveCommand(int userId, int roleId) implements Command {
    @Override
    public Response accept(CommandVisitor visitor) {
        return visitor.visit(this);
    }
}
