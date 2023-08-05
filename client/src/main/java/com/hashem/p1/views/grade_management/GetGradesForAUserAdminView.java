package com.hashem.p1.views.grade_management;

import com.hashem.p1.HttpClient;
import com.hashem.p1.context.Context;
import com.hashem.p1.queries.GetClassesForUserQuery;
import com.hashem.p1.queries.GetGradesForUserQuery;
import com.hashem.p1.responses.classes.GetClassesForUserQueryResponse;
import com.hashem.p1.responses.grades.GetGradesForUserQueryResponse;
import com.hashem.p1.views.Helpers;
import com.hashem.p1.views.core.View;

import java.util.Scanner;

public class GetGradesForAUserAdminView implements View {
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

        System.out.print("Enter id of user to get grades for: ");
        var userIdToGetGradesFor = scanner.nextInt();

        var gradesResponse = HttpClient.sendRequest(
                GetGradesForUserQueryResponse.class,
                new GetGradesForUserQuery(classId, userIdToGetGradesFor)
        );

       Helpers.displayGrades(gradesResponse.grades());
    }
}