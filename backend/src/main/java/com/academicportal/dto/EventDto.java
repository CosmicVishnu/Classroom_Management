package com.academicportal.dto;

import com.academicportal.entity.Event;

// Validation annotations removed for now
import java.time.LocalDate;

public class EventDto {

    private Long id;

    private String title;

    private String description;

    private LocalDate eventDate;

    private String startTime;

    private String endTime;

    private String location;

    private Event.EventType type;

    private Event.Priority priority = Event.Priority.MEDIUM;

    private Long createdById;

    private String createdByName;

    private Boolean isAllDay = false;

    private String color;

    public EventDto() {}

    public EventDto(Long id, String title, String description, LocalDate eventDate, String startTime, String endTime, String location, Event.EventType type, Event.Priority priority, Long createdById, String createdByName, Boolean isAllDay, String color) {
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

    public Event.EventType getType() { return type; }
    public void setType(Event.EventType type) { this.type = type; }

    public Event.Priority getPriority() { return priority; }
    public void setPriority(Event.Priority priority) { this.priority = priority; }

    public Long getCreatedById() { return createdById; }
    public void setCreatedById(Long createdById) { this.createdById = createdById; }

    public String getCreatedByName() { return createdByName; }
    public void setCreatedByName(String createdByName) { this.createdByName = createdByName; }

    public Boolean getIsAllDay() { return isAllDay; }
    public void setIsAllDay(Boolean isAllDay) { this.isAllDay = isAllDay; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
}
