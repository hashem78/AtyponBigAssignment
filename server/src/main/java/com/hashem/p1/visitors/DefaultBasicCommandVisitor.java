package com.hashem.p1.visitors;

import com.hashem.p1.*;
import com.hashem.p1.commands.*;
import com.hashem.p1.responses.CreateRoleCommandResponse;
import com.hashem.p1.responses.CreateUserCommandResponse;
import com.hashem.p1.responses.Response;
import com.hashem.p1.responses.UpdateUserCommandResponse;

public class DefaultBasicCommandVisitor implements BasicCommandVisitor {
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
    public Response visit(AddUserToClassCommand command) {
        return null;
    }

    @Override
    public Response visit(CreateClassCommand command) {
        return null;
    }

    @Override
    public Response visit(RemoveUserCommand command) {
        return null;
    }

    @Override
    public Response visit(RemoveUserFromClassCommand command) {
        return null;
    }
}
