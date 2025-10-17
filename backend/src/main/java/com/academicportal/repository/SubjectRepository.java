package com.academicportal.repository;

import com.academicportal.entity.Subject;

import java.util.List;
import java.util.Optional;

public interface SubjectRepository {
    
    List<Subject> findAll();
    
    Subject findById(Long id);

    Subject findByCode(String code);
    
    List<Subject> findByTeacherId(Long teacherId);
    
    List<Subject> findByStudentId(Long studentId);
    
    List<Subject> findByNameContainingIgnoreCase(String name);
    
    Subject save(Subject subject);
    
    Subject update(Subject subject);
    
    void deleteById(Long id);
    
    boolean existsByCode(String code);
}





