package com.testproject.blogapp.auth;

import com.testproject.blogapp.dto.ResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseDTO<String> register(
            @RequestBody RegisterRequest request
    )
    {
        return service.register(request);
    }

    @PostMapping("/verifyOtp")
    public ResponseDTO<String> verifyOtp(
            @RequestBody Map<String, String> otpRequestBody)
    {
        String email = otpRequestBody.get("email");
        String otp = otpRequestBody.get("otp");
        return service.verifyOtp(email, Integer.parseInt(otp));
    }

    @PostMapping("/authenticate")
    public ResponseDTO<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request, HttpServletRequest servletRequest
    )
    {
        return service.authenticate(request, servletRequest);
    }
}
