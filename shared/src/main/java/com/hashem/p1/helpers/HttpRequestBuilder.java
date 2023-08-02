package com.hashem.p1.helpers;

public class HttpRequestBuilder {
    final StringBuilder builder;

    HttpRequestBuilder(String method, String path) {
        this.builder = new StringBuilder();
        builder.append(method)
                .append(" ")
                .append(path)
                .append(" HTTP/1.1\n");
    }

    public HttpRequestBuilder(String method) {
        this.builder = new StringBuilder();
        builder.append(method)
                .append(" / ")
                .append("HTTP/1.1\n");
    }

    HttpRequestBuilder addHeader(String headerName, String headerValue) {

        builder
                .append(headerName)
                .append(": ")
                .append(headerValue)
                .append("\n");
        return this;
    }

    HttpRequestBuilder addBody(String body) {

        builder.append("\n")
                .append(body);
        return this;
    }

    public String build() {
        return builder.toString();
    }

    public static String JsonRequest(String body) {
        return new HttpRequestBuilder("POST")
                .addHeader("Host", "127.0.0.1:8000")
                .addHeader("Content-Type", "application/json")
                .addHeader("Content-Length", String.valueOf(body.length()))
                .addBody(body)
                .build();
    }
}
