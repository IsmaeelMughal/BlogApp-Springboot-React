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

@Service
@RequiredArgsConstructor
public class AdminService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    public ResponseDTO<List<UserEntityDTO>> getAllUsersByRole(String authHeader, Role role)
    {
        String jwt = authHeader.substring(7);
        String userEmail = jwtService.extractUsername(jwt);
        UserEntity user = userRepository.findByEmail(userEmail).orElseThrow();
        if (user.getRole() != Role.ROLE_ADMIN)
        {
            return new ResponseDTO<>(null, HttpStatus.UNAUTHORIZED, "You are Not Authorized!!!!");
        }
        List<UserEntity> userEntityList = userRepository.findByRole(role);
        List<UserEntityDTO> userEntityDTOList = userEntityList.stream().map(
                userEntity -> {
                    return new UserEntityDTO(
                            userEntity.getId(),
                            userEntity.getName(),
                            userEntity.getEmail(),
                            userEntity.getRole().name()
                    );
                }
        ).toList();
        return new ResponseDTO<>(userEntityDTOList, HttpStatus.OK, "List of All Users!!!");
    }

    public ResponseDTO<String> deleteUserById(String authHeader, Integer id)
    {
        String jwt = authHeader.substring(7);
        String userEmail = jwtService.extractUsername(jwt);
        UserEntity user = userRepository.findByEmail(userEmail).orElseThrow();
        if (user.getRole() != Role.ROLE_ADMIN)
        {
            return new ResponseDTO<>(null, HttpStatus.UNAUTHORIZED, "You are Not Authorized!!!!");
        }

        Optional<UserEntity> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty())
        {
            return new ResponseDTO<>(null, HttpStatus.NOT_FOUND, "User Does Not Exist!!!");
        }
        userRepository.deleteById(id);
        return new ResponseDTO<>("User Deleted Successfully!!!", HttpStatus.OK,"User Deleted Successfully!!!");
    }
}
