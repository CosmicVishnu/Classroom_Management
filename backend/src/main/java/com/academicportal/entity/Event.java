package com.academicportal.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Event {

    private Long id;
    private String title;
    private String description;
    private LocalDate eventDate;
    private String startTime;
    private String endTime;
    private String location;
    private EventType type;
    private Priority priority = Priority.MEDIUM;
    private Long createdById;
    private String createdByName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isAllDay = false;
    private String color; // For calendar display

    public Event() {}

    public Event(Long id, String title, String description, LocalDate eventDate, String startTime, String endTime, String location, EventType type, Priority priority, Long createdById, String createdByName, LocalDateTime createdAt, LocalDateTime updatedAt, Boolean isAllDay, String color) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.eventDate = eventDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.type = type;
        this.priority = priority;
        this.createdById = createdById;
        this.createdByName = createdByName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isAllDay = isAllDay;
        this.color = color;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getEventDate() { return eventDate; }
    public void setEventDate(LocalDate eventDate) { this.eventDate = eventDate; }

    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }

    public String getEndTime() { return endTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public EventType getType() { return type; }
    public void setType(EventType type) { this.type = type; }

    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }

    public Long getCreatedById() { return createdById; }
    public void setCreatedById(Long createdById) { this.createdById = createdById; }

    public String getCreatedByName() { return createdByName; }
    public void setCreatedByName(String createdByName) { this.createdByName = createdByName; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public Boolean getIsAllDay() { return isAllDay; }
    public void setIsAllDay(Boolean isAllDay) { this.isAllDay = isAllDay; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public enum EventType {
        EXAM, HOLIDAY, SEMINAR, WORKSHOP, MEETING, DEADLINE, FESTIVAL,
        SPORTS_EVENT, CULTURAL_EVENT, CONFERENCE, OTHER
    }

    public enum Priority {
        LOW, MEDIUM, HIGH, URGENT
    }
}
