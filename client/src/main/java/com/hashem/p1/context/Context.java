package com.hashem.p1.context;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hashem.p1.auth.AuthService;
import com.hashem.p1.views.ViewRegistry;
import lombok.Builder;
import lombok.With;
import rawhttp.core.RawHttp;

import java.net.Socket;
import java.util.Optional;

@With
@Builder
public record Context(
        ViewRegistry callbackStore,
        AuthService authService,
        RawHttp http,
        ObjectMapper objectMapper,
        Optional<Object> viewBag) {
}
