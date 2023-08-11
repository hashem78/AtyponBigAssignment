package com.hashem.spring.dtos;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public
class DeleteGradeDto{
    int gradeId;
    int classId;
    int userId;
}
