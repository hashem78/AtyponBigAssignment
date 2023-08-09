package com.hashem.p1;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "MainServlet", urlPatterns = {"/"})
public class MainServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        var userId = (int) req.getSession().getAttribute("userId");
        try (var classDao = new ClassDao(); var gradDao = new GradeDao()) {
            var classes = classDao.getClasses(userId);
            var classGradesPairs = new ArrayList<MainViewModel>();

            for (var clazz : classes) {
                var stats = classDao.getClassStatistics(clazz.id());
                var grades = gradDao.getGrades(clazz.id(), userId);
                classGradesPairs.add(new MainViewModel(clazz, grades, stats));
            }
            req.setAttribute("classGradesPairs", classGradesPairs);
            req.getRequestDispatcher("main.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}