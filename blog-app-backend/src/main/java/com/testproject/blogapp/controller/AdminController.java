package com.testproject.blogapp.controller;

import com.testproject.blogapp.dto.ResponseDTO;
import com.testproject.blogapp.dto.UserEntityDTO;
import com.testproject.blogapp.model.Role;
import com.testproject.blogapp.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/admin/getAllUsers")
    public ResponseDTO<List<UserEntityDTO>> getAllUsersByUserRole(@RequestHeader("Authorization") String authHeader)
    {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new ResponseDTO<>(null, HttpStatus.UNAUTHORIZED, "You are not authorized");
        }
        return adminService.getAllUsersByRole(authHeader, Role.ROLE_USER);
    }

    @GetMapping("/admin/getAllModerators")
    public ResponseDTO<List<UserEntityDTO>> getAllModeratorsByRole(@RequestHeader("Authorization") String authHeader)
    {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new ResponseDTO<>(null, HttpStatus.UNAUTHORIZED, "You are not authorized");
        }
        return adminService.getAllUsersByRole(authHeader, Role.ROLE_MODERATOR);
    }

    @DeleteMapping("/admin/deleteUser/{userId}")
    public ResponseDTO<String> deleteUserById(@RequestHeader("Authorization") String authHeader,
                                              @PathVariable("userId") Integer userId)
    {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new ResponseDTO<>(null, HttpStatus.UNAUTHORIZED, "You are not authorized");
        }
        return adminService.deleteUserById(authHeader, userId);
    }
}
