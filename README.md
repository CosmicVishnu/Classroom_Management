# Academic Portal

A comprehensive academic management system with React frontend and Spring Boot backend, featuring user management, attendance tracking, timetable management, notices, study materials, and events.

## 🏗️ Project Structure

```
Academic Portal/
├── backend/                    # Spring Boot Backend (Maven)
│   ├── src/main/java/         # Java source code
│   ├── src/main/resources/    # Configuration files
│   ├── pom.xml               # Maven configuration
│   └── README.md             # Backend documentation
├── src/                       # React Frontend
│   ├── components/           # React components
│   ├── styles/              # CSS styles
│   └── main.tsx             # Frontend entry point
├── package.json              # Frontend dependencies
├── vite.config.ts           # Vite configuration
└── README.md                 # This file
```

## 🚀 Quick Start

### Prerequisites

- **Java 17** or higher
- **Maven 3.6** or higher
- **Node.js 18** or higher
- **MySQL 8.0** or higher

### Manual Setup

#### Prerequisites

- **Java 17** or higher
- **Maven 3.6** or higher
- **Node.js 18** or higher
- **MySQL 8.0** or higher

#### Backend Setup

```bash
cd backend
mvn clean compile
mvn spring-boot:run
```

#### Frontend Setup

```bash
npm install
npm run dev
```

## 🔧 Configuration

### Database Setup

1. **Start MySQL service:**
   ```bash
   net start mysql
   ```

2. **Create database:**
   ```bash
   mysql -u root -p
   ```
   Then run:
   ```sql
   CREATE DATABASE academic_portal;
   ```

3. **Run setup script:**
   ```bash
   cd backend
   setup-mysql.bat
   ```

### Backend Configuration

The backend configuration is in `backend/src/main/resources/application.yml`:

```yaml
server:
  port: 8080
  servlet:
    context-path: /api

spring:
  datasource:
    url: jdbc:mysql://localhost:3306/academic_portal
    username: root
    password: password
```

### Frontend Configuration

The frontend uses Vite and is configured in `vite.config.ts`. The development server runs on port 5173.

## 🌐 Access Points

- **Frontend**: http://localhost:5173
- **Backend API**: http://localhost:8080/api
- **Backend Health**: http://localhost:8080/api/health

## 👥 Default Users

### Students
- **S001** (Nava) - Password: `password123`
- **S002** (Aryan) - Password: `password123`
- **S003** (Vishnu) - Password: `password123`
- **S004** (Niloofer) - Password: `password123`
- **S005** (Rashid) - Password: `password123`
- **S006** (George) - Password: `password123`

### Teachers
- **T001** (Nilakshi) - Password: `password456`
- **T002** (Rekha R) - Password: `password456`

### Admin
- **A001** (Nitha) - Password: `password789`

## 📚 Features

### Student Features
- View personal dashboard
- Check attendance records
- View timetable
- Access study materials
- Read notices and announcements
- View academic calendar

### Teacher Features
- Mark student attendance
- Post notices and announcements
- Manage timetable
- Upload study materials
- View student information

### Admin Features
- Manage all users (students, teachers, admins)
- Create and edit timetables
- Manage academic events
- System-wide notices
- User role management

## 🛠️ Development

### Backend Development

The backend uses Spring Boot with Maven:

```bash
cd backend

# Compile
mvn compile

# Run tests
mvn test

# Run application
mvn spring-boot:run

# Build JAR
mvn clean package
```

### Frontend Development

The frontend uses React with Vite:

```bash
# Install dependencies
npm install

# Start development server
npm run dev

# Build for production
npm run build

# Preview production build
npm run preview
```

### API Documentation

The backend provides REST APIs for:

- **Authentication**: `/api/auth/*`
- **Users**: `/api/users/*`
- **Attendance**: `/api/attendance/*`
- **Notices**: `/api/notices/*`
- **Subjects**: `/api/subjects/*`

## 🔒 Security

- JWT-based authentication
- Role-based access control
- Password encryption with BCrypt
- CORS configuration for frontend integration

## 📦 Technology Stack

### Backend
- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Security**
- **Spring JDBC**
- **MySQL Database**
- **JWT Authentication**
- **Maven Build Tool**

### Frontend
- **React 18**
- **TypeScript**
- **Vite**
- **Tailwind CSS**
- **Shadcn/ui Components**
- **React Router**
- **Axios**

## 🚀 Deployment

### Backend Deployment

1. Build the JAR file:
   ```bash
   cd backend
   mvn clean package
   ```

2. Run the JAR:
   ```bash
   java -jar target/academic-portal-backend-0.0.1-SNAPSHOT.jar
   ```

### Frontend Deployment

1. Build for production:
   ```bash
   npm run build
   ```

2. Deploy the `dist` folder to your web server

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License.

## 🆘 Support

For issues and questions:
1. Check the backend README: `backend/README.md`
2. Review the API documentation
3. Check the database setup
4. Verify all prerequisites are installed

## 📝 Notes

- The project uses Maven for backend dependency management
- Frontend uses npm for package management
- Database initialization is automatic on first run
- All default users and data are seeded automatically
- CORS is configured for local development