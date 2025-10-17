package com.academicportal.dto;

public class SignUpRequest {

    private String name;

    private String email;

    private String password;

    private String role;

    private String department;

    private String userId; // Optional - will be auto-generated if not provided

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
}
