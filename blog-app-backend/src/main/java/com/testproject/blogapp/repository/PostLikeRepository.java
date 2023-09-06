package com.testproject.blogapp.repository;

import com.testproject.blogapp.model.PostEntity;
import com.testproject.blogapp.model.PostLikeEntity;
import com.testproject.blogapp.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLikeEntity, Integer> {

    Optional<PostLikeEntity> findByPostByPostIdAndUserByUserId(PostEntity post, UserEntity user);


    @Query("SELECT COUNT(pl) FROM PostLikeEntity pl WHERE pl.postByPostId.id = :postId")
    int countLikesForPost(int postId);

    @Query("SELECT COUNT(pl) FROM PostLikeEntity pl ")
    int countAllLikesForPost();
}
