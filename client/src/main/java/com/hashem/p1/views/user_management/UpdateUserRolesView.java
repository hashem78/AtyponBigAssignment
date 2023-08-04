package com.hashem.p1.views.user_management;

import com.hashem.p1.context.Context;
import com.hashem.p1.models.User;
import com.hashem.p1.views.Helpers;
import com.hashem.p1.views.core.View;
import com.hashem.p1.views.core.ViewProperties;

import java.util.ArrayList;

public class UpdateUserRolesView implements View {
    @Override
    public void run(Context context) {

        var userOptional = context.viewBag();
        if (userOptional.isEmpty()) return;
        var user = (User) userOptional.get();

        Helpers.displayUser(user);
        var viewProperties = new ArrayList<ViewProperties>() {{
            add(new ViewProperties("update_user_roles_add", "Add a role to the user"));
            add(new ViewProperties("update_user_roles_remove", "Remove a role from the user"));
        }};

        var userChoice = Helpers.getUserChoice(viewProperties);

        context
                .callbackStore()
                .get(viewProperties.get(userChoice).name())
                .run(context);
    }
}
