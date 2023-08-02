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

        var authState = context.authService().getAuthState();
        var user = context.authService().getUser();
        List<MainMenuView.ViewProperties> viewProperties = switch (authState) {
            case LoggedIn -> new ArrayList<>() {
                {
                    if(user.hasRole("admin"))
                        add(new ViewProperties("get_users", "Get All Users"));
                    add(new ViewProperties("logout", "Logout"));
                }
            };
            case LoggedOut -> new ArrayList<>() {
                {
                    add(new ViewProperties("login", "Login"));
                }
            };
        };

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
}
