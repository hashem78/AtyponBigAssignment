package com.hashem.p1.commands;

import com.hashem.p1.Response;

public record CreateRoleCommand(String roleName) implements BasicCommand {
    @Override
    public Response accept(BasicCommandVisitor visitor) {
        return visitor.visit(this);
    }
}
