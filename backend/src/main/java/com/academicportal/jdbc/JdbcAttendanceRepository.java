package com.academicportal.jdbc;

import com.academicportal.entity.Attendance;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JdbcAttendanceRepository {
    
    private final JdbcTemplate jdbcTemplate;
    
    private final RowMapper<Attendance> attendanceRowMapper = (rs, rowNum) -> {
        Attendance attendance = new Attendance();
        attendance.setId(rs.getLong("id"));
        attendance.setStudentId(rs.getLong("student_id"));
        attendance.setStudentName(rs.getString("student_name"));
        attendance.setSubjectId(rs.getLong("subject_id"));
        attendance.setSubjectName(rs.getString("subject_name"));
        attendance.setTeacherId(rs.getLong("teacher_id"));
        attendance.setTeacherName(rs.getString("teacher_name"));
        attendance.setAttendanceDate(rs.getDate("attendance_date").toLocalDate());
        attendance.setPeriod(rs.getString("period"));
        attendance.setStatus(Attendance.AttendanceStatus.valueOf(rs.getString("status")));
        attendance.setRemarks(rs.getString("remarks"));
        attendance.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        attendance.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        return attendance;
    };
    
    public Attendance save(Attendance attendance) {
        if (attendance.getId() == null) {
            return insert(attendance);
        } else {
            return update(attendance);
        }
    }
    
    public Attendance insert(Attendance attendance) {
        String sql = "INSERT INTO attendances (student_id, subject_id, teacher_id, attendance_date, period, status, remarks) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        KeyHolder keyHolder = new GeneratedKeyHolder();
        
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, attendance.getStudentId());
            ps.setLong(2, attendance.getSubjectId());
            ps.setLong(3, attendance.getTeacherId());
            ps.setDate(4, java.sql.Date.valueOf(attendance.getAttendanceDate()));
            ps.setString(5, attendance.getPeriod());
            ps.setString(6, attendance.getStatus().name());
            ps.setString(7, attendance.getRemarks());
            return ps;
        }, keyHolder);
        
        attendance.setId(keyHolder.getKey().longValue());
        return attendance;
    }
    
    public Attendance update(Attendance attendance) {
        String sql = "UPDATE attendances SET status = ?, remarks = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";
        
        jdbcTemplate.update(sql, 
            attendance.getStatus().name(),
            attendance.getRemarks(),
            attendance.getId());
        
        return attendance;
    }
    
    public List<Attendance> findByStudentId(Long studentId) {
        String sql = """
            SELECT a.*, u1.name as student_name, s.name as subject_name, u2.name as teacher_name
            FROM attendances a
            JOIN users u1 ON a.student_id = u1.id
            JOIN subjects s ON a.subject_id = s.id
            JOIN users u2 ON a.teacher_id = u2.id
            WHERE a.student_id = ?
            ORDER BY a.attendance_date DESC
            """;
        return jdbcTemplate.query(sql, attendanceRowMapper, studentId);
    }
    
    public List<Attendance> findByStudentIdAndSubjectId(Long studentId, Long subjectId) {
        String sql = """
            SELECT a.*, u1.name as student_name, s.name as subject_name, u2.name as teacher_name
            FROM attendances a
            JOIN users u1 ON a.student_id = u1.id
            JOIN subjects s ON a.subject_id = s.id
            JOIN users u2 ON a.teacher_id = u2.id
            WHERE a.student_id = ? AND a.subject_id = ?
            ORDER BY a.attendance_date DESC
            """;
        return jdbcTemplate.query(sql, attendanceRowMapper, studentId, subjectId);
    }
    
    public List<Attendance> findByStudentIdAndDateRange(Long studentId, LocalDate startDate, LocalDate endDate) {
        String sql = """
            SELECT a.*, u1.name as student_name, s.name as subject_name, u2.name as teacher_name
            FROM attendances a
            JOIN users u1 ON a.student_id = u1.id
            JOIN subjects s ON a.subject_id = s.id
            JOIN users u2 ON a.teacher_id = u2.id
            WHERE a.student_id = ? AND a.attendance_date BETWEEN ? AND ?
            ORDER BY a.attendance_date DESC
            """;
        return jdbcTemplate.query(sql, attendanceRowMapper, studentId, startDate, endDate);
    }
    
    public List<Attendance> findByTeacherId(Long teacherId) {
        String sql = """
            SELECT a.*, u1.name as student_name, s.name as subject_name, u2.name as teacher_name
            FROM attendances a
            JOIN users u1 ON a.student_id = u1.id
            JOIN subjects s ON a.subject_id = s.id
            JOIN users u2 ON a.teacher_id = u2.id
            WHERE a.teacher_id = ?
            ORDER BY a.attendance_date DESC
            """;
        return jdbcTemplate.query(sql, attendanceRowMapper, teacherId);
    }
    
    public Long countPresentByStudentAndSubject(Long studentId, Long subjectId) {
        String sql = "SELECT COUNT(*) FROM attendances WHERE student_id = ? AND subject_id = ? AND status = 'PRESENT'";
        Long count = jdbcTemplate.queryForObject(sql, Long.class, studentId, subjectId);
        return count != null ? count : 0L;
    }
    
    public Long countTotalByStudentAndSubject(Long studentId, Long subjectId) {
        String sql = "SELECT COUNT(*) FROM attendances WHERE student_id = ? AND subject_id = ?";
        Long count = jdbcTemplate.queryForObject(sql, Long.class, studentId, subjectId);
        return count != null ? count : 0L;
    }
    
    public Attendance findById(Long id) {
        String sql = """
            SELECT a.*, u1.name as student_name, s.name as subject_name, u2.name as teacher_name
            FROM attendances a
            JOIN users u1 ON a.student_id = u1.id
            JOIN subjects s ON a.subject_id = s.id
            JOIN users u2 ON a.teacher_id = u2.id
            WHERE a.id = ?
            """;
        List<Attendance> attendances = jdbcTemplate.query(sql, attendanceRowMapper, id);
        return attendances.isEmpty() ? null : attendances.get(0);
    }
    
    public void deleteById(Long id) {
        String sql = "DELETE FROM attendances WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
    
    public long count() {
        String sql = "SELECT COUNT(*) FROM attendances";
        Long count = jdbcTemplate.queryForObject(sql, Long.class);
        return count != null ? count : 0;
    }
}





