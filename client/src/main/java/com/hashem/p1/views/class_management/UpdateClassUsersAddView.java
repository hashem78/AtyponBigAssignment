package com.hashem.p1.views.class_management;

import com.hashem.p1.HttpClient;
import com.hashem.p1.commands.classes.UpdateClassUsersAddCommand;
import com.hashem.p1.commands.user.UpdateUserRolesAddCommand;
import com.hashem.p1.context.Context;
import com.hashem.p1.models.CClass;
import com.hashem.p1.queries.GetUsersQuery;
import com.hashem.p1.responses.classes.UpdateClassCommandResponse;
import com.hashem.p1.responses.users.GetUsersQueryResponse;
import com.hashem.p1.views.Helpers;
import com.hashem.p1.views.core.View;

import java.util.Scanner;

public class UpdateClassUsersAddView implements View {
    @Override
    public void run(Context context) {
        var classOptional = context.viewBag();
        if (classOptional.isEmpty()) return;
        var clazz = (CClass) classOptional.get();
        var scanner = new Scanner(System.in);
        Helpers.displayClass(clazz);

        var usersResponse = HttpClient.sendRequest(
                GetUsersQueryResponse.class,
                new GetUsersQuery()
        );

        Helpers.displayUsers(usersResponse.users());
        System.out.print("Enter user id to add to class: ");
        var userId = scanner.nextInt();

        var usersAddResponse = HttpClient.sendRequest(
                UpdateClassCommandResponse.class,
                new UpdateClassUsersAddCommand(clazz.id(), userId)
        );

        if (usersAddResponse.success())
            System.out.println("User added Successfully");
        else
            System.out.println("Update Failed");
    }
}
