package com.hashem.p1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hashem.p1.helpers.RootObject;
import com.hashem.p1.helpers.HttpRequestBuilder;
import com.hashem.p1.responses.Response;
import rawhttp.core.RawHttp;
import rawhttp.core.body.EagerBodyReader;

import java.io.IOException;
import java.net.Socket;

public class HttpClient {

    public static <T extends Response, U extends RootObject> T sendRequest(Class<T> clazz, U obj) {

        var objectMapper = new ObjectMapper();
        var http = new RawHttp();

        try (var server = new Socket("localhost", 8000)) {

            var bodyJson = objectMapper.writeValueAsString(obj);
            var json = HttpRequestBuilder.JsonRequest(bodyJson);

            http
                    .parseRequest(json)
                    .eagerly()
                    .writeTo(server.getOutputStream());

            var responseBody = http
                    .parseResponse(server.getInputStream())
                    .eagerly()
                    .getBody()
                    .map(EagerBodyReader::toString)
                    .orElseThrow(() -> new RuntimeException("No body"));

            return objectMapper.readValue(responseBody, clazz);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
