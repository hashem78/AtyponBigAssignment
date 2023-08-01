package com.hashem.p1.views;

import com.hashem.p1.context.Context;

public class LogoutView implements View {

    @Override
    public void run(Context context) {
        context.authService().logout();
        System.out.println(context.authService().getAuthState());
    }
}
