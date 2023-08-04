package com.hashem.p1.commands;

import com.hashem.p1.Response;
import com.hashem.p1.models.Role;

import java.util.List;

public record CreateUserCommand(String email, String password, List<Role> roles) implements BasicCommand {
    @Override
    public Response accept(BasicCommandVisitor visitor) {
        return visitor.visit(this);
    }
}
