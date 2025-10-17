package com.academicportal.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.academicportal.dto.AttendanceDto;
import com.academicportal.dto.UserDto;
import com.academicportal.entity.User;
import com.academicportal.service.AttendanceService;
import com.academicportal.service.AuthService;

@RestController
@RequestMapping("/attendance")
@CrossOrigin(origins = "*")
public class AttendanceController {

    private final AttendanceService attendanceService;
    private final AuthService authService;

    public AttendanceController(AttendanceService attendanceService, AuthService authService) {
        this.attendanceService = attendanceService;
        this.authService = authService;
    }
    
    @PostMapping
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<AttendanceDto> markAttendance(@RequestBody AttendanceDto attendanceDto) {
        AttendanceDto savedAttendance = attendanceService.markAttendance(attendanceDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAttendance);
    }
    
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<AttendanceDto>> getAttendanceByStudent(@PathVariable Long studentId) {
        UserDto currentUser = authService.getCurrentUser();
        
        // Students can only view their own attendance, teachers and admins can view any student's attendance
        if (currentUser.getRole().equals(User.UserRole.STUDENT) && !currentUser.getId().equals(studentId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        List<AttendanceDto> attendance = attendanceService.getAttendanceByStudent(studentId);
        return ResponseEntity.ok(attendance);
    }
    
    @GetMapping("/student/{studentId}/subject/{subjectId}")
    public ResponseEntity<List<AttendanceDto>> getAttendanceByStudentAndSubject(
            @PathVariable Long studentId, 
            @PathVariable Long subjectId) {
        
        UserDto currentUser = authService.getCurrentUser();
        
        // Students can only view their own attendance, teachers and admins can view any student's attendance
        if (currentUser.getRole().equals(User.UserRole.STUDENT) && !currentUser.getId().equals(studentId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        List<AttendanceDto> attendance = attendanceService.getAttendanceByStudentAndSubject(studentId, subjectId);
        return ResponseEntity.ok(attendance);
    }
    
    @GetMapping("/student/{studentId}/date-range")
    public ResponseEntity<List<AttendanceDto>> getAttendanceByStudentAndDateRange(
            @PathVariable Long studentId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        
        UserDto currentUser = authService.getCurrentUser();
        
        // Students can only view their own attendance, teachers and admins can view any student's attendance
        if (currentUser.getRole().equals(User.UserRole.STUDENT) && !currentUser.getId().equals(studentId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        List<AttendanceDto> attendance = attendanceService.getAttendanceByStudentAndDateRange(studentId, startDate, endDate);
        return ResponseEntity.ok(attendance);
    }
    
    @GetMapping("/teacher/{teacherId}")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<List<AttendanceDto>> getAttendanceByTeacher(@PathVariable Long teacherId) {
        List<AttendanceDto> attendance = attendanceService.getAttendanceByTeacher(teacherId);
        return ResponseEntity.ok(attendance);
    }
    
    @GetMapping("/percentage/student/{studentId}/subject/{subjectId}")
    public ResponseEntity<Double> getAttendancePercentage(
            @PathVariable Long studentId, 
            @PathVariable Long subjectId) {
        
        UserDto currentUser = authService.getCurrentUser();
        
        // Students can only view their own attendance percentage, teachers and admins can view any student's
        if (currentUser.getRole().equals(User.UserRole.STUDENT) && !currentUser.getId().equals(studentId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        double percentage = attendanceService.getAttendancePercentage(studentId, subjectId);
        return ResponseEntity.ok(percentage);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<AttendanceDto> updateAttendance(@PathVariable Long id, @RequestBody AttendanceDto attendanceDto) {
        AttendanceDto updatedAttendance = attendanceService.updateAttendance(id, attendanceDto);
        return ResponseEntity.ok(updatedAttendance);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAttendance(@PathVariable Long id) {
        attendanceService.deleteAttendance(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<AttendanceDto> getAttendanceById(@PathVariable Long id) {
        AttendanceDto attendance = attendanceService.getAttendanceById(id);
        return ResponseEntity.ok(attendance);
    }
}





