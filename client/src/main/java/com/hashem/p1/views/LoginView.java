package com.hashem.p1.views;

import com.hashem.p1.auth.AuthException;
import com.hashem.p1.context.Context;

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
            context.authService().loginWithEmailAndPassword(email, password);
        } catch (AuthException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
