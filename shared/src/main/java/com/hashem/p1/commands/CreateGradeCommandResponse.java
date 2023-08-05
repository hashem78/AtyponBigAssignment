package com.hashem.p1.commands;

import com.hashem.p1.responses.Response;

public record CreateGradeCommandResponse(boolean success) implements Response {
}
