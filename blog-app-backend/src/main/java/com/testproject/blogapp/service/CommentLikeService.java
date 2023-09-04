package com.testproject.blogapp.service;


import com.testproject.blogapp.config.JwtService;
import com.testproject.blogapp.dto.ResponseDTO;
import com.testproject.blogapp.dto.UserEntityDTO;
import com.testproject.blogapp.model.CommentEntity;
import com.testproject.blogapp.model.CommentLikeEntity;
import com.testproject.blogapp.model.PostEntity;
import com.testproject.blogapp.model.UserEntity;
import com.testproject.blogapp.repository.CommentLikeRepository;
import com.testproject.blogapp.repository.CommentRepository;
import com.testproject.blogapp.repository.PostRepository;
import com.testproject.blogapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentLikeService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final UserService userService;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;
    public ResponseDTO<String> commentLike(String authHeader, Integer postId, Integer commentId)
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

        Optional<CommentLikeEntity> optionalCommentLikeEntity =
                commentLikeRepository.findByPostByPostIdAndCommentByCommentIdAndUserByUserId(
                        optionalPost.get(), optionalComment.get(),optionalUser.get()
                );

        if(optionalCommentLikeEntity.isPresent())
        {
            return new ResponseDTO<>(userEntityDTO, null, HttpStatus.ALREADY_REPORTED, "You have already Liked This comment!!!");
        }

        CommentLikeEntity commentLikeEntity = new CommentLikeEntity();
        commentLikeEntity.setCommentByCommentId(optionalComment.get());
        commentLikeEntity.setPostByPostId(optionalPost.get());
        commentLikeEntity.setUserByUserId(optionalUser.get());

        commentLikeRepository.save(commentLikeEntity);
        return new ResponseDTO<>(userEntityDTO, null, HttpStatus.OK, "Comment Liked Successfully!!!");
    }
}
