package com.hashem.p1.views.user_management;

import com.hashem.p1.HttpClient;
import com.hashem.p1.context.Context;
import com.hashem.p1.queries.GetUsersQuery;
import com.hashem.p1.responses.GetUsersQueryResponse;
import com.hashem.p1.views.Helpers;
import com.hashem.p1.views.core.View;

public class GetUsersView implements View {
    @Override
    public void run(Context context) {

        var response = HttpClient.sendRequest(
                GetUsersQueryResponse.class,
                new GetUsersQuery());

        Helpers.displayUsers(response.users());
    }
}
