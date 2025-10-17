package com.academicportal.service;

import com.academicportal.dto.NoticeDto;
import com.academicportal.entity.Notice;
import com.academicportal.entity.User;
import com.academicportal.jdbc.JdbcNoticeRepository;
import com.academicportal.jdbc.JdbcUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeService {
    
    private final JdbcNoticeRepository noticeRepository;
    private final JdbcUserRepository userRepository;
    
    public NoticeDto createNotice(NoticeDto noticeDto, Long authorId) {
        User author = userRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Notice notice = new Notice();
        notice.setTitle(noticeDto.getTitle());
        notice.setContent(noticeDto.getContent());
        notice.setAuthorId(author.getId());
        notice.setAuthorName(author.getName());
        notice.setType(noticeDto.getType());
        notice.setPriority(noticeDto.getPriority());
        notice.setExpiresAt(noticeDto.getExpiresAt());
        notice.setAttachmentUrl(noticeDto.getAttachmentUrl());
        notice.setAttachmentName(noticeDto.getAttachmentName());
        
        Notice savedNotice = noticeRepository.save(notice);
        return convertToDto(savedNotice);
    }
    
    public List<NoticeDto> getAllNotices() {
        return noticeRepository.findAllOrderByPriorityAndDate().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public List<NoticeDto> getActiveNotices() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime since = now.minusMonths(3); // Show notices from last 3 months
        
        return noticeRepository.findActiveNotices(since, now).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public List<NoticeDto> getNoticesByType(Notice.NoticeType type) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime since = now.minusMonths(3);
        
        return noticeRepository.findActiveNoticesByType(type, since, now).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public List<NoticeDto> getNoticesByAuthor(Long authorId) {
        return noticeRepository.findByAuthorId(authorId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public Optional<NoticeDto> getNoticeById(Long id) {
        Notice notice = noticeRepository.findById(id);
        if (notice == null) {
            return Optional.empty();
        }
        return Optional.of(convertToDto(notice));
    }
    
    public NoticeDto updateNotice(Long id, NoticeDto noticeDto) {
        Notice notice = noticeRepository.findById(id);
        if (notice == null) {
            throw new RuntimeException("Notice not found");
        }
        
        notice.setTitle(noticeDto.getTitle());
        notice.setContent(noticeDto.getContent());
        notice.setType(noticeDto.getType());
        notice.setPriority(noticeDto.getPriority());
        notice.setExpiresAt(noticeDto.getExpiresAt());
        notice.setAttachmentUrl(noticeDto.getAttachmentUrl());
        notice.setAttachmentName(noticeDto.getAttachmentName());
        
        Notice updatedNotice = noticeRepository.save(notice);
        return convertToDto(updatedNotice);
    }
    
    public void deleteNotice(Long id) {
        noticeRepository.deleteById(id);
    }
    
    private NoticeDto convertToDto(Notice notice) {
        return new NoticeDto(
                notice.getId(),
                notice.getTitle(),
                notice.getContent(),
                notice.getAuthorId(),
                notice.getAuthorName(),
                notice.getType(),
                notice.getPriority(),
                notice.getCreatedAt(),
                notice.getExpiresAt(),
                notice.getAttachmentUrl(),
                notice.getAttachmentName()
        );
    }
}
