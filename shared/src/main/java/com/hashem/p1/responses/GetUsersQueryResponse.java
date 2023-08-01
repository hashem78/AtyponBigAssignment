package com.hashem.p1.responses;

import com.hashem.p1.Response;
import com.hashem.p1.models.User;

import java.util.List;

public record GetUsersQueryResponse(List<User> users) implements Response {

}
