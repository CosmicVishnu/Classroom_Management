package com.academicportal.service;

import com.academicportal.dto.AttendanceDto;
import com.academicportal.entity.Attendance;
import com.academicportal.entity.Subject;
import com.academicportal.entity.User;
import com.academicportal.jdbc.JdbcAttendanceRepository;
import com.academicportal.jdbc.JdbcSubjectRepository;
import com.academicportal.jdbc.JdbcUserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AttendanceService {

    private final JdbcAttendanceRepository attendanceRepository;
    private final JdbcUserRepository userRepository;
    private final JdbcSubjectRepository subjectRepository;

    public AttendanceService(JdbcAttendanceRepository attendanceRepository, JdbcUserRepository userRepository, JdbcSubjectRepository subjectRepository) {
        this.attendanceRepository = attendanceRepository;
        this.userRepository = userRepository;
        this.subjectRepository = subjectRepository;
    }
    
    public AttendanceDto markAttendance(AttendanceDto attendanceDto) {
        User student = userRepository.findById(attendanceDto.getStudentId()).orElse(null);
        if (student == null) {
            throw new RuntimeException("Student not found");
        }

        Subject subject = subjectRepository.findById(attendanceDto.getSubjectId());
        if (subject == null) {
            throw new RuntimeException("Subject not found");
        }

        User teacher = userRepository.findById(attendanceDto.getTeacherId()).orElse(null);
        if (teacher == null) {
            throw new RuntimeException("Teacher not found");
        }
        
        Attendance attendance = new Attendance();
        attendance.setStudentId(student.getId());
        attendance.setStudentName(student.getName());
        attendance.setSubjectId(subject.getId());
        attendance.setSubjectName(subject.getName());
        attendance.setTeacherId(teacher.getId());
        attendance.setTeacherName(teacher.getName());
        attendance.setAttendanceDate(attendanceDto.getAttendanceDate());
        attendance.setPeriod(attendanceDto.getPeriod());
        attendance.setStatus(attendanceDto.getStatus());
        attendance.setRemarks(attendanceDto.getRemarks());
        
        Attendance savedAttendance = attendanceRepository.save(attendance);
        return convertToDto(savedAttendance);
    }
    
    public List<AttendanceDto> getAttendanceByStudent(Long studentId) {
        return attendanceRepository.findByStudentId(studentId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public List<AttendanceDto> getAttendanceByStudentAndSubject(Long studentId, Long subjectId) {
        return attendanceRepository.findByStudentIdAndSubjectId(studentId, subjectId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public List<AttendanceDto> getAttendanceByStudentAndDateRange(Long studentId, LocalDate startDate, LocalDate endDate) {
        return attendanceRepository.findByStudentIdAndDateRange(studentId, startDate, endDate).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public List<AttendanceDto> getAttendanceByTeacher(Long teacherId) {
        return attendanceRepository.findByTeacherId(teacherId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public AttendanceDto updateAttendance(Long id, AttendanceDto attendanceDto) {
        Attendance attendance = attendanceRepository.findById(id);
        if (attendance == null) {
            throw new RuntimeException("Attendance not found");
        }
        
        attendance.setStatus(attendanceDto.getStatus());
        attendance.setRemarks(attendanceDto.getRemarks());
        
        Attendance updatedAttendance = attendanceRepository.save(attendance);
        return convertToDto(updatedAttendance);
    }
    
    public void deleteAttendance(Long id) {
        attendanceRepository.deleteById(id);
    }
    
    public AttendanceDto getAttendanceById(Long id) {
        Attendance attendance = attendanceRepository.findById(id);
        if (attendance == null) {
            throw new RuntimeException("Attendance not found");
        }
        return convertToDto(attendance);
    }
    
    public double getAttendancePercentage(Long studentId, Long subjectId) {
        Long presentCount = attendanceRepository.countPresentByStudentAndSubject(studentId, subjectId);
        Long totalCount = attendanceRepository.countTotalByStudentAndSubject(studentId, subjectId);
        
        if (totalCount == 0) {
            return 0.0;
        }
        
        return (double) presentCount / totalCount * 100;
    }
    
    private AttendanceDto convertToDto(Attendance attendance) {
        return new AttendanceDto(
                attendance.getId(),
                attendance.getStudentId(),
                attendance.getStudentName(),
                attendance.getSubjectId(),
                attendance.getSubjectName(),
                attendance.getTeacherId(),
                attendance.getTeacherName(),
                attendance.getAttendanceDate(),
                attendance.getPeriod(),
                attendance.getStatus(),
                attendance.getRemarks(),
                attendance.getCreatedAt(),
                attendance.getUpdatedAt()
        );
    }
}
