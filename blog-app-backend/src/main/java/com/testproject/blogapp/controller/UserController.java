package com.testproject.blogapp.controller;

import com.testproject.blogapp.dto.PostEntityDTO;
import com.testproject.blogapp.dto.ResponseDTO;
import com.testproject.blogapp.dto.UserEntityDTO;
import com.testproject.blogapp.service.MailService;
import com.testproject.blogapp.service.PostService;
import com.testproject.blogapp.service.UserService;
import com.testproject.blogapp.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final PostService postService;
    @GetMapping("/user/getDetails")
    public ResponseDTO<UserEntityDTO> getUserDetailsFromToken(@RequestHeader(Constants.AUTHORIZATION) String authHeader)
    {
        UserEntityDTO userEntityDTO = userService.getUserDetailsFromToken(authHeader);
        try {
            return new ResponseDTO<>(userEntityDTO, userEntityDTO, HttpStatus.OK, "User Details!!!");
        } catch (Exception e) {
            return new ResponseDTO<>(userEntityDTO, null, HttpStatus.INTERNAL_SERVER_ERROR, "You are not authorized");
        }
    }


    @GetMapping("/user/posts")
    public ResponseDTO<List<PostEntityDTO>> getUserSpecificPosts(@RequestHeader(Constants.AUTHORIZATION) String authHeader) {
        UserEntityDTO userEntityDTO = userService.getUserDetailsFromToken(authHeader);
        try {
            List<PostEntityDTO> postEntityDTOS = postService.getUserPosts(authHeader);
            return new ResponseDTO<>(userEntityDTO, postEntityDTOS, HttpStatus.OK, "List of Posts By this user");
        } catch (Exception e) {
            return new ResponseDTO<>(userEntityDTO, null, HttpStatus.INTERNAL_SERVER_ERROR, "You are not authorized");
        }
    }

    @PostMapping("/user/updateName")
    public ResponseDTO<String> updateUsername(@RequestHeader(Constants.AUTHORIZATION) String authHeader,
                                              @RequestBody Map<String, String> updateData) {
        String newUsername = updateData.get("username");
        return userService.updateUsername(authHeader, newUsername);
    }

    @PostMapping("/user/updatePassword")
    public ResponseDTO<String> updatePassword(@RequestHeader(Constants.AUTHORIZATION) String authHeader,
                                              @RequestBody Map<String, String> updateData) {
        String newUsername = updateData.get("password");
        return userService.updatePassword(authHeader, newUsername);
    }

    @PostMapping("/user/sendOtp")
    public ResponseDTO<String> sendOtpToEmail(@RequestHeader(Constants.AUTHORIZATION) String authHeader,
                                              @RequestBody Map<String, String> otpData) {
        String newEmail = otpData.get("email");
        return userService.sendOtpForEmailVerification(authHeader, newEmail);
    }

    @PostMapping("/user/validateOtpForEmailUpdate")
    public ResponseDTO<String> validateOtpForEmailUpdate(@RequestHeader(Constants.AUTHORIZATION) String authHeader,
                                              @RequestBody Map<String, String> otpData) {
        String newEmail = otpData.get("email");
        Integer otp = Integer.parseInt(otpData.get("otp"));
        return userService.validateOtpForEmailUpdate(authHeader, newEmail, otp);
    }
}
