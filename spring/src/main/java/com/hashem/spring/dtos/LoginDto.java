package com.hashem.spring.dtos;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class LoginDto {
    String email;
    String password;
}
