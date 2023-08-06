package com.hashem.p1;

import com.hashem.p1.models.CClass;
import com.hashem.p1.models.Grade;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "MainServlet", urlPatterns = {"/"})
public class MainServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        var userId = (int) req.getSession().getAttribute("userId");
        try (var classDao = new ClassDao(); var gradDao = new GradeDao()) {
            var classes = classDao.getClasses(userId);
            var classGradesPairs = new ArrayList<ClassGradesPair>();

            for (var clazz : classes) {
                var grades = gradDao.getGrades(clazz.id(), userId);
                classGradesPairs.add(new ClassGradesPair(clazz, grades));
            }
            req.setAttribute("classGradesPairs", classGradesPairs);
            req.getRequestDispatcher("main.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}