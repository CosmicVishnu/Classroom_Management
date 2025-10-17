package com.academicportal.repository;

import com.academicportal.entity.TimetableEntry;

import java.util.List;
import java.util.Optional;

public interface TimetableEntryRepository {
    
    List<TimetableEntry> findAll();
    
    TimetableEntry findById(Long id);
    
    List<TimetableEntry> findByTeacherId(Long teacherId);
    
    List<TimetableEntry> findByStudentId(Long studentId);
    
    List<TimetableEntry> findBySubjectId(Long subjectId);
    
    List<TimetableEntry> findByDayOfWeek(TimetableEntry.DayOfWeek dayOfWeek);
    
    List<TimetableEntry> findByTeacherAndDay(Long teacherId, TimetableEntry.DayOfWeek dayOfWeek);
    
    List<TimetableEntry> findByStudentAndDay(Long studentId, TimetableEntry.DayOfWeek dayOfWeek);
    
    TimetableEntry save(TimetableEntry timetableEntry);
    
    TimetableEntry update(TimetableEntry timetableEntry);
    
    void deleteById(Long id);
}





