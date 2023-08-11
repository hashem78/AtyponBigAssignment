package com.hashem.spring.dtos;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class RemoveUserFromClassDto {
    int userId;
    int classId;
}
