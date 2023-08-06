package com.hashem.p1;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try (var userDao = new UserDao()) {
            var user = userDao.getByEmailAndPassword(email, password);
            request.getSession().setAttribute("userEmail", email);
            request.getSession().setAttribute("userId", user.id());
            System.out.println(user);
            var dispatcher = request.getRequestDispatcher("/MainServlet");
            dispatcher.forward(request, response);
        } catch (UserDoesNotExistException e) {
            request.setAttribute("errorMessage","Invalid email or password");
            var dispatcher = request.getRequestDispatcher("/index.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}