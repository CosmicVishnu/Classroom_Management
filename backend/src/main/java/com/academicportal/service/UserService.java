package com.academicportal.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.academicportal.dto.UserDto;
import com.academicportal.entity.User;
import com.academicportal.jdbc.JdbcUserRepository;

@Service
public class UserService {

    private final JdbcUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(JdbcUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    public UserDto createUser(User user) {
        if (user.getJoinDate() == null) {
            user.setJoinDate(LocalDate.now());
        }
        User savedUser = userRepository.save(user);
        return convertToDto(savedUser);
    }
    
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public Optional<UserDto> getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::convertToDto);
    }
    
    public Optional<UserDto> getUserByUserId(String userId) {
        return userRepository.findByUserId(userId)
                .map(this::convertToDto);
    }
    
    public List<UserDto> getUsersByRole(User.UserRole role) {
        return userRepository.findByRole(role).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public List<UserDto> getStudents() {
        return getUsersByRole(User.UserRole.STUDENT);
    }
    
    public List<UserDto> getTeachers() {
        return getUsersByRole(User.UserRole.TEACHER);
    }
    
    public UserDto updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        
        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        user.setDepartment(userDetails.getDepartment());
        user.setStatus(userDetails.getStatus());
        user.setAvatar(userDetails.getAvatar());
        
        if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        }
        
        User updatedUser = userRepository.save(user);
        return convertToDto(updatedUser);
    }
    
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    
    public UserDto convertToDto(User user) {
        return new UserDto(
                user.getId(),
                user.getUserId(),
                user.getName(),
                user.getRole(),
                user.getEmail(),
                user.getDepartment(),
                user.getStatus(),
                user.getJoinDate(),
                user.getAvatar()
        );
    }
    
    public User convertToEntity(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setUserId(userDto.getUserId());
        user.setName(userDto.getName());
        user.setRole(userDto.getRole());
        user.setEmail(userDto.getEmail());
        user.setDepartment(userDto.getDepartment());
        user.setStatus(userDto.getStatus());
        user.setJoinDate(userDto.getJoinDate());
        user.setAvatar(userDto.getAvatar());
        return user;
    }
}
