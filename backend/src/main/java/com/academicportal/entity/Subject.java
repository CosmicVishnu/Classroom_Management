package com.academicportal.entity;

import java.util.ArrayList;
import java.util.List;

public class Subject {

    private Long id;
    private String name;
    private String code;
    private String description;
    private Long teacherId;
    private String teacherName;
    private List<Long> studentIds = new ArrayList<>();
    private List<String> studentNames = new ArrayList<>();

    // Additional fields
    private MaterialType type;
    private String department;
    private Integer credits;
    private String semester;
    private String academicYear;

    public Subject() {}

    public Subject(Long id, String name, String code, String description, Long teacherId, String teacherName, List<Long> studentIds, List<String> studentNames, MaterialType type, String department, Integer credits, String semester, String academicYear) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.description = description;
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.studentIds = studentIds;
        this.studentNames = studentNames;
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

    public List<Long> getStudentIds() { return studentIds; }
    public void setStudentIds(List<Long> studentIds) { this.studentIds = studentIds; }

    public List<String> getStudentNames() { return studentNames; }
    public void setStudentNames(List<String> studentNames) { this.studentNames = studentNames; }

    public MaterialType getType() { return type; }
    public void setType(MaterialType type) { this.type = type; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public Integer getCredits() { return credits; }
    public void setCredits(Integer credits) { this.credits = credits; }

    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }

    public String getAcademicYear() { return academicYear; }
    public void setAcademicYear(String academicYear) { this.academicYear = academicYear; }
    
    public enum MaterialType {
        LECTURE_NOTES, ASSIGNMENT, REFERENCE_BOOK, VIDEO, PRESENTATION, 
        PAST_PAPERS, SYLLABUS, TUTORIAL_SHEET, LAB_MANUAL
    }
}
