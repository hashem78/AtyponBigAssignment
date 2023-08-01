package com.hashem.p1;

import com.hashem.p1.auth.AuthService;
import com.hashem.p1.context.Context;
import com.hashem.p1.views.*;


public class Main {
    public static void main(String[] args)  {

        var viewRegistry = new ViewRegistry.Factory()
                .register("main_menu", new MainMenuView())
                .register("login", new LoginView())
                .register("logout", new LogoutView())
                .register("exit", new ExitView())
                .create();

        var authService = new AuthService();

        while (true) {
            viewRegistry.get("main_menu")
                    .run(new Context(viewRegistry, authService));
        }
    }
}