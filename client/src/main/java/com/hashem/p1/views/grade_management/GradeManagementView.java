package com.hashem.p1.views.grade_management;

import com.hashem.p1.context.Context;
import com.hashem.p1.views.Helpers;
import com.hashem.p1.views.core.View;
import com.hashem.p1.views.core.ViewProperties;

import java.util.ArrayList;

public class GradeManagementView implements View {
    @Override
    public void run(Context context) {
        var viewProperties = new ArrayList<ViewProperties>() {{
            add(new ViewProperties("get_grades_admin", "Get Grades for a Class"));
            add(new ViewProperties("edit_grades", "Edit Grades for a Class"));
        }};

        var userChoice = Helpers.getUserChoice(viewProperties);

        context
                .callbackStore()
                .get(viewProperties.get(userChoice).name())
                .run(context);
    }
}
