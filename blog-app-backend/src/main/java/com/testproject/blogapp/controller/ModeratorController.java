package com.testproject.blogapp.controller;

import com.testproject.blogapp.dto.PostEntityDTO;
import com.testproject.blogapp.dto.ReportedPostDTO;
import com.testproject.blogapp.dto.ResponseDTO;
import com.testproject.blogapp.service.PostReportService;
import com.testproject.blogapp.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ModeratorController {
    private final PostService postService;
    private final PostReportService postReportService;
    @GetMapping("/moderator/posts/unapproved")
    public ResponseDTO<List<PostEntityDTO>> getAllUnapprovedPosts(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new ResponseDTO<>(null, HttpStatus.UNAUTHORIZED, "You are not authorized");
        }
        try {
            List<PostEntityDTO> postEntityDTOS = postService.getAllUnapprovedPosts();
            return new ResponseDTO<>(postEntityDTOS, HttpStatus.OK, "List of All UnApproved Posts");
        } catch (Exception e) {
            return new ResponseDTO<>(null, HttpStatus.INTERNAL_SERVER_ERROR, "You are not authorized");
        }
    }

    @PostMapping("/moderator/post/approve/{id}")
    public ResponseDTO<String> approvePostById(@RequestHeader("Authorization") String authHeader,
                                                     @PathVariable("id") Integer id) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new ResponseDTO<>(null, HttpStatus.UNAUTHORIZED, "You are not authorized");
        }
        try {
            if(postService.approvePostById(id))
            {
                return new ResponseDTO<>("Post Approved Successfully!!!", HttpStatus.OK, "Successfully to Updated Status!!!");
            }
            return new ResponseDTO<>("Failed to Approve Post!!!", HttpStatus.NOT_FOUND, "Failed to Update Status!!!");
        } catch (Exception e) {
            return new ResponseDTO<>(null, HttpStatus.INTERNAL_SERVER_ERROR, "Failed to Update Status!!!");
        }
    }
    @DeleteMapping("/moderator/post/delete/{id}")
    public ResponseDTO<String> deletePostById(@RequestHeader("Authorization") String authHeader,
                                               @PathVariable("id") Integer id) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new ResponseDTO<>(null, HttpStatus.UNAUTHORIZED, "You are not authorized");
        }
        try {
            if(postService.deletePostById(id))
            {
                return new ResponseDTO<>("Post Deleted Successfully!!!", HttpStatus.OK, "Successfully to Delete Status!!!");
            }
            return new ResponseDTO<>("Failed to Delete Post!!!", HttpStatus.NOT_FOUND, "Failed to Delete Status!!!");
        } catch (Exception e) {
            return new ResponseDTO<>(null, HttpStatus.INTERNAL_SERVER_ERROR, "Failed to Delete Status!!!");
        }
    }

    @GetMapping("/moderator/post/getReportedPosts")
    public ResponseDTO<List<ReportedPostDTO>> getAllReportedPosts(@RequestHeader("Authorization") String authHeader)
    {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new ResponseDTO<>(null, HttpStatus.UNAUTHORIZED, "You are not authorized");
        }
        return  postReportService.getAllReportedPosts();
    }
}
