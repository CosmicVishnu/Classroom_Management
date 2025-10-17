@echo off
REM Maven Wrapper for Academic Portal Backend
REM This script provides Maven commands for the project

if "%1"=="" (
    echo Academic Portal Backend - Maven Commands
    echo.
    echo Usage: mvnw.bat [command]
    echo.
    echo Commands:
    echo   clean     - Clean the project
    echo   compile   - Compile the project
    echo   test      - Run tests
    echo   run       - Run the Spring Boot application
    echo   build    - Build the project
    echo   install   - Install to local repository
    echo.
    echo Examples:
    echo   mvnw.bat run
    echo   mvnw.bat clean compile
    echo   mvnw.bat test
    goto :eof
)

if "%1"=="run" (
    echo Starting Academic Portal Backend...
    mvn spring-boot:run
) else if "%1"=="clean" (
    mvn clean
) else if "%1"=="compile" (
    mvn compile
) else if "%1"=="test" (
    mvn test
) else if "%1"=="build" (
    mvn clean compile
) else if "%1"=="install" (
    mvn clean install
) else (
    echo Unknown command: %1
    echo Run mvnw.bat without arguments to see available commands.
)
