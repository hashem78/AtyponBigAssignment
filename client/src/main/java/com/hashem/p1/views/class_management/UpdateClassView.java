package com.hashem.p1.views.class_management;

import com.hashem.p1.HttpClient;
import com.hashem.p1.context.Context;
import com.hashem.p1.queries.GetClassesQuery;
import com.hashem.p1.responses.classes.GetClassesQueryResponse;
import com.hashem.p1.views.Helpers;
import com.hashem.p1.views.core.View;
import com.hashem.p1.views.core.ViewProperties;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

public class UpdateClassView implements View {
    @Override
    public void run(Context context) {

        var classesResponse = HttpClient.sendRequest(
                GetClassesQueryResponse.class,
                new GetClassesQuery());

        var scanner = new Scanner(System.in);

        Helpers.displayClasses(classesResponse.classes());

        System.out.print("Enter class id to update: ");

        var id = scanner.nextInt();

        var classOptional = classesResponse.classes()
                .stream()
                .filter(x -> x.id() == id)
                .findFirst();

        if (classOptional.isEmpty()) {
            System.out.println("Class not found!");
            return;
        }

        var viewProperties = new ArrayList<ViewProperties>() {{
            add(new ViewProperties("update_class_name", "Update the Class's name"));
            add(new ViewProperties("update_class_users", "Update the Class's users"));
            add(new ViewProperties("delete_class", "Delete the class"));
        }};

        var userChoice = Helpers.getUserChoice(viewProperties);

        context
                .callbackStore()
                .get(viewProperties.get(userChoice).name())
                .run(context.withViewBag(Optional.of(classOptional.get())));

    }
}
