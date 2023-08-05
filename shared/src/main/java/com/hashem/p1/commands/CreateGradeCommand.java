package com.hashem.p1.commands;

import com.hashem.p1.responses.Response;

public record CreateGradeCommand(int classId, int userId, float grade) implements Command {
    @Override
    public Response accept(CommandVisitor visitor) {
        return visitor.visit(this);
    }
}
