package com.testproject.blogapp.service;

import com.testproject.blogapp.config.JwtService;
import com.testproject.blogapp.dto.ResponseDTO;
import com.testproject.blogapp.dto.UserEntityDTO;
import com.testproject.blogapp.model.*;
import com.testproject.blogapp.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CommentReportService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final UserService userService;
    private final CommentRepository commentRepository;
    private final ReportedCommentRepository reportedCommentRepository;
    public ResponseDTO<String> commentReport(String authHeader, Integer postId, Integer commentId)
    {
        String jwt = authHeader.substring(7);
        String userEmail = jwtService.extractUsername(jwt);
        Optional<UserEntity> optionalUser = userRepository.findByEmail(userEmail);
        Optional<PostEntity> optionalPost =postRepository.findById(postId);
        Optional<CommentEntity> optionalComment = commentRepository.findById(commentId);
        UserEntityDTO userEntityDTO = userService.getUserDetailsFromToken(authHeader);
        if(optionalPost.isEmpty())
        {
            return new ResponseDTO<>(userEntityDTO, null, HttpStatus.NOT_FOUND, "Post Does Not Exists");
        }
        if (optionalUser.isEmpty())
        {
            return new ResponseDTO<>(userEntityDTO, null, HttpStatus.NOT_FOUND, "User Does Not Exists");
        }
        if (optionalComment.isEmpty())
        {
            return new ResponseDTO<>(userEntityDTO, null, HttpStatus.NOT_FOUND, "Comment Does Not Exists");
        }

        Optional<ReportedCommentEntity> optionalReportedCommentEntity =
                reportedCommentRepository.findByPostByPostIdAndCommentByCommentIdAndUserByUserId(
                        optionalPost.get(), optionalComment.get(),optionalUser.get()
                );

        if(optionalReportedCommentEntity.isPresent())
        {
            return new ResponseDTO<>(userEntityDTO, null, HttpStatus.ALREADY_REPORTED, "You have already Reported This comment!!!");
        }

        ReportedCommentEntity reportedCommentEntity = new ReportedCommentEntity();
        reportedCommentEntity.setCommentByCommentId(optionalComment.get());
        reportedCommentEntity.setPostByPostId(optionalPost.get());
        reportedCommentEntity.setUserByUserId(optionalUser.get());

        reportedCommentRepository.save(reportedCommentEntity);
        return new ResponseDTO<>(userEntityDTO, null, HttpStatus.OK, "Comment Reported Successfully!!!");
    }
}
