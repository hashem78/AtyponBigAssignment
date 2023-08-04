package com.hashem.p1.views;

import com.hashem.p1.HttpClient;
import com.hashem.p1.context.Context;
import com.hashem.p1.queries.GetRolesQuery;
import com.hashem.p1.responses.GetRolesQueryResponse;
import de.vandermeer.asciitable.AsciiTable;

public class GetRolesView implements View {
    @Override
    public void run(Context context) {

        var response = HttpClient.sendRequest(
                GetRolesQueryResponse.class,
                new GetRolesQuery(),
                context.socket());

        var table = new AsciiTable();
        table.addRule();
        table.addRow("Id", "Name");
        table.addRule();

        response.roles().stream().sorted().forEach(role -> {
            table.addRow(role.id(), role.name());
            table.addRule();
        });

        System.out.println(table.render());
    }
}
