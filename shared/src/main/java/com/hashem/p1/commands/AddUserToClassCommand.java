package com.hashem.p1.commands;
import com.hashem.p1.responses.Response;

public class AddUserToClassCommand implements Command {
    @Override
    public Response accept(BasicCommandVisitor visitor) {
        return visitor.visit(this);
    }
}
