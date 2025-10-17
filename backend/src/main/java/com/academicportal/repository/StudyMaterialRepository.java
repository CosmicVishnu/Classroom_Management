package com.academicportal.repository;

import com.academicportal.entity.StudyMaterial;

import java.util.List;
import java.util.Optional;

public interface StudyMaterialRepository {
    
    List<StudyMaterial> findAll();
    
    StudyMaterial findById(Long id);
    
    List<StudyMaterial> findBySubjectId(Long subjectId);
    
    List<StudyMaterial> findByUploadedById(Long uploadedById);
    
    StudyMaterial save(StudyMaterial studyMaterial);
    
    StudyMaterial update(StudyMaterial studyMaterial);
    
    void deleteById(Long id);
    
    List<StudyMaterial> findByType(StudyMaterial.MaterialType type);
    
    List<StudyMaterial> findByStudentId(Long studentId);
    
    List<StudyMaterial> findByTeacherId(Long teacherId);
    
    List<StudyMaterial> findByTitleContainingIgnoreCase(String title);
    
    List<StudyMaterial> findByIsPublicTrue();
    
    List<StudyMaterial> findPublicBySubjectId(Long subjectId);
}





