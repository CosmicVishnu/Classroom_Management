import React, { useState } from 'react';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '../ui/card';
import { Button } from '../ui/button';
import { Input } from '../ui/input';
import { Label } from '../ui/label';
import { Textarea } from '../ui/textarea';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '../ui/select';
import { Badge } from '../ui/badge';
import { Tabs, TabsContent, TabsList, TabsTrigger } from '../ui/tabs';
import { Separator } from '../ui/separator';
import { 
  FileText, 
  Send, 
  Save, 
  Eye, 
  Calendar, 
  Users, 
  Tag, 
  Upload,
  Clock,
  Edit,
  Trash2
} from 'lucide-react';

export function PostNotices() {
  const [noticeForm, setNoticeForm] = useState({
    title: '',
    content: '',
    priority: '',
    category: '',
    targetAudience: '',
    attachments: [] as File[],
    scheduledDate: '',
    tags: ''
  });

  const [recentNotices] = useState([
    {
      id: 1,
      title: 'Assignment Submission Deadline Extended',
      content: 'The deadline for Mathematics Assignment 3 has been extended to January 20th due to technical difficulties.',
      priority: 'medium',
      category: 'assignment',
      targetAudience: 'Mathematics Students',
      date: '2024-01-12',
      status: 'published',
      views: 45
    },
    {
      id: 2,
      title: 'Extra Tutorial Sessions - Calculus',
      content: 'Additional tutorial sessions for Calculus will be conducted every Saturday from 2-4 PM in Room 101.',
      priority: 'low',
      category: 'tutorial',
      targetAudience: 'Class A & B',
      date: '2024-01-11',
      status: 'published',
      views: 32
    },
    {
      id: 3,
      title: 'Mid-term Exam Guidelines',
      content: 'Important guidelines and instructions for the upcoming mid-term examinations. Please read carefully.',
      priority: 'high',
      category: 'exam',
      targetAudience: 'All Students',
      date: '2024-01-10',
      status: 'draft',
      views: 0
    }
  ]);

  const handleInputChange = (field: string, value: string) => {
    setNoticeForm(prev => ({
      ...prev,
      [field]: value
    }));
  };

  const handleFileUpload = (event: React.ChangeEvent<HTMLInputElement>) => {
    const files = Array.from(event.target.files || []);
    setNoticeForm(prev => ({
      ...prev,
      attachments: [...prev.attachments, ...files]
    }));
  };

  const removeAttachment = (index: number) => {
    setNoticeForm(prev => ({
      ...prev,
      attachments: prev.attachments.filter((_, i) => i !== index)
    }));
  };

  const handlePublishNotice = () => {
    if (!noticeForm.title || !noticeForm.content) {
      alert('Please fill in all required fields');
      return;
    }
    
    console.log('Publishing notice:', noticeForm);
    alert('Notice published successfully!');
    
    // Reset form
    setNoticeForm({
      title: '',
      content: '',
      priority: '',
      category: '',
      targetAudience: '',
      attachments: [],
      scheduledDate: '',
      tags: ''
    });
  };

  const handleSaveDraft = () => {
    console.log('Saving draft:', noticeForm);
    alert('Notice saved as draft!');
  };

  const getPriorityColor = (priority: string) => {
    switch (priority) {
      case 'high':
        return 'bg-red-100 text-red-800 border-red-200';
      case 'medium':
        return 'bg-yellow-100 text-yellow-800 border-yellow-200';
      case 'low':
        return 'bg-green-100 text-green-800 border-green-200';
      default:
        return 'bg-gray-100 text-gray-800 border-gray-200';
    }
  };

  const getCategoryColor = (category: string) => {
    switch (category) {
      case 'assignment':
        return 'bg-blue-100 text-blue-800';
      case 'exam':
        return 'bg-red-100 text-red-800';
      case 'tutorial':
        return 'bg-green-100 text-green-800';
      case 'announcement':
        return 'bg-purple-100 text-purple-800';
      default:
        return 'bg-gray-100 text-gray-800';
    }
  };

  const getStatusColor = (status: string) => {
    switch (status) {
      case 'published':
        return 'bg-green-100 text-green-800 border-green-200';
      case 'draft':
        return 'bg-yellow-100 text-yellow-800 border-yellow-200';
      case 'scheduled':
        return 'bg-blue-100 text-blue-800 border-blue-200';
      default:
        return 'bg-gray-100 text-gray-800 border-gray-200';
    }
  };

  return (
    <div className="space-y-6">
      {/* Page Header */}
      <div>
        <h1 className="text-2xl font-bold text-[#1A141A] mb-2">Post Notices</h1>
        <p className="text-[#423738]">Create and manage notices for your students</p>
      </div>

      <Tabs defaultValue="create" className="space-y-6">
        <TabsList className="grid w-full grid-cols-2 max-w-md">
          <TabsTrigger value="create">Create Notice</TabsTrigger>
          <TabsTrigger value="manage">Manage Notices</TabsTrigger>
        </TabsList>

        <TabsContent value="create" className="space-y-6">
          {/* Notice Form */}
          <Card className="shadow-academic border-0">
            <CardHeader>
              <CardTitle className="text-[#1A141A] flex items-center space-x-2">
                <FileText className="h-5 w-5" />
                <span>Create New Notice</span>
              </CardTitle>
              <CardDescription>Fill in the details to create a new notice for your students</CardDescription>
            </CardHeader>
            <CardContent className="space-y-6">
              {/* Basic Information */}
              <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                <div className="space-y-2">
                  <Label htmlFor="title">Notice Title *</Label>
                  <Input
                    id="title"
                    placeholder="Enter notice title"
                    value={noticeForm.title}
                    onChange={(e) => handleInputChange('title', e.target.value)}
                    className="border-[#D3AF85]/30 focus:border-[#F4B315]"
                  />
                </div>

                <div className="space-y-2">
                  <Label htmlFor="priority">Priority</Label>
                  <Select value={noticeForm.priority} onValueChange={(value) => handleInputChange('priority', value)}>
                    <SelectTrigger id="priority" className="border-[#D3AF85]/30">
                      <SelectValue placeholder="Select priority" />
                    </SelectTrigger>
                    <SelectContent>
                      <SelectItem value="high">High Priority</SelectItem>
                      <SelectItem value="medium">Medium Priority</SelectItem>
                      <SelectItem value="low">Low Priority</SelectItem>
                    </SelectContent>
                  </Select>
                </div>
              </div>

              <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                <div className="space-y-2">
                  <Label htmlFor="category">Category</Label>
                  <Select value={noticeForm.category} onValueChange={(value) => handleInputChange('category', value)}>
                    <SelectTrigger id="category" className="border-[#D3AF85]/30">
                      <SelectValue placeholder="Select category" />
                    </SelectTrigger>
                    <SelectContent>
                      <SelectItem value="assignment">Assignment</SelectItem>
                      <SelectItem value="exam">Examination</SelectItem>
                      <SelectItem value="tutorial">Tutorial</SelectItem>
                      <SelectItem value="announcement">General Announcement</SelectItem>
                    </SelectContent>
                  </Select>
                </div>

                <div className="space-y-2">
                  <Label htmlFor="audience">Target Audience</Label>
                  <Select value={noticeForm.targetAudience} onValueChange={(value) => handleInputChange('targetAudience', value)}>
                    <SelectTrigger id="audience" className="border-[#D3AF85]/30">
                      <SelectValue placeholder="Select audience" />
                    </SelectTrigger>
                    <SelectContent>
                      <SelectItem value="all">All Students</SelectItem>
                      <SelectItem value="class-a">Mathematics - Class A</SelectItem>
                      <SelectItem value="class-b">Mathematics - Class B</SelectItem>
                      <SelectItem value="class-c">Mathematics - Class C</SelectItem>
                      <SelectItem value="class-d">Mathematics - Class D</SelectItem>
                    </SelectContent>
                  </Select>
                </div>
              </div>

              {/* Content */}
              <div className="space-y-2">
                <Label htmlFor="content">Notice Content *</Label>
                <Textarea
                  id="content"
                  placeholder="Enter the notice content here..."
                  value={noticeForm.content}
                  onChange={(e) => handleInputChange('content', e.target.value)}
                  className="min-h-32 border-[#D3AF85]/30 focus:border-[#F4B315]"
                />
              </div>

              {/* Additional Options */}
              <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                <div className="space-y-2">
                  <Label htmlFor="tags">Tags (comma-separated)</Label>
                  <Input
                    id="tags"
                    placeholder="e.g., mathematics, assignment, deadline"
                    value={noticeForm.tags}
                    onChange={(e) => handleInputChange('tags', e.target.value)}
                    className="border-[#D3AF85]/30 focus:border-[#F4B315]"
                  />
                </div>

                <div className="space-y-2">
                  <Label htmlFor="scheduled-date">Schedule for Later (Optional)</Label>
                  <Input
                    id="scheduled-date"
                    type="datetime-local"
                    value={noticeForm.scheduledDate}
                    onChange={(e) => handleInputChange('scheduledDate', e.target.value)}
                    className="border-[#D3AF85]/30 focus:border-[#F4B315]"
                  />
                </div>
              </div>

              {/* File Attachments */}
              <div className="space-y-2">
                <Label htmlFor="attachments">Attachments</Label>
                <div className="border-2 border-dashed border-[#D3AF85]/30 rounded-lg p-4">
                  <div className="text-center">
                    <Upload className="h-8 w-8 text-[#8E5915] mx-auto mb-2" />
                    <p className="text-sm text-[#423738] mb-2">
                      Click to upload files or drag and drop
                    </p>
                    <Input
                      id="attachments"
                      type="file"
                      multiple
                      onChange={handleFileUpload}
                      className="hidden"
                    />
                    <Button
                      type="button"
                      variant="outline"
                      onClick={() => document.getElementById('attachments')?.click()}
                      className="border-[#F4B315] text-[#F4B315] hover:bg-[#F4B315] hover:text-[#1A141A]"
                    >
                      Choose Files
                    </Button>
                  </div>
                  
                  {noticeForm.attachments.length > 0 && (
                    <div className="mt-4 space-y-2">
                      <Separator className="bg-[#D3AF85]/20" />
                      <p className="text-sm font-medium text-[#1A141A]">Selected Files:</p>
                      {noticeForm.attachments.map((file, index) => (
                        <div key={index} className="flex items-center justify-between p-2 bg-[#F4B315]/5 rounded">
                          <span className="text-sm text-[#423738]">{file.name}</span>
                          <Button
                            type="button"
                            variant="ghost"
                            size="sm"
                            onClick={() => removeAttachment(index)}
                            className="text-red-500 hover:text-red-700 hover:bg-red-50"
                          >
                            <Trash2 className="h-3 w-3" />
                          </Button>
                        </div>
                      ))}
                    </div>
                  )}
                </div>
              </div>

              {/* Preview */}
              {(noticeForm.title || noticeForm.content) && (
                <div className="space-y-2">
                  <Label className="flex items-center space-x-2">
                    <Eye className="h-4 w-4" />
                    <span>Preview</span>
                  </Label>
                  <div className="border border-[#D3AF85]/30 rounded-lg p-4 bg-[#F4B315]/5">
                    <div className="space-y-3">
                      <div className="flex items-start justify-between">
                        <h3 className="font-semibold text-[#1A141A]">
                          {noticeForm.title || 'Notice Title'}
                        </h3>
                        <div className="flex space-x-2">
                          {noticeForm.priority && (
                            <Badge className={`${getPriorityColor(noticeForm.priority)} text-xs border`} variant="outline">
                              {noticeForm.priority.toUpperCase()}
                            </Badge>
                          )}
                          {noticeForm.category && (
                            <Badge className={`${getCategoryColor(noticeForm.category)} text-xs`} variant="secondary">
                              {noticeForm.category}
                            </Badge>
                          )}
                        </div>
                      </div>
                      <p className="text-sm text-[#423738]">
                        {noticeForm.content || 'Notice content will appear here...'}
                      </p>
                      {noticeForm.tags && (
                        <div className="flex items-center space-x-2">
                          <Tag className="h-3 w-3 text-[#8E5915]" />
                          <div className="flex flex-wrap gap-1">
                            {noticeForm.tags.split(',').map((tag, index) => (
                              <Badge key={index} variant="outline" className="text-xs bg-[#F4B315]/10 text-[#8E5915] border-[#F4B315]/30">
                                {tag.trim()}
                              </Badge>
                            ))}
                          </div>
                        </div>
                      )}
                    </div>
                  </div>
                </div>
              )}

              {/* Action Buttons */}
              <div className="flex justify-end space-x-4 pt-4 border-t border-[#D3AF85]/20">
                <Button
                  variant="outline"
                  onClick={handleSaveDraft}
                  className="border-[#8E5915] text-[#8E5915] hover:bg-[#8E5915] hover:text-white"
                >
                  <Save className="h-4 w-4 mr-2" />
                  Save as Draft
                </Button>
                <Button
                  onClick={handlePublishNotice}
                  className="bg-[#F4B315] hover:bg-[#E59312] text-[#1A141A]"
                >
                  <Send className="h-4 w-4 mr-2" />
                  Publish Notice
                </Button>
              </div>
            </CardContent>
          </Card>
        </TabsContent>

        <TabsContent value="manage" className="space-y-6">
          {/* Recent Notices */}
          <Card className="shadow-academic border-0">
            <CardHeader>
              <CardTitle className="text-[#1A141A]">Recent Notices</CardTitle>
              <CardDescription>Manage your published and draft notices</CardDescription>
            </CardHeader>
            <CardContent>
              <div className="space-y-4">
                {recentNotices.map((notice, index) => (
                  <div key={notice.id}>
                    <div className="flex items-start justify-between p-4 hover:bg-[#F4B315]/5 rounded-lg transition-colors">
                      <div className="flex-1">
                        <div className="flex items-start justify-between mb-2">
                          <h4 className="font-medium text-[#1A141A]">{notice.title}</h4>
                          <div className="flex space-x-2">
                            <Badge className={`${getPriorityColor(notice.priority)} text-xs border`} variant="outline">
                              {notice.priority.toUpperCase()}
                            </Badge>
                            <Badge className={`${getCategoryColor(notice.category)} text-xs`} variant="secondary">
                              {notice.category}
                            </Badge>
                            <Badge className={`${getStatusColor(notice.status)} text-xs border`} variant="outline">
                              {notice.status}
                            </Badge>
                          </div>
                        </div>
                        <p className="text-sm text-[#423738] mb-3 line-clamp-2">{notice.content}</p>
                        <div className="flex items-center space-x-4 text-xs text-[#8E5915]">
                          <div className="flex items-center space-x-1">
                            <Calendar className="h-3 w-3" />
                            <span>{new Date(notice.date).toLocaleDateString()}</span>
                          </div>
                          <div className="flex items-center space-x-1">
                            <Users className="h-3 w-3" />
                            <span>{notice.targetAudience}</span>
                          </div>
                          {notice.status === 'published' && (
                            <div className="flex items-center space-x-1">
                              <Eye className="h-3 w-3" />
                              <span>{notice.views} views</span>
                            </div>
                          )}
                        </div>
                      </div>
                      <div className="flex items-center space-x-2 ml-4">
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
                          className="text-red-500 hover:bg-red-50"
                        >
                          <Trash2 className="h-4 w-4" />
                        </Button>
                      </div>
                    </div>
                    {index < recentNotices.length - 1 && (
                      <Separator className="bg-[#D3AF85]/20" />
                    )}
                  </div>
                ))}
              </div>
            </CardContent>
          </Card>

          {/* Notice Statistics */}
          <div className="grid grid-cols-1 md:grid-cols-4 gap-4">
            <Card className="shadow-academic border-0">
              <CardContent className="p-4">
                <div className="flex items-center justify-between">
                  <div>
                    <p className="text-sm font-medium text-[#423738]">Total Notices</p>
                    <p className="text-2xl font-bold text-[#1A141A]">24</p>
                  </div>
                  <FileText className="h-8 w-8 text-[#F4B315]" />
                </div>
              </CardContent>
            </Card>

            <Card className="shadow-academic border-0">
              <CardContent className="p-4">
                <div className="flex items-center justify-between">
                  <div>
                    <p className="text-sm font-medium text-[#423738]">Published</p>
                    <p className="text-2xl font-bold text-green-600">18</p>
                  </div>
                  <Send className="h-8 w-8 text-green-600" />
                </div>
              </CardContent>
            </Card>

            <Card className="shadow-academic border-0">
              <CardContent className="p-4">
                <div className="flex items-center justify-between">
                  <div>
                    <p className="text-sm font-medium text-[#423738]">Drafts</p>
                    <p className="text-2xl font-bold text-yellow-600">4</p>
                  </div>
                  <Save className="h-8 w-8 text-yellow-600" />
                </div>
              </CardContent>
            </Card>

            <Card className="shadow-academic border-0">
              <CardContent className="p-4">
                <div className="flex items-center justify-between">
                  <div>
                    <p className="text-sm font-medium text-[#423738]">Scheduled</p>
                    <p className="text-2xl font-bold text-blue-600">2</p>
                  </div>
                  <Clock className="h-8 w-8 text-blue-600" />
                </div>
              </CardContent>
            </Card>
          </div>
        </TabsContent>
      </Tabs>
    </div>
  );
}