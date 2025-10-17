package com.academicportal.dto;

import java.time.LocalDateTime;

import com.academicportal.entity.Notice;

public class NoticeDto {
    private Long id;
    private String title;
    private String content;
    private Long authorId;
    private String authorName;
    private Notice.NoticeType type;
    private Notice.Priority priority;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private String attachmentUrl;
    private String attachmentName;

    public NoticeDto() {}

    public NoticeDto(Long id, String title, String content, Long authorId, String authorName, Notice.NoticeType type, Notice.Priority priority, LocalDateTime createdAt, LocalDateTime expiresAt, String attachmentUrl, String attachmentName) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.authorId = authorId;
        this.authorName = authorName;
        this.type = type;
        this.priority = priority;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.attachmentUrl = attachmentUrl;
        this.attachmentName = attachmentName;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Long getAuthorId() { return authorId; }
    public void setAuthorId(Long authorId) { this.authorId = authorId; }

    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }

    public Notice.NoticeType getType() { return type; }
    public void setType(Notice.NoticeType type) { this.type = type; }

    public Notice.Priority getPriority() { return priority; }
    public void setPriority(Notice.Priority priority) { this.priority = priority; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getExpiresAt() { return expiresAt; }
    public void setExpiresAt(LocalDateTime expiresAt) { this.expiresAt = expiresAt; }

    public String getAttachmentUrl() { return attachmentUrl; }
    public void setAttachmentUrl(String attachmentUrl) { this.attachmentUrl = attachmentUrl; }

    public String getAttachmentName() { return attachmentName; }
    public void setAttachmentName(String attachmentName) { this.attachmentName = attachmentName; }
}
