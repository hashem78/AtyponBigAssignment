package com.hashem.p1.views;

import com.hashem.p1.HttpClient;
import com.hashem.p1.commands.CreateRoleCommand;
import com.hashem.p1.context.Context;
import com.hashem.p1.responses.CreateRoleCommandResponse;
import de.vandermeer.asciitable.AsciiTable;

import java.util.Scanner;

public class CreateRoleView implements View {
    @Override
    public void run(Context context) {
        var scanner = new Scanner(System.in);
        System.out.print("Enter role name: ");
        var roleName = scanner.next();

        var response = HttpClient.sendRequest(
                CreateRoleCommandResponse.class,
                new CreateRoleCommand(roleName),
                context.socket()
        );

        if (response.id() == -1) {
            System.out.println("Role with name " + roleName + " already exists!");
            return;
        }

        var table = new AsciiTable();
        table.addRule();
        table.addRow("Id", "Name");
        table.addRule();
        table.addRow(response.id(), roleName);

        System.out.println(table.render());
    }
}
