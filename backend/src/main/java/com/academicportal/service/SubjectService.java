package com.academicportal.service;

import com.academicportal.dto.SubjectDto;
import com.academicportal.entity.Subject;
import com.academicportal.jdbc.JdbcSubjectRepository;
import com.academicportal.jdbc.JdbcUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubjectService {

    private final JdbcSubjectRepository subjectRepository;
    private final JdbcUserRepository userRepository;

    public SubjectService(JdbcSubjectRepository subjectRepository, JdbcUserRepository userRepository) {
        this.subjectRepository = subjectRepository;
        this.userRepository = userRepository;
    }
    
    public List<SubjectDto> getAllSubjects() {
        return subjectRepository.findAll().stream()
                .map(subject -> convertToDto(subject))
                .collect(Collectors.toList());
    }
    
    public Optional<SubjectDto> getSubjectById(Long id) {
        Subject subject = subjectRepository.findById(id);
        return subject != null ? Optional.of(convertToDto(subject)) : Optional.empty();
    }
    
    public Optional<SubjectDto> getSubjectByCode(String code) {
        Subject subject = subjectRepository.findByCode(code);
        return subject != null ? Optional.of(convertToDto(subject)) : Optional.empty();
    }
    
    public List<SubjectDto> getSubjectsByTeacher(Long teacherId) {
        return subjectRepository.findByTeacherId(teacherId).stream()
                .map(subject -> convertToDto(subject))
                .collect(Collectors.toList());
    }
    
    public List<SubjectDto> getSubjectsByStudent(Long studentId) {
        return subjectRepository.findByStudentId(studentId).stream()
                .map(subject -> convertToDto(subject))
                .collect(Collectors.toList());
    }
    
    public List<SubjectDto> searchSubjectsByName(String name) {
        return subjectRepository.findByNameContainingIgnoreCase(name).stream()
                .map(subject -> convertToDto(subject))
                .collect(Collectors.toList());
    }
    
    public SubjectDto createSubject(SubjectDto subjectDto) {
        Subject subject = convertToEntity(subjectDto);
        Subject savedSubject = subjectRepository.save(subject);
        return convertToDto(savedSubject);
    }
    
    public SubjectDto updateSubject(Long id, SubjectDto subjectDto) {
        Subject existingSubject = subjectRepository.findById(id);
        if (existingSubject == null) {
            throw new RuntimeException("Subject not found");
        }

        // Update fields
        existingSubject.setName(subjectDto.getName());
        existingSubject.setCode(subjectDto.getCode());
        existingSubject.setDescription(subjectDto.getDescription());
        existingSubject.setTeacherId(subjectDto.getTeacherId());
        existingSubject.setType(subjectDto.getType());
        existingSubject.setDepartment(subjectDto.getDepartment());
        existingSubject.setCredits(subjectDto.getCredits());
        existingSubject.setSemester(subjectDto.getSemester());
        existingSubject.setAcademicYear(subjectDto.getAcademicYear());

        Subject updatedSubject = subjectRepository.update(existingSubject);
        return convertToDto(updatedSubject);
    }
    
    public void deleteSubject(Long id) {
        subjectRepository.deleteById(id);
    }
    
    public boolean existsByCode(String code) {
        return subjectRepository.existsByCode(code);
    }
    
    private SubjectDto convertToDto(Subject subject) {
        SubjectDto dto = new SubjectDto();
        dto.setId(subject.getId());
        dto.setName(subject.getName());
        dto.setCode(subject.getCode());
        dto.setDescription(subject.getDescription());
        dto.setTeacherId(subject.getTeacherId());
        dto.setType(subject.getType());
        dto.setDepartment(subject.getDepartment());
        dto.setCredits(subject.getCredits());
        dto.setSemester(subject.getSemester());
        dto.setAcademicYear(subject.getAcademicYear());
        
        // Set teacher name
        if (subject.getTeacherId() != null) {
            userRepository.findById(subject.getTeacherId())
                    .ifPresent(teacher -> dto.setTeacherName(teacher.getName()));
        }
        
        return dto;
    }
    
    private Subject convertToEntity(SubjectDto dto) {
        Subject subject = new Subject();
        subject.setName(dto.getName());
        subject.setCode(dto.getCode());
        subject.setDescription(dto.getDescription());
        subject.setTeacherId(dto.getTeacherId());
        subject.setType(dto.getType());
        subject.setDepartment(dto.getDepartment());
        subject.setCredits(dto.getCredits());
        subject.setSemester(dto.getSemester());
        subject.setAcademicYear(dto.getAcademicYear());
        return subject;
    }
}
