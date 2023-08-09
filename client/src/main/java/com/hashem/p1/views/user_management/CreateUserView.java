package com.hashem.p1.views.user_management;

import com.hashem.p1.HttpClient;
import com.hashem.p1.commands.user.CreateUserCommand;
import com.hashem.p1.context.Context;
import com.hashem.p1.responses.users.CreateUserCommandResponse;
import com.hashem.p1.views.core.View;
import de.vandermeer.asciitable.AsciiTable;
import org.apache.commons.codec.digest.DigestUtils;

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

        String passwordHash = DigestUtils.sha256Hex(password);

        var response = HttpClient.sendRequest(
                CreateUserCommandResponse.class,
                new CreateUserCommand(email, passwordHash, new ArrayList<>())
        );

        if (response.id() == -1) {
            System.out.println("User with email " + email + " already exists!");
            return;
        }

        var table = new AsciiTable();
        table.addRule();
        table.addRow("Id", "Email");
        table.addRule();
        table.addRow(response.id(), email);
        table.addRule();
    }
}
