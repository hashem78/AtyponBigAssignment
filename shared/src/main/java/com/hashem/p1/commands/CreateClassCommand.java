package com.hashem.p1.commands;

import com.hashem.p1.Response;

public class CreateClassCommand implements BasicCommand {
    @Override
    public Response accept(BasicCommandVisitor visitor) {

        return visitor.visit(this);
    }
}
