package com.hashem.p1.responses.roles;

import com.hashem.p1.models.Role;
import com.hashem.p1.responses.Response;

import java.util.Set;

public record GetRolesQueryResponse(Set<Role> roles) implements Response {

}
