package com.hashem.p1.views;

import com.hashem.p1.HttpClient;
import com.hashem.p1.commands.CreateUserCommand;
import com.hashem.p1.context.Context;
import com.hashem.p1.responses.CreateUserCommandResponse;
import de.vandermeer.asciitable.AsciiTable;

import java.util.ArrayList;
import java.util.Scanner;

public class CreateUserView implements View {
    @Override
    public void run(Context context) {
        var scanner = new Scanner(System.in);
        System.out.print("Enter email: ");
        var email = scanner.next();
        System.out.print("Enter password: ");
        var password = scanner.next();

        var response = HttpClient.sendRequest(
                CreateUserCommandResponse.class,
                new CreateUserCommand(email, password, new ArrayList<>())
        );

        if (response.id() == -1) {
            System.out.println("User with email " + email + " already exists!");
            return;
        }

        var table = new AsciiTable();
        table.addRule();
        table.addRow("Id", "Email", "Password");
        table.addRule();
        table.addRow(response.id(), email, password);
        table.addRule();
    }
}
