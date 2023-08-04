package com.hashem.p1.views.class_management;

import com.hashem.p1.context.Context;
import com.hashem.p1.views.Helpers;
import com.hashem.p1.views.core.View;
import com.hashem.p1.views.core.ViewProperties;

import java.util.ArrayList;

public class ClassManagementView implements View {
    @Override
    public void run(Context context) {

        var viewProperties = new ArrayList<ViewProperties>() {{
            add(new ViewProperties("get_classes", "Get All Classes"));
            add(new ViewProperties("create_class", "Create a new Class"));
            add(new ViewProperties("update_class", "Update a Class"));
        }};

        var userChoice = Helpers.getUserChoice(viewProperties);

        context
                .callbackStore()
                .get(viewProperties.get(userChoice).name())
                .run(context);
    }
}
