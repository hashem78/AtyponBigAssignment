package com.hashem.spring.controllers;

import com.hashem.p1.RoleDao;
import com.hashem.p1.models.Role;
import com.hashem.spring.dtos.PatchUserRoleDto;
import com.hashem.spring.dtos.UpdateRoleDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/roles")
public class RolesController {

    @GetMapping("/getRoles")
    public ResponseEntity<Set<Role>> getRoles() {
        try (var roleDao = new RoleDao()) {
            return ResponseEntity.ok(roleDao.getAllRoles());
        } catch (Exception e) {
            return ResponseEntity.ok(new HashSet<>());
        }
    }

    @PutMapping("/createRole")
    public ResponseEntity<Integer> createRole(@RequestBody String name) {
        try (var roleDao = new RoleDao()) {
            return ResponseEntity.ok(roleDao.createRole(name));
        } catch (Exception e) {
            return ResponseEntity.ok(-1);
        }
    }

    @PatchMapping("/updateRole")
    public ResponseEntity<Boolean> updateRole(@RequestBody UpdateRoleDto dto) {
        try (var roleDao = new RoleDao()) {
            return ResponseEntity.ok(roleDao.updateRole(dto.getId(), dto.getName()));
        } catch (Exception e) {
            return ResponseEntity.ok(false);
        }
    }

    @PatchMapping("/addRoleToUser")
    public ResponseEntity<Boolean> addRoleToUser(@RequestBody PatchUserRoleDto dto) {
        try (var roleDao = new RoleDao()) {
            return ResponseEntity.ok(roleDao.addRoleToUser(dto.getUserId(),dto.getRoleId()));
        } catch (Exception e) {
            return ResponseEntity.ok(false);
        }
    }

    @PatchMapping("/removeRoleFromUser")
    public ResponseEntity<Boolean> removeRoleFromUser(@RequestBody PatchUserRoleDto dto) {
        try (var roleDao = new RoleDao()) {
            return ResponseEntity.ok(roleDao.removeRoleFromUser(dto.getUserId(),dto.getRoleId()));
        } catch (Exception e) {
            return ResponseEntity.ok(false);
        }
    }

    @DeleteMapping("/deleteRole")
    public ResponseEntity<Boolean> updateRole(@RequestBody int roleId) {
        try (var roleDao = new RoleDao()) {
            return ResponseEntity.ok(roleDao.deleteRole(roleId));
        } catch (Exception e) {
            return ResponseEntity.ok(false);
        }
    }
}
