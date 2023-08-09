package com.hashem.p1;

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
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

@WebServlet(name = "MainServlet", urlPatterns = {"/"})
public class MainServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        var loggedInUser = (User) req.getSession().getAttribute("user");
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