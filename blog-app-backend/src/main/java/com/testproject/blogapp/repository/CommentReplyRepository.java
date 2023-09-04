package com.testproject.blogapp.repository;

import com.testproject.blogapp.model.CommentReplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentReplyRepository extends JpaRepository<CommentReplyEntity, Integer> {
}
