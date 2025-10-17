package com.academicportal.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.academicportal.dto.TimetableEntryDto;
import com.academicportal.entity.TimetableEntry;
import com.academicportal.jdbc.JdbcTimetableEntryRepository;
import com.academicportal.jdbc.JdbcUserRepository;

@Service
public class TimetableService {

    private final JdbcTimetableEntryRepository timetableEntryRepository;
    private final JdbcUserRepository userRepository;

    public TimetableService(JdbcTimetableEntryRepository timetableEntryRepository, JdbcUserRepository userRepository) {
        this.timetableEntryRepository = timetableEntryRepository;
        this.userRepository = userRepository;
    }
    
    public List<TimetableEntryDto> getAllTimetableEntries() {
        return timetableEntryRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public Optional<TimetableEntryDto> getTimetableEntryById(Long id) {
        TimetableEntry entry = timetableEntryRepository.findById(id);
        return entry != null ? Optional.of(convertToDto(entry)) : Optional.empty();
    }
    
    public List<TimetableEntryDto> getTimetableEntriesByTeacher(Long teacherId) {
        return timetableEntryRepository.findByTeacherId(teacherId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public List<TimetableEntryDto> getTimetableEntriesByStudent(Long studentId) {
        return timetableEntryRepository.findByStudentId(studentId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public List<TimetableEntryDto> getTimetableEntriesBySubject(Long subjectId) {
        return timetableEntryRepository.findBySubjectId(subjectId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public List<TimetableEntryDto> getTimetableEntriesByDay(TimetableEntry.DayOfWeek dayOfWeek) {
        return timetableEntryRepository.findByDayOfWeek(dayOfWeek).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public List<TimetableEntryDto> getTimetableEntriesByTeacherAndDay(Long teacherId, TimetableEntry.DayOfWeek dayOfWeek) {
        return timetableEntryRepository.findByTeacherAndDay(teacherId, dayOfWeek).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public List<TimetableEntryDto> getTimetableEntriesByStudentAndDay(Long studentId, TimetableEntry.DayOfWeek dayOfWeek) {
        return timetableEntryRepository.findByStudentAndDay(studentId, dayOfWeek).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public TimetableEntryDto createTimetableEntry(TimetableEntryDto timetableEntryDto) {
        TimetableEntry entry = convertToEntity(timetableEntryDto);
        TimetableEntry savedEntry = timetableEntryRepository.save(entry);
        return convertToDto(savedEntry);
    }
    
    public TimetableEntryDto updateTimetableEntry(Long id, TimetableEntryDto timetableEntryDto) {
        TimetableEntry existingEntry = timetableEntryRepository.findById(id);
        if (existingEntry == null) {
            throw new RuntimeException("Timetable entry not found");
        }
        
        // Update fields
        existingEntry.setSubjectId(timetableEntryDto.getSubjectId());
        existingEntry.setTeacherId(timetableEntryDto.getTeacherId());
        existingEntry.setDayOfWeek(timetableEntryDto.getDayOfWeek());
        existingEntry.setPeriod(timetableEntryDto.getPeriod());
        existingEntry.setStartTime(timetableEntryDto.getStartTime());
        existingEntry.setEndTime(timetableEntryDto.getEndTime());
        existingEntry.setRoom(timetableEntryDto.getRoom());
        existingEntry.setBatch(timetableEntryDto.getBatch());
        existingEntry.setType(timetableEntryDto.getType());
        
        TimetableEntry updatedEntry = timetableEntryRepository.update(existingEntry);
        return convertToDto(updatedEntry);
    }
    
    public void deleteTimetableEntry(Long id) {
        timetableEntryRepository.deleteById(id);
    }
    
    private TimetableEntryDto convertToDto(TimetableEntry entry) {
        TimetableEntryDto dto = new TimetableEntryDto();
        dto.setId(entry.getId());
        dto.setSubjectId(entry.getSubjectId());
        dto.setTeacherId(entry.getTeacherId());
        dto.setDayOfWeek(entry.getDayOfWeek());
        dto.setPeriod(entry.getPeriod());
        dto.setStartTime(entry.getStartTime());
        dto.setEndTime(entry.getEndTime());
        dto.setRoom(entry.getRoom());
        dto.setBatch(entry.getBatch());
        dto.setType(entry.getType());
        
        // Set names
        if (entry.getSubjectId() != null) {
            dto.setSubjectName("Subject Name"); // Placeholder - you might need to add subject name lookup
        }
        
        if (entry.getTeacherId() != null) {
            userRepository.findById(entry.getTeacherId())
                    .ifPresent(teacher -> dto.setTeacherName(teacher.getName()));
        }
        
        return dto;
    }
    
    private TimetableEntry convertToEntity(TimetableEntryDto dto) {
        TimetableEntry entry = new TimetableEntry();
        entry.setSubjectId(dto.getSubjectId());
        entry.setTeacherId(dto.getTeacherId());
        entry.setDayOfWeek(dto.getDayOfWeek());
        entry.setPeriod(dto.getPeriod());
        entry.setStartTime(dto.getStartTime());
        entry.setEndTime(dto.getEndTime());
        entry.setRoom(dto.getRoom());
        entry.setBatch(dto.getBatch());
        entry.setType(dto.getType());
        return entry;
    }
}
