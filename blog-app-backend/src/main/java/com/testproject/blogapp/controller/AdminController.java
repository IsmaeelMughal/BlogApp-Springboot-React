package com.testproject.blogapp.controller;

import com.testproject.blogapp.dto.*;
import com.testproject.blogapp.model.Role;
import com.testproject.blogapp.service.AdminService;
import com.testproject.blogapp.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/admin/getAllUsers")
    public ResponseDTO<List<UserEntityDTO>> getAllUsersByUserRole(@RequestHeader(Constants.AUTHORIZATION) String authHeader)
    {
        return adminService.getAllUsersByRole(authHeader, Role.ROLE_USER);
    }

    @GetMapping("/admin/getDetailsOfApp")
    public ResponseDTO<AppDetailsDTO> getCountDetailsOfApp(@RequestHeader(Constants.AUTHORIZATION) String authHeader)
    {
        return adminService.getCountDetails(authHeader);
    }

    @GetMapping("/admin/getAllModerators")
    public ResponseDTO<List<UserEntityDTO>> getAllModeratorsByRole(@RequestHeader(Constants.AUTHORIZATION) String authHeader)
    {
        return adminService.getAllUsersByRole(authHeader, Role.ROLE_MODERATOR);
    }
    @DeleteMapping("/admin/deleteUser/{userId}")
    public ResponseDTO<String> deleteUserById(@RequestHeader(Constants.AUTHORIZATION) String authHeader,
                                              @PathVariable("userId") Integer userId)
    {
        return adminService.deleteUserById(authHeader, userId);
    }
    @GetMapping("/admin/showAllPosts")
    public ResponseDTO<List<AdminPostEntityDTO>> showAllPosts(@RequestHeader(Constants.AUTHORIZATION) String authHeader)
    {
        return adminService.getAllPosts(authHeader);
    }
    @GetMapping("/admin/getAllSuggestions")
    public ResponseDTO<List<SuggestionEntityDTO>> getAllSuggestions(@RequestHeader(Constants.AUTHORIZATION) String authHeader)
    {
        return adminService.getAllSuggestions(authHeader);
    }
}
