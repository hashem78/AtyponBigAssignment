package com.hashem.p1.responses;

import com.hashem.p1.models.Role;

import java.util.Set;

public record GetRolesQueryResponse(Set<Role> roles) implements Response {

}
