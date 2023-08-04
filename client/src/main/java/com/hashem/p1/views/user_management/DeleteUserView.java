package com.hashem.p1.views.user_management;

import com.hashem.p1.HttpClient;
import com.hashem.p1.commands.DeleteUserCommand;
import com.hashem.p1.context.Context;
import com.hashem.p1.models.User;
import com.hashem.p1.responses.DeleteUserCommandResponse;
import com.hashem.p1.views.Helpers;
import com.hashem.p1.views.core.View;

public class DeleteUserView implements View {
    @Override
    public void run(Context context) {

        var userOptional = context.viewBag();
        if (userOptional.isEmpty()) return;
        var user = (User) userOptional.get();

        Helpers.displayUser(user);

        var deleteUserResponse = HttpClient.sendRequest(
                DeleteUserCommandResponse.class,
                new DeleteUserCommand(user.id())
        );

        if (deleteUserResponse.success())
            System.out.println("Deleted user successfully");
        else
            System.out.println("Delete failed");
    }
}
