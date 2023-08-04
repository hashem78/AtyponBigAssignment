package com.hashem.p1.commands.classes;

import com.hashem.p1.commands.BasicCommandVisitor;
import com.hashem.p1.commands.Command;
import com.hashem.p1.responses.Response;

public class RemoveUserFromClassCommand implements Command {
    @Override
    public Response accept(BasicCommandVisitor visitor) {

        return visitor.visit(this);
    }
}
