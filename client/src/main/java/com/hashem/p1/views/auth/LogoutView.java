package com.hashem.p1.views.auth;

import com.hashem.p1.context.Context;
import com.hashem.p1.views.core.View;

public class LogoutView implements View {

    @Override
    public void run(Context context) {
        context.authService().logout();
        System.out.println(context.authService().getAuthState());
    }
}
