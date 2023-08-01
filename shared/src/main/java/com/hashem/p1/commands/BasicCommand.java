package com.hashem.p1.commands;

import com.hashem.p1.Command;
import com.hashem.p1.helpers.BasicObject;
import com.hashem.p1.helpers.BasicObjectVisitor;

public abstract class BasicCommand implements Command, BasicObject {
    BasicCommand() {

    }

    @Override
    public void accept(BasicObjectVisitor visitor) {
        visitor.visit(this);
    }

    public abstract void accept(BasicCommandVisitor visitor);
}
