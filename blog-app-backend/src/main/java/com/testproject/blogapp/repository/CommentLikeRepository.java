package com.testproject.blogapp.repository;

import com.testproject.blogapp.model.CommentEntity;
import com.testproject.blogapp.model.CommentLikeEntity;
import com.testproject.blogapp.model.PostEntity;
import com.testproject.blogapp.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLikeEntity, Integer> {
    Optional<CommentLikeEntity> findByPostByPostIdAndCommentByCommentIdAndUserByUserId(PostEntity postByPostId, CommentEntity commentByCommentId, UserEntity userByUserId);

    @Query("SELECT COUNT(cle) FROM CommentLikeEntity cle WHERE cle.commentByCommentId.id = :commentId")
    int countLikesForComment(int commentId);
}
