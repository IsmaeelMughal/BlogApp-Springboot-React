package com.testproject.blogapp.service;


import com.testproject.blogapp.config.JwtService;
import com.testproject.blogapp.dto.ResponseDTO;
import com.testproject.blogapp.dto.UserEntityDTO;
import com.testproject.blogapp.model.Role;
import com.testproject.blogapp.model.UserEntity;
import com.testproject.blogapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public UserEntityDTO getUserDetailsFromToken(String authHeader)
    {
        String jwt = authHeader.substring(7);
        String userEmail = jwtService.extractUsername(jwt);
        UserEntity user = userRepository.findByEmail(userEmail).orElseThrow();
        UserEntityDTO userEntityDTO = new UserEntityDTO();
        userEntityDTO.setId(user.getId());
        userEntityDTO.setName(user.getName());
        userEntityDTO.setEmail(user.getEmail());
        userEntityDTO.setRole(user.getRole().name());
        return  userEntityDTO;
    }

    public boolean userExistsById(Integer userId)
    {
        Optional<UserEntity> optionalUser =userRepository.findById(userId);
        return optionalUser.isPresent();
    }
}
