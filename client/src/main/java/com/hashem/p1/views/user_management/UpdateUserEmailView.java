package com.hashem.p1.views.user_management;

import com.hashem.p1.HttpClient;
import com.hashem.p1.commands.user.UpdateUserCommand;
import com.hashem.p1.context.Context;
import com.hashem.p1.models.User;
import com.hashem.p1.responses.users.UpdateUserCommandResponse;
import com.hashem.p1.views.Helpers;
import com.hashem.p1.views.core.View;

import java.util.Scanner;

public class UpdateUserEmailView implements View {
    @Override
    public void run(Context context) {

        var userOptional = context.viewBag();
        if (userOptional.isEmpty()) return;
        var user = (User) userOptional.get();
        var scanner = new Scanner(System.in);
        Helpers.displayUser(user);

        System.out.print("Enter new email: ");
        var email = scanner.next();

        var response = HttpClient.sendRequest(
                UpdateUserCommandResponse.class,
                new UpdateUserCommand(user.withEmail(email)));

        if (response.success())
            System.out.println("Update Successful");
        else
            System.out.println("Update Failed");
    }
}
