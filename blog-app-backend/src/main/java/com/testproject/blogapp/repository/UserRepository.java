package com.testproject.blogapp.repository;

import com.testproject.blogapp.model.Role;
import com.testproject.blogapp.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByEmail(String email);

    List<UserEntity> findByRole(Role role);

    @Query("SELECT COUNT(ue) FROM  UserEntity ue")
    Integer countOfAllUsers();
}
