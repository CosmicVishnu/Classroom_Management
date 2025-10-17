package com.academicportal.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.academicportal.dto.StudyMaterialDto;
import com.academicportal.entity.StudyMaterial;
import com.academicportal.jdbc.JdbcStudyMaterialRepository;
import com.academicportal.jdbc.JdbcUserRepository;

@Service
public class StudyMaterialService {
    
    private final JdbcStudyMaterialRepository studyMaterialRepository;
    private final JdbcUserRepository userRepository;
    private final String uploadDir = "uploads/study-materials";

    public StudyMaterialService(JdbcStudyMaterialRepository studyMaterialRepository, JdbcUserRepository userRepository) {
        this.studyMaterialRepository = studyMaterialRepository;
        this.userRepository = userRepository;
    }
    
    public List<StudyMaterialDto> getAllStudyMaterials() {
        return studyMaterialRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public Optional<StudyMaterialDto> getStudyMaterialById(Long id) {
        StudyMaterial material = studyMaterialRepository.findById(id);
        return material != null ? Optional.of(convertToDto(material)) : Optional.empty();
    }
    
    public List<StudyMaterialDto> getStudyMaterialsBySubject(Long subjectId) {
        return studyMaterialRepository.findBySubjectId(subjectId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public List<StudyMaterialDto> getStudyMaterialsByUploader(Long uploadedById) {
        return studyMaterialRepository.findByUploadedById(uploadedById).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public List<StudyMaterialDto> getStudyMaterialsByType(StudyMaterial.MaterialType type) {
        return studyMaterialRepository.findByType(type).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public List<StudyMaterialDto> getPublicStudyMaterials() {
        return studyMaterialRepository.findByIsPublicTrue().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public List<StudyMaterialDto> getPublicStudyMaterialsBySubject(Long subjectId) {
        return studyMaterialRepository.findPublicBySubjectId(subjectId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public List<StudyMaterialDto> searchStudyMaterialsByTitle(String title) {
        return studyMaterialRepository.findByTitleContainingIgnoreCase(title).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public StudyMaterialDto createStudyMaterial(StudyMaterialDto studyMaterialDto, MultipartFile file) {
        try {
            // Create upload directory if it doesn't exist
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            
            // Generate unique filename
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String uniqueFilename = UUID.randomUUID().toString() + fileExtension;
            
            // Save file
            Path filePath = uploadPath.resolve(uniqueFilename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            
            // Create study material entity
            StudyMaterial studyMaterial = new StudyMaterial();
            studyMaterial.setTitle(studyMaterialDto.getTitle());
            studyMaterial.setDescription(studyMaterialDto.getDescription());
            studyMaterial.setSubjectId(studyMaterialDto.getSubjectId());
            studyMaterial.setUploadedById(studyMaterialDto.getUploadedById());
            studyMaterial.setType(studyMaterialDto.getType());
            studyMaterial.setFileName(originalFilename);
            studyMaterial.setFilePath(filePath.toString());
            studyMaterial.setFileSize(file.getSize());
            studyMaterial.setMimeType(file.getContentType());
            studyMaterial.setTags(studyMaterialDto.getTags());
            studyMaterial.setIsPublic(studyMaterialDto.getIsPublic());
            
            StudyMaterial savedMaterial = studyMaterialRepository.save(studyMaterial);
            return convertToDto(savedMaterial);
            
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file: " + e.getMessage());
        }
    }
    
    public StudyMaterialDto updateStudyMaterial(Long id, StudyMaterialDto studyMaterialDto) {
        StudyMaterial existingMaterial = studyMaterialRepository.findById(id);
        if (existingMaterial == null) {
            throw new RuntimeException("Study material not found");
        }
        
        // Update fields
        existingMaterial.setTitle(studyMaterialDto.getTitle());
        existingMaterial.setDescription(studyMaterialDto.getDescription());
        existingMaterial.setSubjectId(studyMaterialDto.getSubjectId());
        existingMaterial.setType(studyMaterialDto.getType());
        existingMaterial.setTags(studyMaterialDto.getTags());
        existingMaterial.setIsPublic(studyMaterialDto.getIsPublic());
        
        StudyMaterial updatedMaterial = studyMaterialRepository.update(existingMaterial);
        return convertToDto(updatedMaterial);
    }
    
    public void deleteStudyMaterial(Long id) {
        StudyMaterial material = studyMaterialRepository.findById(id);
        if (material != null) {
            // Delete file from filesystem
            try {
                Files.deleteIfExists(Paths.get(material.getFilePath()));
            } catch (IOException e) {
                // Log error but don't fail the operation
                System.err.println("Failed to delete file: " + e.getMessage());
            }
        }
        studyMaterialRepository.deleteById(id);
    }
    
    private StudyMaterialDto convertToDto(StudyMaterial material) {
        StudyMaterialDto dto = new StudyMaterialDto();
        dto.setId(material.getId());
        dto.setTitle(material.getTitle());
        dto.setDescription(material.getDescription());
        dto.setSubjectId(material.getSubjectId());
        dto.setUploadedById(material.getUploadedById());
        dto.setType(material.getType());
        dto.setFileName(material.getFileName());
        dto.setFilePath(material.getFilePath());
        dto.setFileSize(material.getFileSize());
        dto.setMimeType(material.getMimeType());
        dto.setTags(material.getTags());
        dto.setIsPublic(material.getIsPublic());
        
        // Set names
        if (material.getSubjectId() != null) {
            // Note: You might need to add a method to get subject name
            dto.setSubjectName("Subject Name"); // Placeholder
        }
        
        if (material.getUploadedById() != null) {
            userRepository.findById(material.getUploadedById())
                    .ifPresent(uploader -> dto.setUploadedByName(uploader.getName()));
        }
        
        return dto;
    }
}
