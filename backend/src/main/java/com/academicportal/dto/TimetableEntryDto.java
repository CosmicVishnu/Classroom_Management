package com.academicportal.dto;

import com.academicportal.entity.TimetableEntry;

// Validation annotations removed for now
import java.time.LocalTime;

public class TimetableEntryDto {

    private Long id;

    private Long subjectId;

    private String subjectName;

    private Long teacherId;

    private String teacherName;

    private TimetableEntry.DayOfWeek dayOfWeek;

    private String period;

    private LocalTime startTime;

    private LocalTime endTime;

    private String room;

    private String batch;

    private TimetableEntry.EntryType type = TimetableEntry.EntryType.LECTURE;

    private String notes;

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

    public TimetableEntry.DayOfWeek getDayOfWeek() { return dayOfWeek; }
    public void setDayOfWeek(TimetableEntry.DayOfWeek dayOfWeek) { this.dayOfWeek = dayOfWeek; }

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

    public TimetableEntry.EntryType getType() { return type; }
    public void setType(TimetableEntry.EntryType type) { this.type = type; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
