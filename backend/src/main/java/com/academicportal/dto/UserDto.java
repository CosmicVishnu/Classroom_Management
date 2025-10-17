package com.academicportal.dto;

import com.academicportal.entity.User;

import java.time.LocalDate;

public class UserDto {
    private Long id;
    private String userId;
    private String name;
    private User.UserRole role;
    private String email;
    private String department;
    private User.UserStatus status;
    private LocalDate joinDate;
    private String avatar;

    // Exclude password for security

    public UserDto() {}

    public UserDto(Long id, String userId, String name, User.UserRole role, String email, String department, User.UserStatus status, LocalDate joinDate, String avatar) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.role = role;
        this.email = email;
        this.department = department;
        this.status = status;
        this.joinDate = joinDate;
        this.avatar = avatar;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public User.UserRole getRole() { return role; }
    public void setRole(User.UserRole role) { this.role = role; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public User.UserStatus getStatus() { return status; }
    public void setStatus(User.UserStatus status) { this.status = status; }

    public LocalDate getJoinDate() { return joinDate; }
    public void setJoinDate(LocalDate joinDate) { this.joinDate = joinDate; }

    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }
}

