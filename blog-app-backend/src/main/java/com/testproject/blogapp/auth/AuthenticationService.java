package com.testproject.blogapp.auth;

import com.testproject.blogapp.config.JwtService;
import com.testproject.blogapp.dto.ResponseDTO;
import com.testproject.blogapp.model.Role;
import com.testproject.blogapp.model.UserAccountStatus;
import com.testproject.blogapp.model.UserEntity;
import com.testproject.blogapp.repository.UserRepository;
import com.testproject.blogapp.service.MailService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final MailService mailService;

    private Integer generateRandomOtp()
    {
        // Define the length of the OTP
        int otpLength = 6;
        // Create a Random object
        Random random = new Random();
        // Generate the OTP
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < otpLength; i++) {
            otp.append(random.nextInt(10)); // Generate a random digit (0-9)
        }
        // Print the OTP
       return Integer.parseInt(String.valueOf(otp));
    }

    public ResponseDTO<String> register(RegisterRequest request) {
        Optional<UserEntity> optionalUserEntity = userRepository.findByEmail(request.getEmail());
        Integer randomOtp = generateRandomOtp();
        if (optionalUserEntity.isPresent())
        {
            UserEntity userEntity = optionalUserEntity.get();
            if(userEntity.getStatus() == UserAccountStatus.VERIFIED)
            {
                return new ResponseDTO<>(null, null, HttpStatus.BAD_REQUEST, "Email Already Exists!!!");
            }
            userEntity.setName(request.getName());
            userEntity.setRole(Role.valueOf(request.getRole()));
            userEntity.setEmail(request.getEmail());
            userEntity.setPassword(passwordEncoder.encode(request.getPassword()));
            userEntity.setStatus(UserAccountStatus.UNVERIFIED);
            userEntity.setOtp(randomOtp);
            // Get the current LocalDateTime
            LocalDateTime currentDateTime = LocalDateTime.now();
            // Add 15 minutes to the current LocalDateTime
            LocalDateTime expireTime = currentDateTime.plusMinutes(15);
            userEntity.setOtpExpiration(expireTime);

            if(!mailService.sendVerificationEmail(request.getEmail(), randomOtp))
            {
                return new ResponseDTO<>(null, null, HttpStatus.BAD_GATEWAY,"Failed To send Email!!!");
            }
            userRepository.save(userEntity);
            return new ResponseDTO<>(null, null, HttpStatus.OK, "A verification Email is sent on your Email!!!");
        }
        else {
            UserEntity userEntity = new UserEntity();
            userEntity.setName(request.getName());
            userEntity.setRole(Role.valueOf(request.getRole()));
            userEntity.setEmail(request.getEmail());
            userEntity.setPassword(passwordEncoder.encode(request.getPassword()));
            userEntity.setStatus(UserAccountStatus.UNVERIFIED);
            userEntity.setOtp(randomOtp);
            // Get the current LocalDateTime
            LocalDateTime currentDateTime = LocalDateTime.now();
            // Add 15 minutes to the current LocalDateTime
            LocalDateTime expireTime = currentDateTime.plusMinutes(15);
            userEntity.setOtpExpiration(expireTime);

            if(!mailService.sendVerificationEmail(request.getEmail(), randomOtp))
            {
                return new ResponseDTO<>(null, null, HttpStatus.BAD_GATEWAY,"Failed To send Email!!!");
            }
            userRepository.save(userEntity);
            return new ResponseDTO<>(null, null, HttpStatus.OK, "A verification Email is sent on your Email!!!");
        }
    }

    public ResponseDTO<AuthenticationResponse> authenticate(AuthenticationRequest request) {
        String email = request.getEmail();;
        String password = request.getPassword();

        Optional<UserEntity> optionalUserEntity = userRepository.findByEmail(email);
        if(optionalUserEntity.isEmpty())
        {
            return new ResponseDTO<>(null, null, HttpStatus.BAD_REQUEST, "Invalid Credentials!!!");
        }
        try {
            UserEntity userEntity = optionalUserEntity.get();
            if(userEntity.getStatus()==UserAccountStatus.UNVERIFIED)
            {
                return new ResponseDTO<>(null, null, HttpStatus.BAD_REQUEST, "Invalid Credentials!!!");
            }
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            var jwtToken = jwtService.generateToken(userEntity);
            return new ResponseDTO<>(null, new AuthenticationResponse(
                    jwtToken,
                    userEntity.getRole().name()
            ), HttpStatus.OK, "Authenticated Successfully!!!");
        }
        catch (Exception e)
        {
            return new ResponseDTO<>(null, null, HttpStatus.BAD_REQUEST, "Invalid Credentials!!!");
        }

    }

    public ResponseDTO<String> verifyOtp(String email, int otp) {
        Optional<UserEntity> optionalUserEntity = userRepository.findByEmail(email);
        if (optionalUserEntity.isEmpty())
        {
            return new ResponseDTO<>(null, null, HttpStatus.BAD_REQUEST, "Email Does Not Exists Please Register First!!!");
        }
        UserEntity user = optionalUserEntity.get();
        if(user.getOtp() == otp)
        {
            LocalDateTime currentDateTime = LocalDateTime.now();
            LocalDateTime otpExpiration = user.getOtpExpiration();
            boolean isExpired = currentDateTime.isAfter(otpExpiration);
            if(isExpired)
            {
                return new ResponseDTO<>(null, null, HttpStatus.BAD_REQUEST, "OTP expired Please register again!!!");
            }

            user.setStatus(UserAccountStatus.VERIFIED);
            userRepository.save(user);
            return new ResponseDTO<>(null, null, HttpStatus.OK, "Email Verified Successfully!!!");
        }
        else {
            return new ResponseDTO<>(null, null, HttpStatus.BAD_REQUEST, "Invalid OTP!!!");
        }
    }
}
