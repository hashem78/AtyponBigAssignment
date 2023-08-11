package com.hashem.spring.dtos;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class PatchGradeDto {
    int gradeId;
    int classId;
    int userId;
    float grade;
}
