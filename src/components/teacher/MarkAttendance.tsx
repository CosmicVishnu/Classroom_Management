import React, { useState } from 'react';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '../ui/card';
import { Button } from '../ui/button';
import { Input } from '../ui/input';
import { Label } from '../ui/label';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '../ui/select';
import { Checkbox } from '../ui/checkbox';
import { Badge } from '../ui/badge';
import { Separator } from '../ui/separator';
import { Calendar, Users, Search, Filter, Save, Clock, CheckCircle, XCircle } from 'lucide-react';

export function MarkAttendance() {
  const [selectedClass, setSelectedClass] = useState('');
  const [selectedDate, setSelectedDate] = useState(new Date().toISOString().split('T')[0]);
  const [selectedPeriod, setSelectedPeriod] = useState('');
  const [searchTerm, setSearchTerm] = useState('');
  const [attendanceData, setAttendanceData] = useState<Record<string, boolean>>({});

  const classes = [
    { id: 'class-a', name: 'Mathematics - Class A', subject: 'Mathematics', students: 32 },
    { id: 'class-b', name: 'Mathematics - Class B', subject: 'Mathematics', students: 28 },
    { id: 'class-c', name: 'Mathematics - Class C', subject: 'Mathematics', students: 30 },
    { id: 'class-d', name: 'Mathematics - Class D', subject: 'Mathematics', students: 25 },
  ];

  const periods = [
    { id: '1', name: 'Period 1', time: '9:00 - 10:00 AM' },
    { id: '2', name: 'Period 2', time: '10:15 - 11:15 AM' },
    { id: '3', name: 'Period 3', time: '11:30 - 12:30 PM' },
    { id: '4', name: 'Period 4', time: '1:30 - 2:30 PM' },
    { id: '5', name: 'Period 5', time: '2:45 - 3:45 PM' },
    { id: '6', name: 'Period 6', time: '4:00 - 5:00 PM' },
  ];

  const students = [
    { id: '1', name: 'Alice Johnson', rollNo: 'CS001', email: 'alice@college.edu', previousAttendance: 92 },
    { id: '2', name: 'Bob Smith', rollNo: 'CS002', email: 'bob@college.edu', previousAttendance: 85 },
    { id: '3', name: 'Carol Davis', rollNo: 'CS003', email: 'carol@college.edu', previousAttendance: 78 },
    { id: '4', name: 'David Brown', rollNo: 'CS004', email: 'david@college.edu', previousAttendance: 95 },
    { id: '5', name: 'Emma Wilson', rollNo: 'CS005', email: 'emma@college.edu', previousAttendance: 88 },
    { id: '6', name: 'Frank Miller', rollNo: 'CS006', email: 'frank@college.edu', previousAttendance: 73 },
    { id: '7', name: 'Grace Lee', rollNo: 'CS007', email: 'grace@college.edu', previousAttendance: 91 },
    { id: '8', name: 'Henry Taylor', rollNo: 'CS008', email: 'henry@college.edu', previousAttendance: 82 },
    { id: '9', name: 'Ivy Chen', rollNo: 'CS009', email: 'ivy@college.edu', previousAttendance: 96 },
    { id: '10', name: 'Jack Rodriguez', rollNo: 'CS010', email: 'jack@college.edu', previousAttendance: 89 },
    { id: '11', name: 'Kate Anderson', rollNo: 'CS011', email: 'kate@college.edu', previousAttendance: 87 },
    { id: '12', name: 'Liam Thompson', rollNo: 'CS012', email: 'liam@college.edu', previousAttendance: 79 },
    { id: '13', name: 'Maya Patel', rollNo: 'CS013', email: 'maya@college.edu', previousAttendance: 93 },
    { id: '14', name: 'Noah Kim', rollNo: 'CS014', email: 'noah@college.edu', previousAttendance: 86 },
    { id: '15', name: 'Olivia Martinez', rollNo: 'CS015', email: 'olivia@college.edu', previousAttendance: 90 },
  ];

  const handleAttendanceToggle = (studentId: string, present: boolean) => {
    setAttendanceData(prev => ({
      ...prev,
      [studentId]: present
    }));
  };

  const handleMarkAll = (present: boolean) => {
    const newAttendanceData: Record<string, boolean> = {};
    filteredStudents.forEach(student => {
      newAttendanceData[student.id] = present;
    });
    setAttendanceData(prev => ({
      ...prev,
      ...newAttendanceData
    }));
  };

  const handleSaveAttendance = () => {
    if (!selectedClass || !selectedPeriod) {
      alert('Please select class and period');
      return;
    }
    
    // Here you would typically send the data to your backend
    console.log('Saving attendance:', {
      class: selectedClass,
      date: selectedDate,
      period: selectedPeriod,
      attendance: attendanceData
    });
    
    alert('Attendance saved successfully!');
  };

  const filteredStudents = students.filter(student =>
    student.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
    student.rollNo.toLowerCase().includes(searchTerm.toLowerCase())
  );

  const presentCount = Object.values(attendanceData).filter(Boolean).length;
  const totalCount = filteredStudents.length;
  const attendancePercentage = totalCount > 0 ? Math.round((presentCount / totalCount) * 100) : 0;

  const getAttendanceColor = (percentage: number) => {
    if (percentage >= 90) return 'text-green-600';
    if (percentage >= 75) return 'text-yellow-600';
    return 'text-red-600';
  };

  return (
    <div className="space-y-6">
      {/* Page Header */}
      <div>
        <h1 className="text-2xl font-bold text-[#1A141A] mb-2">Mark Attendance</h1>
        <p className="text-[#423738]">Record student attendance for your classes</p>
      </div>

      {/* Class Selection */}
      <Card className="shadow-academic border-0">
        <CardHeader>
          <CardTitle className="text-[#1A141A] flex items-center space-x-2">
            <Users className="h-5 w-5" />
            <span>Class Selection</span>
          </CardTitle>
          <CardDescription>Choose class, date, and period for attendance marking</CardDescription>
        </CardHeader>
        <CardContent>
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
            <div className="space-y-2">
              <Label htmlFor="class-select">Class</Label>
              <Select value={selectedClass} onValueChange={setSelectedClass}>
                <SelectTrigger id="class-select" className="border-[#D3AF85]/30">
                  <SelectValue placeholder="Select class" />
                </SelectTrigger>
                <SelectContent>
                  {classes.map(cls => (
                    <SelectItem key={cls.id} value={cls.id}>
                      {cls.name} ({cls.students} students)
                    </SelectItem>
                  ))}
                </SelectContent>
              </Select>
            </div>

            <div className="space-y-2">
              <Label htmlFor="date-select">Date</Label>
              <Input
                id="date-select"
                type="date"
                value={selectedDate}
                onChange={(e) => setSelectedDate(e.target.value)}
                className="border-[#D3AF85]/30 focus:border-[#F4B315]"
              />
            </div>

            <div className="space-y-2">
              <Label htmlFor="period-select">Period</Label>
              <Select value={selectedPeriod} onValueChange={setSelectedPeriod}>
                <SelectTrigger id="period-select" className="border-[#D3AF85]/30">
                  <SelectValue placeholder="Select period" />
                </SelectTrigger>
                <SelectContent>
                  {periods.map(period => (
                    <SelectItem key={period.id} value={period.id}>
                      {period.name} ({period.time})
                    </SelectItem>
                  ))}
                </SelectContent>
              </Select>
            </div>

            <div className="flex items-end">
              <Button
                onClick={() => {
                  setAttendanceData({});
                  setSearchTerm('');
                }}
                className="w-full bg-[#F4B315] hover:bg-[#E59312] text-[#1A141A]"
                disabled={!selectedClass || !selectedPeriod}
              >
                Load Students
              </Button>
            </div>
          </div>
        </CardContent>
      </Card>

      {selectedClass && selectedPeriod && (
        <>
          {/* Attendance Summary */}
          <div className="grid grid-cols-1 md:grid-cols-4 gap-4">
            <Card className="shadow-academic border-0">
              <CardContent className="p-4">
                <div className="flex items-center justify-between">
                  <div>
                    <p className="text-sm font-medium text-[#423738]">Total Students</p>
                    <p className="text-2xl font-bold text-[#1A141A]">{totalCount}</p>
                  </div>
                  <Users className="h-8 w-8 text-[#F4B315]" />
                </div>
              </CardContent>
            </Card>

            <Card className="shadow-academic border-0">
              <CardContent className="p-4">
                <div className="flex items-center justify-between">
                  <div>
                    <p className="text-sm font-medium text-[#423738]">Present</p>
                    <p className="text-2xl font-bold text-green-600">{presentCount}</p>
                  </div>
                  <CheckCircle className="h-8 w-8 text-green-600" />
                </div>
              </CardContent>
            </Card>

            <Card className="shadow-academic border-0">
              <CardContent className="p-4">
                <div className="flex items-center justify-between">
                  <div>
                    <p className="text-sm font-medium text-[#423738]">Absent</p>
                    <p className="text-2xl font-bold text-red-600">{totalCount - presentCount}</p>
                  </div>
                  <XCircle className="h-8 w-8 text-red-600" />
                </div>
              </CardContent>
            </Card>

            <Card className="shadow-academic border-0">
              <CardContent className="p-4">
                <div className="flex items-center justify-between">
                  <div>
                    <p className="text-sm font-medium text-[#423738]">Attendance %</p>
                    <p className={`text-2xl font-bold ${getAttendanceColor(attendancePercentage)}`}>
                      {attendancePercentage}%
                    </p>
                  </div>
                  <Calendar className="h-8 w-8 text-[#E59312]" />
                </div>
              </CardContent>
            </Card>
          </div>

          {/* Search and Bulk Actions */}
          <Card className="shadow-academic border-0">
            <CardContent className="p-4">
              <div className="flex flex-wrap gap-4 items-center justify-between">
                <div className="flex-1 min-w-64">
                  <div className="relative">
                    <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 h-4 w-4 text-[#423738]" />
                    <Input
                      placeholder="Search students by name or roll number..."
                      value={searchTerm}
                      onChange={(e) => setSearchTerm(e.target.value)}
                      className="pl-10 border-[#D3AF85]/30 focus:border-[#F4B315]"
                    />
                  </div>
                </div>
                <div className="flex space-x-2">
                  <Button
                    variant="outline"
                    onClick={() => handleMarkAll(true)}
                    className="border-green-500 text-green-600 hover:bg-green-50"
                  >
                    Mark All Present
                  </Button>
                  <Button
                    variant="outline"
                    onClick={() => handleMarkAll(false)}
                    className="border-red-500 text-red-600 hover:bg-red-50"
                  >
                    Mark All Absent
                  </Button>
                </div>
              </div>
            </CardContent>
          </Card>

          {/* Student List */}
          <Card className="shadow-academic border-0">
            <CardHeader>
              <div className="flex items-center justify-between">
                <div>
                  <CardTitle className="text-[#1A141A]">Student Attendance</CardTitle>
                  <CardDescription>
                    {classes.find(c => c.id === selectedClass)?.name} - {periods.find(p => p.id === selectedPeriod)?.name}
                  </CardDescription>
                </div>
                <Button
                  onClick={handleSaveAttendance}
                  className="bg-[#E59312] hover:bg-[#8E5915] text-white"
                >
                  <Save className="h-4 w-4 mr-2" />
                  Save Attendance
                </Button>
              </div>
            </CardHeader>
            <CardContent>
              <div className="space-y-2">
                {filteredStudents.map((student, index) => {
                  const isPresent = attendanceData[student.id] || false;
                  
                  return (
                    <div key={student.id}>
                      <div className="flex items-center justify-between p-4 hover:bg-[#F4B315]/5 rounded-lg transition-colors">
                        <div className="flex items-center space-x-4">
                          <div className="w-8 h-8 bg-[#F4B315] rounded-lg flex items-center justify-center text-sm font-semibold text-[#1A141A]">
                            {index + 1}
                          </div>
                          <div>
                            <h4 className="font-medium text-[#1A141A]">{student.name}</h4>
                            <div className="flex items-center space-x-4 text-sm text-[#423738]">
                              <span>Roll No: {student.rollNo}</span>
                              <span>•</span>
                              <span>{student.email}</span>
                            </div>
                          </div>
                        </div>

                        <div className="flex items-center space-x-4">
                          <div className="text-right">
                            <p className="text-sm text-[#423738]">Previous Attendance</p>
                            <p className={`text-sm font-semibold ${getAttendanceColor(student.previousAttendance)}`}>
                              {student.previousAttendance}%
                            </p>
                          </div>

                          <div className="flex items-center space-x-4">
                            <Button
                              variant={isPresent ? "default" : "outline"}
                              size="sm"
                              onClick={() => handleAttendanceToggle(student.id, true)}
                              className={isPresent ? "bg-green-600 hover:bg-green-700 text-white" : "border-green-500 text-green-600 hover:bg-green-50"}
                            >
                              <CheckCircle className="h-4 w-4 mr-1" />
                              Present
                            </Button>
                            <Button
                              variant={!isPresent && attendanceData.hasOwnProperty(student.id) ? "default" : "outline"}
                              size="sm"
                              onClick={() => handleAttendanceToggle(student.id, false)}
                              className={!isPresent && attendanceData.hasOwnProperty(student.id) ? "bg-red-600 hover:bg-red-700 text-white" : "border-red-500 text-red-600 hover:bg-red-50"}
                            >
                              <XCircle className="h-4 w-4 mr-1" />
                              Absent
                            </Button>
                          </div>
                        </div>
                      </div>
                      {index < filteredStudents.length - 1 && (
                        <Separator className="bg-[#D3AF85]/20" />
                      )}
                    </div>
                  );
                })}
              </div>

              {filteredStudents.length === 0 && (
                <div className="text-center py-8">
                  <Users className="h-12 w-12 text-[#D3AF85] mx-auto mb-4" />
                  <p className="text-[#423738]">No students found matching your search criteria.</p>
                </div>
              )}
            </CardContent>
          </Card>

          {/* Recent Attendance History */}
          <Card className="shadow-academic border-0">
            <CardHeader>
              <CardTitle className="text-[#1A141A]">Recent Attendance History</CardTitle>
              <CardDescription>Last 5 attendance records for this class</CardDescription>
            </CardHeader>
            <CardContent>
              <div className="space-y-3">
                {[
                  { date: '2024-01-11', period: 'Period 2', present: 28, total: 32, percentage: 87.5 },
                  { date: '2024-01-10', period: 'Period 3', present: 30, total: 32, percentage: 93.8 },
                  { date: '2024-01-09', period: 'Period 1', present: 29, total: 32, percentage: 90.6 },
                  { date: '2024-01-08', period: 'Period 4', present: 27, total: 32, percentage: 84.4 },
                  { date: '2024-01-05', period: 'Period 2', present: 31, total: 32, percentage: 96.9 },
                ].map((record, index) => (
                  <div key={index} className="flex items-center justify-between p-3 bg-[#F4B315]/5 rounded-lg">
                    <div className="flex items-center space-x-3">
                      <Clock className="h-4 w-4 text-[#8E5915]" />
                      <div>
                        <p className="font-medium text-[#1A141A] text-sm">
                          {new Date(record.date).toLocaleDateString()} - {record.period}
                        </p>
                        <p className="text-xs text-[#423738]">
                          {record.present}/{record.total} students present
                        </p>
                      </div>
                    </div>
                    <Badge className={`${getAttendanceColor(record.percentage)} bg-transparent border-current`} variant="outline">
                      {record.percentage}%
                    </Badge>
                  </div>
                ))}
              </div>
            </CardContent>
          </Card>
        </>
      )}
    </div>
  );
}