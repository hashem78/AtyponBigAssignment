package com.hashem.p1;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        var cookieOptional = Arrays.stream(req.getCookies())
                .filter(x -> Objects.equals(x.getName(), "user"))
                .findFirst();

        if (cookieOptional.isPresent()) {
            System.out.println("ICookie is present!");
            cookieOptional.get().setMaxAge(0);
            resp.addCookie(cookieOptional.get());
        }
        req.getSession().invalidate();
        resp.sendRedirect(req.getContextPath() + "/");
    }
}