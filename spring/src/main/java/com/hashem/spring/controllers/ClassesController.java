package com.hashem.spring.controllers;

import com.hashem.p1.ClassDao;
import com.hashem.p1.UserDao;
import com.hashem.p1.models.CClass;
import com.hashem.p1.models.User;
import com.hashem.spring.dtos.AddUserToClassDto;
import com.hashem.spring.dtos.CreateClassDto;
import com.hashem.spring.dtos.RemoveUserFromClassDto;
import com.hashem.spring.dtos.UpdateClassDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/classes")
public class ClassesController {

    @GetMapping("/getClasses")
    public ResponseEntity<Set<CClass>> getClasses() {
        try (var classDao = new ClassDao()) {
            return ResponseEntity.ok(classDao.getClasses());
        } catch (Exception e) {
            return ResponseEntity.ok(new HashSet<>());
        }
    }

    @GetMapping("/getUsersForClass/{classId}")
    public ResponseEntity<Set<User>> getUsersForClass(@PathVariable int classId) {
        try (var classDao = new ClassDao()) {
            return ResponseEntity.ok(classDao.getUsersForClass(classId));
        } catch (Exception e) {
            return ResponseEntity.ok(new HashSet<>());
        }
    }

    @GetMapping("/getClassesForUser/{id}")
    public ResponseEntity<Set<CClass>> getClasses(@PathVariable int id) {
        try (var classDao = new ClassDao()) {
            return ResponseEntity.ok(classDao.getClasses(id));
        } catch (Exception e) {
            return ResponseEntity.ok(new HashSet<>());
        }
    }

    @PutMapping("/createClass")
    public ResponseEntity<Integer> createClass(@RequestBody CreateClassDto dto) {
        try (var classDao = new ClassDao()) {
            return ResponseEntity.ok(
                    classDao.create(
                            dto.getCreatorId(),
                            dto.getName()
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.ok(-1);
        }
    }

    @PatchMapping("/updateClass")
    public ResponseEntity<Boolean> updateClass(@RequestBody UpdateClassDto dto) {
        try (var classDao = new ClassDao()) {
            return ResponseEntity.ok(classDao.update(dto.getClassId(), dto.getName()));
        } catch (Exception e) {
            return ResponseEntity.ok(false);
        }
    }

    @DeleteMapping("/deleteClass")
    public ResponseEntity<Boolean> deleteClass(@RequestBody int userId) {
        try (var classDao = new ClassDao()) {
            return ResponseEntity.ok(classDao.delete(userId));
        } catch (Exception e) {
            return ResponseEntity.ok(false);
        }
    }

    @DeleteMapping("/removeUserFromClass")
    public ResponseEntity<Boolean> removeUserFromClass(@RequestBody RemoveUserFromClassDto dto) {
        try (var userDao = new UserDao()) {
            return ResponseEntity.ok(
                    userDao.removeUserFromClass(
                            dto.getClassId(),
                            dto.getUserId()
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.ok(false);
        }
    }

    @PostMapping("/addUserToClass")
    public ResponseEntity<Boolean> addUserToClass(@RequestBody AddUserToClassDto dto) {
        try (var userDao = new UserDao()) {
            return ResponseEntity.ok(
                    userDao.addUserToClass(
                            dto.getClassId(),
                            dto.getUserId()
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.ok(false);
        }
    }
}
