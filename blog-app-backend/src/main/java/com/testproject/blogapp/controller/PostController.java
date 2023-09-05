package com.testproject.blogapp.controller;

import com.testproject.blogapp.dto.PostEntityDTO;
import com.testproject.blogapp.dto.ResponseDTO;
import com.testproject.blogapp.dto.UserEntityDTO;
import com.testproject.blogapp.service.PostLikeService;
import com.testproject.blogapp.service.PostReportService;
import com.testproject.blogapp.service.PostService;
import com.testproject.blogapp.service.UserService;
import com.testproject.blogapp.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class PostController {
    private final PostService postService;
    private final PostLikeService postLikeService;
    private final PostReportService postReportService;
    private final UserService userService;

    @PostMapping(value = "/user/addPost")
    public ResponseEntity<String> addPost(
            @RequestParam("image") MultipartFile file,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestHeader(Constants.AUTHORIZATION) String authHeader
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

    @PatchMapping("/user/editPost/{postId}")
    public ResponseDTO<String> editPost(@PathVariable("postId") Integer postId,
                                        @RequestBody Map<String, String> postData,
                                        @RequestHeader(Constants.AUTHORIZATION) String authHeader)
    {
        String title = postData.get("title");
        String content = postData.get("content");
        return postService.editPost(authHeader, postId, title, content);
    }

    @GetMapping("/post/{id}")
    public ResponseDTO<PostEntityDTO> getPostById(@RequestHeader(Constants.AUTHORIZATION) String authHeader,
                                                  @PathVariable("id") Integer postId)
    {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new ResponseDTO<>(null, null, HttpStatus.UNAUTHORIZED, "You are not authorized");
        }
        UserEntityDTO userEntityDTO = userService.getUserDetailsFromToken(authHeader);
        try {
            PostEntityDTO postEntity = postService.getPost(postId);
            return new ResponseDTO<>(userEntityDTO, postEntity, HttpStatus.OK, "List of Posts By this user");
        }
        catch (IllegalArgumentException illegalArgumentException)
        {
            return new ResponseDTO<>(userEntityDTO,null, HttpStatus.BAD_REQUEST, "Post Does not exists");
        }
        catch (Exception e) {
            return new ResponseDTO<>(userEntityDTO,null, HttpStatus.INTERNAL_SERVER_ERROR, "You are not authorized");
        }
    }

    @GetMapping("/post/allApprovedPosts")
    public ResponseDTO<List<PostEntityDTO>> getAllApprovedPosts(@RequestHeader(Constants.AUTHORIZATION) String authHeader)
    {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new ResponseDTO<>(null, null, HttpStatus.UNAUTHORIZED, "You are not authorized");
        }
        UserEntityDTO userEntityDTO = userService.getUserDetailsFromToken(authHeader);
        try {
            List<PostEntityDTO> postEntityDTOS = postService.getAllApprovedPosts();
            return new ResponseDTO<>(userEntityDTO, postEntityDTOS, HttpStatus.OK, "List of Posts By this user");
        } catch (Exception e) {
            return new ResponseDTO<>(userEntityDTO, null, HttpStatus.INTERNAL_SERVER_ERROR, "You are not authorized");
        }
    }


    @PostMapping("/user/post/like/{postId}")
    public ResponseDTO<String> likePost(@RequestHeader(Constants.AUTHORIZATION) String authHeader,
                                           @PathVariable("postId") Integer postId)
    {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new ResponseDTO<>(null, null, HttpStatus.UNAUTHORIZED, "You are not authorized");
        }

        return  postLikeService.postLikeByIds(authHeader, postId);
    }

    @PostMapping("/user/post/report/{postId}")
    public ResponseDTO<String> reportPost(@RequestHeader(Constants.AUTHORIZATION) String authHeader,
                                        @PathVariable("postId") Integer postId)
    {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new ResponseDTO<>(null, null, HttpStatus.UNAUTHORIZED, "You are not authorized");
        }

        return  postReportService.postReportById(authHeader, postId);
    }

    @DeleteMapping("/user/post/delete/{id}")
    public ResponseDTO<String> deletePostById(@RequestHeader(Constants.AUTHORIZATION) String authHeader,
                                              @PathVariable("id") Integer id) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new ResponseDTO<>(null, null, HttpStatus.UNAUTHORIZED, "You are not authorized");
        }
        UserEntityDTO userEntityDTO = userService.getUserDetailsFromToken(authHeader);

        try {
            if(postService.deletePostById(id))
            {
                return new ResponseDTO<>(userEntityDTO,"Post Deleted Successfully!!!", HttpStatus.OK, "Successfully to Delete Post!!!");
            }
            return new ResponseDTO<>(userEntityDTO, "Failed to Delete Post!!!", HttpStatus.NOT_FOUND, "Failed to Delete Post!!!");
        } catch (Exception e) {
            return new ResponseDTO<>(userEntityDTO, null, HttpStatus.INTERNAL_SERVER_ERROR, "Failed to Delete Status!!!");
        }
    }

}
