package com.hashem.p1.commands;

import com.hashem.p1.helpers.BasicObjectVisitor;

public class RemoveUserCommand extends BasicCommand{
    @Override
    void accept(BasicCommandVisitor visitor) {
        visitor.visit(this);
    }
}
