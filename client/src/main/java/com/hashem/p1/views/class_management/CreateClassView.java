package com.hashem.p1.views.class_management;

import com.hashem.p1.HttpClient;
import com.hashem.p1.commands.classes.CreateClassCommand;
import com.hashem.p1.context.Context;
import com.hashem.p1.responses.classes.CreateClassCommandResponse;
import com.hashem.p1.views.core.View;
import de.vandermeer.asciitable.AsciiTable;

import java.util.Scanner;

public class CreateClassView implements View {
    @Override
    public void run(Context context) {
        var scanner = new Scanner(System.in);
        System.out.print("Enter name: ");
        var name = scanner.next();

        var response = HttpClient.sendRequest(
                CreateClassCommandResponse.class,
                new CreateClassCommand(name)
        );

        if (response.id() == -1) {
            System.out.println("Class with name " + name + " already exists!");
            return;
        }

        var table = new AsciiTable();
        table.addRule();
        table.addRow("Id", "Name");
        table.addRule();
        table.addRow(response.id(), name);
        table.addRule();
    }
}
