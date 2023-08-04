package com.hashem.p1.views.class_management;

import com.hashem.p1.HttpClient;
import com.hashem.p1.commands.classes.UpdateClassUsersRemoveCommand;
import com.hashem.p1.commands.user.UpdateUserRolesRemoveCommand;
import com.hashem.p1.context.Context;
import com.hashem.p1.models.CClass;
import com.hashem.p1.models.User;
import com.hashem.p1.queries.GetRolesQuery;
import com.hashem.p1.responses.classes.UpdateClassCommandResponse;
import com.hashem.p1.responses.roles.GetRolesQueryResponse;
import com.hashem.p1.responses.users.UpdateUserCommandResponse;
import com.hashem.p1.views.Helpers;
import com.hashem.p1.views.core.View;

import java.util.Scanner;

public class UpdateClassUsersRemoveView implements View {
    @Override
    public void run(Context context) {

        var classOptional = context.viewBag();
        if (classOptional.isEmpty()) return;
        var clazz = (CClass) classOptional.get();
        var scanner = new Scanner(System.in);
        Helpers.displayClass(clazz);

        System.out.print("Enter user id to remove from class: ");
        var userId = scanner.nextInt();

        var userRemovedResponse = HttpClient.sendRequest(
                UpdateClassCommandResponse.class,
                new UpdateClassUsersRemoveCommand(clazz.id(), userId)
        );

        if (userRemovedResponse.success())
            System.out.println("User removed Successfully");
        else
            System.out.println("Update Failed");
    }
}
