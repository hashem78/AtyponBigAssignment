package com.hashem.p1.views.auth;

import com.hashem.p1.UserDoesNotExistException;
import com.hashem.p1.context.Context;
import com.hashem.p1.views.core.View;

import java.sql.SQLException;
import java.util.Scanner;

public class LoginView implements View {

    @Override
    public void run(Context context) {

        var scanner = new Scanner(System.in);
        System.out.print("Enter your email: ");
        var email = scanner.next();
        System.out.println("Enter your password: ");
        var password = scanner.next();
        try {
//            context.authService().loginWithEmailAndPassword("hashem3@labiba.ai", "Mythi@2023");
            context.authService().loginWithEmailAndPassword(email, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (UserDoesNotExistException e) {
            System.out.println("Email or password are wrong");
        }
    }
}
