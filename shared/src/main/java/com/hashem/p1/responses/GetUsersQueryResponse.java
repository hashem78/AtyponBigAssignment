package com.hashem.p1.responses;

import com.hashem.p1.models.User;

import java.util.Set;

public record GetUsersQueryResponse(Set<User> users) implements Response {
}