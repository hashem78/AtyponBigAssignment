package com.hashem.p1.commands.user;

import com.hashem.p1.commands.BasicCommandVisitor;
import com.hashem.p1.commands.Command;
import com.hashem.p1.models.Role;
import com.hashem.p1.responses.Response;

import java.util.List;

public record CreateUserCommand(String email, String password, List<Role> roles) implements Command {
    @Override
    public Response accept(BasicCommandVisitor visitor) {
        return visitor.visit(this);
    }
}
