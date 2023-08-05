package com.hashem.p1.visitors;

import com.hashem.p1.ClassDao;
import com.hashem.p1.GradeDao;
import com.hashem.p1.RoleDao;
import com.hashem.p1.UserDao;
import com.hashem.p1.queries.*;
import com.hashem.p1.responses.classes.GetClassesForUserQueryResponse;
import com.hashem.p1.responses.classes.GetClassesQueryResponse;
import com.hashem.p1.responses.grades.GetGradesForUserQueryResponse;
import com.hashem.p1.responses.roles.GetRolesQueryResponse;
import com.hashem.p1.responses.users.GetUsersQueryResponse;
import com.hashem.p1.responses.Response;

public class DefaultQueryVisitor implements QueryVisitor {

    @Override
    public Response visit(GetUsersQuery query) {

        try (var dao = new UserDao()) {
            return new GetUsersQueryResponse(dao.getUsers());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Response visit(GetRolesQuery query) {

        try (var dao = new RoleDao()) {
            return new GetRolesQueryResponse(dao.getAllRoles());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Response visit(GetClassMembersQuery query) {
        return null;
    }

    @Override
    public Response visit(GetClassesQuery query) {
        try (var dao = new ClassDao()) {
            return new GetClassesQueryResponse(dao.getClasses());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Response visit(GetClassesForUserQuery query) {
        try (var dao = new ClassDao()) {
            return new GetClassesForUserQueryResponse(dao.getClasses(query.id()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Response visit(GetGradesForUserQuery query) {
        try (var dao = new GradeDao()) {
            var grades = dao.getGrades(query.classId(), query.userId());
            return new GetGradesForUserQueryResponse(grades);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
