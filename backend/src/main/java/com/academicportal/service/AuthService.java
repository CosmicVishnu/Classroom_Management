package com.academicportal.service;

import java.time.LocalDate;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.academicportal.dto.LoginRequest;
import com.academicportal.dto.LoginResponse;
import com.academicportal.dto.SignUpRequest;
import com.academicportal.dto.UserDto;
import com.academicportal.entity.User;
import com.academicportal.jdbc.JdbcUserRepository;
import com.academicportal.security.JwtTokenProvider;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final JdbcUserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider, JdbcUserRepository userRepository, UserService userService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.userRepository = userRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }
    
    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUserId(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.generateToken(authentication);

        User user = userRepository.findByUserId(loginRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserDto userDto = userService.convertToDto(user);

        return new LoginResponse(token, "Bearer", userDto);
    }
    
    public UserDto getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return userService.convertToDto(user);
    }

    public User getCurrentUserEntity() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    
    public UserDto signUp(SignUpRequest signUpRequest) {
        // Temporarily skip email check for testing
        // if (userRepository.existsByEmail(signUpRequest.getEmail())) {
        //     throw new RuntimeException("Email already exists");
        // }
        
        // Convert role to enum
        User.UserRole userRole = User.UserRole.valueOf(signUpRequest.getRole().toUpperCase());

        // Generate user ID if not provided
        String userId = signUpRequest.getUserId();
        if (userId == null || userId.trim().isEmpty()) {
            userId = generateUserId(userRole);
        }

        // Check if user ID already exists
        if (userRepository.existsByUserId(userId)) {
            throw new RuntimeException("User ID already exists");
        }

        // Create new user
        User user = new User();
        user.setUserId(userId);
        user.setName(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setRole(userRole);
        user.setDepartment(signUpRequest.getDepartment());
        user.setStatus(User.UserStatus.ACTIVE);
        user.setJoinDate(LocalDate.now());
        
        // Save user to database
        User savedUser = userRepository.save(user);
        
        return userService.convertToDto(savedUser);
    }
    
    private String generateUserId(User.UserRole role) {
        String prefix = switch (role) {
            case STUDENT -> "S";
            case TEACHER -> "T";
            case ADMIN -> "A";
        };
        
        // Find the next available ID
        int nextNumber = 1;
        String userId;
        do {
            userId = prefix + String.format("%03d", nextNumber);
            nextNumber++;
        } while (userRepository.existsByUserId(userId));
        
        return userId;
    }
}
