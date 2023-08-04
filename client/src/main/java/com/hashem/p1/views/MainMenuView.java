package com.hashem.p1.views;

import com.hashem.p1.context.Context;

import java.util.ArrayList;
import java.util.List;

public class MainMenuView implements View {

    @Override
    public void run(Context context) {

        var viewProperties = getViewProperties(context);

        viewProperties.add(new ViewProperties("exit", "Exit"));

        var userChoice = Helpers.getUserChoice(viewProperties);

        context
                .callbackStore()
                .get(viewProperties.get(userChoice).name())
                .run(context);
    }

    private static List<ViewProperties> getViewProperties(Context context) {
        var authState = context.authService().getAuthState();
        var user = context.authService().getUser();
        return switch (authState) {
            case LoggedIn -> new ArrayList<>() {
                {
                    if(user.hasRole("admin"))
                    {
                        add(new ViewProperties("get_users", "Get All Users"));
                        add(new ViewProperties("get_roles", "Get All Roles"));
                        add(new ViewProperties("create_user", "Create a new User"));
                        add(new ViewProperties("create_role", "Create a new Role"));
                        add(new ViewProperties("update_user", "Update a user"));
                    }
                    add(new ViewProperties("logout", "Logout"));
                }
            };
            case LoggedOut -> new ArrayList<>() {
                {
                    add(new ViewProperties("login", "Login"));
                }
            };
        };
    }
}
