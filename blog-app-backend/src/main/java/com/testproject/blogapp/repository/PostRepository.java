package com.testproject.blogapp.repository;

import com.testproject.blogapp.model.PostEntity;
import com.testproject.blogapp.model.PostStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Integer> {
    List<PostEntity> findByUserByUserId_Id(Integer userId);

    List<PostEntity> findByPostStatus(PostStatus postStatus);

    @Override
    void deleteById(Integer integer);

    @Query("SELECT COUNT(ue) FROM  PostEntity ue")
    Integer countOfAllPosts();
}
