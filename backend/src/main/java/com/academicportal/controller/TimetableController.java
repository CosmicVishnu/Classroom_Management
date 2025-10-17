package com.academicportal.controller;

import com.academicportal.dto.TimetableEntryDto;
import com.academicportal.dto.UserDto;
import com.academicportal.entity.TimetableEntry;
import com.academicportal.entity.User;
import com.academicportal.service.AuthService;
import com.academicportal.service.TimetableService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/timetable")
@CrossOrigin(origins = "*")
public class TimetableController {

    private final TimetableService timetableService;
    private final AuthService authService;

    public TimetableController(TimetableService timetableService, AuthService authService) {
        this.timetableService = timetableService;
        this.authService = authService;
    }
    
    @GetMapping
    public ResponseEntity<List<TimetableEntryDto>> getAllTimetableEntries() {
        List<TimetableEntryDto> entries = timetableService.getAllTimetableEntries();
        return ResponseEntity.ok(entries);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<TimetableEntryDto> getTimetableEntryById(@PathVariable Long id) {
        return timetableService.getTimetableEntryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/teacher/{teacherId}")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<List<TimetableEntryDto>> getTimetableEntriesByTeacher(@PathVariable Long teacherId) {
        UserDto currentUser = authService.getCurrentUser();
        
        // Teachers can only view their own timetable unless admin
        if (currentUser.getRole().equals(User.UserRole.TEACHER.toString()) && !currentUser.getId().equals(teacherId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        List<TimetableEntryDto> entries = timetableService.getTimetableEntriesByTeacher(teacherId);
        return ResponseEntity.ok(entries);
    }
    
    @GetMapping("/my")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<List<TimetableEntryDto>> getMyTimetableEntries() {
        UserDto currentUser = authService.getCurrentUser();
        List<TimetableEntryDto> entries = timetableService.getTimetableEntriesByTeacher(currentUser.getId());
        return ResponseEntity.ok(entries);
    }
    
    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<List<TimetableEntryDto>> getTimetableEntriesByStudent(@PathVariable Long studentId) {
        UserDto currentUser = authService.getCurrentUser();
        
        // Students can only view their own timetable unless teacher/admin
        if (currentUser.getRole().equals(User.UserRole.STUDENT.toString()) && !currentUser.getId().equals(studentId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        List<TimetableEntryDto> entries = timetableService.getTimetableEntriesByStudent(studentId);
        return ResponseEntity.ok(entries);
    }
    
    @GetMapping("/subject/{subjectId}")
    public ResponseEntity<List<TimetableEntryDto>> getTimetableEntriesBySubject(@PathVariable Long subjectId) {
        List<TimetableEntryDto> entries = timetableService.getTimetableEntriesBySubject(subjectId);
        return ResponseEntity.ok(entries);
    }
    
    @GetMapping("/day/{dayOfWeek}")
    public ResponseEntity<List<TimetableEntryDto>> getTimetableEntriesByDay(@PathVariable String dayOfWeek) {
        try {
            TimetableEntry.DayOfWeek day = TimetableEntry.DayOfWeek.valueOf(dayOfWeek.toUpperCase());
            List<TimetableEntryDto> entries = timetableService.getTimetableEntriesByDay(day);
            return ResponseEntity.ok(entries);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/teacher/{teacherId}/day/{dayOfWeek}")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<List<TimetableEntryDto>> getTimetableEntriesByTeacherAndDay(
            @PathVariable Long teacherId, 
            @PathVariable String dayOfWeek) {
        
        UserDto currentUser = authService.getCurrentUser();
        
        // Teachers can only view their own timetable unless admin
        if (currentUser.getRole().equals(User.UserRole.TEACHER.toString()) && !currentUser.getId().equals(teacherId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        try {
            TimetableEntry.DayOfWeek day = TimetableEntry.DayOfWeek.valueOf(dayOfWeek.toUpperCase());
            List<TimetableEntryDto> entries = timetableService.getTimetableEntriesByTeacherAndDay(teacherId, day);
            return ResponseEntity.ok(entries);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/student/{studentId}/day/{dayOfWeek}")
    @PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<List<TimetableEntryDto>> getTimetableEntriesByStudentAndDay(
            @PathVariable Long studentId, 
            @PathVariable String dayOfWeek) {
        
        UserDto currentUser = authService.getCurrentUser();
        
        // Students can only view their own timetable unless teacher/admin
        if (currentUser.getRole().equals(User.UserRole.STUDENT.toString()) && !currentUser.getId().equals(studentId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        try {
            TimetableEntry.DayOfWeek day = TimetableEntry.DayOfWeek.valueOf(dayOfWeek.toUpperCase());
            List<TimetableEntryDto> entries = timetableService.getTimetableEntriesByStudentAndDay(studentId, day);
            return ResponseEntity.ok(entries);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
    public ResponseEntity<TimetableEntryDto> createTimetableEntry(@RequestBody TimetableEntryDto timetableEntryDto) {
        TimetableEntryDto createdEntry = timetableService.createTimetableEntry(timetableEntryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEntry);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
    public ResponseEntity<TimetableEntryDto> updateTimetableEntry(@PathVariable Long id, @RequestBody TimetableEntryDto timetableEntryDto) {
        TimetableEntryDto updatedEntry = timetableService.updateTimetableEntry(id, timetableEntryDto);
        return ResponseEntity.ok(updatedEntry);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
    public ResponseEntity<Void> deleteTimetableEntry(@PathVariable Long id) {
        timetableService.deleteTimetableEntry(id);
        return ResponseEntity.noContent().build();
    }
}
