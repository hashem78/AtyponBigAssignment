package com.hashem.p1.context;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hashem.p1.auth.AuthService;
import com.hashem.p1.views.ViewRegistry;
import rawhttp.core.RawHttp;

import java.net.Socket;

public record Context(
        ViewRegistry callbackStore,
        AuthService authService,
        Socket socket,
        RawHttp http,
        ObjectMapper objectMapper) {

}
