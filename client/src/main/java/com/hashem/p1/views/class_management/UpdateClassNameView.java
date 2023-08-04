package com.hashem.p1.views.class_management;

import com.hashem.p1.HttpClient;
import com.hashem.p1.commands.classes.UpdateClassCommand;
import com.hashem.p1.commands.user.UpdateUserCommand;
import com.hashem.p1.context.Context;
import com.hashem.p1.models.CClass;
import com.hashem.p1.models.User;
import com.hashem.p1.responses.classes.UpdateClassCommandResponse;
import com.hashem.p1.responses.users.UpdateUserCommandResponse;
import com.hashem.p1.views.Helpers;
import com.hashem.p1.views.core.View;

import java.util.HashSet;
import java.util.Scanner;

public class UpdateClassNameView implements View {
    @Override
    public void run(Context context) {

        var classOptional = context.viewBag();
        if (classOptional.isEmpty()) return;
        var clazz = (CClass) classOptional.get();
        var scanner = new Scanner(System.in);
        Helpers.displayClass(clazz);

        System.out.print("Enter new name: ");
        var name = scanner.next();

        var response = HttpClient.sendRequest(
                UpdateClassCommandResponse.class,
                new UpdateClassCommand(clazz.id(), name));

        if (response.success())
            System.out.println("Update Successful");
        else
            System.out.println("Update Failed");
    }
}
