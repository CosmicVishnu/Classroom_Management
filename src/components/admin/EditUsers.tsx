import React, { useState } from 'react';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '../ui/card';
import { Button } from '../ui/button';
import { Input } from '../ui/input';
import { Badge } from '../ui/badge';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '../ui/select';
import { 
  Users, 
  Search, 
  Plus, 
  Edit, 
  Trash2, 
  Filter,
  UserCheck,
  UserX,
  Mail,
  Phone
} from 'lucide-react';

export function EditUsers() {
  const [searchTerm, setSearchTerm] = useState('');
  const [roleFilter, setRoleFilter] = useState('all');
  const [statusFilter, setStatusFilter] = useState('all');

  const users = [
    { id: 1, name: 'Aryan', email: 'aryan@college.edu', role: 'student', status: 'active', department: 'Computer Science', joinDate: '2023-09-01' },
    { id: 2, name: 'Minu K K', email: 'minu@college.edu', role: 'teacher', status: 'active', department: 'Mathematics', joinDate: '2020-08-15' },
    { id: 3, name: 'George James', email: 'george@college.edu', role: 'student', status: 'active', department: 'Computer Science', joinDate: '2023-09-01' },
    { id: 4, name: 'Rekha R', email: 'rekha@college.edu', role: 'teacher', status: 'active', department: 'Computer Science ', joinDate: '2019-01-10' },
    { id: 5, name: 'Nitha Admin', email: 'admin@college.edu', role: 'admin', status: 'active', department: 'Administration', joinDate: '2018-03-01' },
    { id: 6, name: 'Niloofer', email: 'niloofer@college.edu', role: 'student', status: 'inactive', department: 'Computer Science', joinDate: '2023-09-01' },
  ];

  const getRoleColor = (role: string) => {
    switch (role) {
      case 'admin':
        return 'bg-red-100 text-red-800 border-red-200';
      case 'teacher':
        return 'bg-blue-100 text-blue-800 border-blue-200';
      case 'student':
        return 'bg-green-100 text-green-800 border-green-200';
      default:
        return 'bg-gray-100 text-gray-800 border-gray-200';
    }
  };

  const getStatusColor = (status: string) => {
    switch (status) {
      case 'active':
        return 'bg-green-100 text-green-800 border-green-200';
      case 'inactive':
        return 'bg-gray-100 text-gray-800 border-gray-200';
      case 'suspended':
        return 'bg-red-100 text-red-800 border-red-200';
      default:
        return 'bg-gray-100 text-gray-800 border-gray-200';
    }
  };

  const filteredUsers = users.filter(user => {
    const matchesSearch = user.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
                         user.email.toLowerCase().includes(searchTerm.toLowerCase()) ||
                         user.department.toLowerCase().includes(searchTerm.toLowerCase());
    const matchesRole = roleFilter === 'all' || user.role === roleFilter;
    const matchesStatus = statusFilter === 'all' || user.status === statusFilter;
    
    return matchesSearch && matchesRole && matchesStatus;
  });

  const userCounts = {
    total: users.length,
    students: users.filter(u => u.role === 'student').length,
    teachers: users.filter(u => u.role === 'teacher').length,
    admins: users.filter(u => u.role === 'admin').length,
    active: users.filter(u => u.status === 'active').length,
  };

  return (
    <div className="space-y-6">
      {/* Page Header */}
      <div>
        <h1 className="text-2xl font-bold text-[#1A141A] mb-2">Edit Users</h1>
        <p className="text-[#423738]">Manage system users and their permissions</p>
      </div>

      {/* User Statistics */}
      <div className="grid grid-cols-1 md:grid-cols-5 gap-4">
        <Card className="shadow-academic border-0">
          <CardContent className="p-4">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-sm font-medium text-[#423738]">Total Users</p>
                <p className="text-2xl font-bold text-[#1A141A]">{userCounts.total}</p>
              </div>
              <Users className="h-8 w-8 text-[#F4B315]" />
            </div>
          </CardContent>
        </Card>

        <Card className="shadow-academic border-0">
          <CardContent className="p-4">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-sm font-medium text-[#423738]">Students</p>
                <p className="text-2xl font-bold text-green-600">{userCounts.students}</p>
              </div>
              <UserCheck className="h-8 w-8 text-green-600" />
            </div>
          </CardContent>
        </Card>

        <Card className="shadow-academic border-0">
          <CardContent className="p-4">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-sm font-medium text-[#423738]">Teachers</p>
                <p className="text-2xl font-bold text-blue-600">{userCounts.teachers}</p>
              </div>
              <UserCheck className="h-8 w-8 text-blue-600" />
            </div>
          </CardContent>
        </Card>

        <Card className="shadow-academic border-0">
          <CardContent className="p-4">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-sm font-medium text-[#423738]">Admins</p>
                <p className="text-2xl font-bold text-red-600">{userCounts.admins}</p>
              </div>
              <UserCheck className="h-8 w-8 text-red-600" />
            </div>
          </CardContent>
        </Card>

        <Card className="shadow-academic border-0">
          <CardContent className="p-4">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-sm font-medium text-[#423738]">Active</p>
                <p className="text-2xl font-bold text-[#E59312]">{userCounts.active}</p>
              </div>
              <UserCheck className="h-8 w-8 text-[#E59312]" />
            </div>
          </CardContent>
        </Card>
      </div>

      {/* Search and Filters */}
      <Card className="shadow-academic border-0">
        <CardContent className="p-4">
          <div className="flex flex-wrap gap-4 items-center justify-between">
            <div className="flex-1 min-w-64">
              <div className="relative">
                <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 h-4 w-4 text-[#423738]" />
                <Input
                  placeholder="Search users by name, email, or department..."
                  value={searchTerm}
                  onChange={(e) => setSearchTerm(e.target.value)}
                  className="pl-10 border-[#D3AF85]/30 focus:border-[#F4B315]"
                />
              </div>
            </div>
            <div className="flex space-x-2">
              <Select value={roleFilter} onValueChange={setRoleFilter}>
                <SelectTrigger className="w-40 border-[#D3AF85]/30">
                  <Filter className="h-4 w-4 mr-2" />
                  <SelectValue placeholder="Role" />
                </SelectTrigger>
                <SelectContent>
                  <SelectItem value="all">All Roles</SelectItem>
                  <SelectItem value="student">Students</SelectItem>
                  <SelectItem value="teacher">Teachers</SelectItem>
                  <SelectItem value="admin">Admins</SelectItem>
                </SelectContent>
              </Select>
              <Select value={statusFilter} onValueChange={setStatusFilter}>
                <SelectTrigger className="w-40 border-[#D3AF85]/30">
                  <SelectValue placeholder="Status" />
                </SelectTrigger>
                <SelectContent>
                  <SelectItem value="all">All Status</SelectItem>
                  <SelectItem value="active">Active</SelectItem>
                  <SelectItem value="inactive">Inactive</SelectItem>
                  <SelectItem value="suspended">Suspended</SelectItem>
                </SelectContent>
              </Select>
              <Button className="bg-[#F4B315] hover:bg-[#E59312] text-[#1A141A]">
                <Plus className="h-4 w-4 mr-2" />
                Add User
              </Button>
            </div>
          </div>
        </CardContent>
      </Card>

      {/* Users List */}
      <Card className="shadow-academic border-0">
        <CardHeader>
          <CardTitle className="text-[#1A141A]">User Management</CardTitle>
          <CardDescription>
            Showing {filteredUsers.length} of {users.length} users
          </CardDescription>
        </CardHeader>
        <CardContent>
          <div className="overflow-x-auto">
            <table className="w-full">
              <thead>
                <tr className="border-b border-[#D3AF85]/20">
                  <th className="text-left py-3 px-4 font-medium text-[#1A141A]">User</th>
                  <th className="text-center py-3 px-4 font-medium text-[#1A141A]">Role</th>
                  <th className="text-center py-3 px-4 font-medium text-[#1A141A]">Department</th>
                  <th className="text-center py-3 px-4 font-medium text-[#1A141A]">Status</th>
                  <th className="text-center py-3 px-4 font-medium text-[#1A141A]">Join Date</th>
                  <th className="text-center py-3 px-4 font-medium text-[#1A141A]">Actions</th>
                </tr>
              </thead>
              <tbody>
                {filteredUsers.map((user) => (
                  <tr key={user.id} className="border-b border-[#D3AF85]/10 hover:bg-[#F4B315]/5">
                    <td className="py-4 px-4">
                      <div>
                        <p className="font-medium text-[#1A141A]">{user.name}</p>
                        <div className="flex items-center space-x-2 text-sm text-[#423738]">
                          <Mail className="h-3 w-3" />
                          <span>{user.email}</span>
                        </div>
                      </div>
                    </td>
                    <td className="py-4 px-4 text-center">
                      <Badge className={`${getRoleColor(user.role)} text-xs border`} variant="outline">
                        {user.role.charAt(0).toUpperCase() + user.role.slice(1)}
                      </Badge>
                    </td>
                    <td className="py-4 px-4 text-center text-[#423738]">{user.department}</td>
                    <td className="py-4 px-4 text-center">
                      <Badge className={`${getStatusColor(user.status)} text-xs border`} variant="outline">
                        {user.status.charAt(0).toUpperCase() + user.status.slice(1)}
                      </Badge>
                    </td>
                    <td className="py-4 px-4 text-center text-[#423738]">
                      {new Date(user.joinDate).toLocaleDateString()}
                    </td>
                    <td className="py-4 px-4 text-center">
                      <div className="flex items-center justify-center space-x-2">
                        <Button
                          variant="ghost"
                          size="sm"
                          className="text-[#8E5915] hover:bg-[#F4B315]/10"
                        >
                          <Edit className="h-4 w-4" />
                        </Button>
                        <Button
                          variant="ghost"
                          size="sm"
                          className={user.status === 'active' 
                            ? "text-red-500 hover:bg-red-50" 
                            : "text-green-500 hover:bg-green-50"
                          }
                        >
                          {user.status === 'active' ? (
                            <UserX className="h-4 w-4" />
                          ) : (
                            <UserCheck className="h-4 w-4" />
                          )}
                        </Button>
                        <Button
                          variant="ghost"
                          size="sm"
                          className="text-red-500 hover:bg-red-50"
                        >
                          <Trash2 className="h-4 w-4" />
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>

          {filteredUsers.length === 0 && (
            <div className="text-center py-8">
              <Users className="h-12 w-12 text-[#D3AF85] mx-auto mb-4" />
              <p className="text-[#423738]">No users found matching your criteria.</p>
            </div>
          )}
        </CardContent>
      </Card>
    </div>
  );
}