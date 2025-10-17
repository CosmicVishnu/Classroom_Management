package com.academicportal.dto;

import com.academicportal.entity.Subject;

// Validation annotations removed for now

public class SubjectDto {

    private Long id;

    private String name;
    private String code;
    private String description;
    private Long teacherId;

    private String teacherName;

    private Subject.MaterialType type;

    private String department;

    private Integer credits;

    private String semester;

    private String academicYear;

    public SubjectDto() {}

    public SubjectDto(Long id, String name, String code, String description, Long teacherId, String teacherName, Subject.MaterialType type, String department, Integer credits, String semester, String academicYear) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.description = description;
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.type = type;
        this.department = department;
        this.credits = credits;
        this.semester = semester;
        this.academicYear = academicYear;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Long getTeacherId() { return teacherId; }
    public void setTeacherId(Long teacherId) { this.teacherId = teacherId; }

    public String getTeacherName() { return teacherName; }
    public void setTeacherName(String teacherName) { this.teacherName = teacherName; }

    public Subject.MaterialType getType() { return type; }
    public void setType(Subject.MaterialType type) { this.type = type; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public Integer getCredits() { return credits; }
    public void setCredits(Integer credits) { this.credits = credits; }

    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }

    public String getAcademicYear() { return academicYear; }
    public void setAcademicYear(String academicYear) { this.academicYear = academicYear; }
}
