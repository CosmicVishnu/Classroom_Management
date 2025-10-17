package com.academicportal.jdbc;

import com.academicportal.entity.Event;
import com.academicportal.repository.EventRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcEventRepository implements EventRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcEventRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    private final RowMapper<Event> eventRowMapper = new RowMapper<Event>() {
        @Override
        public Event mapRow(ResultSet rs, int rowNum) throws SQLException {
            Event event = new Event();
            event.setId(rs.getLong("id"));
            event.setTitle(rs.getString("title"));
            event.setDescription(rs.getString("description"));
            event.setEventDate(rs.getDate("event_date").toLocalDate());
            
            // Handle nullable time fields
            java.sql.Time startTime = rs.getTime("start_time");
            if (startTime != null) {
                event.setStartTime(startTime.toString());
            }

            java.sql.Time endTime = rs.getTime("end_time");
            if (endTime != null) {
                event.setEndTime(endTime.toString());
            }
            
            event.setLocation(rs.getString("location"));
            event.setType(Event.EventType.valueOf(rs.getString("type")));
            event.setPriority(Event.Priority.valueOf(rs.getString("priority")));
            event.setCreatedById(rs.getLong("created_by"));
            event.setIsAllDay(rs.getBoolean("is_all_day"));
            event.setColor(rs.getString("color"));
            return event;
        }
    };
    
    @Override
    public List<Event> findAll() {
        String sql = "SELECT * FROM events";
        return jdbcTemplate.query(sql, eventRowMapper);
    }
    
    @Override
    public Event findById(Long id) {
        String sql = "SELECT * FROM events WHERE id = ?";
        List<Event> events = jdbcTemplate.query(sql, eventRowMapper, id);
        return events.stream().findFirst().orElse(null);
    }
    
    @Override
    public List<Event> findByCreatedById(Long createdById) {
        String sql = "SELECT * FROM events WHERE created_by = ?";
        return jdbcTemplate.query(sql, eventRowMapper, createdById);
    }
    
    @Override
    public List<Event> findByType(Event.EventType type) {
        String sql = "SELECT * FROM events WHERE type = ?";
        return jdbcTemplate.query(sql, eventRowMapper, type.name());
    }
    
    @Override
    public List<Event> findByEventDateBetween(LocalDate startDate, LocalDate endDate) {
        String sql = "SELECT * FROM events WHERE event_date BETWEEN ? AND ?";
        return jdbcTemplate.query(sql, eventRowMapper, startDate, endDate);
    }
    
    @Override
    public List<Event> findByEventDate(LocalDate eventDate) {
        String sql = "SELECT * FROM events WHERE event_date = ?";
        return jdbcTemplate.query(sql, eventRowMapper, eventDate);
    }
    
    @Override
    public List<Event> findEventsInRange(LocalDate startDate, LocalDate endDate) {
        String sql = "SELECT * FROM events WHERE event_date BETWEEN ? AND ? ORDER BY event_date, start_time";
        return jdbcTemplate.query(sql, eventRowMapper, startDate, endDate);
    }
    
    @Override
    public List<Event> findByDateOrderByTime(LocalDate date) {
        String sql = "SELECT * FROM events WHERE event_date = ? ORDER BY start_time";
        return jdbcTemplate.query(sql, eventRowMapper, date);
    }
    
    @Override
    public List<Event> findByPriority(Event.Priority priority) {
        String sql = "SELECT * FROM events WHERE priority = ?";
        return jdbcTemplate.query(sql, eventRowMapper, priority.name());
    }
    
    @Override
    public List<Event> findAllOrderByPriorityAndDate() {
        String sql = "SELECT * FROM events ORDER BY priority DESC, event_date ASC, start_time ASC";
        return jdbcTemplate.query(sql, eventRowMapper);
    }
    
    @Override
    public Event save(Event event) {
        String sql = """
            INSERT INTO events (title, description, event_date, start_time, end_time, 
            location, type, priority, created_by, is_all_day, color)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;
        jdbcTemplate.update(sql,
            event.getTitle(),
            event.getDescription(),
            event.getEventDate(),
            event.getStartTime(),
            event.getEndTime(),
            event.getLocation(),
            event.getType().name(),
            event.getPriority().name(),
            event.getCreatedById(),
            event.getIsAllDay(),
            event.getColor()
        );
        
        // Get the generated ID
        Long id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
        event.setId(id);
        return event;
    }
    
    @Override
    public Event update(Event event) {
        String sql = """
            UPDATE events SET title = ?, description = ?, event_date = ?, start_time = ?, 
            end_time = ?, location = ?, type = ?, priority = ?, is_all_day = ?, color = ?
            WHERE id = ?
            """;
        jdbcTemplate.update(sql,
            event.getTitle(),
            event.getDescription(),
            event.getEventDate(),
            event.getStartTime(),
            event.getEndTime(),
            event.getLocation(),
            event.getType().name(),
            event.getPriority().name(),
            event.getIsAllDay(),
            event.getColor(),
            event.getId()
        );
        return event;
    }
    
    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM events WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
