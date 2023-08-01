package com.hashem.p1.helpers;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.time.format.DateTimeFormatter.RFC_1123_DATE_TIME;

public class HttpResponseBuilder {

    private static final Map<Integer, String> replies = new LinkedHashMap<>() {{
        put(100, "Continue");
        put(101, "Switching Protocols");
        put(200, "OK");
        put(201, "Created");
        put(202, "Accepted");
        put(203, "Non-Authoritative Information");
        put(204, "No Content");
        put(205, "Reset Content");
        put(206, "Partial Content");
        put(300, "Multiple Choices");
        put(301, "Moved Permanently");
        put(302, "Found");
        put(303, "See Other");
        put(304, "Not Modified");
        put(305, "Use Proxy");
        put(306, "(Unused)");
        put(307, "Temporary Redirect");
        put(400, "Bad Request");
        put(401, "Unauthorized");
        put(402, "Payment Required");
        put(403, "Forbidden");
        put(404, "Not Found");
        put(405, "Method Not Allowed");
        put(406, "Not Acceptable");
        put(407, "Proxy Authentication Required");
        put(408, "Request Timeout");
        put(409, "Conflict");
        put(410, "Gone");
        put(411, "Length Required");
        put(412, "Precondition Failed");
        put(413, "Request Entity Too Large");
        put(414, "Request-URI Too Long");
        put(415, "Unsupported Media Type");
        put(416, "Requested Range Not Satisfiable");
        put(417, "Expectation Failed");
        put(500, "Internal Server Error");
        put(501, "Not Implemented");
        put(502, "Bad Gateway");
        put(503, "Service Unavailable");
        put(504, "Gateway Timeout");
        put(505, "HTTP Version Not Supported");
    }};

    final StringBuilder builder;

    HttpResponseBuilder(Integer statusCode) {
        this.builder = new StringBuilder();
        this.builder.append("HTTP/1.1 ")
                .append(statusCode)
                .append(" ")
                .append(replies.get(statusCode))
                .append("\r\n");
    }

    HttpResponseBuilder addHeader(String headerName, String headerValue) {

        builder
                .append(headerName)
                .append(": ")
                .append(headerValue)
                .append("\r\n");
        return this;
    }

    HttpResponseBuilder addBody(String body) {

        builder.append("\r\n")
                .append(body);
        return this;
    }

    String build() {
        return builder.toString();
    }

    public static String JsonResponse(String body) {
        return new HttpResponseBuilder(200)
                .addHeader("Content-Type", "application/json")
                .addHeader("Content-Length", String.valueOf(body.length()))
                .addHeader("Server", "AtyponAssignmentServer")
                .addHeader("Date", RFC_1123_DATE_TIME.format(ZonedDateTime.now(ZoneOffset.UTC)))
                .addBody(body)
                .build();
    }

    public static String MethodNotFoundResponse() {
        return new HttpResponseBuilder(405)
                .addHeader("Content-Type", "text/plain")
                .addHeader("Content-Length", "0")
                .addHeader("Server", "AtyponAssignmentServer")
                .build();
    }
}
