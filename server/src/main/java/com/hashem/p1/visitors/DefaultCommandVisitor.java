package com.hashem.p1.visitors;

import com.hashem.p1.*;
import com.hashem.p1.commands.*;
import com.hashem.p1.commands.classes.*;
import com.hashem.p1.commands.grades.DeleteGradeCommand;
import com.hashem.p1.commands.grades.UpdateGradeCommand;
import com.hashem.p1.commands.role.CreateRoleCommand;
import com.hashem.p1.commands.role.DeleteRoleCommand;
import com.hashem.p1.commands.role.UpdateRoleCommand;
import com.hashem.p1.commands.user.*;
import com.hashem.p1.responses.*;
import com.hashem.p1.responses.classes.CreateClassCommandResponse;
import com.hashem.p1.responses.classes.DeleteClassCommandResponse;
import com.hashem.p1.responses.classes.UpdateClassCommandResponse;
import com.hashem.p1.responses.grades.DeleteGradeCommandResponse;
import com.hashem.p1.responses.grades.UpdateGradeCommandResponse;
import com.hashem.p1.responses.roles.CreateRoleCommandResponse;
import com.hashem.p1.responses.roles.UpdateRoleCommandResponse;
import com.hashem.p1.responses.users.CreateUserCommandResponse;
import com.hashem.p1.responses.roles.DeleteRoleCommandResponse;
import com.hashem.p1.responses.users.DeleteUserCommandResponse;
import com.hashem.p1.responses.users.UpdateUserCommandResponse;

public class DefaultCommandVisitor implements CommandVisitor {
    @Override
    public Response visit(CreateUserCommand command) {

        try (var dao = new UserDao()) {
            var userId = dao.createUser(command.email(), command.passwordHash(), command.roles());
            return new CreateUserCommandResponse(userId);
        } catch (UserAlreadyExistsException e) {
            return new CreateUserCommandResponse(-1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Response visit(CreateRoleCommand command) {
        try (var dao = new RoleDao()) {
            var roleId = dao.createRole(command.roleName());
            return new CreateRoleCommandResponse(roleId);
        } catch (RoleAlreadyExistsException e) {
            return new CreateRoleCommandResponse(-1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Response visit(UpdateUserCommand command) {

        try (var dao = new UserDao()) {
            var success = dao.update(command.user());
            return new UpdateUserCommandResponse(success);
        } catch (UserAlreadyExistsException e) {
            return new UpdateUserCommandResponse(false);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Response visit(UpdateUserRolesAddCommand command) {

        try (var dao = new RoleDao()) {
            var success = dao.addRoleToUser(command.userId(), command.roleId());
            return new UpdateUserCommandResponse(success);
        } catch (RoleAlreadyExistsException e) {
            return new UpdateUserCommandResponse(false);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Response visit(UpdateUserRolesRemoveCommand command) {

        try (var dao = new RoleDao()) {
            var success = dao.removeRoleFromUser(command.userId(), command.roleId());
            return new UpdateUserCommandResponse(success);
        } catch (RoleAlreadyExistsException e) {
            return new UpdateUserCommandResponse(false);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Response visit(DeleteUserCommand command) {

        try (var dao = new UserDao()) {
            var success = dao.deleteUser(command.id());
            return new DeleteUserCommandResponse(success);
        } catch (UserAlreadyExistsException e) {
            return new DeleteUserCommandResponse(false);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Response visit(UpdateRoleCommand command) {

        try (var dao = new RoleDao()) {
            var success = dao.updateRole(command.id(), command.newRoleName());
            return new UpdateRoleCommandResponse(success);
        } catch (RoleAlreadyExistsException e) {
            return new UpdateRoleCommandResponse(false);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Response visit(DeleteRoleCommand command) {

        try (var dao = new RoleDao()) {
            var success = dao.deleteRole(command.id());
            return new DeleteRoleCommandResponse(success);
        } catch (RoleDoesNotExistException e) {
            return new DeleteRoleCommandResponse(false);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Response visit(CreateClassCommand command) {
        try (var dao = new ClassDao()) {
            var id = dao.create(command.creatorId(), command.name());
            return new CreateClassCommandResponse(id);
        } catch (ClassAlreadyExistsException e) {
            return new CreateClassCommandResponse(-1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Response visit(UpdateClassCommand command) {
        try (var dao = new ClassDao()) {
            var success = dao.update(command.id(), command.name());
            return new UpdateClassCommandResponse(success);
        } catch (ClassDoesNotExistException e) {
            return new UpdateClassCommandResponse(false);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Response visit(DeleteClassCommand command) {
        try (var dao = new ClassDao()) {
            var success = dao.delete(command.id());
            return new DeleteClassCommandResponse(success);
        } catch (ClassDoesNotExistException e) {
            return new DeleteClassCommandResponse(false);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Response visit(UpdateClassUsersAddCommand command) {
        try (var dao = new UserDao()) {
            var success = dao.addUserToClass(command.classId(), command.userId());
            return new UpdateClassCommandResponse(success);
        } catch (RelationshipDoesNotExist | ClassDoesNotExistException e) {
            return new DeleteClassCommandResponse(false);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Response visit(UpdateClassUsersRemoveCommand command) {
        try (var dao = new UserDao()) {
            var success = dao.removeUserFromClass(command.classId(), command.userId());
            return new UpdateClassCommandResponse(success);
        } catch (RelationshipDoesNotExist | ClassDoesNotExistException e) {
            return new UpdateClassCommandResponse(false);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Response visit(CreateGradeCommand command) {
        try (var dao = new GradeDao()) {
            var success = dao.createGrade(command.classId(), command.userId(), command.grade());
            return new CreateGradeCommandResponse(success);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Response visit(UpdateGradeCommand command) {
        try (var dao = new GradeDao()) {
            var success = dao.updateGrade(command.gradeId(), command.classId(), command.userId(), command.grade());
            return new UpdateGradeCommandResponse(success);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Response visit(DeleteGradeCommand command) {
        try (var dao = new GradeDao()) {
            var success = dao.deleteGrade(command.gradeId(), command.classId(), command.userId());
            return new DeleteGradeCommandResponse(success);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
