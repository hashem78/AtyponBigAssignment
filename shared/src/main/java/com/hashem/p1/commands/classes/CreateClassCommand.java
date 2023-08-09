package com.hashem.p1.commands.classes;

import com.hashem.p1.commands.Command;
import com.hashem.p1.commands.CommandVisitor;
import com.hashem.p1.models.Role;
import com.hashem.p1.responses.Response;

import java.util.List;

public record CreateClassCommand(int creatorId, String name) implements Command {
    @Override
    public Response accept(CommandVisitor visitor) {
        return visitor.visit(this);
    }
}
