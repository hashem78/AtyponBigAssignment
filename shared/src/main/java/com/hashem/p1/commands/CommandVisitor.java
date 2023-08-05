package com.hashem.p1.commands;

import com.hashem.p1.commands.classes.*;
import com.hashem.p1.commands.grades.DeleteGradeCommand;
import com.hashem.p1.commands.grades.UpdateGradeCommand;
import com.hashem.p1.commands.role.CreateRoleCommand;
import com.hashem.p1.commands.role.DeleteRoleCommand;
import com.hashem.p1.commands.role.UpdateRoleCommand;
import com.hashem.p1.commands.user.*;
import com.hashem.p1.responses.Response;

public interface CommandVisitor {
    Response visit(CreateUserCommand command);
    Response visit(CreateRoleCommand command);
    Response visit(UpdateUserCommand command);
    Response visit(UpdateUserRolesAddCommand command);
    Response visit(UpdateUserRolesRemoveCommand command);
    Response visit(DeleteUserCommand command);
    Response visit(UpdateRoleCommand command);
    Response visit(DeleteRoleCommand command);
    Response visit(CreateClassCommand command);
    Response visit(UpdateClassCommand command);
    Response visit(DeleteClassCommand command);
    Response visit(UpdateClassUsersAddCommand command);
    Response visit(UpdateClassUsersRemoveCommand command);
    Response visit(CreateGradeCommand command);
    Response visit(UpdateGradeCommand command);
    Response visit(DeleteGradeCommand command);
}
