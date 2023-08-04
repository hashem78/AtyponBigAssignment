package com.hashem.p1.commands.classes;

import com.hashem.p1.commands.Command;
import com.hashem.p1.commands.CommandVisitor;
import com.hashem.p1.models.CClass;
import com.hashem.p1.models.User;
import com.hashem.p1.responses.Response;

public record UpdateClassCommand(int id, String name) implements Command {
    @Override
    public Response accept(CommandVisitor visitor) {
        return visitor.visit(this);
    }
}
