package com.hashem.p1.views;

import com.hashem.p1.HttpClient;
import com.hashem.p1.context.Context;
import com.hashem.p1.models.Role;
import com.hashem.p1.queries.GetUsersQuery;
import com.hashem.p1.responses.GetUsersQueryResponse;
import de.vandermeer.asciitable.AsciiTable;

import java.util.stream.Collectors;

public class GetUsersView implements View {
    @Override
    public void run(Context context) {

        var response = HttpClient.sendRequest(
                GetUsersQueryResponse.class,
                new GetUsersQuery(),
                context.socket());

        var table = new AsciiTable();
        table.addRule();
        table.addRow("Id", "Email", "Password", "Roles");
        table.addRule();

        response.users().forEach(user -> {
            var roles = user.roles().stream()
                    .map(Role::name)
                    .collect(Collectors.joining(", "));

            table.addRow(user.id(), user.email(), user.password(), roles);
            table.addRule();
        });

        System.out.println(table.render());
    }
}
