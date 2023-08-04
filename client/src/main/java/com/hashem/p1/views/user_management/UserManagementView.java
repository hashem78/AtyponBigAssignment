package com.hashem.p1.views.user_management;

import com.hashem.p1.HttpClient;
import com.hashem.p1.context.Context;
import com.hashem.p1.queries.GetUsersQuery;
import com.hashem.p1.responses.GetUsersQueryResponse;
import com.hashem.p1.views.Helpers;
import com.hashem.p1.views.core.View;
import com.hashem.p1.views.core.ViewProperties;

import java.util.ArrayList;

public class UserManagementView implements View {
    @Override
    public void run(Context context) {

        var viewProperties = new ArrayList<ViewProperties>() {{
            add(new ViewProperties("get_users", "Get All Users"));
            add(new ViewProperties("create_user", "Create a new User"));
            add(new ViewProperties("update_user", "Update a User"));
        }};

        var userChoice = Helpers.getUserChoice(viewProperties);

        context
                .callbackStore()
                .get(viewProperties.get(userChoice).name())
                .run(context);
    }
}
