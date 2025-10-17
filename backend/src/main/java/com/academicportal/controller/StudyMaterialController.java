package com.academicportal.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.academicportal.dto.StudyMaterialDto;
import com.academicportal.dto.UserDto;
import com.academicportal.entity.User;
import com.academicportal.service.AuthService;
import com.academicportal.service.StudyMaterialService;

@RestController
@RequestMapping("/study-materials")
@CrossOrigin(origins = "*")
public class StudyMaterialController {

    private final StudyMaterialService studyMaterialService;
    private final AuthService authService;

    public StudyMaterialController(StudyMaterialService studyMaterialService, AuthService authService) {
        this.studyMaterialService = studyMaterialService;
        this.authService = authService;
    }
    
    @GetMapping
    public ResponseEntity<List<StudyMaterialDto>> getAllStudyMaterials() {
        List<StudyMaterialDto> materials = studyMaterialService.getAllStudyMaterials();
        return ResponseEntity.ok(materials);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<StudyMaterialDto> getStudyMaterialById(@PathVariable Long id) {
        return studyMaterialService.getStudyMaterialById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/subject/{subjectId}")
    public ResponseEntity<List<StudyMaterialDto>> getStudyMaterialsBySubject(@PathVariable Long subjectId) {
        List<StudyMaterialDto> materials = studyMaterialService.getStudyMaterialsBySubject(subjectId);
        return ResponseEntity.ok(materials);
    }
    
    @GetMapping("/uploader/{uploadedById}")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<List<StudyMaterialDto>> getStudyMaterialsByUploader(@PathVariable Long uploadedById) {
        UserDto currentUser = authService.getCurrentUser();
        
        // Teachers can only view their own materials unless admin
        if (currentUser.getRole().equals(User.UserRole.TEACHER.toString()) && !currentUser.getId().equals(uploadedById)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        List<StudyMaterialDto> materials = studyMaterialService.getStudyMaterialsByUploader(uploadedById);
        return ResponseEntity.ok(materials);
    }
    
    @GetMapping("/my")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<List<StudyMaterialDto>> getMyStudyMaterials() {
        UserDto currentUser = authService.getCurrentUser();
        List<StudyMaterialDto> materials = studyMaterialService.getStudyMaterialsByUploader(currentUser.getId());
        return ResponseEntity.ok(materials);
    }
    
    @GetMapping("/type/{type}")
    public ResponseEntity<List<StudyMaterialDto>> getStudyMaterialsByType(@PathVariable String type) {
        try {
            com.academicportal.entity.StudyMaterial.MaterialType materialType = 
                com.academicportal.entity.StudyMaterial.MaterialType.valueOf(type.toUpperCase());
            List<StudyMaterialDto> materials = studyMaterialService.getStudyMaterialsByType(materialType);
            return ResponseEntity.ok(materials);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/public")
    public ResponseEntity<List<StudyMaterialDto>> getPublicStudyMaterials() {
        List<StudyMaterialDto> materials = studyMaterialService.getPublicStudyMaterials();
        return ResponseEntity.ok(materials);
    }
    
    @GetMapping("/public/subject/{subjectId}")
    public ResponseEntity<List<StudyMaterialDto>> getPublicStudyMaterialsBySubject(@PathVariable Long subjectId) {
        List<StudyMaterialDto> materials = studyMaterialService.getPublicStudyMaterialsBySubject(subjectId);
        return ResponseEntity.ok(materials);
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<StudyMaterialDto>> searchStudyMaterialsByTitle(@RequestParam String title) {
        List<StudyMaterialDto> materials = studyMaterialService.searchStudyMaterialsByTitle(title);
        return ResponseEntity.ok(materials);
    }
    
    @PostMapping(consumes = "multipart/form-data")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<StudyMaterialDto> createStudyMaterial(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("subjectId") Long subjectId,
            @RequestParam("type") String type,
            @RequestParam("tags") String tags,
            @RequestParam("isPublic") Boolean isPublic,
            @RequestParam("file") MultipartFile file) {
        
        try {
            StudyMaterialDto studyMaterialDto = new StudyMaterialDto();
            studyMaterialDto.setTitle(title);
            studyMaterialDto.setDescription(description);
            studyMaterialDto.setSubjectId(subjectId);
            studyMaterialDto.setType(com.academicportal.entity.StudyMaterial.MaterialType.valueOf(type.toUpperCase()));
            studyMaterialDto.setTags(tags);
            studyMaterialDto.setIsPublic(isPublic);
            
            UserDto currentUser = authService.getCurrentUser();
            studyMaterialDto.setUploadedById(currentUser.getId());
            
            StudyMaterialDto createdMaterial = studyMaterialService.createStudyMaterial(studyMaterialDto, file);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdMaterial);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<StudyMaterialDto> updateStudyMaterial(@PathVariable Long id, @RequestBody StudyMaterialDto studyMaterialDto) {
        UserDto currentUser = authService.getCurrentUser();
        StudyMaterialDto updatedMaterial = studyMaterialService.updateStudyMaterial(id, studyMaterialDto);
        return ResponseEntity.ok(updatedMaterial);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER') or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteStudyMaterial(@PathVariable Long id) {
        studyMaterialService.deleteStudyMaterial(id);
        return ResponseEntity.noContent().build();
    }
}
