package com.testproject.blogapp.controller;


import com.testproject.blogapp.dto.CommentEntityDTO;
import com.testproject.blogapp.dto.CommentReplyEntityDTO;
import com.testproject.blogapp.dto.ReportedCommentDTO;
import com.testproject.blogapp.dto.ResponseDTO;
import com.testproject.blogapp.service.CommentLikeService;
import com.testproject.blogapp.service.CommentReportService;
import com.testproject.blogapp.service.CommentService;
import com.testproject.blogapp.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final CommentLikeService commentLikeService;
    private final CommentReportService commentReportService;

    @GetMapping("/post/comments/{postId}")
    public ResponseDTO<List<CommentEntityDTO>> getPostCommentsByPostId(
            @RequestHeader(Constants.AUTHORIZATION) String authHeader,
            @PathVariable("postId") Integer postId)
    {
        return commentService.getAllCommentsByPostId(authHeader, postId);
    }

    @PostMapping("/post/addComment")
    public ResponseDTO<CommentEntityDTO> addCommentByPostId(
            @RequestHeader(Constants.AUTHORIZATION) String authHeader,
            @RequestBody Map<String, String> commentData
    ) {
        String postId = commentData.get("postId");
        String content = commentData.get("content");

        return commentService.addComment(authHeader, Integer.parseInt(postId), content);
    }
    @PostMapping("/post/comment/addReply")
    public ResponseDTO<CommentReplyEntityDTO> addReplyToComment(
            @RequestHeader(Constants.AUTHORIZATION) String authHeader,
            @RequestBody Map<String, String> commentData
    ) {
        String commentId = commentData.get("commentId");
        String content = commentData.get("content");

        return commentService.addReplyToComment(authHeader, Integer.parseInt(commentId), content);
    }

    @PostMapping("/post/comment/like")
    public ResponseDTO<String> likeComment(
            @RequestHeader(Constants.AUTHORIZATION) String authHeader,
            @RequestBody Map<String, String> commentData
    ) {
        String postId = commentData.get("postId");
        String commentId = commentData.get("commentId");

        return commentLikeService.commentLike(authHeader, Integer.parseInt(postId), Integer.parseInt(commentId));
    }

    @PostMapping("/post/comment/report")
    public ResponseDTO<String> reportComment(
            @RequestHeader(Constants.AUTHORIZATION) String authHeader,
            @RequestBody Map<String, String> commentData
    ) {
        String postId = commentData.get("postId");
        String commentId = commentData.get("commentId");

        return commentReportService.commentReport(authHeader, Integer.parseInt(postId), Integer.parseInt(commentId));
    }

    @DeleteMapping("/post/comment/delete/{commentId}")
    public ResponseDTO<CommentEntityDTO> reportComment(
            @RequestHeader(Constants.AUTHORIZATION) String authHeader,
            @PathVariable("commentId") Integer commentId
    ) {
        return commentService.deleteComment(authHeader, commentId);
    }

    @GetMapping("/admin/comment/reportedComments")
    public ResponseDTO<List<ReportedCommentDTO>> getAllReportedComments(@RequestHeader(Constants.AUTHORIZATION) String authHeader)
    {
        return commentService.getAllReportedComments(authHeader);
    }
}
