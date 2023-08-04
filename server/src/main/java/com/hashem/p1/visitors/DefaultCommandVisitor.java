package com.hashem.p1.visitors;

import com.hashem.p1.*;
import com.hashem.p1.commands.*;
import com.hashem.p1.commands.classes.*;
import com.hashem.p1.commands.role.CreateRoleCommand;
import com.hashem.p1.commands.role.DeleteRoleCommand;
import com.hashem.p1.commands.role.UpdateRoleCommand;
import com.hashem.p1.commands.user.*;
import com.hashem.p1.responses.*;
import com.hashem.p1.responses.classes.CreateClassCommandResponse;
import com.hashem.p1.responses.classes.UpdateClassCommandResponse;
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
            var userId = dao.createUser(command.email(), command.password(), command.roles());
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
        } catch (UserAlreadyExistsException e) {
            return new DeleteRoleCommandResponse(false);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Response visit(CreateClassCommand command) {
        try (var dao = new ClassDao()) {
            var id = dao.create(command.name());
            return new CreateClassCommandResponse(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Response visit(UpdateClassCommand command) {
        try (var dao = new ClassDao()) {
            var success = dao.update(command.id(), command.name());
            return new DeleteRoleCommandResponse(success);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Response visit(DeleteClassCommand command) {
        try (var dao = new ClassDao()) {
            var success = dao.delete(command.id());
            return new UpdateClassCommandResponse(success);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Response visit(UpdateClassUsersAddCommand command) {
        return null;
    }

    @Override
    public Response visit(UpdateClassUsersRemoveCommand command) {
        return null;
    }
}
