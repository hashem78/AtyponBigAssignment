package com.hashem.p1.views.auth;

import com.hashem.p1.context.Context;
import com.hashem.p1.views.core.View;

public class ExitView implements View {

    @Override
    public void run(Context context) {
        System.exit(0);
    }
}
