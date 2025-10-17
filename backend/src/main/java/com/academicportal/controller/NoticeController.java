package com.academicportal.controller;

import com.academicportal.dto.NoticeDto;
import com.academicportal.dto.UserDto;
import com.academicportal.entity.Notice;
import com.academicportal.entity.User;
import com.academicportal.service.AuthService;
import com.academicportal.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notices")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class NoticeController {
    
    private final NoticeService noticeService;
    private final AuthService authService;
    
    @GetMapping
    public ResponseEntity<List<NoticeDto>> getAllNotices() {
        List<NoticeDto> notices = noticeService.getAllNotices();
        return ResponseEntity.ok(notices);
    }
    
    @GetMapping("/active")
    public ResponseEntity<List<NoticeDto>> getActiveNotices() {
        List<NoticeDto> notices = noticeService.getActiveNotices();
        return ResponseEntity.ok(notices);
    }
    
    @GetMapping("/type/{type}")
    public ResponseEntity<List<NoticeDto>> getNoticesByType(@PathVariable Notice.NoticeType type) {
        List<NoticeDto> notices = noticeService.getNoticesByType(type);
        return ResponseEntity.ok(notices);
    }
    
    @GetMapping("/author/{authorId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
    public ResponseEntity<List<NoticeDto>> getNoticesByAuthor(@PathVariable Long authorId) {
        UserDto currentUser = authService.getCurrentUser();
        
        // Teachers can only view their own notices, admins can view all
        if (currentUser.getRole().equals(User.UserRole.TEACHER) && !currentUser.getId().equals(authorId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        List<NoticeDto> notices = noticeService.getNoticesByAuthor(authorId);
        return ResponseEntity.ok(notices);
    }
    
    @GetMapping("/my")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
    public ResponseEntity<List<NoticeDto>> getMyNotices() {
        UserDto currentUser = authService.getCurrentUser();
        List<NoticeDto> notices = noticeService.getNoticesByAuthor(currentUser.getId());
        return ResponseEntity.ok(notices);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<NoticeDto> getNoticeById(@PathVariable Long id) {
        return noticeService.getNoticeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
    public ResponseEntity<NoticeDto> createNotice(@RequestBody NoticeDto noticeDto) {
        UserDto currentUser = authService.getCurrentUser();
        NoticeDto createdNotice = noticeService.createNotice(noticeDto, currentUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdNotice);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
    public ResponseEntity<NoticeDto> updateNotice(@PathVariable Long id, @RequestBody NoticeDto noticeDto) {
        // TODO: Add authorization check to ensure user can only update their own notices unless admin
        NoticeDto updatedNotice = noticeService.updateNotice(id, noticeDto);
        return ResponseEntity.ok(updatedNotice);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
    public ResponseEntity<Void> deleteNotice(@PathVariable Long id) {
        // TODO: Add authorization check to ensure user can only delete their own notices unless admin
        noticeService.deleteNotice(id);
        return ResponseEntity.noContent().build();
    }
}





