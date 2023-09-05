package com.testproject.blogapp.service;

import com.testproject.blogapp.dto.*;
import com.testproject.blogapp.model.CommentEntity;
import com.testproject.blogapp.model.CommentReplyEntity;
import com.testproject.blogapp.model.PostEntity;
import com.testproject.blogapp.model.ReportedCommentEntity;
import com.testproject.blogapp.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final UserService userService;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final CommentReplyRepository commentReplyRepository;
    private final ReportedCommentRepository reportedCommentRepository;
    private final CommentLikeRepository commentLikeRepository;

    public ResponseDTO<List<CommentEntityDTO>> getAllCommentsByPostId(String authHeader, Integer postId)
    {
        UserEntityDTO userEntityDTO = userService.getUserDetailsFromToken(authHeader);
        Optional<PostEntity> optionalPost = postRepository.findById(postId);
        if(optionalPost.isEmpty()) {
            return new ResponseDTO<>(userEntityDTO, null, HttpStatus.NOT_FOUND, "Post Does Not Exists");
        }
        List<CommentEntity> commentEntities = commentRepository.findByPostByPostId_Id(postId);
        List<CommentEntityDTO> commentEntityDTOList = commentEntities.stream().map(commentEntity -> {
            return new CommentEntityDTO(
                    commentEntity.getId(),
                    commentEntity.getContent(),
                    new UserEntityDTO(
                            commentEntity.getUserByUserId().getId(),
                            commentEntity.getUserByUserId().getName(),
                            commentEntity.getUserByUserId().getEmail(),
                            commentEntity.getUserByUserId().getRole().name()
                    ),
                    commentEntity.getCommentRepliesById().stream().map(
                            commentReplyEntity -> {
                                return new CommentReplyEntityDTO(
                                        commentReplyEntity.getId(),
                                        commentReplyEntity.getContent(),
                                        new UserEntityDTO(
                                                commentReplyEntity.getUserByUserId().getId(),
                                                commentReplyEntity.getUserByUserId().getName(),
                                                commentReplyEntity.getUserByUserId().getEmail(),
                                                commentReplyEntity.getUserByUserId().getRole().name()
                                        )
                                );
                            }
                    ).toList()
            );
        }).toList();
        return new ResponseDTO<>(userEntityDTO, commentEntityDTOList, HttpStatus.OK, "List of Comments On this Post!!!");
    }

    public ResponseDTO<CommentEntityDTO> addComment(String authHeader, Integer postId, String comment)
    {
        UserEntityDTO userEntityDTO = userService.getUserDetailsFromToken(authHeader);
        Optional<PostEntity> optionalPost = postRepository.findById(postId);
        if(optionalPost.isEmpty()) {
            return new ResponseDTO<>(userEntityDTO, null, HttpStatus.NOT_FOUND, "Post Does Not Exists");
        }
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setPostByPostId(optionalPost.get());
        commentEntity.setContent(comment);
        commentEntity.setUserByUserId(userService.getUserEntityFromId(userEntityDTO.getId()));
        commentRepository.save(commentEntity);

        CommentEntityDTO commentEntityDTO = new CommentEntityDTO(
                commentEntity.getId(),
                commentEntity.getContent(),
                userEntityDTO,
                new ArrayList<>()
        );
        return new ResponseDTO<>(userEntityDTO,commentEntityDTO, HttpStatus.OK, "Comment Added Successfully");
    }

    public ResponseDTO<CommentReplyEntityDTO> addReplyToComment(String authHeader, Integer commentId, String comment)
    {
        UserEntityDTO userEntityDTO = userService.getUserDetailsFromToken(authHeader);
        Optional<CommentEntity> optionalComment = commentRepository.findById(commentId);
        if(optionalComment.isEmpty()) {
            return new ResponseDTO<>(userEntityDTO, null, HttpStatus.NOT_FOUND, "Comment Does Not Exists");
        }

        CommentReplyEntity commentReplyEntity = new CommentReplyEntity();
        commentReplyEntity.setCommentByCommentId(optionalComment.get());
        commentReplyEntity.setContent(comment);
        commentReplyEntity.setUserByUserId(userRepository.findById(userEntityDTO.getId()).get());
        commentReplyEntity = commentReplyRepository.save(commentReplyEntity);

        CommentReplyEntityDTO commentReplyEntityDTO = new CommentReplyEntityDTO(
                commentReplyEntity.getId(),
                commentReplyEntity.getContent(),
                userEntityDTO
        );
        return new ResponseDTO<>(userEntityDTO,commentReplyEntityDTO, HttpStatus.OK, "Comment Replied Successfully");
    }


    public ResponseDTO<CommentEntityDTO> deleteComment(String authHeader, Integer commentId)
    {
        UserEntityDTO userEntityDTO = userService.getUserDetailsFromToken(authHeader);
        Optional<CommentEntity> optionalCommentEntity = commentRepository.findById(commentId);

        if(optionalCommentEntity.isPresent()){
            commentRepository.deleteById(commentId);
            CommentEntity commentEntity = optionalCommentEntity.get();
            CommentEntityDTO commentEntityDTO = new CommentEntityDTO(
                    commentEntity.getId(),
                    commentEntity.getContent(),
                    userEntityDTO,
                    new ArrayList<>()
            );
            return new ResponseDTO<>(userEntityDTO,commentEntityDTO, HttpStatus.OK, "Comment Deleted Successfully");
        }
        return new ResponseDTO<>(userEntityDTO,null, HttpStatus.OK, "Comment Does not Exists!!!");
    }

    public ResponseDTO<List<ReportedCommentDTO>> getAllReportedComments(String authHeader) {
        UserEntityDTO userEntityDTO = userService.getUserDetailsFromToken(authHeader);
        List<ReportedCommentEntity> reportedCommentEntities = reportedCommentRepository.findAll();
        List<ReportedCommentDTO> reportedCommentDTOList = reportedCommentEntities.stream().map(
                reportedCommentEntity -> {
                    return new ReportedCommentDTO(
                            reportedCommentEntity.getCommentByCommentId().getId(),
                            reportedCommentEntity.getPostByPostId().getId(),
                            reportedCommentEntity.getCommentByCommentId().getContent(),
                            commentLikeRepository.countLikesForComment(reportedCommentEntity.getCommentByCommentId().getId()),
                            reportedCommentRepository.countReportsForComment(reportedCommentEntity.getCommentByCommentId().getId()),
                            new UserEntityDTO(
                                    reportedCommentEntity.getUserByUserId().getId(),
                                    reportedCommentEntity.getUserByUserId().getName(),
                                    reportedCommentEntity.getUserByUserId().getEmail(),
                                    reportedCommentEntity.getUserByUserId().getRole().name()
                            )
                    );
                }
        ).toList();
        return new ResponseDTO<>(userEntityDTO, reportedCommentDTOList, HttpStatus.OK, "List of All Reported Comments!!!");
    }
}
