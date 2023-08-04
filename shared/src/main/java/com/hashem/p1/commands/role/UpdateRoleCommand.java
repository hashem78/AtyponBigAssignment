package com.hashem.p1.commands.role;

import com.hashem.p1.commands.BasicCommandVisitor;
import com.hashem.p1.commands.Command;
import com.hashem.p1.responses.Response;

public record UpdateRoleCommand(int id, String newRoleName) implements Command
{
    @Override
    public Response accept(BasicCommandVisitor visitor) {
        return visitor.visit(this);
    }
}
