package com.academicportal.repository;

import com.academicportal.entity.Attendance;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository {
    
    List<Attendance> findAll();
    
    Optional<Attendance> findById(Long id);
    
    List<Attendance> findByStudentId(Long studentId);
    
    List<Attendance> findBySubjectId(Long subjectId);
    
    List<Attendance> findByTeacherId(Long teacherId);
    
    List<Attendance> findByStudentIdAndSubjectId(Long studentId, Long subjectId);
    
    List<Attendance> findByAttendanceDateBetween(LocalDate startDate, LocalDate endDate);
    
    List<Attendance> findByStudentIdAndAttendanceDateBetween(Long studentId, LocalDate startDate, LocalDate endDate);
    
    List<Attendance> findByStudentAndSubjectAndDateRange(Long studentId, Long subjectId, LocalDate startDate, LocalDate endDate);
    
    Long countPresentByStudentAndSubject(Long studentId, Long subjectId);
    
    Long countTotalByStudentAndSubject(Long studentId, Long subjectId);
    
    Attendance save(Attendance attendance);
    
    Attendance update(Attendance attendance);
    
    void deleteById(Long id);
}

