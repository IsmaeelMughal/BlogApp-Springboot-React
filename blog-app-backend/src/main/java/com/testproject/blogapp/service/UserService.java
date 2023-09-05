package com.testproject.blogapp.service;


import com.testproject.blogapp.auth.AuthenticationService;
import com.testproject.blogapp.config.JwtService;
import com.testproject.blogapp.dto.ResponseDTO;
import com.testproject.blogapp.dto.UserEntityDTO;
import com.testproject.blogapp.model.UserEntity;
import com.testproject.blogapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final AuthenticationService authenticationService;

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

    public ResponseDTO<String> updateUsername(String authHeader, String newUsername) {
        UserEntityDTO userEntityDTO = getUserDetailsFromToken(authHeader);
        if(newUsername.equals(userEntityDTO.getName()))
        {
            return new ResponseDTO<>(userEntityDTO, "Username cannot be Same!!!", HttpStatus.BAD_REQUEST, "Username cannot be Same!!!");
        }
        UserEntity userEntity = userRepository.findById(userEntityDTO.getId()).orElseThrow();
        userEntity.setName(newUsername);
        userRepository.save(userEntity);

        return new ResponseDTO<>(userEntityDTO, "Username Updated Successfully!!!", HttpStatus.OK, "Username Updated Successfully!!!");
    }

    public ResponseDTO<String> updatePassword(String authHeader, String newPassword) {
        UserEntityDTO userEntityDTO = getUserDetailsFromToken(authHeader);

        UserEntity userEntity = userRepository.findById(userEntityDTO.getId()).orElseThrow();
        userEntity.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(userEntity);

        return new ResponseDTO<>(userEntityDTO, "Password Updated Successfully!!!", HttpStatus.OK, "Password Updated Successfully!!!");
    }

    public ResponseDTO<String> sendOtpForEmailVerification(String authHeader, String newEmail) {
        UserEntityDTO userEntityDTO = getUserDetailsFromToken(authHeader);

        Optional<UserEntity> optional = userRepository.findByEmail(newEmail);
        if (optional.isPresent())
        {
            return new ResponseDTO<>(userEntityDTO, null, HttpStatus.BAD_REQUEST, "Email Already Exists!!!");
        }

        Integer randomOtp = authenticationService.generateRandomOtp();
        Optional<UserEntity> optionalUserEntity = userRepository.findById(userEntityDTO.getId());
        if(optionalUserEntity.isEmpty())
        {
            return new ResponseDTO<>(userEntityDTO, null, HttpStatus.BAD_REQUEST, "User Does Not Exists!!!");
        }
        if(mailService.sendVerificationEmail(newEmail, randomOtp))
        {
            UserEntity userEntity = optionalUserEntity.get();
            userEntity.setOtp(randomOtp);
            LocalDateTime currentDateTime = LocalDateTime.now();
            LocalDateTime expireTime = currentDateTime.plusMinutes(15);
            userEntity.setOtpExpiration(expireTime);
            userRepository.save(userEntity);
            return new ResponseDTO<>(userEntityDTO, "An Otp is Sent On your Email!!!", HttpStatus.OK, "An Otp is Sent On your Email!!!");
        }
        else {
            return new ResponseDTO<>(userEntityDTO, "Failed to send OTP!!!", HttpStatus.OK, "Failed to send OTP!!!");
        }
    }

    public ResponseDTO<String> validateOtpForEmailUpdate(String authHeader, String newEmail, Integer otp) {
        UserEntityDTO userEntityDTO = getUserDetailsFromToken(authHeader);
        ResponseDTO<String> otpVerificationResponse = authenticationService.verifyOtp(userEntityDTO.getEmail(), otp);
        if(otpVerificationResponse.getStatus() == HttpStatus.OK)
        {
            Optional<UserEntity> userEntity = userRepository.findById(userEntityDTO.getId());
            if ((userEntity.isEmpty()))
            {
                return new ResponseDTO<>(userEntityDTO, null, HttpStatus.BAD_REQUEST, "User Does Not Exists!!!");
            }
            UserEntity user = userEntity.get();
            user.setEmail(newEmail);
            userRepository.save(user);
            return new ResponseDTO<>(null, "Email updated successfully!!!", HttpStatus.OK, "Email updated successfully!!!");

        }
        return otpVerificationResponse;
    }
}
