package com.testproject.blogapp.repository;

import com.testproject.blogapp.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReportedCommentRepository extends JpaRepository<ReportedCommentEntity, Integer> {
    Optional<ReportedCommentEntity> findByPostByPostIdAndCommentByCommentIdAndUserByUserId(PostEntity postByPostId, CommentEntity commentByCommentId, UserEntity userByUserId);
    @Query("SELECT COUNT(rce) FROM ReportedCommentEntity rce WHERE rce.commentByCommentId.id = :commentId")
    int countReportsForComment(int commentId);
}
