package com.hashem.p1.commands.grades;

import com.hashem.p1.commands.Command;
import com.hashem.p1.commands.CommandVisitor;
import com.hashem.p1.responses.Response;

public record DeleteGradeCommand(int gradeId, int classId, int userId) implements Command {
    @Override
    public Response accept(CommandVisitor visitor) {
        return visitor.visit(this);
    }
}
