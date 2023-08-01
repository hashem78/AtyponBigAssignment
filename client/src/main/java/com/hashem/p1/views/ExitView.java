package com.hashem.p1.views;

import com.hashem.p1.context.Context;

public class ExitView implements View {

    @Override
    public void run(Context context) {
        System.exit(0);
    }
}
