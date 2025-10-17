package com.academicportal.jdbc;

import com.academicportal.entity.StudyMaterial;
import com.academicportal.repository.StudyMaterialRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcStudyMaterialRepository implements StudyMaterialRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcStudyMaterialRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    private final RowMapper<StudyMaterial> studyMaterialRowMapper = new RowMapper<StudyMaterial>() {
        @Override
        public StudyMaterial mapRow(ResultSet rs, int rowNum) throws SQLException {
            StudyMaterial material = new StudyMaterial();
            material.setId(rs.getLong("id"));
            material.setTitle(rs.getString("title"));
            material.setDescription(rs.getString("description"));
            material.setSubjectId(rs.getLong("subject_id"));
            material.setUploadedById(rs.getLong("uploaded_by"));
            material.setType(StudyMaterial.MaterialType.valueOf(rs.getString("type")));
            material.setFileName(rs.getString("file_name"));
            material.setFilePath(rs.getString("file_path"));
            material.setFileSize(rs.getLong("file_size"));
            material.setMimeType(rs.getString("mime_type"));
            material.setTags(rs.getString("tags"));
            material.setIsPublic(rs.getBoolean("is_public"));
            return material;
        }
    };
    
    @Override
    public List<StudyMaterial> findAll() {
        String sql = "SELECT * FROM study_materials";
        return jdbcTemplate.query(sql, studyMaterialRowMapper);
    }
    
    @Override
    public StudyMaterial findById(Long id) {
        String sql = "SELECT * FROM study_materials WHERE id = ?";
        List<StudyMaterial> materials = jdbcTemplate.query(sql, studyMaterialRowMapper, id);
        return materials.stream().findFirst().orElse(null);
    }
    
    @Override
    public List<StudyMaterial> findBySubjectId(Long subjectId) {
        String sql = "SELECT * FROM study_materials WHERE subject_id = ?";
        return jdbcTemplate.query(sql, studyMaterialRowMapper, subjectId);
    }
    
    @Override
    public List<StudyMaterial> findByUploadedById(Long uploadedById) {
        String sql = "SELECT * FROM study_materials WHERE uploaded_by = ?";
        return jdbcTemplate.query(sql, studyMaterialRowMapper, uploadedById);
    }
    
    @Override
    public List<StudyMaterial> findByType(StudyMaterial.MaterialType type) {
        String sql = "SELECT * FROM study_materials WHERE type = ?";
        return jdbcTemplate.query(sql, studyMaterialRowMapper, type.name());
    }
    
    @Override
    public List<StudyMaterial> findByStudentId(Long studentId) {
        String sql = """
            SELECT sm.* FROM study_materials sm
            JOIN subjects s ON sm.subject_id = s.id
            JOIN subject_students ss ON s.id = ss.subject_id
            WHERE ss.student_id = ?
            """;
        return jdbcTemplate.query(sql, studyMaterialRowMapper, studentId);
    }
    
    @Override
    public List<StudyMaterial> findByTeacherId(Long teacherId) {
        String sql = """
            SELECT sm.* FROM study_materials sm
            JOIN subjects s ON sm.subject_id = s.id
            WHERE s.teacher_id = ?
            """;
        return jdbcTemplate.query(sql, studyMaterialRowMapper, teacherId);
    }
    
    @Override
    public List<StudyMaterial> findByTitleContainingIgnoreCase(String title) {
        String sql = "SELECT * FROM study_materials WHERE LOWER(title) LIKE LOWER(?)";
        return jdbcTemplate.query(sql, studyMaterialRowMapper, "%" + title + "%");
    }
    
    @Override
    public List<StudyMaterial> findByIsPublicTrue() {
        String sql = "SELECT * FROM study_materials WHERE is_public = true";
        return jdbcTemplate.query(sql, studyMaterialRowMapper);
    }
    
    @Override
    public List<StudyMaterial> findPublicBySubjectId(Long subjectId) {
        String sql = "SELECT * FROM study_materials WHERE subject_id = ? AND is_public = true";
        return jdbcTemplate.query(sql, studyMaterialRowMapper, subjectId);
    }
    
    @Override
    public StudyMaterial save(StudyMaterial studyMaterial) {
        String sql = """
            INSERT INTO study_materials (title, description, subject_id, uploaded_by, type, 
            file_name, file_path, file_size, mime_type, tags, is_public)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;
        jdbcTemplate.update(sql,
            studyMaterial.getTitle(),
            studyMaterial.getDescription(),
            studyMaterial.getSubjectId(),
            studyMaterial.getUploadedById(),
            studyMaterial.getType().name(),
            studyMaterial.getFileName(),
            studyMaterial.getFilePath(),
            studyMaterial.getFileSize(),
            studyMaterial.getMimeType(),
            studyMaterial.getTags(),
            studyMaterial.getIsPublic()
        );
        
        // Get the generated ID
        Long id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
        studyMaterial.setId(id);
        return studyMaterial;
    }
    
    @Override
    public StudyMaterial update(StudyMaterial studyMaterial) {
        String sql = """
            UPDATE study_materials SET title = ?, description = ?, subject_id = ?, 
            type = ?, tags = ?, is_public = ? WHERE id = ?
            """;
        jdbcTemplate.update(sql,
            studyMaterial.getTitle(),
            studyMaterial.getDescription(),
            studyMaterial.getSubjectId(),
            studyMaterial.getType().name(),
            studyMaterial.getTags(),
            studyMaterial.getIsPublic(),
            studyMaterial.getId()
        );
        return studyMaterial;
    }
    
    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM study_materials WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
