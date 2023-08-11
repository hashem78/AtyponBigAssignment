package com.hashem.spring.controllers;

import com.hashem.p1.ClassDao;
import com.hashem.p1.GradeDao;
import com.hashem.p1.models.CClass;
import com.hashem.p1.models.Grade;
import com.hashem.spring.dtos.CreateGradeDto;
import com.hashem.spring.dtos.DeleteGradeDto;
import com.hashem.spring.dtos.PatchGradeDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/grades")
public class GradesController {
    @GetMapping("/getGradesForClass/{classId}/{userId}")
    public ResponseEntity<List<Grade>> getGradesForUserInClass(
            @PathVariable int classId,
            @PathVariable int userId) {
        try (var gradeDao = new GradeDao()) {
            return ResponseEntity.ok(gradeDao.getGrades(classId, userId));
        } catch (Exception e) {
            return ResponseEntity.ok(new ArrayList<>());
        }
    }

    @PostMapping("/createGradeForUserInClass")
    public ResponseEntity<Boolean> createGradeForUserInClass(@RequestBody CreateGradeDto dto) {
        try (var gradeDao = new GradeDao()) {
            return ResponseEntity.ok(
                    gradeDao.createGrade(
                            dto.getClassId(),
                            dto.getUserId(),
                            dto.getGrade())
            );
        } catch (Exception e) {
            return ResponseEntity.ok(false);
        }
    }

    @DeleteMapping("/deleteGradeForUserInClass")
    public ResponseEntity<Boolean> deleteGradeForUserInClass(@RequestBody DeleteGradeDto dto) {
        try (var gradeDao = new GradeDao()) {
            return ResponseEntity.ok(
                    gradeDao.deleteGrade(
                            dto.getGradeId(),
                            dto.getClassId(),
                            dto.getUserId())
            );
        } catch (Exception e) {
            return ResponseEntity.ok(false);
        }
    }

    @PatchMapping("/updateGradeForUserInClass")
    public ResponseEntity<Boolean> updateGradeForUserInClass(@RequestBody PatchGradeDto dto) {
        try (var gradeDao = new GradeDao()) {
            return ResponseEntity.ok(
                    gradeDao.updateGrade(
                            dto.getGradeId(),
                            dto.getClassId(),
                            dto.getUserId(),
                            dto.getGrade()
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.ok(false);
        }
    }
}
