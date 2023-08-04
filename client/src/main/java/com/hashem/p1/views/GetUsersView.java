package com.hashem.p1.views;

import com.hashem.p1.HttpClient;
import com.hashem.p1.context.Context;
import com.hashem.p1.queries.GetUsersQuery;
import com.hashem.p1.responses.GetUsersQueryResponse;

public class GetUsersView implements View {
    @Override
    public void run(Context context) {

        var response = HttpClient.sendRequest(
                GetUsersQueryResponse.class,
                new GetUsersQuery());

        Helpers.displayUsers(response.users());
    }
}
