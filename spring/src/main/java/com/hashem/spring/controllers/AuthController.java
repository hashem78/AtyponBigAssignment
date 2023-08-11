package com.hashem.spring.controllers;

import com.hashem.p1.UserDao;
import com.hashem.spring.dtos.LoginDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginDto dto) {
        try (var userDao = new UserDao()) {
            var user= userDao.getByEmailAndPassword(dto.getEmail(), dto.getPassword());
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(401).build();
        }
    }
}
