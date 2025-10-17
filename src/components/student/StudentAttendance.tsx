import React, { useState } from 'react';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '../ui/card';
import { Progress } from '../ui/progress';
import { Badge } from '../ui/badge';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '../ui/select';
import { Calendar, TrendingUp, AlertTriangle, CheckCircle, XCircle } from 'lucide-react';

export function StudentAttendance() {
  const [selectedPeriod, setSelectedPeriod] = useState('current-semester');

  const attendanceData = [
    {
      subject: 'Mathematics',
      totalClasses: 25,
      attended: 23,
      percentage: 92,
      status: 'good',
      lastAttended: '2024-01-10',
      weeklyData: [
        { week: 'Week 1', present: 5, total: 5 },
        { week: 'Week 2', present: 4, total: 5 },
        { week: 'Week 3', present: 5, total: 5 },
        { week: 'Week 4', present: 4, total: 5 },
        { week: 'Week 5', present: 5, total: 5 },
      ]
    },
    {
      subject: 'Theory of Computation',
      totalClasses: 22,
      attended: 15,
      percentage: 68,
      status: 'warning',
      lastAttended: '2024-01-08',
      weeklyData: [
        { week: 'Week 1', present: 3, total: 4 },
        { week: 'Week 2', present: 2, total: 4 },
        { week: 'Week 3', present: 3, total: 5 },
        { week: 'Week 4', present: 3, total: 4 },
        { week: 'Week 5', present: 4, total: 5 },
      ]
    },
    {
      subject: 'Object Oriented Programming',
      totalClasses: 20,
      attended: 17,
      percentage: 85,
      status: 'good',
      lastAttended: '2024-01-09',
      weeklyData: [
        { week: 'Week 1', present: 4, total: 4 },
        { week: 'Week 2', present: 3, total: 4 },
        { week: 'Week 3', present: 4, total: 4 },
        { week: 'Week 4', present: 3, total: 4 },
        { week: 'Week 5', present: 3, total: 4 },
      ]
    },
    {
      subject: 'Data Structures and Algorithms',
      totalClasses: 18,
      attended: 17,
      percentage: 94,
      status: 'excellent',
      lastAttended: '2024-01-10',
      weeklyData: [
        { week: 'Week 1', present: 4, total: 4 },
        { week: 'Week 2', present: 3, total: 3 },
        { week: 'Week 3', present: 4, total: 4 },
        { week: 'Week 4', present: 3, total: 3 },
        { week: 'Week 5', present: 3, total: 4 },
      ]
    },
    {
      subject: 'Digital Circuits and Logic Design',
      totalClasses: 24,
      attended: 16,
      percentage: 67,
      status: 'critical',
      lastAttended: '2024-01-07',
      weeklyData: [
        { week: 'Week 1', present: 3, total: 5 },
        { week: 'Week 2', present: 2, total: 5 },
        { week: 'Week 3', present: 4, total: 5 },
        { week: 'Week 4', present: 3, total: 4 },
        { week: 'Week 5', present: 4, total: 5 },
      ]
    },
  ];

  const overallAttendance = {
    totalClasses: attendanceData.reduce((sum, subject) => sum + subject.totalClasses, 0),
    totalAttended: attendanceData.reduce((sum, subject) => sum + subject.attended, 0),
  };

  const overallPercentage = Math.round((overallAttendance.totalAttended / overallAttendance.totalClasses) * 100);

  const getStatusColor = (status: string) => {
    switch (status) {
      case 'excellent':
        return 'bg-green-100 text-green-800 border-green-200';
      case 'good':
        return 'bg-blue-100 text-blue-800 border-blue-200';
      case 'warning':
        return 'bg-yellow-100 text-yellow-800 border-yellow-200';
      case 'critical':
        return 'bg-red-100 text-red-800 border-red-200';
      default:
        return 'bg-gray-100 text-gray-800 border-gray-200';
    }
  };

  const getStatusIcon = (status: string) => {
    switch (status) {
      case 'excellent':
      case 'good':
        return <CheckCircle className="h-4 w-4" />;
      case 'warning':
        return <AlertTriangle className="h-4 w-4" />;
      case 'critical':
        return <XCircle className="h-4 w-4" />;
      default:
        return <CheckCircle className="h-4 w-4" />;
    }
  };

  return (
    <div className="space-y-6">
      {/* Page Header */}
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-2xl font-bold text-[#1A141A] mb-2">Attendance</h1>
          <p className="text-[#423738]">Track your class attendance and performance</p>
        </div>
        <Select value={selectedPeriod} onValueChange={setSelectedPeriod}>
          <SelectTrigger className="w-48 border-[#D3AF85]/30">
            <SelectValue placeholder="Select period" />
          </SelectTrigger>
          <SelectContent>
            <SelectItem value="current-semester">Current Semester</SelectItem>
            <SelectItem value="last-semester">Last Semester</SelectItem>
            <SelectItem value="current-year">Current Academic Year</SelectItem>
          </SelectContent>
        </Select>
      </div>

      {/* Overall Attendance Summary */}
      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        <Card className="shadow-academic border-0">
          <CardContent className="p-6">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-sm font-medium text-[#423738]">Overall Attendance</p>
                <p className="text-3xl font-bold text-[#1A141A]">{overallPercentage}%</p>
                <p className="text-sm text-[#8E5915] mt-1">
                  {overallAttendance.totalAttended}/{overallAttendance.totalClasses} classes
                </p>
              </div>
              <div className="h-16 w-16 bg-[#F4B315]/10 rounded-xl flex items-center justify-center">
                <TrendingUp className="h-8 w-8 text-[#F4B315]" />
              </div>
            </div>
          </CardContent>
        </Card>

        <Card className="shadow-academic border-0">
          <CardContent className="p-6">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-sm font-medium text-[#423738]">Classes This Week</p>
                <p className="text-3xl font-bold text-[#1A141A]">18</p>
                <p className="text-sm text-[#8E5915] mt-1">15 attended</p>
              </div>
              <div className="h-16 w-16 bg-[#E59312]/10 rounded-xl flex items-center justify-center">
                <Calendar className="h-8 w-8 text-[#E59312]" />
              </div>
            </div>
          </CardContent>
        </Card>

        <Card className="shadow-academic border-0">
          <CardContent className="p-6">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-sm font-medium text-[#423738]">Subjects Below 75%</p>
                <p className="text-3xl font-bold text-red-600">
                  {attendanceData.filter(s => s.percentage < 75).length}
                </p>
                <p className="text-sm text-[#8E5915] mt-1">Need attention</p>
              </div>
              <div className="h-16 w-16 bg-red-100 rounded-xl flex items-center justify-center">
                <AlertTriangle className="h-8 w-8 text-red-600" />
              </div>
            </div>
          </CardContent>
        </Card>
      </div>

      {/* Subject-wise Attendance */}
      <Card className="shadow-academic border-0">
        <CardHeader>
          <CardTitle className="text-[#1A141A]">Subject-wise Attendance</CardTitle>
          <CardDescription>Detailed attendance breakdown for each subject</CardDescription>
        </CardHeader>
        <CardContent>
          <div className="space-y-6">
            {attendanceData.map((subject, index) => (
              <div key={index} className="space-y-4">
                <div className="flex items-center justify-between">
                  <div className="flex items-center space-x-3">
                    <h3 className="font-semibold text-[#1A141A] text-lg">{subject.subject}</h3>
                    <Badge className={`${getStatusColor(subject.status)} border`} variant="outline">
                      <div className="flex items-center space-x-1">
                        {getStatusIcon(subject.status)}
                        <span className="capitalize">{subject.status}</span>
                      </div>
                    </Badge>
                  </div>
                  <div className="text-right">
                    <p className={`text-2xl font-bold ${subject.percentage < 75 ? 'text-red-600' : 'text-[#8E5915]'}`}>
                      {subject.percentage}%
                    </p>
                    <p className="text-sm text-[#423738]">
                      {subject.attended}/{subject.totalClasses} classes
                    </p>
                  </div>
                </div>

                <Progress
                  value={subject.percentage}
                  className="h-3"
                  style={{
                    background: subject.percentage < 75 ? '#fee2e2' : '#f0f9ff'
                  }}
                />

                <div className="grid grid-cols-2 md:grid-cols-5 gap-4">
                  {subject.weeklyData.map((week, weekIndex) => (
                    <div key={weekIndex} className="bg-[#F4B315]/5 p-3 rounded-lg text-center">
                      <p className="text-xs font-medium text-[#423738] mb-1">{week.week}</p>
                      <p className="text-sm font-semibold text-[#1A141A]">
                        {week.present}/{week.total}
                      </p>
                      <p className="text-xs text-[#8E5915]">
                        {Math.round((week.present / week.total) * 100)}%
                      </p>
                    </div>
                  ))}
                </div>

                <div className="flex items-center justify-between text-sm text-[#423738] bg-[#f8f8f8] p-3 rounded-lg">
                  <span>Last attended: {new Date(subject.lastAttended).toLocaleDateString()}</span>
                  {subject.percentage < 75 && (
                    <span className="text-red-600 font-medium">
                      Need {Math.ceil((0.75 * subject.totalClasses) - subject.attended)} more classes for 75%
                    </span>
                  )}
                </div>

                {index < attendanceData.length - 1 && (
                  <div className="border-b border-[#D3AF85]/20" />
                )}
              </div>
            ))}
          </div>
        </CardContent>
      </Card>

      {/* Attendance Insights */}
      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
        <Card className="shadow-academic border-0">
          <CardHeader>
            <CardTitle className="text-[#1A141A]">Attendance Trends</CardTitle>
            <CardDescription>Your attendance pattern over time</CardDescription>
          </CardHeader>
          <CardContent>
            <div className="space-y-4">
              <div className="flex items-center justify-between p-3 bg-green-50 rounded-lg">
                <span className="text-sm font-medium text-green-800">Best Performance</span>
                <span className="text-sm font-semibold text-green-800">Data Structures and Algorithms (94%)</span>
              </div>
              <div className="flex items-center justify-between p-3 bg-red-50 rounded-lg">
                <span className="text-sm font-medium text-red-800">Needs Improvement</span>
                <span className="text-sm font-semibold text-red-800">DSLD (67%)</span>
              </div>
              <div className="flex items-center justify-between p-3 bg-blue-50 rounded-lg">
                <span className="text-sm font-medium text-blue-800">Most Consistent</span>
                <span className="text-sm font-semibold text-blue-800">Mathematics (97%)</span>
              </div>
            </div>
          </CardContent>
        </Card>

        <Card className="shadow-academic border-0">
          <CardHeader>
            <CardTitle className="text-[#1A141A]">Quick Actions</CardTitle>
            <CardDescription>Improve your attendance</CardDescription>
          </CardHeader>
          <CardContent>
            <div className="space-y-3">
              <div className="p-4 bg-[#F4B315]/5 rounded-lg">
                <h4 className="font-medium text-[#1A141A] mb-2">Attendance Goal</h4>
                <p className="text-sm text-[#423738]">
                  Maintain at least 75% attendance in all subjects to remain eligible for examinations.
                </p>
              </div>
              <div className="p-4 bg-[#E59312]/5 rounded-lg">
                <h4 className="font-medium text-[#1A141A] mb-2">Reminder</h4>
                <p className="text-sm text-[#423738]">
                  Set up notifications to never miss important classes and maintain consistent attendance.
                </p>
              </div>
            </div>
          </CardContent>
        </Card>
      </div>
    </div>
  );
}