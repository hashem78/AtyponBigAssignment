package com.hashem.p1.views;

import com.hashem.p1.HttpClient;
import com.hashem.p1.commands.UpdateUserCommand;
import com.hashem.p1.context.Context;
import com.hashem.p1.models.User;
import com.hashem.p1.responses.UpdateUserCommandResponse;

import java.util.Scanner;

public class UpdateUserPasswordView implements View {
    @Override
    public void run(Context context) {

        var userOptional = context.viewBag();
        if (userOptional.isEmpty()) return;
        var user = (User) userOptional.get();
        var scanner = new Scanner(System.in);
        Helpers.displayUser(user);

        System.out.print("Enter new password: ");
        var password = scanner.next();

        var response = HttpClient.sendRequest(
                UpdateUserCommandResponse.class,
                new UpdateUserCommand(user.withPassword(password)));

        if (response.success())
            System.out.println("Update Successful");
        else
            System.out.println("Update Failed");
    }
}
