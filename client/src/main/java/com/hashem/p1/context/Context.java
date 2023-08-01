package com.hashem.p1.context;

import com.hashem.p1.auth.AuthService;
import com.hashem.p1.views.ViewRegistry;

public record Context(ViewRegistry callbackStore, AuthService authService) {

}
