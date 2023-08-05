package com.hashem.p1.responses.classes;

import com.hashem.p1.models.CClass;
import com.hashem.p1.responses.Response;

import java.util.Set;

public record GetClassesForUserQueryResponse(Set<CClass> classes) implements Response {
}
