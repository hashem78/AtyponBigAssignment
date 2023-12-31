package com.hashem.p1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hashem.p1.models.CClass;
import com.hashem.p1.models.Grade;
import com.hashem.p1.models.Role;
import com.hashem.p1.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet(name = "MainServlet", urlPatterns = {"/"})
public class MainServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        var objectMapper = new ObjectMapper();
        var cookieOptional = Arrays.stream(req.getCookies())
                .filter(x -> Objects.equals(x.getName(), "user"))
                .findFirst();

        if (cookieOptional.isEmpty()) {
            System.out.println("Cookie not found redirecting");
            resp.sendRedirect(req.getContextPath() + "/");
            return;
        }

        var loggedInUser = objectMapper.readValue(Base64.getDecoder().decode(cookieOptional.get().getValue()), User.class);
        var loggedInUserRoles = loggedInUser.roles().stream()
                .map(Role::name)
                .collect(Collectors.joining(", "));

        req.setAttribute("loggedInUserRoles", loggedInUserRoles);

        try (var classDao = new ClassDao(); var gradeDao = new GradeDao()) {

            var classes = classDao.getClasses(loggedInUser.id());

            var classGradesPairs = new ArrayList<MainViewModel>();

            for (var clazz : classes) {
                var classStats = classDao.getClassStatistics(clazz.id());
                var classGrades = new ArrayList<Grade>();
                for (var user : clazz.users()) {
                    if (loggedInUser.hasRole("student")) {
                        if (user.id() == loggedInUser.id()) {
                            var userGrades = gradeDao.getGrades(clazz.id(), user.id());
                            classGrades.addAll(userGrades);
                            break;
                        }
                    } else {
                        var userGrades = gradeDao.getGrades(clazz.id(), user.id());
                        classGrades.addAll(userGrades);
                    }
                }
                classGradesPairs.add(new MainViewModel(clazz, classGrades, classStats));
            }
            req.setAttribute("classGradesPairs", classGradesPairs);
            req.getRequestDispatcher("main.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}