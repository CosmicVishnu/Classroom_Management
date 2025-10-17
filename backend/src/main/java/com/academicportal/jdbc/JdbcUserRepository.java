package com.academicportal.jdbc;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.academicportal.entity.User;

@Repository
public class JdbcUserRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    private final RowMapper<User> userRowMapper = (rs, rowNum) -> {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setUserId(rs.getString("user_id"));
        user.setName(rs.getString("name"));
        user.setRole(User.UserRole.valueOf(rs.getString("role")));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setDepartment(rs.getString("department"));
        user.setStatus(User.UserStatus.valueOf(rs.getString("status")));
        user.setJoinDate(rs.getDate("join_date") != null ? rs.getDate("join_date").toLocalDate() : null);
        user.setAvatar(rs.getString("avatar"));
        return user;
    };
    
    public User save(User user) {
        if (user.getId() == null) {
            return insert(user);
        } else {
            return update(user);
        }
    }
    
    public User insert(User user) {
        String sql = "INSERT INTO users (user_id, name, role, email, password, department, status, join_date, avatar) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        KeyHolder keyHolder = new GeneratedKeyHolder();
        
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getUserId());
            ps.setString(2, user.getName());
            ps.setString(3, user.getRole().name());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getPassword());
            ps.setString(6, user.getDepartment());
            ps.setString(7, user.getStatus().name());
            ps.setDate(8, user.getJoinDate() != null ? java.sql.Date.valueOf(user.getJoinDate()) : null);
            ps.setString(9, user.getAvatar());
            return ps;
        }, keyHolder);
        
        user.setId(keyHolder.getKey().longValue());
        return user;
    }
    
    public User update(User user) {
        String sql = "UPDATE users SET name = ?, email = ?, department = ?, status = ?, avatar = ? WHERE id = ?";
        
        jdbcTemplate.update(sql, 
            user.getName(),
            user.getEmail(),
            user.getDepartment(),
            user.getStatus().name(),
            user.getAvatar(),
            user.getId());
        
        return user;
    }
    
    public Optional<User> findById(Long id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        List<User> users = jdbcTemplate.query(sql, userRowMapper, id);
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }
    
    public Optional<User> findByUserId(String userId) {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        List<User> users = jdbcTemplate.query(sql, userRowMapper, userId);
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }
    
    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        List<User> users = jdbcTemplate.query(sql, userRowMapper, email);
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }
    
    public List<User> findAll() {
        String sql = "SELECT * FROM users ORDER BY id";
        return jdbcTemplate.query(sql, userRowMapper);
    }
    
    public List<User> findByRole(User.UserRole role) {
        String sql = "SELECT * FROM users WHERE role = ? ORDER BY id";
        return jdbcTemplate.query(sql, userRowMapper, role.name());
    }
    
    public List<User> findByStatus(User.UserStatus status) {
        String sql = "SELECT * FROM users WHERE status = ? ORDER BY id";
        return jdbcTemplate.query(sql, userRowMapper, status.name());
    }
    
    public List<User> findByDepartment(String department) {
        String sql = "SELECT * FROM users WHERE department = ? ORDER BY id";
        return jdbcTemplate.query(sql, userRowMapper, department);
    }
    
    public List<User> findByRoleAndStatus(User.UserRole role, User.UserStatus status) {
        String sql = "SELECT * FROM users WHERE role = ? AND status = ? ORDER BY id";
        return jdbcTemplate.query(sql, userRowMapper, role.name(), status.name());
    }
    
    public boolean existsByUserId(String userId) {
        String sql = "SELECT COUNT(*) FROM users WHERE user_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, userId);
        return count != null && count > 0;
    }
    
    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }
    
    public void deleteById(Long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
    
    public long count() {
        String sql = "SELECT COUNT(*) FROM users";
        Long count = jdbcTemplate.queryForObject(sql, Long.class);
        return count != null ? count : 0;
    }
}





