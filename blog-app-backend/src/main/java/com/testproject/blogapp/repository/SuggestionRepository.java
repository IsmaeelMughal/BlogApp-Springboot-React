package com.testproject.blogapp.repository;

import com.testproject.blogapp.model.SuggestionEntity;
import com.testproject.blogapp.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuggestionRepository extends JpaRepository<SuggestionEntity, Integer> {
    List<SuggestionEntity> findByUserByUserId(UserEntity userByUserId);

    List<SuggestionEntity> findByPostByPostId_UserByUserId_Id(Integer postByPostId_userByUserId_id);
}
