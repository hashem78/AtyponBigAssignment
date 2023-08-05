package com.hashem.p1.views.grade_management;

import com.hashem.p1.HttpClient;
import com.hashem.p1.commands.CreateGradeCommand;
import com.hashem.p1.commands.CreateGradeCommandResponse;
import com.hashem.p1.context.Context;
import com.hashem.p1.views.core.View;

import java.util.Scanner;

public class CreateGradeView implements View {
    @Override
    public void run(Context context) {

        var viewBagOptional = context.viewBag();
        if (viewBagOptional.isEmpty())
            return;
        var viewBag = (GradesViewBag) viewBagOptional.get();

        var scanner = new Scanner(System.in);
        System.out.print("Enter the grade you want to add to the user: ");
        var grade = scanner.nextFloat();

        var response = HttpClient.sendRequest(
                CreateGradeCommandResponse.class,
                new CreateGradeCommand(viewBag.classId(), viewBag.userId(), grade)
        );

        if (response.success()) {
            System.out.println("Created grade successfully");
        } else {
            System.out.println("Grade creation failed");
        }
    }
}
