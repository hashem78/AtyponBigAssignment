package com.hashem.p1.responses.grades;

import com.hashem.p1.responses.Response;

public record DeleteGradeCommandResponse(boolean success) implements Response {
}
