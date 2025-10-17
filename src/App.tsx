import React, { useState } from 'react';
import { LoginPage } from './components/LoginPage';
import { StudentDashboard } from './components/StudentDashboard';
import { TeacherDashboard } from './components/TeacherDashboard';
import { AdminDashboard } from './components/AdminDashboard';



type UserRole = 'student' | 'teacher' | 'admin';

interface User {
  id: string;
  name: string;
  role: UserRole;
  email: string;
  avatar?: string;
  password?: string;
}

export default function App() {
  const [currentUser, setCurrentUser] = useState<User | null>(null);
  const [currentPage, setCurrentPage] = useState<string>('home');

  const handleLogin = async ({ id, password }: { id: string; password: string }) => {
    try {
      const response = await fetch('http://localhost:8080/api/auth/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          userId: id,
          password: password,
        }),
      });

      if (response.ok) {
        const loginResponse = await response.json();
        console.log('Login successful:', loginResponse);

        // Set current user from backend response, normalize role to lowercase
        const userData = loginResponse.user;
        const normalizedUser = {
          ...userData,
          role: userData.role.toLowerCase() as UserRole
        };
        setCurrentUser(normalizedUser);
        setCurrentPage('home');
      } else {
        const errorData = await response.json();
        alert(`Login failed: ${errorData.message || 'Invalid credentials'}`);
      }
    } catch (error) {
      console.error('Login error:', error);
      alert('Login failed: Unable to connect to server');
    }
  };

  const handleSignup = async (newUserData: User) => {
    try {
      console.log('New user signing up:', newUserData);

      // Call backend API to create user
      const response = await fetch('http://localhost:8080/api/auth/signup', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          name: newUserData.name,
          email: newUserData.email,
          password: newUserData.password,
          role: newUserData.role.toUpperCase(), // Convert to uppercase for backend
          department: 'General', // Default department
        }),
      });

      if (response.ok) {
        const createdUser = await response.json();
        console.log('User created in database:', createdUser);

        alert(`User ${createdUser.name} signed up successfully!`);
      } else {
        const errorData = await response.json();
        alert(`Sign-up failed: ${errorData.message || 'Unknown error'}`);
      }
    } catch (error) {
      console.error('Sign-up error:', error);
      alert('Sign-up failed: Unable to connect to server');
    }
  };

  const handleLogout = () => {
    setCurrentUser(null);
    setCurrentPage('home');
  };

  const handlePageChange = (page: string) => {
    setCurrentPage(page);
  };

  if (!currentUser) {
    return <LoginPage onLogin={handleLogin} onSignup={handleSignup} />;
  }

  const renderDashboard = () => {
    switch (currentUser.role) {
      case 'student':
        return (
          <StudentDashboard
            user={currentUser}
            currentPage={currentPage}
            onPageChange={handlePageChange}
            onLogout={handleLogout}
          />
        );
      case 'teacher':
        return (
          <TeacherDashboard
            user={currentUser}
            currentPage={currentPage}
            onPageChange={handlePageChange}
            onLogout={handleLogout}
          />
        );
      case 'admin':
        return (
          <AdminDashboard
            user={currentUser}
            currentPage={currentPage}
            onPageChange={handlePageChange}
            onLogout={handleLogout}
          />
        );
      default:
        return <div>Invalid user role</div>;
    }
  };

  return <div className="min-h-screen bg-background">{renderDashboard()}</div>;
}