package com.hashem.p1.views.user_management;

import com.hashem.p1.HttpClient;
import com.hashem.p1.commands.UpdateUserRolesRemoveCommand;
import com.hashem.p1.context.Context;
import com.hashem.p1.models.User;
import com.hashem.p1.queries.GetRolesQuery;
import com.hashem.p1.responses.GetRolesQueryResponse;
import com.hashem.p1.responses.UpdateUserCommandResponse;
import com.hashem.p1.views.Helpers;
import com.hashem.p1.views.core.View;

import java.util.Scanner;

public class UpdateUserRolesRemoveView implements View {
    @Override
    public void run(Context context) {

        var userOptional = context.viewBag();
        if (userOptional.isEmpty()) return;
        var user = (User) userOptional.get();
        var scanner = new Scanner(System.in);
        Helpers.displayUser(user);

        var rolesResponse = HttpClient.sendRequest(
                GetRolesQueryResponse.class,
                new GetRolesQuery()
        );

        Helpers.displayRoles(rolesResponse.roles());
        System.out.print("Enter role id to remove from user: ");
        var roleId = scanner.nextInt();

        var roleRemovedResponse = HttpClient.sendRequest(
                UpdateUserCommandResponse.class,
                new UpdateUserRolesRemoveCommand(user.id(), roleId)
        );

        if (roleRemovedResponse.success())
            System.out.println("Role removed Successfully");
        else
            System.out.println("Update Failed");
    }
}
