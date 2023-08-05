package com.hashem.p1.responses.grades;

import com.hashem.p1.models.Grade;
import com.hashem.p1.responses.Response;

import java.util.List;

public record GetGradesForUserQueryResponse(List<Grade> grades) implements Response {
}
