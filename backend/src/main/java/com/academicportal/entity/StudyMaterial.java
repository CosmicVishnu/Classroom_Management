package com.academicportal.entity;

import java.time.LocalDateTime;

public class StudyMaterial {

    private Long id;
    private String title;
    private String description;
    private Long subjectId;
    private String subjectName;
    private Long uploadedById;
    private String uploadedByName;
    private MaterialType type;
    private String fileName;
    private String filePath;
    private Long fileSize;
    private String mimeType;
    private LocalDateTime uploadedAt;
    private String tags;
    private Boolean isPublic = true;

    public StudyMaterial() {}

    public StudyMaterial(Long id, String title, String description, Long subjectId, String subjectName, Long uploadedById, String uploadedByName, MaterialType type, String fileName, String filePath, Long fileSize, String mimeType, LocalDateTime uploadedAt, String tags, Boolean isPublic) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.uploadedById = uploadedById;
        this.uploadedByName = uploadedByName;
        this.type = type;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.mimeType = mimeType;
        this.uploadedAt = uploadedAt;
        this.tags = tags;
        this.isPublic = isPublic;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Long getSubjectId() { return subjectId; }
    public void setSubjectId(Long subjectId) { this.subjectId = subjectId; }

    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }

    public Long getUploadedById() { return uploadedById; }
    public void setUploadedById(Long uploadedById) { this.uploadedById = uploadedById; }

    public String getUploadedByName() { return uploadedByName; }
    public void setUploadedByName(String uploadedByName) { this.uploadedByName = uploadedByName; }

    public MaterialType getType() { return type; }
    public void setType(MaterialType type) { this.type = type; }

    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }

    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }

    public Long getFileSize() { return fileSize; }
    public void setFileSize(Long fileSize) { this.fileSize = fileSize; }

    public String getMimeType() { return mimeType; }
    public void setMimeType(String mimeType) { this.mimeType = mimeType; }

    public LocalDateTime getUploadedAt() { return uploadedAt; }
    public void setUploadedAt(LocalDateTime uploadedAt) { this.uploadedAt = uploadedAt; }

    public String getTags() { return tags; }
    public void setTags(String tags) { this.tags = tags; }

    public Boolean getIsPublic() { return isPublic; }
    public void setIsPublic(Boolean isPublic) { this.isPublic = isPublic; }
    
    public enum MaterialType {
        LECTURE_NOTES, ASSIGNMENT, REFERENCE_BOOK, VIDEO, PRESENTATION, 
        PAST_PAPERS, SYLLABUS, TUTORIAL_SHEET, LAB_MANUAL
    }
}
