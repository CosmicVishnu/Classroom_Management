#!/bin/bash

# Academic Portal Backend Startup Script

echo "Starting Academic Portal Backend..."

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "Error: Java is not installed. Please install Java 25 or higher."
    exit 1
fi

# Check if Gradle is installed
if ! command -v gradle &> /dev/null && [ ! -f "./gradlew" ]; then
    echo "Error: Gradle is not installed and gradlew wrapper not found."
    echo "Please install Gradle or ensure gradlew is present."
    exit 1
fi

# Check Java version
JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2 | cut -d'.' -f1)
if [ "$JAVA_VERSION" -lt 25 ]; then
    echo "Error: Java 25 or higher is required. Current version: $JAVA_VERSION"
    exit 1
fi

echo "Java version: $(java -version 2>&1 | head -n 1)"

# Use gradlew if available, otherwise use system gradle
if [ -f "./gradlew" ]; then
    echo "Using Gradle wrapper..."
    GRADLE_CMD="./gradlew"
else
    echo "Using system Gradle..."
    GRADLE_CMD="gradle"
fi

echo "Gradle version: $($GRADLE_CMD --version | head -n 1)"

# Clean and build the project
echo "Building the project..."
$GRADLE_CMD clean build -x test

if [ $? -ne 0 ]; then
    echo "Error: Build failed. Please check the errors above."
    exit 1
fi

echo "Build successful!"

# Start the application
echo "Starting the application..."
echo "Backend will be available at: http://localhost:8080/api"
echo "H2 Database Console: http://localhost:8080/api/h2-console"
echo "Press Ctrl+C to stop the application"
echo ""

$GRADLE_CMD bootRun
