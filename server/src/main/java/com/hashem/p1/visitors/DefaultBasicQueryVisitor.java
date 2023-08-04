package com.hashem.p1.visitors;

import com.hashem.p1.RoleDao;
import com.hashem.p1.UserDao;
import com.hashem.p1.queries.BasicQueryVisitor;
import com.hashem.p1.queries.GetClassMembersQuery;
import com.hashem.p1.queries.GetRolesQuery;
import com.hashem.p1.queries.GetUsersQuery;
import com.hashem.p1.responses.GetRolesQueryResponse;
import com.hashem.p1.responses.GetUsersQueryResponse;
import com.hashem.p1.responses.Response;

public class DefaultBasicQueryVisitor implements BasicQueryVisitor {

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
}
