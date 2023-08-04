package com.hashem.p1.views.user_management;

import com.hashem.p1.HttpClient;
import com.hashem.p1.context.Context;
import com.hashem.p1.queries.GetUsersQuery;
import com.hashem.p1.responses.users.GetUsersQueryResponse;
import com.hashem.p1.views.Helpers;
import com.hashem.p1.views.core.View;
import com.hashem.p1.views.core.ViewProperties;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

public class UpdateUsersView implements View {
    @Override
    public void run(Context context) {

        var usersResponse = HttpClient.sendRequest(
                GetUsersQueryResponse.class,
                new GetUsersQuery());

        var scanner = new Scanner(System.in);

        Helpers.displayUsers(usersResponse.users());

        System.out.print("Enter user id to update: ");

        var id = scanner.nextInt();

        var userOptional = usersResponse.users()
                .stream()
                .filter(x -> x.id() == id)
                .findFirst();

        if (userOptional.isEmpty()) {
            System.out.println("User not found!");
            return;
        }

        var viewProperties = new ArrayList<ViewProperties>() {{
            add(new ViewProperties("update_user_email", "Update the User's email"));
            add(new ViewProperties("update_user_password", "Update the User's password"));
            add(new ViewProperties("update_user_roles", "Update the User's roles"));
            add(new ViewProperties("delete_user", "Delete the User"));
        }};

        var userChoice = Helpers.getUserChoice(viewProperties);

        context
                .callbackStore()
                .get(viewProperties.get(userChoice).name())
                .run(context.withViewBag(Optional.of(userOptional.get())));

    }
}
