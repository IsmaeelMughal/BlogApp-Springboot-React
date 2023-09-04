package com.testproject.blogapp.service;


import com.testproject.blogapp.config.JwtService;
import com.testproject.blogapp.dto.UserEntityDTO;
import com.testproject.blogapp.model.UserEntity;
import com.testproject.blogapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public UserEntity getUserEntityFromId(Integer id)
    {
        return userRepository.findById(id).orElseThrow();
    }
}
