package com.hashem.p1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hashem.p1.auth.AuthService;
import com.hashem.p1.context.Context;
import com.hashem.p1.views.*;
import rawhttp.core.RawHttp;

import java.io.IOException;
import java.net.Socket;


public class Main {
    public static void main(String[] args) throws IOException {

        var http = new RawHttp();
        var objectMapper = new ObjectMapper();
        var authService = new AuthService(new UserDao());
        var viewRegistry = new ViewRegistry.Factory()
                .register("main_menu", new MainMenuView())
                .register("login", new LoginView())
                .register("get_users", new GetUsersView())
                .register("get_roles", new GetRolesView())
                .register("create_user", new CreateUserView())
                .register("create_role", new CreateRoleView())
                .register("logout", new LogoutView())
                .register("exit", new ExitView())
                .create();

        while (true) {
            try (var server = new Socket("localhost", 8000)) {

                viewRegistry.get("main_menu")
                        .run(new Context(viewRegistry, authService, server, http, objectMapper));
            }
        }
    }
}