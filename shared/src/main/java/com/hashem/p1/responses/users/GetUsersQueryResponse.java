package com.hashem.p1.responses.users;

import com.hashem.p1.models.User;
import com.hashem.p1.responses.Response;

import java.util.Set;

public record GetUsersQueryResponse(Set<User> users) implements Response {
}