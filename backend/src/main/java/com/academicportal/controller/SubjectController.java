package com.academicportal.controller;

import com.academicportal.dto.SubjectDto;
import com.academicportal.dto.UserDto;
import com.academicportal.entity.User;
import com.academicportal.service.AuthService;
import com.academicportal.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subjects")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SubjectController {
    
    private final SubjectService subjectService;
    private final AuthService authService;
    
    @GetMapping
    public ResponseEntity<List<SubjectDto>> getAllSubjects() {
        List<SubjectDto> subjects = subjectService.getAllSubjects();
        return ResponseEntity.ok(subjects);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<SubjectDto> getSubjectById(@PathVariable Long id) {
        return subjectService.getSubjectById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/code/{code}")
    public ResponseEntity<SubjectDto> getSubjectByCode(@PathVariable String code) {
        return subjectService.getSubjectByCode(code)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/teacher/{teacherId}")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<List<SubjectDto>> getSubjectsByTeacher(@PathVariable Long teacherId) {
        UserDto currentUser = authService.getCurrentUser();
        
        // Teachers can only view their own subjects unless admin
        if (currentUser.getRole().equals(User.UserRole.TEACHER.toString()) && !currentUser.getId().equals(teacherId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        List<SubjectDto> subjects = subjectService.getSubjectsByTeacher(teacherId);
        return ResponseEntity.ok(subjects);
    }
    
    @GetMapping("/my")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<List<SubjectDto>> getMySubjects() {
        UserDto currentUser = authService.getCurrentUser();
        List<SubjectDto> subjects = subjectService.getSubjectsByTeacher(currentUser.getId());
        return ResponseEntity.ok(subjects);
    }
    
    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<List<SubjectDto>> getSubjectsByStudent(@PathVariable Long studentId) {
        UserDto currentUser = authService.getCurrentUser();
        
        // Students can only view their own subjects unless teacher/admin
        if (currentUser.getRole().equals(User.UserRole.STUDENT.toString()) && !currentUser.getId().equals(studentId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        List<SubjectDto> subjects = subjectService.getSubjectsByStudent(studentId);
        return ResponseEntity.ok(subjects);
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<SubjectDto>> searchSubjectsByName(@RequestParam String name) {
        List<SubjectDto> subjects = subjectService.searchSubjectsByName(name);
        return ResponseEntity.ok(subjects);
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
    public ResponseEntity<SubjectDto> createSubject(@RequestBody SubjectDto subjectDto) {
        SubjectDto createdSubject = subjectService.createSubject(subjectDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSubject);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
    public ResponseEntity<SubjectDto> updateSubject(@PathVariable Long id, @RequestBody SubjectDto subjectDto) {
        SubjectDto updatedSubject = subjectService.updateSubject(id, subjectDto);
        return ResponseEntity.ok(updatedSubject);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteSubject(@PathVariable Long id) {
        subjectService.deleteSubject(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/exists/code/{code}")
    public ResponseEntity<Boolean> checkSubjectCodeExists(@PathVariable String code) {
        boolean exists = subjectService.existsByCode(code);
        return ResponseEntity.ok(exists);
    }
}
