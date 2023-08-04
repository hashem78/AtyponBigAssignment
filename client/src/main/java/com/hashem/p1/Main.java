package com.hashem.p1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hashem.p1.auth.AuthService;
import com.hashem.p1.context.Context;
import com.hashem.p1.views.*;
import com.hashem.p1.views.auth.ExitView;
import com.hashem.p1.views.auth.LoginView;
import com.hashem.p1.views.auth.LogoutView;
import com.hashem.p1.views.core.ViewRegistry;
import com.hashem.p1.views.role_management.*;
import com.hashem.p1.views.user_management.*;
import rawhttp.core.RawHttp;

import java.util.Optional;


public class Main {
    public static void main(String[] args) {

        var http = new RawHttp();
        var objectMapper = new ObjectMapper();
        var authService = new AuthService(new UserDao());
        var viewRegistry = new ViewRegistry.Factory()
                .register("main_menu", new MainMenuView())
                .register("login", new LoginView())
                .register("get_users", new GetUsersView())
                .register("get_roles", new GetRolesView())
                .register("role_management", new RoleManagementView())
                .register("create_user", new CreateUserView())
                .register("create_role", new CreateRoleView())
                .register("update_role", new UpdateRoleView())
                .register("delete_role", new DeleteRoleView())
                .register("user_management", new UserManagementView())
                .register("update_user", new UpdateUsersView())
                .register("update_user_email", new UpdateUserEmailView())
                .register("update_user_password", new UpdateUserPasswordView())
                .register("update_user_roles", new UpdateUserRolesView())
                .register("update_user_roles_add", new UpdateUserRolesAddView())
                .register("update_user_roles_remove", new UpdateUserRolesRemoveView())
                .register("delete_user", new DeleteUserView())
                .register("logout", new LogoutView())
                .register("exit", new ExitView())
                .create();

        var contextBuilder = Context.builder()
                .callbackStore(viewRegistry)
                .authService(authService)
                .http(http)
                .objectMapper(objectMapper)
                .viewBag(Optional.empty());

        while (true) {
            viewRegistry.get("main_menu").run(contextBuilder.build());
        }
    }
}