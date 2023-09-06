package com.testproject.blogapp.repository;

import com.testproject.blogapp.model.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {
    List<CommentEntity> findByPostByPostId_Id(Integer postId);
    @Query("SELECT COUNT(ce) FROM CommentEntity ce WHERE ce.postByPostId.id = :postId")
    int countCommentsForPost(int postId);
    @Query("SELECT COUNT(ce) FROM CommentEntity ce")
    Integer countTotalComments();
}
