package com.hashem.p1.commands;

public class AddUserCommand extends BasicCommand {

    @Override
    public void accept(BasicCommandVisitor visitor) {
        visitor.visit(this);
    }
}
