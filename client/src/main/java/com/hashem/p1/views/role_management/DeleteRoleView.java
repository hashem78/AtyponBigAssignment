package com.hashem.p1.views.role_management;

import com.hashem.p1.HttpClient;
import com.hashem.p1.commands.role.DeleteRoleCommand;
import com.hashem.p1.context.Context;
import com.hashem.p1.queries.GetRolesQuery;
import com.hashem.p1.responses.users.DeleteRoleCommandResponse;
import com.hashem.p1.responses.roles.GetRolesQueryResponse;
import com.hashem.p1.views.Helpers;
import com.hashem.p1.views.core.View;

import java.util.Scanner;

public class DeleteRoleView implements View {
    @Override
    public void run(Context context) {

        var getRolesResponse = HttpClient.sendRequest(
                GetRolesQueryResponse.class,
                new GetRolesQuery());

        var scanner = new Scanner(System.in);

        Helpers.displayRoles(getRolesResponse.roles());

        System.out.print("Enter role id to delete: ");
        var id = scanner.nextInt();

        var response = HttpClient.sendRequest(
                DeleteRoleCommandResponse.class,
                new DeleteRoleCommand(id));

        if(response.success())
            System.out.println("Deleted role successfully");
        else
            System.out.println("Delete role failed");
    }
}
