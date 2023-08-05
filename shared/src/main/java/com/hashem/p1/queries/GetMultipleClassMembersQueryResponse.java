package com.hashem.p1.queries;

import com.hashem.p1.models.CClass;
import com.hashem.p1.responses.Response;

import java.util.List;

public record GetMultipleClassMembersQueryResponse(List<CClass> classes) implements Response {
}
