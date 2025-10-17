package com.academicportal.repository;

import com.academicportal.entity.Notice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface NoticeRepository {
    
    List<Notice> findAll();
    
    Optional<Notice> findById(Long id);
    
    List<Notice> findByAuthorId(Long authorId);
    
    List<Notice> findByType(Notice.NoticeType type);
    
    List<Notice> findByPriority(Notice.Priority priority);
    
    List<Notice> findActiveNotices(LocalDateTime since, LocalDateTime now);
    
    List<Notice> findActiveNoticesByType(Notice.NoticeType type, LocalDateTime since, LocalDateTime now);
    
    List<Notice> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    List<Notice> findAllOrderByPriorityAndDate();
    
    Notice save(Notice notice);
    
    Notice update(Notice notice);
    
    void deleteById(Long id);
}





