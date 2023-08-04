package com.hashem.p1.views.class_management;

import com.hashem.p1.HttpClient;
import com.hashem.p1.commands.classes.DeleteClassCommand;
import com.hashem.p1.context.Context;
import com.hashem.p1.models.CClass;
import com.hashem.p1.responses.classes.DeleteClassCommandResponse;
import com.hashem.p1.views.Helpers;
import com.hashem.p1.views.core.View;

public class DeleteClassView implements View {
    @Override
    public void run(Context context) {

        var classOptional = context.viewBag();
        if (classOptional.isEmpty()) return;
        var clazz = (CClass) classOptional.get();

        Helpers.displayClass(clazz);

        var deleteClassResponse = HttpClient.sendRequest(
                DeleteClassCommandResponse.class,
                new DeleteClassCommand(clazz.id())
        );

        if (deleteClassResponse.success())
            System.out.println("Deleted class successfully");
        else
            System.out.println("Delete failed");
    }
}
