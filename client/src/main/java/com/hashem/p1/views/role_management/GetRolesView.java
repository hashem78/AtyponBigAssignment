package com.hashem.p1.views.role_management;

import com.hashem.p1.HttpClient;
import com.hashem.p1.context.Context;
import com.hashem.p1.queries.GetRolesQuery;
import com.hashem.p1.responses.GetRolesQueryResponse;
import com.hashem.p1.views.Helpers;
import com.hashem.p1.views.core.View;

public class GetRolesView implements View {
    @Override
    public void run(Context context) {

        var response = HttpClient.sendRequest(
                GetRolesQueryResponse.class,
                new GetRolesQuery());

        Helpers.displayRoles(response.roles());
    }
}
