import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { BookOpenCheck, Filter, ArrowUpDown } from 'lucide-react';
import { Link } from 'react-router-dom';

const ContentAdminDashboard = () => {
  const [courses, setCourses] = useState([]);
  const [statusFilter, setStatusFilter] = useState('');
  const [sortOption, setSortOption] = useState('createdAt');
  const [selectedCourse, setSelectedCourse] = useState(null);
  const [loading, setLoading] = useState(false);
  
  const userRole = localStorage.getItem('userRole');
  const adminId = localStorage.getItem('userId') || 2;

  const fetchCourses = async () => {
    setLoading(true);
    try {
      const response = await axios.get('http://localhost:8080/api/admin/courses', {
        params: {
          status: statusFilter || undefined,
          sortOption: sortOption
        }
      });
      setCourses(response.data);
    } catch (error) {
      console.error("Failed to fetch courses", error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchCourses();
  }, [statusFilter, sortOption]);

  if (userRole !== 'ContentAdmin') {
    return (
      <div className="flex flex-col items-center justify-center p-12 mt-12 text-center bg-white rounded-2xl shadow-sm border border-slate-200">
        <h2 className="text-3xl font-bold text-red-600 mb-4">403 Forbidden</h2>
        <p className="text-slate-600 mb-6">You do not have permission to access the Content Administrator Dashboard. Your current role is {userRole || 'Guest'}.</p>
        <Link to="/" className="px-6 py-2 bg-slate-900 text-white rounded-lg hover:bg-slate-800 transition-colors">Return to Home</Link>
      </div>
    );
  }

  const handleStatusUpdate = async (id, newStatus) => {
    try {
      const response = await axios.put(`http://localhost:8080/api/admin/courses/${id}/status`, {
        status: newStatus,
        adminId: adminId
      });
      setCourses(response.data);
      if (selectedCourse && selectedCourse.courseId === id) {
        setSelectedCourse(response.data.find(c => c.courseId === id));
      }
    } catch (error) {
      console.error("Error updating status", error);
      const errorMessage = error.response?.data || error.message;
      alert(`[Permission Exception Demo]\nServer responded with Error:\n\n${errorMessage}`);
    }
  };

  const getStatusBadge = (status) => {
    switch (status) {
      case 'APPROVED': return <span className="px-3 py-1 bg-emerald-100 text-emerald-800 text-xs font-semibold rounded-full">Approved</span>;
      case 'REJECTED': return <span className="px-3 py-1 bg-red-100 text-red-800 text-xs font-semibold rounded-full">Rejected</span>;
      default: return <span className="px-3 py-1 bg-amber-100 text-amber-800 text-xs font-semibold rounded-full">Pending</span>;
    }
  };

  return (
    <div className="py-8 space-y-6">
      <div className="flex flex-col md:flex-row md:items-center justify-between gap-4">
        <div className="flex items-center space-x-4">
          <div className="bg-emerald-100 p-3 rounded-xl">
            <BookOpenCheck className="w-6 h-6 text-emerald-600" />
          </div>
          <div>
            <h2 className="text-xl font-bold text-slate-900">Content Administrator Demo</h2>
            <p className="text-sm text-slate-500">Course Approval Dashboard (UC28)</p>
          </div>
        </div>

        <div className="flex items-center space-x-3 bg-white p-2 rounded-xl shadow-sm border border-slate-200">
          <div className="flex items-center px-3 border-r border-slate-200">
            <Filter className="w-4 h-4 text-slate-400 mr-2" />
            <select
              value={statusFilter}
              onChange={(e) => setStatusFilter(e.target.value)}
              className="bg-transparent text-sm text-slate-700 focus:outline-none"
            >
              <option value="">All Statuses</option>
              <option value="PENDING">Pending</option>
              <option value="APPROVED">Approved</option>
              <option value="REJECTED">Rejected</option>
            </select>
          </div>
          <div className="flex items-center px-3">
            <ArrowUpDown className="w-4 h-4 text-slate-400 mr-2" />
            <select
              value={sortOption}
              onChange={(e) => setSortOption(e.target.value)}
              className="bg-transparent text-sm text-slate-700 focus:outline-none"
            >
              <option value="createdAt">Newest First</option>
              <option value="name">Alphabetical</option>
            </select>
          </div>
        </div>
      </div>

      <div className="bg-white rounded-2xl shadow-sm border border-slate-200 overflow-hidden">
          <div className="overflow-x-auto">
            <table className="w-full text-left text-sm text-slate-600">
              <thead className="bg-slate-50 text-slate-900 font-medium border-b border-slate-200">
                <tr>
                  <th className="px-6 py-4">Course Code</th>
                  <th className="px-6 py-4">Course Name</th>
                  <th className="px-6 py-4">Status</th>
                  <th className="px-6 py-4 text-right">Actions</th>
                </tr>
              </thead>
              <tbody className="divide-y divide-slate-100">
                {courses.length === 0 ? (
                   <tr><td colSpan="4" className="px-6 py-8 text-center text-slate-500">No courses found matching criteria.</td></tr>
                ) : (
                  courses.map((course) => (
                    <tr key={course.courseId} className={`hover:bg-slate-50 transition-colors ${selectedCourse?.courseId === course.courseId ? 'bg-blue-50/50' : ''}`}>
                      <td className="px-6 py-4 font-medium text-slate-900">{course.courseCode}</td>
                      <td className="px-6 py-4">{course.name}</td>
                      <td className="px-6 py-4">{getStatusBadge(course.status)}</td>
                      <td className="px-6 py-4 text-right space-x-4">
                        {course.status === 'PENDING' && (
                          <>
                            <button
                              onClick={(e) => { e.stopPropagation(); handleStatusUpdate(course.courseId, 'APPROVED'); }}
                              className="text-emerald-600 hover:text-emerald-800 font-medium text-sm transition-colors bg-emerald-50 px-2 py-1 rounded"
                            >
                              Approve
                            </button>
                            <button
                              onClick={(e) => { e.stopPropagation(); handleStatusUpdate(course.courseId, 'REJECTED'); }}
                              className="text-red-600 hover:text-red-800 font-medium text-sm transition-colors bg-red-50 px-2 py-1 rounded"
                            >
                              Reject
                            </button>
                          </>
                        )}
                        <button
                          onClick={() => setSelectedCourse(course)}
                          className="text-blue-600 hover:text-blue-800 font-medium text-sm transition-colors border border-blue-100 px-2 py-1 rounded hover:bg-blue-50"
                        >
                          Details
                        </button>
                      </td>
                    </tr>
                  ))
                )}
              </tbody>
            </table>
          </div>
      </div>

      {/* Modal Popup for Details */}
      {selectedCourse && (
        <div className="fixed inset-0 z-50 flex items-center justify-center p-4 bg-slate-900/50 backdrop-blur-sm">
          <div className="bg-white rounded-2xl shadow-xl w-full max-w-lg overflow-hidden flex flex-col max-h-[90vh]">
            <div className="p-6 border-b border-slate-100 bg-slate-50/50 flex justify-between items-center">
              <h3 className="font-bold text-slate-900">Course Details</h3>
              <div className="flex items-center space-x-3">
                {getStatusBadge(selectedCourse.status)}
                <button onClick={() => setSelectedCourse(null)} className="text-slate-400 hover:text-slate-600 transition-colors">
                  <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M6 18L18 6M6 6l12 12" /></svg>
                </button>
              </div>
            </div>
            <div className="p-6 space-y-4 overflow-y-auto">
              <div>
                <p className="text-xs font-semibold text-slate-400 uppercase tracking-wider mb-1">Course Code</p>
                <p className="font-medium text-slate-900">{selectedCourse.courseCode}</p>
              </div>
              <div>
                <p className="text-xs font-semibold text-slate-400 uppercase tracking-wider mb-1">Course Name</p>
                <p className="font-medium text-slate-900">{selectedCourse.name}</p>
              </div>
              <div className="grid grid-cols-2 gap-4">
                <div>
                  <p className="text-xs font-semibold text-slate-400 uppercase tracking-wider mb-1">Author</p>
                  <p className="text-slate-700">{selectedCourse.author || 'N/A'}</p>
                </div>
                <div>
                  <p className="text-xs font-semibold text-slate-400 uppercase tracking-wider mb-1">Category</p>
                  <p className="text-slate-700">{selectedCourse.category || 'N/A'}</p>
                </div>
              </div>
              <div className="grid grid-cols-2 gap-4">
                <div>
                  <p className="text-xs font-semibold text-slate-400 uppercase tracking-wider mb-1">Credits</p>
                  <p className="text-slate-700">{selectedCourse.credits || 'N/A'}</p>
                </div>
                <div>
                  <p className="text-xs font-semibold text-slate-400 uppercase tracking-wider mb-1">Submitted On</p>
                  <p className="text-slate-700">{new Date(selectedCourse.createdAt).toLocaleString()}</p>
                </div>
              </div>
              <div>
                <p className="text-xs font-semibold text-slate-400 uppercase tracking-wider mb-1">Description</p>
                <p className="text-sm text-slate-700 leading-relaxed bg-slate-50 p-3 rounded-lg border border-slate-100">{selectedCourse.description || 'No description provided.'}</p>
              </div>
              {selectedCourse.approvedAt && (
                <div>
                  <p className="text-xs font-semibold text-slate-400 uppercase tracking-wider mb-1">Reviewed On</p>
                  <p className="text-slate-700">{new Date(selectedCourse.approvedAt).toLocaleString()}</p>
                </div>
              )}
            </div>
            <div className="p-4 border-t border-slate-100 bg-slate-50 flex justify-end">
              <button 
                onClick={() => setSelectedCourse(null)}
                className="px-4 py-2 bg-white border border-slate-200 text-slate-700 rounded-lg hover:bg-slate-50 font-medium transition-colors shadow-sm"
              >
                Close
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default ContentAdminDashboard;
