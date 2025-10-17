package com.academicportal.controller;

import com.academicportal.dto.LoginRequest;
import com.academicportal.dto.LoginResponse;
import com.academicportal.dto.SignUpRequest;
import com.academicportal.dto.UserDto;
import com.academicportal.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {
    
    private final AuthService authService;
    
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpRequest signUpRequest) {
        UserDto user = authService.signUp(signUpRequest);
        return ResponseEntity.ok(user);
    }
    
    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser() {
        UserDto user = authService.getCurrentUser();
        return ResponseEntity.ok(user);
    }
}





