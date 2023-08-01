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
        List<MainMenuView.ViewProperties> viewProperties = switch (authState) {
            case LoggedIn -> {

                yield new ArrayList<>() {
                    {
                        add(new MainMenuView.ViewProperties("logout", "Logout"));
                    }
                };
            }
            case LoggedOut -> {
                yield new ArrayList<>() {
                    {
                        add(new MainMenuView.ViewProperties("login", "Login"));
                    }
                };
            }
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
