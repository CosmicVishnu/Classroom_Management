package com.academicportal.jdbc;

import com.academicportal.entity.Subject;
import com.academicportal.repository.SubjectRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcSubjectRepository implements SubjectRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcSubjectRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    private final RowMapper<Subject> subjectRowMapper = new RowMapper<Subject>() {
        @Override
        public Subject mapRow(ResultSet rs, int rowNum) throws SQLException {
            Subject subject = new Subject();
            subject.setId(rs.getLong("id"));
            subject.setName(rs.getString("name"));
            subject.setCode(rs.getString("code"));
            subject.setDescription(rs.getString("description"));
            subject.setTeacherId(rs.getLong("teacher_id"));
            subject.setType(Subject.MaterialType.valueOf(rs.getString("type")));
            subject.setDepartment(rs.getString("department"));
            subject.setCredits(rs.getInt("credits"));
            subject.setSemester(rs.getString("semester"));
            subject.setAcademicYear(rs.getString("academic_year"));
            return subject;
        }
    };
    
    @Override
    public List<Subject> findAll() {
        String sql = "SELECT * FROM subjects";
        return jdbcTemplate.query(sql, subjectRowMapper);
    }
    
    @Override
    public Subject findById(Long id) {
        String sql = "SELECT * FROM subjects WHERE id = ?";
        List<Subject> subjects = jdbcTemplate.query(sql, subjectRowMapper, id);
        return subjects.stream().findFirst().orElse(null);
    }

    @Override
    public Subject findByCode(String code) {
        String sql = "SELECT * FROM subjects WHERE code = ?";
        List<Subject> subjects = jdbcTemplate.query(sql, subjectRowMapper, code);
        return subjects.stream().findFirst().orElse(null);
    }
    
    @Override
    public List<Subject> findByTeacherId(Long teacherId) {
        String sql = "SELECT * FROM subjects WHERE teacher_id = ?";
        return jdbcTemplate.query(sql, subjectRowMapper, teacherId);
    }
    
    @Override
    public List<Subject> findByStudentId(Long studentId) {
        String sql = """
            SELECT s.* FROM subjects s
            JOIN subject_students ss ON s.id = ss.subject_id
            WHERE ss.student_id = ?
            """;
        return jdbcTemplate.query(sql, subjectRowMapper, studentId);
    }
    
    @Override
    public List<Subject> findByNameContainingIgnoreCase(String name) {
        String sql = "SELECT * FROM subjects WHERE LOWER(name) LIKE LOWER(?)";
        return jdbcTemplate.query(sql, subjectRowMapper, "%" + name + "%");
    }
    
    @Override
    public Subject save(Subject subject) {
        String sql = """
            INSERT INTO subjects (name, code, description, teacher_id, type, department, credits, semester, academic_year)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;
        jdbcTemplate.update(sql, 
            subject.getName(), 
            subject.getCode(), 
            subject.getDescription(), 
            subject.getTeacherId(),
            subject.getType() != null ? subject.getType().name() : null,
            subject.getDepartment(),
            subject.getCredits(),
            subject.getSemester(),
            subject.getAcademicYear()
        );
        
        // Get the generated ID
        Long id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
        subject.setId(id);
        return subject;
    }
    
    @Override
    public Subject update(Subject subject) {
        String sql = """
            UPDATE subjects SET name = ?, code = ?, description = ?, teacher_id = ?, 
            type = ?, department = ?, credits = ?, semester = ?, academic_year = ?
            WHERE id = ?
            """;
        jdbcTemplate.update(sql,
            subject.getName(),
            subject.getCode(),
            subject.getDescription(),
            subject.getTeacherId(),
            subject.getType() != null ? subject.getType().name() : null,
            subject.getDepartment(),
            subject.getCredits(),
            subject.getSemester(),
            subject.getAcademicYear(),
            subject.getId()
        );
        return subject;
    }
    
    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM subjects WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
    
    @Override
    public boolean existsByCode(String code) {
        String sql = "SELECT COUNT(*) FROM subjects WHERE code = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, code);
        return count != null && count > 0;
    }
}
