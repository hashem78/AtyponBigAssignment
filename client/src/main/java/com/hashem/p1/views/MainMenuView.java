package com.hashem.p1.views;

import com.hashem.p1.context.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class MainMenuView implements View {

    record ViewProperties(String name, String description) {
    }

    record ViewMapping(int index, MainMenuView.ViewProperties properties) {
        @Override
        public String toString() {
            return index + ". " + properties.description;
        }
    }

    @Override
    public void run(Context context) {

        var viewProperties = getViewProperties(context);

        viewProperties.add(new MainMenuView.ViewProperties("exit", "Exit"));

        IntStream.range(0, viewProperties.size())
                .mapToObj(i -> new MainMenuView.ViewMapping(i, viewProperties.get(i)))
                .forEach(System.out::println);

        System.out.print("Enter your choice: ");
        var scanner = new Scanner(System.in);
        var userChoice = scanner.nextInt();

        context
                .callbackStore()
                .get(viewProperties.get(userChoice).name)
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
                        add(new ViewProperties("assign_role_to_user", "Assign a role to a user"));
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
