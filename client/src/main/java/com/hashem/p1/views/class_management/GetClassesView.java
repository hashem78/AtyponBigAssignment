package com.hashem.p1.views.class_management;

import com.hashem.p1.HttpClient;
import com.hashem.p1.context.Context;
import com.hashem.p1.queries.GetClassesQuery;
import com.hashem.p1.responses.classes.GetClassesQueryResponse;
import com.hashem.p1.views.Helpers;
import com.hashem.p1.views.core.View;

public class GetClassesView implements View {
    @Override
    public void run(Context context) {

        var response = HttpClient.sendRequest(
                GetClassesQueryResponse.class,
                new GetClassesQuery());

        Helpers.displayClasses(response.classes());
    }
}
