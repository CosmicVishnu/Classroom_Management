package com.academicportal.repository;

import com.academicportal.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    
    List<User> findAll();
    
    Optional<User> findById(Long id);
    
    Optional<User> findByUserId(String userId);
    
    Optional<User> findByEmail(String email);
    
    List<User> findByRole(User.UserRole role);
    
    List<User> findByStatus(User.UserStatus status);
    
    List<User> findByDepartment(String department);
    
    List<User> findByRoleAndStatus(User.UserRole role, User.UserStatus status);
    
    User save(User user);
    
    User update(User user);
    
    void deleteById(Long id);
    
    boolean existsByUserId(String userId);
    
    boolean existsByEmail(String email);
}

