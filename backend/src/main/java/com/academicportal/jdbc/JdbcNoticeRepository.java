package com.academicportal.jdbc;

import com.academicportal.entity.Notice;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JdbcNoticeRepository {
    
    private final JdbcTemplate jdbcTemplate;
    
    private final RowMapper<Notice> noticeRowMapper = (rs, rowNum) -> {
        Notice notice = new Notice();
        notice.setId(rs.getLong("id"));
        notice.setTitle(rs.getString("title"));
        notice.setContent(rs.getString("content"));
        notice.setAuthorId(rs.getLong("author_id"));
        notice.setAuthorName(rs.getString("author_name"));
        notice.setType(Notice.NoticeType.valueOf(rs.getString("type")));
        notice.setPriority(Notice.Priority.valueOf(rs.getString("priority")));
        notice.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        notice.setExpiresAt(rs.getTimestamp("expires_at") != null ? 
                           rs.getTimestamp("expires_at").toLocalDateTime() : null);
        notice.setAttachmentUrl(rs.getString("attachment_url"));
        notice.setAttachmentName(rs.getString("attachment_name"));
        return notice;
    };
    
    public Notice save(Notice notice) {
        if (notice.getId() == null) {
            return insert(notice);
        } else {
            return update(notice);
        }
    }
    
    public Notice insert(Notice notice) {
        String sql = "INSERT INTO notices (title, content, author_id, type, priority, expires_at, attachment_url, attachment_name) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        KeyHolder keyHolder = new GeneratedKeyHolder();
        
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, notice.getTitle());
            ps.setString(2, notice.getContent());
            ps.setLong(3, notice.getAuthorId());
            ps.setString(4, notice.getType().name());
            ps.setString(5, notice.getPriority().name());
            ps.setTimestamp(6, notice.getExpiresAt() != null ? 
                          java.sql.Timestamp.valueOf(notice.getExpiresAt()) : null);
            ps.setString(7, notice.getAttachmentUrl());
            ps.setString(8, notice.getAttachmentName());
            return ps;
        }, keyHolder);
        
        notice.setId(keyHolder.getKey().longValue());
        return notice;
    }
    
    public Notice update(Notice notice) {
        String sql = "UPDATE notices SET title = ?, content = ?, type = ?, priority = ?, expires_at = ?, " +
                    "attachment_url = ?, attachment_name = ? WHERE id = ?";
        
        jdbcTemplate.update(sql, 
            notice.getTitle(),
            notice.getContent(),
            notice.getType().name(),
            notice.getPriority().name(),
            notice.getExpiresAt() != null ? java.sql.Timestamp.valueOf(notice.getExpiresAt()) : null,
            notice.getAttachmentUrl(),
            notice.getAttachmentName(),
            notice.getId());
        
        return notice;
    }
    
    public List<Notice> findAll() {
        String sql = """
            SELECT n.*, u.name as author_name
            FROM notices n
            JOIN users u ON n.author_id = u.id
            ORDER BY n.priority DESC, n.created_at DESC
            """;
        return jdbcTemplate.query(sql, noticeRowMapper);
    }
    
    public List<Notice> findActiveNotices(LocalDateTime since, LocalDateTime now) {
        String sql = """
            SELECT n.*, u.name as author_name
            FROM notices n
            JOIN users u ON n.author_id = u.id
            WHERE n.created_at >= ? AND (n.expires_at IS NULL OR n.expires_at >= ?)
            ORDER BY n.priority DESC, n.created_at DESC
            """;
        return jdbcTemplate.query(sql, noticeRowMapper, since, now);
    }
    
    public List<Notice> findActiveNoticesByType(Notice.NoticeType type, LocalDateTime since, LocalDateTime now) {
        String sql = """
            SELECT n.*, u.name as author_name
            FROM notices n
            JOIN users u ON n.author_id = u.id
            WHERE n.type = ? AND n.created_at >= ? AND (n.expires_at IS NULL OR n.expires_at >= ?)
            ORDER BY n.priority DESC, n.created_at DESC
            """;
        return jdbcTemplate.query(sql, noticeRowMapper, type.name(), since, now);
    }
    
    public List<Notice> findByAuthorId(Long authorId) {
        String sql = """
            SELECT n.*, u.name as author_name
            FROM notices n
            JOIN users u ON n.author_id = u.id
            WHERE n.author_id = ?
            ORDER BY n.created_at DESC
            """;
        return jdbcTemplate.query(sql, noticeRowMapper, authorId);
    }
    
    public Notice findById(Long id) {
        String sql = """
            SELECT n.*, u.name as author_name
            FROM notices n
            JOIN users u ON n.author_id = u.id
            WHERE n.id = ?
            """;
        List<Notice> notices = jdbcTemplate.query(sql, noticeRowMapper, id);
        return notices.isEmpty() ? null : notices.get(0);
    }
    
    public List<Notice> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate) {
        String sql = """
            SELECT n.*, u.name as author_name
            FROM notices n
            JOIN users u ON n.author_id = u.id
            WHERE n.created_at BETWEEN ? AND ?
            ORDER BY n.created_at DESC
            """;
        return jdbcTemplate.query(sql, noticeRowMapper, startDate, endDate);
    }
    
    public void deleteById(Long id) {
        String sql = "DELETE FROM notices WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
    
    public List<Notice> findAllOrderByPriorityAndDate() {
        String sql = """
            SELECT n.*, u.name as author_name
            FROM notices n
            JOIN users u ON n.author_id = u.id
            ORDER BY n.priority DESC, n.created_at DESC
            """;
        return jdbcTemplate.query(sql, noticeRowMapper);
    }
    
    public long count() {
        String sql = "SELECT COUNT(*) FROM notices";
        Long count = jdbcTemplate.queryForObject(sql, Long.class);
        return count != null ? count : 0;
    }
}





