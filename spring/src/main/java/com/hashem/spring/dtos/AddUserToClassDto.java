package com.hashem.spring.dtos;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class AddUserToClassDto {
    int userId;
    int classId;
}
