# Academic Portal Backend

A Spring Boot REST API backend for the Academic Portal application that provides user management, attendance tracking, timetable management, notices, study materials, and events.

## Features

- **User Management**: Support for Students, Teachers, and Admins with role-based access control
- **Authentication & Authorization**: JWT-based authentication with Spring Security
- **Attendance Tracking**: Mark and track student attendance with detailed reporting
- **Timetable Management**: Manage class schedules and timetables
- **Notices & Announcements**: Create and manage notices with different priorities and types
- **Study Materials**: Upload and manage study materials with file support
- **Events Calendar**: Manage academic events, exams, and holidays
- **Database Integration**: MySQL database with JDBC/JdbcTemplate

## Technology Stack

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Security** - Authentication and authorization
- **Spring JDBC** - Database operations with JdbcTemplate
- **MySQL Database** - Relational database for production-ready applications
- **JWT** - JSON Web Tokens for authentication
- **Maven** - Build automation and dependency management
- **Lombok** - Boilerplate code reduction

## Prerequisites

- Java 17 or higher
- MySQL 8.0 or higher
- Maven 3.6 or higher
- IDE (IntelliJ IDEA, Eclipse, VS Code)

## Getting Started

### 1. Clone the Repository

```bash
git clone <repository-url>
cd backend
```

### 1.1. Quick Setup (Windows)

Run the setup script to automatically configure Maven and compile the project:

```bash
setup-maven.bat
```

This will:
- Check if Maven and Java are installed
- Clean and compile the project
- Provide instructions for running the application

### 2. Setup MySQL Database

**Option A: Using the setup script (Windows)**
```bash
setup-mysql.bat
```

**Option B: Manual setup**
```bash
# Start MySQL service
net start mysql

# Connect to MySQL and create database
mysql -u root -p
```

Then run the SQL commands from `mysql-setup.sql`:
```sql
CREATE DATABASE IF NOT EXISTS academic_portal;
USE academic_portal;
-- ... (run the schema.sql and data.sql files)
```

### 3. Build the Project

```bash
mvn clean compile
```

Or use the wrapper script:
```bash
mvnw.bat build
```

### 4. Run the Application

```bash
mvn spring-boot:run
```

Or use the wrapper script:
```bash
mvnw.bat run
```

### 4.1. Link to Academic Portal Folder

To link this backend to the main Academic Portal folder (`C:\Users\ASUS\Desktop\Academic portal`):

```bash
link-to-academic-portal.bat
```

This will create a symbolic link so you can access the backend from the main Academic Portal directory.

The application will start on `http://localhost:8080`

### 5. Database Connection Details

- **Host**: `localhost`
- **Port**: `3306`
- **Database**: `academic_portal`
- **Username**: `root` (or your configured MySQL user)
- **Password**: `password` (or your MySQL password)

## API Endpoints

### Authentication
- `POST /api/auth/login` - User login
- `GET /api/auth/me` - Get current user details

### Users
- `GET /api/users` - Get all users (Admin only)
- `GET /api/users/{id}` - Get user by ID
- `GET /api/users/role/{role}` - Get users by role
- `GET /api/users/students` - Get all students
- `GET /api/users/teachers` - Get all teachers
- `POST /api/users` - Create user (Admin only)
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Delete user (Admin only)

### Attendance
- `POST /api/attendance` - Mark attendance (Teacher/Admin)
- `GET /api/attendance/student/{studentId}` - Get student attendance
- `GET /api/attendance/student/{studentId}/subject/{subjectId}` - Get attendance by student and subject
- `GET /api/attendance/percentage/student/{studentId}/subject/{subjectId}` - Get attendance percentage
- `PUT /api/attendance/{id}` - Update attendance
- `DELETE /api/attendance/{id}` - Delete attendance (Admin only)

### Notices
- `GET /api/notices` - Get all notices
- `GET /api/notices/active` - Get active notices
- `GET /api/notices/type/{type}` - Get notices by type
- `POST /api/notices` - Create notice (Teacher/Admin)
- `PUT /api/notices/{id}` - Update notice
- `DELETE /api/notices/{id}` - Delete notice

## Initial Data

The application automatically seeds the database with initial data including:

### Users
- **Students**: S001 (Nava), S002 (Aryan), S003 (Vishnu), S004 (Niloofer), S005 (Rashid), S006 (George)
- **Teachers**: T001 (Nilakshi), T002 (Rekha R)
- **Admin**: A001 (Nitha)

### Default Passwords
- Students: `password123`
- Teachers: `password456`
- Admin: `password789`

### Subjects
- Mathematics (MATH101)
- Theory of Computation (TOC201)
- Object Oriented Programming (OOP202)
- Data Structures and Algorithms (DSA203)
- Digital Circuits and Logic Design (DCL204)

## Configuration

### Application Properties

The main configuration is in `src/main/resources/application.yml`:

```yaml
server:
  port: 8080
  servlet:
    context-path: /api

spring:
  datasource:
    url: jdbc:h2:mem:academic_portal
    driver-class-name: org.h2.Driver
    username: sa
    password: password
  
  h2:
    console:
      enabled: true
      path: /h2-console
  
  sql:
    init:
      mode: always
      schema-locations: classpath:schema.sql
      data-locations: classpath:data.sql

jwt:
  secret: mySecretKey
  expiration: 86400000 # 24 hours
```

### CORS Configuration

The application is configured to allow CORS requests from:
- `http://localhost:5173` (Vite dev server)
- `http://localhost:3000` (React dev server)

## Security

- **Authentication**: JWT tokens with 24-hour expiration
- **Authorization**: Role-based access control (STUDENT, TEACHER, ADMIN)
- **Password Encryption**: BCrypt password hashing
- **CORS**: Configured for frontend integration

## Database Schema

### Main Tables
- **users**: Students, Teachers, Admins with role-based authentication
- **subjects**: Course subjects with teacher assignments
- **subject_students**: Many-to-many relationship between subjects and students
- **attendances**: Student attendance records with detailed status tracking
- **notices**: Announcements and notices with priorities and expiration
- **study_materials**: Course materials and files with metadata
- **events**: Calendar events and academic schedules
- **timetable_entries**: Class schedules and timetables

### JDBC Implementation
- **JdbcTemplate**: Spring's JDBC abstraction for database operations
- **RowMapper**: Custom mappers for converting ResultSet to entity objects
- **SQL Scripts**: Schema and data initialization via SQL files
- **Manual Query Management**: Direct SQL queries with proper parameter binding

## Development

### Adding New Features

1. Create entity classes in `com.academicportal.entity` (POJOs without JPA annotations)
2. Create JDBC repository classes in `com.academicportal.jdbc` using JdbcTemplate
3. Create service classes in `com.academicportal.service`
4. Create controller classes in `com.academicportal.controller`
5. Add DTOs in `com.academicportal.dto` if needed
6. Update SQL schema in `schema.sql` and data in `data.sql`

### Testing

```bash
mvn test
```

## Production Deployment

For production deployment:

1. Change the database configuration to use a persistent database (MySQL, PostgreSQL)
2. Update JWT secret key
3. Configure proper CORS origins
4. Set up file storage for study materials
5. Configure logging levels

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests
5. Submit a pull request

## License

This project is licensed under the MIT License.
