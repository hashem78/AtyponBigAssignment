package com.hashem.p1.views.role_management;

import com.hashem.p1.HttpClient;
import com.hashem.p1.commands.role.UpdateRoleCommand;
import com.hashem.p1.context.Context;
import com.hashem.p1.queries.GetRolesQuery;
import com.hashem.p1.responses.roles.GetRolesQueryResponse;
import com.hashem.p1.responses.roles.UpdateRoleCommandResponse;
import com.hashem.p1.views.Helpers;
import com.hashem.p1.views.core.View;

import java.util.Scanner;

public class UpdateRoleView implements View {
    @Override
    public void run(Context context) {

        var getRolesResponse = HttpClient.sendRequest(
                GetRolesQueryResponse.class,
                new GetRolesQuery());

        var scanner = new Scanner(System.in);

        Helpers.displayRoles(getRolesResponse.roles());

        System.out.print("Enter role id to update: ");
        var id = scanner.nextInt();

        var roleOptional = getRolesResponse.roles()
                .stream()
                .filter(x -> x.id() == id)
                .findFirst();

        if (roleOptional.isEmpty()) {
            System.out.println("Role does not exist!");
            return;
        }

        var role = roleOptional.get();
        System.out.print("Enter new role name: ");
        var newRoleName = scanner.next();

        var updateRoleResponse = HttpClient.sendRequest(
                UpdateRoleCommandResponse.class,
                new UpdateRoleCommand(role.id(), newRoleName)
        );

        if (updateRoleResponse.success())
            System.out.println("Role updated successfully");
        else
            System.out.println("Failed to update");
    }
}
