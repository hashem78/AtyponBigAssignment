package com.hashem.p1.views.class_management;

import com.hashem.p1.context.Context;
import com.hashem.p1.models.CClass;
import com.hashem.p1.models.User;
import com.hashem.p1.views.Helpers;
import com.hashem.p1.views.core.View;
import com.hashem.p1.views.core.ViewProperties;

import java.util.ArrayList;

public class UpdateClassUsersView implements View {
    @Override
    public void run(Context context) {

        var classesOptional = context.viewBag();
        if (classesOptional.isEmpty()) return;
        var clazz = (CClass) classesOptional.get();

        Helpers.displayClass(clazz);
        var viewProperties = new ArrayList<ViewProperties>() {{
            add(new ViewProperties("update_class_users_add", "Add a user to the class"));
            add(new ViewProperties("update_class_users_remove", "Remove a user from the class"));
        }};

        var userChoice = Helpers.getUserChoice(viewProperties);

        context
                .callbackStore()
                .get(viewProperties.get(userChoice).name())
                .run(context);
    }
}
