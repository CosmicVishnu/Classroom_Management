-- MySQL Setup Script for Academic Portal
-- Run this script to create the database and user

-- Create database
CREATE DATABASE IF NOT EXISTS academic_portal;
USE academic_portal;

-- Create user (optional - you can use root user)
-- CREATE USER 'academic_user'@'localhost' IDENTIFIED BY 'academic_password';
-- GRANT ALL PRIVILEGES ON academic_portal.* TO 'academic_user'@'localhost';
-- FLUSH PRIVILEGES;

-- Verify database creation
SHOW DATABASES LIKE 'academic_portal';

-- Show current user and database
SELECT USER(), DATABASE();





