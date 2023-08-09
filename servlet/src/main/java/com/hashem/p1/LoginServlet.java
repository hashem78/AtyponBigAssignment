package com.hashem.p1;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Base64;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
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

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {


        String email = req.getParameter("email");
        String password = req.getParameter("password");

        try (var userDao = new UserDao()) {
            var user = userDao.getByEmailAndPassword(email, DigestUtils.sha256Hex(password));
            var objectMapper = new ObjectMapper();
            req.getSession().setAttribute("user", user);
            var loginCookie = new Cookie("user", Base64.getEncoder().encodeToString(objectMapper.writeValueAsString(user).getBytes()));
            System.out.println(loginCookie.getValue());
            loginCookie.setMaxAge(1);
            resp.addCookie(loginCookie);
            resp.sendRedirect(req.getContextPath() +"/MainServlet");
        } catch (UserDoesNotExistException e) {
            req.setAttribute("errorMessage", "Invalid email or passwordHash");
            var dispatcher = req.getRequestDispatcher("/index.jsp");
            dispatcher.forward(req, resp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}