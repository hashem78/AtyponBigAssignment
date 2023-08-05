package com.hashem.p1.views.grade_management;

import com.hashem.p1.HttpClient;
import com.hashem.p1.commands.grades.UpdateGradeCommand;
import com.hashem.p1.context.Context;
import com.hashem.p1.responses.grades.UpdateGradeCommandResponse;
import com.hashem.p1.views.core.View;

import java.util.Scanner;

public class UpdateGradeView implements View {
    @Override
    public void run(Context context) {

        var viewBagOptional = context.viewBag();
        if (viewBagOptional.isEmpty())
            return;
        var viewBag = (GradesViewBag) viewBagOptional.get();

        new GetGradesForAUserAdminView().run(context);

        var scanner = new Scanner(System.in);

        System.out.println("Enter the grade id: ");
        var gradeId = scanner.nextInt();

        System.out.println("Enter new grade: ");
        var grade = scanner.nextFloat();

        var response = HttpClient.sendRequest(
                UpdateGradeCommandResponse.class,
                new UpdateGradeCommand(gradeId, viewBag.classId(), viewBag.userId(), grade)
        );

        if (response.success()) {
            System.out.println("Updated grade successfully");
        } else {
            System.out.println("Grade creation failed");
        }
    }
}
