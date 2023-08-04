package com.hashem.p1.views.role_management;

import com.hashem.p1.context.Context;
import com.hashem.p1.views.Helpers;
import com.hashem.p1.views.core.View;
import com.hashem.p1.views.core.ViewProperties;

import java.util.ArrayList;

public class RoleManagementView implements View {
    @Override
    public void run(Context context) {
        var viewProperties = new ArrayList<ViewProperties>() {{
            add(new ViewProperties("get_roles", "Get All Roles"));
            add(new ViewProperties("create_role", "Create a new Role"));
            add(new ViewProperties("update_role", "Update a Role"));
            add(new ViewProperties("delete_role", "Delete a Role"));
        }};

        var userChoice = Helpers.getUserChoice(viewProperties);

        context
                .callbackStore()
                .get(viewProperties.get(userChoice).name())
                .run(context);
    }
}
