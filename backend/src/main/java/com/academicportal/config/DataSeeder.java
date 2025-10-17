package com.academicportal.config;

import com.academicportal.entity.*;
import com.academicportal.jdbc.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {
    
    private final JdbcUserRepository userRepository;
    private final JdbcSubjectRepository subjectRepository;
    private final JdbcAttendanceRepository attendanceRepository;
    private final JdbcNoticeRepository noticeRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) throws Exception {
        // Data is now seeded via data.sql file
        // This method can be used for additional runtime seeding if needed
        System.out.println("Data seeding completed via SQL scripts");
    }
    
}
