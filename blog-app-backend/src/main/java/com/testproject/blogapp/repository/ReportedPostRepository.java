package com.testproject.blogapp.repository;

import com.testproject.blogapp.model.PostEntity;
import com.testproject.blogapp.model.ReportedPostEntity;
import com.testproject.blogapp.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReportedPostRepository extends JpaRepository<ReportedPostEntity, Integer> {

    Optional<ReportedPostEntity> findByPostByPostIdAndUserByUserId(PostEntity post, UserEntity user);

    @Query("SELECT DISTINCT rp.postByPostId FROM ReportedPostEntity rp")
    List<PostEntity> findAllUniqueReportedPosts();

    @Query("SELECT COUNT(rp) FROM ReportedPostEntity rp WHERE rp.postByPostId.id = :postId")
    int countReportsForPost(int postId);
}
