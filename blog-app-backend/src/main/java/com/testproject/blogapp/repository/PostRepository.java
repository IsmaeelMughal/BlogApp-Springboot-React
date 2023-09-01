package com.testproject.blogapp.repository;

import com.testproject.blogapp.model.PostEntity;
import com.testproject.blogapp.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Integer> {
    List<PostEntity> findByUserByUserId_Id(Integer userId);

    List<PostEntity> findByStatus(Status status);

    @Override
    void deleteById(Integer integer);
}
