package com.academicportal.entity;

import java.time.LocalDateTime;

public class Notice {

    private Long id;
    private String title;
    private String content;
    private Long authorId;
    private String authorName;
    private NoticeType type;
    private Priority priority = Priority.MEDIUM;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private String attachmentUrl;
    private String attachmentName;

    public Notice() {}

    public Notice(Long id, String title, String content, Long authorId, String authorName, NoticeType type, Priority priority, LocalDateTime createdAt, LocalDateTime expiresAt, String attachmentUrl, String attachmentName) {
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

    public NoticeType getType() { return type; }
    public void setType(NoticeType type) { this.type = type; }

    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getExpiresAt() { return expiresAt; }
    public void setExpiresAt(LocalDateTime expiresAt) { this.expiresAt = expiresAt; }

    public String getAttachmentUrl() { return attachmentUrl; }
    public void setAttachmentUrl(String attachmentUrl) { this.attachmentUrl = attachmentUrl; }

    public String getAttachmentName() { return attachmentName; }
    public void setAttachmentName(String attachmentName) { this.attachmentName = attachmentName; }

    public enum NoticeType {
        GENERAL, ACADEMIC, EXAM, HOLIDAY, EVENT, URGENT
    }

    public enum Priority {
        LOW, MEDIUM, HIGH, URGENT
    }
}
