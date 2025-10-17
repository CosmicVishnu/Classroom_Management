package com.academicportal.jdbc;

import com.academicportal.entity.TimetableEntry;
import com.academicportal.repository.TimetableEntryRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcTimetableEntryRepository implements TimetableEntryRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTimetableEntryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    private final RowMapper<TimetableEntry> timetableEntryRowMapper = new RowMapper<TimetableEntry>() {
        @Override
        public TimetableEntry mapRow(ResultSet rs, int rowNum) throws SQLException {
            TimetableEntry entry = new TimetableEntry();
            entry.setId(rs.getLong("id"));
            entry.setSubjectId(rs.getLong("subject_id"));
            entry.setTeacherId(rs.getLong("teacher_id"));
            entry.setDayOfWeek(TimetableEntry.DayOfWeek.valueOf(rs.getString("day_of_week")));
            entry.setPeriod(rs.getString("period"));
            entry.setStartTime(rs.getTime("start_time").toLocalTime());
            entry.setEndTime(rs.getTime("end_time").toLocalTime());
            entry.setRoom(rs.getString("room"));
            entry.setBatch(rs.getString("batch"));
            entry.setType(TimetableEntry.EntryType.valueOf(rs.getString("type")));
            return entry;
        }
    };
    
    @Override
    public List<TimetableEntry> findAll() {
        String sql = "SELECT * FROM timetable_entries";
        return jdbcTemplate.query(sql, timetableEntryRowMapper);
    }
    
    @Override
    public TimetableEntry findById(Long id) {
        String sql = "SELECT * FROM timetable_entries WHERE id = ?";
        List<TimetableEntry> entries = jdbcTemplate.query(sql, timetableEntryRowMapper, id);
        return entries.stream().findFirst().orElse(null);
    }
    
    @Override
    public List<TimetableEntry> findByTeacherId(Long teacherId) {
        String sql = "SELECT * FROM timetable_entries WHERE teacher_id = ?";
        return jdbcTemplate.query(sql, timetableEntryRowMapper, teacherId);
    }
    
    @Override
    public List<TimetableEntry> findByStudentId(Long studentId) {
        String sql = """
            SELECT te.* FROM timetable_entries te
            JOIN subjects s ON te.subject_id = s.id
            JOIN subject_students ss ON s.id = ss.subject_id
            WHERE ss.student_id = ?
            """;
        return jdbcTemplate.query(sql, timetableEntryRowMapper, studentId);
    }
    
    @Override
    public List<TimetableEntry> findBySubjectId(Long subjectId) {
        String sql = "SELECT * FROM timetable_entries WHERE subject_id = ?";
        return jdbcTemplate.query(sql, timetableEntryRowMapper, subjectId);
    }
    
    @Override
    public List<TimetableEntry> findByDayOfWeek(TimetableEntry.DayOfWeek dayOfWeek) {
        String sql = "SELECT * FROM timetable_entries WHERE day_of_week = ?";
        return jdbcTemplate.query(sql, timetableEntryRowMapper, dayOfWeek.name());
    }
    
    @Override
    public List<TimetableEntry> findByTeacherAndDay(Long teacherId, TimetableEntry.DayOfWeek dayOfWeek) {
        String sql = "SELECT * FROM timetable_entries WHERE teacher_id = ? AND day_of_week = ?";
        return jdbcTemplate.query(sql, timetableEntryRowMapper, teacherId, dayOfWeek.name());
    }
    
    @Override
    public List<TimetableEntry> findByStudentAndDay(Long studentId, TimetableEntry.DayOfWeek dayOfWeek) {
        String sql = """
            SELECT te.* FROM timetable_entries te
            JOIN subjects s ON te.subject_id = s.id
            JOIN subject_students ss ON s.id = ss.subject_id
            WHERE ss.student_id = ? AND te.day_of_week = ?
            """;
        return jdbcTemplate.query(sql, timetableEntryRowMapper, studentId, dayOfWeek.name());
    }
    
    @Override
    public TimetableEntry save(TimetableEntry timetableEntry) {
        String sql = """
            INSERT INTO timetable_entries (subject_id, teacher_id, day_of_week, period, 
            start_time, end_time, room, batch, type)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;
        jdbcTemplate.update(sql,
            timetableEntry.getSubjectId(),
            timetableEntry.getTeacherId(),
            timetableEntry.getDayOfWeek().name(),
            timetableEntry.getPeriod(),
            timetableEntry.getStartTime(),
            timetableEntry.getEndTime(),
            timetableEntry.getRoom(),
            timetableEntry.getBatch(),
            timetableEntry.getType().name()
        );
        
        // Get the generated ID
        Long id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
        timetableEntry.setId(id);
        return timetableEntry;
    }
    
    @Override
    public TimetableEntry update(TimetableEntry timetableEntry) {
        String sql = """
            UPDATE timetable_entries SET subject_id = ?, teacher_id = ?, day_of_week = ?, 
            period = ?, start_time = ?, end_time = ?, room = ?, batch = ?, type = ?
            WHERE id = ?
            """;
        jdbcTemplate.update(sql,
            timetableEntry.getSubjectId(),
            timetableEntry.getTeacherId(),
            timetableEntry.getDayOfWeek().name(),
            timetableEntry.getPeriod(),
            timetableEntry.getStartTime(),
            timetableEntry.getEndTime(),
            timetableEntry.getRoom(),
            timetableEntry.getBatch(),
            timetableEntry.getType().name(),
            timetableEntry.getId()
        );
        return timetableEntry;
    }
    
    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM timetable_entries WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
