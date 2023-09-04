package com.testproject.blogapp.controller;

import com.testproject.blogapp.dto.PostEntityDTO;
import com.testproject.blogapp.dto.ResponseDTO;
import com.testproject.blogapp.dto.UserEntityDTO;
import com.testproject.blogapp.service.PostService;
import com.testproject.blogapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final PostService postService;
    @GetMapping("/user/getDetails")
    public ResponseDTO<UserEntityDTO> getUserDetailsFromToken(@RequestHeader("Authorization") String authHeader)
    {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new ResponseDTO<>(null, null, HttpStatus.UNAUTHORIZED, "You are not authorized");
        }
        UserEntityDTO userEntityDTO = userService.getUserDetailsFromToken(authHeader);
        try {
            return new ResponseDTO<>(userEntityDTO, userEntityDTO, HttpStatus.OK, "List of Posts By this user");
        } catch (Exception e) {
            return new ResponseDTO<>(userEntityDTO, null, HttpStatus.INTERNAL_SERVER_ERROR, "You are not authorized");
        }
    }


    @GetMapping("/user/posts")
    public ResponseDTO<List<PostEntityDTO>> getUserSpecificPosts(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new ResponseDTO<>(null, null, HttpStatus.UNAUTHORIZED, "You are not authorized");
        }
        UserEntityDTO userEntityDTO = userService.getUserDetailsFromToken(authHeader);

        try {
            List<PostEntityDTO> postEntityDTOS = postService.getUserPosts(authHeader);
            return new ResponseDTO<>(userEntityDTO, postEntityDTOS, HttpStatus.OK, "List of Posts By this user");
        } catch (Exception e) {
            return new ResponseDTO<>(userEntityDTO, null, HttpStatus.INTERNAL_SERVER_ERROR, "You are not authorized");
        }
    }
}
