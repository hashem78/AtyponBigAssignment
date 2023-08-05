package com.hashem.p1;

import com.hashem.p1.models.Grade;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GradeDao implements AutoCloseable {

    final Connection db;

    public GradeDao() {
        this.db = ConnectionFactory.getDbConnection();
    }

    public boolean createGrade(int classId, int userId, float grade) throws SQLException {

        String sql = "insert into Grades (id,class_id,user_id,grade) value (default,?,?,?);";

        var statement = db.prepareStatement(sql);
        statement.setInt(1, classId);
        statement.setInt(2, userId);
        statement.setFloat(3, grade);

        return statement.executeUpdate() > 0;
    }

    public boolean deleteGrade(int gradeId, int classId, int userId) throws SQLException {
        String sql = "delete from Grades where id = ? and class_id = ? and user_id = ?";

        var statement = db.prepareStatement(sql);
        statement.setInt(1, gradeId);
        statement.setInt(2, classId);
        statement.setInt(3, userId);

        return statement.executeUpdate() > 0;
    }

    public boolean updateGrade(int gradeId, int classId, int userId, float newGrade) throws SQLException {
        String sql = "update Grades set grade = ? where id = ? and class_id = ? and user_id = ?";

        var statement = db.prepareStatement(sql);
        statement.setInt(1, gradeId);
        statement.setInt(2, classId);
        statement.setInt(3, userId);
        statement.setFloat(4, newGrade);

        return statement.executeUpdate() > 0;
    }

    public List<Grade> getGrades(int classId, int userId) throws SQLException {
        String sql = """
                SELECT
                    Grades.id,
                    COALESCE(Grades.grade, 0) AS grade
                FROM
                       Users
                LEFT JOIN
                    Grades ON Users.id = Grades.user_id
                LEFT JOIN
                    Classes ON Grades.class_id = Classes.id
                WHERE
                    Classes.id = ? AND Users.id = ?;
                """;

        var statement = db.prepareStatement(sql);

        statement.setInt(1, classId);
        statement.setInt(2, userId);

        var rs = statement.executeQuery();

        var grades = new ArrayList<Grade>();

        while (rs.next()) {
            int id = rs.getInt("id");
            float grade = rs.getFloat("grade");

            Grade gradeObj = new Grade(id, userId, classId, grade);

            grades.add(gradeObj);
        }
        return grades;
    }

    @Override
    public void close() throws Exception {
        db.close();
    }
}
