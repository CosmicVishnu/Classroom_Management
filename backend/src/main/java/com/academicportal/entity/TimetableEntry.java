package com.academicportal.entity;

import java.time.LocalTime;

public class TimetableEntry {

    private Long id;
    private Long subjectId;
    private String subjectName;
    private Long teacherId;
    private String teacherName;
    private DayOfWeek dayOfWeek;
    private String period; // Period 1, Period 2, etc.
    private LocalTime startTime;
    private LocalTime endTime;
    private String room;
    private String batch; // Class A, Class B, etc.
    private EntryType type = EntryType.LECTURE;

    public TimetableEntry() {}

    public TimetableEntry(Long id, Long subjectId, String subjectName, Long teacherId, String teacherName, DayOfWeek dayOfWeek, String period, LocalTime startTime, LocalTime endTime, String room, String batch, EntryType type) {
        this.id = id;
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.dayOfWeek = dayOfWeek;
        this.period = period;
        this.startTime = startTime;
        this.endTime = endTime;
        this.room = room;
        this.batch = batch;
        this.type = type;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getSubjectId() { return subjectId; }
    public void setSubjectId(Long subjectId) { this.subjectId = subjectId; }

    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }

    public Long getTeacherId() { return teacherId; }
    public void setTeacherId(Long teacherId) { this.teacherId = teacherId; }

    public String getTeacherName() { return teacherName; }
    public void setTeacherName(String teacherName) { this.teacherName = teacherName; }

    public DayOfWeek getDayOfWeek() { return dayOfWeek; }
    public void setDayOfWeek(DayOfWeek dayOfWeek) { this.dayOfWeek = dayOfWeek; }

    public String getPeriod() { return period; }
    public void setPeriod(String period) { this.period = period; }

    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }

    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }

    public String getRoom() { return room; }
    public void setRoom(String room) { this.room = room; }

    public String getBatch() { return batch; }
    public void setBatch(String batch) { this.batch = batch; }

    public EntryType getType() { return type; }
    public void setType(EntryType type) { this.type = type; }
    
    public enum DayOfWeek {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }
    
    public enum EntryType {
        LECTURE, LAB, TUTORIAL, EXAM, BREAK
    }
}
