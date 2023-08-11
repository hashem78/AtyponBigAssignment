package com.hashem.spring.controllers;

import com.hashem.p1.UserDao;
import com.hashem.p1.models.User;
import com.hashem.spring.dtos.CreateUserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UsersController {

    @GetMapping("/getUsers")
    public ResponseEntity<Set<User>> getUsers() {
        try (var userDao = new UserDao()) {
            return ResponseEntity.ok(userDao.getUsers());
        } catch (Exception e) {
            return ResponseEntity.ok(new HashSet<>());
        }
    }

    @PutMapping("/createUser")
    public ResponseEntity<Integer> createUser(@RequestBody CreateUserDto dto) {
        try (var userDao = new UserDao()) {
            return ResponseEntity.ok(
                    userDao.createUser(
                            dto.getEmail(),
                            dto.getPasswordHash(),
                            new ArrayList<>()
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.ok(-1);
        }
    }

    @PatchMapping("/updateUser")
    public ResponseEntity<Boolean> updateUser(@RequestBody User dto) {
        try (var userDao = new UserDao()) {
            return ResponseEntity.ok(userDao.update(dto));
        } catch (Exception e) {
            return ResponseEntity.ok(false);
        }
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<Boolean> deleteUser(@RequestBody int userId) {
        try (var userDao = new UserDao()) {
            return ResponseEntity.ok(userDao.deleteUser(userId));
        } catch (Exception e) {
            return ResponseEntity.ok(false);
        }
    }
}
