package com.testproject.blogapp.controller;

import com.testproject.blogapp.config.JwtService;
import com.testproject.blogapp.dto.PostEntityDTO;
import com.testproject.blogapp.dto.ReportedPostDTO;
import com.testproject.blogapp.dto.ResponseDTO;
import com.testproject.blogapp.model.UserEntity;
import com.testproject.blogapp.repository.UserRepository;
import com.testproject.blogapp.service.PostLikeService;
import com.testproject.blogapp.service.PostReportService;
import com.testproject.blogapp.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class PostController {
    private final PostService postService;
    private final PostLikeService postLikeService;
    private final PostReportService postReportService;

    @PostMapping(value = "/user/addPost")
    public ResponseEntity<String> addPost(
            @RequestParam("image") MultipartFile file,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestHeader("Authorization") String authHeader
    ) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
        try {
            postService.addPost(file, title, content, authHeader);
            return ResponseEntity.ok("Post added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

    @GetMapping("/post/{id}")
    public ResponseDTO<PostEntityDTO> getPostById(@RequestHeader("Authorization") String authHeader,
                                                  @PathVariable("id") Integer postId)
    {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new ResponseDTO<>(null, HttpStatus.UNAUTHORIZED, "You are not authorized");
        }
        try {
            PostEntityDTO postEntity = postService.getPost(postId);
            return new ResponseDTO<>(postEntity, HttpStatus.OK, "List of Posts By this user");
        }
        catch (IllegalArgumentException illegalArgumentException)
        {
            return new ResponseDTO<>(null, HttpStatus.BAD_REQUEST, "Post Does not exists");
        }
        catch (Exception e) {
            return new ResponseDTO<>(null, HttpStatus.INTERNAL_SERVER_ERROR, "You are not authorized");
        }
    }

    @GetMapping("/post/allApprovedPosts")
    public ResponseDTO<List<PostEntityDTO>> getAllApprovedPosts(@RequestHeader("Authorization") String authHeader)
    {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new ResponseDTO<>(null, HttpStatus.UNAUTHORIZED, "You are not authorized");
        }
        try {
            List<PostEntityDTO> postEntityDTOS = postService.getAllApprovedPosts();
            return new ResponseDTO<>(postEntityDTOS, HttpStatus.OK, "List of Posts By this user");
        } catch (Exception e) {
            return new ResponseDTO<>(null, HttpStatus.INTERNAL_SERVER_ERROR, "You are not authorized");
        }
    }


    @PostMapping("/user/post/like/{postId}")
    public ResponseDTO<String> likePost(@RequestHeader("Authorization") String authHeader,
                                           @PathVariable("postId") Integer postId)
    {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new ResponseDTO<>(null, HttpStatus.UNAUTHORIZED, "You are not authorized");
        }

        return  postLikeService.postLikeByIds(authHeader, postId);
    }

    @PostMapping("/user/post/report/{postId}")
    public ResponseDTO<String> reportPost(@RequestHeader("Authorization") String authHeader,
                                        @PathVariable("postId") Integer postId)
    {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new ResponseDTO<>(null, HttpStatus.UNAUTHORIZED, "You are not authorized");
        }

        return  postReportService.postReportById(authHeader, postId);
    }


}
