package com.hashem.p1.views.grade_management;

import com.hashem.p1.HttpClient;
import com.hashem.p1.context.Context;
import com.hashem.p1.commands.grades.DeleteGradeCommand;
import com.hashem.p1.responses.grades.DeleteGradeCommandResponse;
import com.hashem.p1.views.core.View;

import java.util.Scanner;

public class DeleteGradeView implements View {
    @Override
    public void run(Context context) {

        var viewBagOptional = context.viewBag();
        if (viewBagOptional.isEmpty())
            return;

        var viewBag = (GradesViewBag) viewBagOptional.get();

        var scanner = new Scanner(System.in);
        System.out.print("Enter the grade id you want to delete from the user: ");
        var gradeId = scanner.nextInt();

        var response = HttpClient.sendRequest(
                DeleteGradeCommandResponse.class,
                new DeleteGradeCommand(gradeId, viewBag.classId(), viewBag.userId())
        );

        if (response.success()) {
            System.out.println("Deleted grade successfully");
        } else {
            System.out.println("Grade creation failed");
        }
    }
}
