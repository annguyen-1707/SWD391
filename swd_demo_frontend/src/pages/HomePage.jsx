import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { UserPlus, BookOpenCheck } from 'lucide-react';

const HomePage = () => {
  const [selectedRole, setSelectedRole] = useState(
    localStorage.getItem('userRole') || 'Learner'
  );

  useEffect(() => {
    let id = 3;
    if (selectedRole === 'PlatformAdmin') id = 1;
    if (selectedRole === 'ContentAdmin') id = 2;
    if (selectedRole === 'CourseAuthor') id = 4;
    
    localStorage.setItem('userRole', selectedRole);
    localStorage.setItem('userId', id);
  }, [selectedRole]);

  return (
    <div className="space-y-12 py-10">
      <div className="text-center space-y-6">
        <h2 className="text-4xl font-extrabold tracking-tight text-slate-900 sm:text-5xl">SWD Demo Portal</h2>
        <p className="max-w-xl mx-auto text-lg text-slate-500">
          Select your role below to simulate a login. Then, try accessing the different system modules to test Role-Based Access Control (RBAC).
        </p>
        
        {/* Role Selector Dropdown */}
        <div className="max-w-xs mx-auto bg-white p-4 rounded-2xl shadow-sm border border-slate-200">
          <label className="block text-sm font-semibold text-slate-700 mb-2">Simulate Login As:</label>
          <select 
            value={selectedRole}
            onChange={(e) => setSelectedRole(e.target.value)}
            className="w-full bg-slate-50 border border-slate-200 text-indigo-700 rounded-xl px-4 py-3 focus:outline-none focus:ring-2 focus:ring-blue-500 font-bold"
          >
            <option value="Learner">Learner (Guest/Student)</option>
            <option value="CourseAuthor">Course Author (Instructor)</option>
            <option value="ContentAdmin">Content Administrator</option>
            <option value="PlatformAdmin">Platform Administrator</option>
          </select>
        </div>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 gap-8 max-w-4xl mx-auto px-4">
        {/* Module 1: Admin System */}
        <Link to="/admin-system" className="group rounded-2xl border border-slate-200 bg-white p-8 hover:shadow-xl transition-all hover:border-blue-500 focus:outline-none focus:ring-2 focus:ring-blue-500">
          <div className="bg-blue-100 w-16 h-16 rounded-full flex items-center justify-center mb-6 group-hover:scale-110 transition-transform">
            <UserPlus className="w-8 h-8 text-blue-600" />
          </div>
          <h3 className="text-2xl font-semibold text-slate-900 mb-2">User Management System</h3>
          <p className="text-slate-500 mb-6">UC55 - Interface for creating users, generating tokens, and mocking notifications.</p>
          <span className="text-blue-600 font-medium inline-flex items-center group-hover:underline">
            Access Module
            <svg className="ml-2 w-4 h-4 group-hover:translate-x-1 transition-transform" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 5l7 7-7 7" />
            </svg>
          </span>
        </Link>

        {/* Module 2: Content Admin */}
        <Link to="/content-admin" className="group rounded-2xl border border-slate-200 bg-white p-8 hover:shadow-xl transition-all hover:border-emerald-500 focus:outline-none focus:ring-2 focus:ring-emerald-500">
          <div className="bg-emerald-100 w-16 h-16 rounded-full flex items-center justify-center mb-6 group-hover:scale-110 transition-transform">
            <BookOpenCheck className="w-8 h-8 text-emerald-600" />
          </div>
          <h3 className="text-2xl font-semibold text-slate-900 mb-2">Course Approval Dashboard</h3>
          <p className="text-slate-500 mb-6">UC28 - Dashboard to view, filter, and approve/reject pending courses.</p>
          <span className="text-emerald-600 font-medium inline-flex items-center group-hover:underline">
            Access Module
            <svg className="ml-2 w-4 h-4 group-hover:translate-x-1 transition-transform" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 5l7 7-7 7" />
            </svg>
          </span>
        </Link>
      </div>
    </div>
  );
};

export default HomePage;
