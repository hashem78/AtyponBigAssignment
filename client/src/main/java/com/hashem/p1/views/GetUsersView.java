package com.hashem.p1.views;

import com.hashem.p1.context.Context;
import com.hashem.p1.helpers.HttpRequestBuilder;
import com.hashem.p1.models.Role;
import com.hashem.p1.queries.GetUsersQuery;
import com.hashem.p1.responses.GetUsersQueryResponse;
import de.vandermeer.asciitable.AsciiTable;
import rawhttp.core.body.EagerBodyReader;

import java.io.IOException;
import java.util.stream.Collectors;

public class GetUsersView implements View {
    @Override
    public void run(Context context) {
        try {
            var bodyJson = context.objectMapper().writeValueAsString(new GetUsersQuery());
            var json = HttpRequestBuilder.JsonRequest(bodyJson);
            try (var server = context.socket()) {

                context.http()
                        .parseRequest(json)
                        .eagerly()
                        .writeTo(server.getOutputStream());

                var responseBody = context.http()
                        .parseResponse(server.getInputStream())
                        .eagerly()
                        .getBody()
                        .map(EagerBodyReader::toString)
                        .orElseThrow(() -> new RuntimeException("No body"));

                var response = context.objectMapper()
                        .readValue(responseBody, GetUsersQueryResponse.class);

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

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
