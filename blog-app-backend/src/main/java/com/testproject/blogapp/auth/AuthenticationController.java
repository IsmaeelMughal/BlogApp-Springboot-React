package com.testproject.blogapp.auth;

import com.testproject.blogapp.dto.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        System.out.println("========");
        return service.verifyOtp(email, Integer.parseInt(otp));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    )
    {
        return ResponseEntity.ok(service.authenticate(request));
    }

}
