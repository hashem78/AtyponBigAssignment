package com.hashem.p1.views.grade_management;

import com.hashem.p1.HttpClient;
import com.hashem.p1.context.Context;
import com.hashem.p1.queries.GetClassesForUserQuery;
import com.hashem.p1.queries.GetGradesForUserQuery;
import com.hashem.p1.responses.classes.GetClassesForUserQueryResponse;
import com.hashem.p1.responses.grades.GetGradesForUserQueryResponse;
import com.hashem.p1.views.Helpers;
import com.hashem.p1.views.core.View;
import com.hashem.p1.views.core.ViewProperties;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

public class EditGradesView implements View {
    @Override
    public void run(Context context) {

        var userIdToGetClassesFor = context.authService().getUser().id();
        var userClassesResponse = HttpClient.sendRequest(
                GetClassesForUserQueryResponse.class,
                new GetClassesForUserQuery(userIdToGetClassesFor)
        );

        var scanner = new Scanner(System.in);

        Helpers.displayClasses(userClassesResponse.classes());

        System.out.print("Enter id of the class the user is in: ");
        var classId = scanner.nextInt();

        System.out.print("Enter id of user to edit grades for: ");
        var userIdToGetGradesFor = scanner.nextInt();

        var gradesResponse = HttpClient.sendRequest(
                GetGradesForUserQueryResponse.class,
                new GetGradesForUserQuery(classId, userIdToGetGradesFor)
        );

        Helpers.displayGrades(gradesResponse.grades());

        var viewProperties = new ArrayList<ViewProperties>() {{
            add(new ViewProperties("create_grade", "Assign a grade to the user"));
            add(new ViewProperties("update_grade", "Update a grade for the user"));
            add(new ViewProperties("delete_grade", "Delete a grade from the user"));
        }};

        var userChoice = Helpers.getUserChoice(viewProperties);

        context
                .callbackStore()
                .get(viewProperties.get(userChoice).name())
                .run(context.withViewBag(Optional.of(new GradesViewBag(classId,userIdToGetGradesFor))));
    }
}
