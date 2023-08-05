package com.hashem.p1.commands.grades;

import com.hashem.p1.commands.Command;
import com.hashem.p1.commands.CommandVisitor;
import com.hashem.p1.responses.Response;

public record UpdateGradeCommand(int gradeId, int classId, int userId, float grade) implements Command {
    @Override
    public Response accept(CommandVisitor visitor) {
        return visitor.visit(this);
    }
}
