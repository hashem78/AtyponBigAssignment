package com.hashem.p1;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.hashem.p1.helpers.BasicObject;
import com.hashem.p1.visitors.DefaultBasicObjectVisitor;
import com.hashem.p1.helpers.HttpResponseBuilder;
import rawhttp.core.RawHttp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException, ExecutionException {

        var threadPool = Executors.newFixedThreadPool(10);

        threadPool
                .submit(new ServerTask(threadPool))
                .get();

        try {
            if (!threadPool.awaitTermination(60, TimeUnit.SECONDS)) {
                threadPool.shutdownNow();

                if (!threadPool.awaitTermination(60, TimeUnit.SECONDS)) {
                    System.err.println("Executor did not terminate");
                }
            }
        } catch (InterruptedException ie) {
            threadPool.shutdownNow();

            Thread.currentThread().interrupt();
        }
    }
}

class ClientTask implements Runnable {

    final Socket client;

    ClientTask(Socket socket) {
        this.client = socket;
    }

    @Override
    public void run() {

        var http = new RawHttp();
        var objectMapper = new ObjectMapper();
        try (var socket = client) {
            var parsedRequest = http.parseRequest(socket.getInputStream());
            var method = parsedRequest.getStartLine().getMethod();
            if (!Objects.equals(method, "GET") && !Objects.equals(method, "POST")) {
                var response = HttpResponseBuilder.MethodNotFoundResponse();
                http.parseResponse(response)
                        .eagerly()
                        .writeTo(socket.getOutputStream());
                return;
            }

            var optionalBody = parsedRequest.getBody();
            if (optionalBody.isPresent()) {
                try (var bodyReader = optionalBody.get().eager()) {
                    var body = bodyReader.toString();
                    var basicObject = objectMapper.readValue(body, BasicObject.class);
                    System.out.println(basicObject);
                    var visitor = new DefaultBasicObjectVisitor();
                    var response = basicObject.accept(visitor);
                    if (response == null)
                        return;
                    var responseBody = objectMapper.writeValueAsString(response);
                    var responseString = HttpResponseBuilder.JsonResponse(responseBody);
                    http.parseResponse(responseString).eagerly().writeTo(socket.getOutputStream());
                }
            } else {
                System.out.println("No body!");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

class ServerTask implements Runnable {

    final ExecutorService threadPool;

    ServerTask(ExecutorService threadPool) {
        this.threadPool = threadPool;
    }

    @Override
    public void run() {
        try (var server = new ServerSocket(8000)) {

            while (true) {
                var client = server.accept();
                System.out.println("New Client!");
                threadPool.submit(new ClientTask(client));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}