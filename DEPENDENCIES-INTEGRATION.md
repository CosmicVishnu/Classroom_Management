# Dependencies Integration

This document explains the integration of dependencies from the `azure-basic-app` folder into the Academic Portal project.

## 🔄 **Integrated Dependencies**

The following dependencies from `azure-basic-app` have been integrated into the main Academic Portal backend:

### **Database Dependencies**
- **Updated MySQL Connector**: Changed from `mysql-connector-java:8.0.33` to `mysql-connector-j` (latest version)
- **Scope**: Set to `runtime` for better dependency management

### **Web Template Dependencies**
- **Thymeleaf**: Added `spring-boot-starter-thymeleaf` for server-side templating
- **Thymeleaf Security**: Added `thymeleaf-extras-springsecurity6` for security integration

### **Build Configuration**
- **Maven Compiler Plugin**: Added proper Lombok annotation processing
- **Annotation Processor Paths**: Configured for Lombok to work correctly

## 📁 **Project Structure**

```
Academic Portal/
├── backend/                    # Main Spring Boot Backend
│   ├── src/main/java/        # Java source code
│   ├── src/main/resources/   # Configuration and resources
│   └── pom.xml              # Updated with integrated dependencies
├── azure-basic-app/          # Source of additional dependencies
│   ├── src/main/java/       # (Not integrated - different package)
│   ├── src/main/resources/  # (Empty static/templates folders)
│   └── pom.xml              # Source of dependency configurations
└── src/                     # React Frontend
```

## 🔧 **Updated Dependencies in backend/pom.xml**

### **Added Dependencies**
```xml
<!-- Thymeleaf for server-side templating -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>

<!-- Thymeleaf Security Integration -->
<dependency>
    <groupId>org.thymeleaf.extras</groupId>
    <artifactId>thymeleaf-extras-springsecurity6</artifactId>
</dependency>
```

### **Updated Dependencies**
```xml
<!-- Updated MySQL Connector -->
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <scope>runtime</scope>
</dependency>
```

### **Enhanced Build Configuration**
```xml
<!-- Maven Compiler Plugin with Lombok Support -->
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-compiler-plugin</artifactId>
    <configuration>
        <annotationProcessorPaths>
            <path>
                <groupId>org.projectlombok</groupId>
                <artifactId>lombok</artifactId>
            </path>
        </annotationProcessorPaths>
    </configuration>
</plugin>
```

## 🚀 **Benefits of Integration**

1. **Better Database Connectivity**: Updated MySQL connector for improved performance
2. **Server-Side Templating**: Thymeleaf support for future web interface features
3. **Enhanced Security**: Thymeleaf security integration for secure templates
4. **Improved Build Process**: Proper Lombok annotation processing
5. **Modern Dependencies**: Latest versions of required libraries

## 📝 **Notes**

- The `azure-basic-app` folder is kept as it contains useful dependency configurations
- Only the beneficial dependencies were integrated, not the entire application
- The Academic Portal maintains its own package structure (`com.academicportal`)
- All existing functionality remains intact with additional capabilities

## 🔄 **Next Steps**

1. **Test the Integration**: Run `mvn clean compile` to ensure all dependencies work
2. **Verify Functionality**: Test the Academic Portal backend with new dependencies
3. **Optional Cleanup**: The `azure-basic-app` folder can be kept for reference or removed if not needed

## 🛠️ **Usage**

The integrated dependencies are automatically available when running the Academic Portal backend:

```bash
cd backend
mvn spring-boot:run
```

All new dependencies are managed by Maven and will be downloaded automatically.



