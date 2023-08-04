package com.hashem.p1.views;

import com.hashem.p1.context.Context;
import com.hashem.p1.views.core.View;
import com.hashem.p1.views.core.ViewProperties;

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
                        add(new ViewProperties("role_management", "Manage Roles"));
                        add(new ViewProperties("user_management", "Manage Users"));
                        add(new ViewProperties("class_management", "Manage Classes"));
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
