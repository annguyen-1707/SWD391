import React, { useState } from 'react';
import axios from 'axios';
import { UserPlus, CheckCircle, XCircle } from 'lucide-react';
import { Link } from 'react-router-dom';

const ROLE_OPTIONS = [
  { id: 1, name: 'Platform Admin' },
  { id: 2, name: 'Content Admin' }
];

const AdminSystemPage = () => {
  const userRole = localStorage.getItem('userRole');

  const [formData, setFormData] = useState({
    username: '',
    email: '',
    password: '',
    roleIds: [2] // default ContentAdmin
  });

  const [status, setStatus] = useState({ type: '', message: '' });
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setStatus({ type: '', message: '' });

    try {
      const payload = {
        ...formData,
        roleIds: formData.roleIds.map(Number)
      };

      const response = await axios.post('http://localhost:8080/api/users', payload);

      setStatus({ type: 'success', message: response.data.message || 'User created successfully.' });
      setFormData({
        username: '',
        email: '',
        password: '',
        roleIds: [2]
      });
    } catch (error) {
      setStatus({
        type: 'error',
        message: error.response?.data?.message || 'Failed to create user. Ensure backend is running.'
      });
    } finally {
      setLoading(false);
    }
  };

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleRoleChange = (roleId) => {
    setFormData((prev) => {
      const exists = prev.roleIds.includes(roleId);

      return {
        ...prev,
        roleIds: exists
          ? prev.roleIds.filter((id) => id !== roleId)
          : [...prev.roleIds, roleId]
      };
    });
  };

  if (userRole !== 'PlatformAdmin') {
    return (
      <div className="flex flex-col items-center justify-center p-12 mt-12 text-center bg-white rounded-2xl shadow-sm border border-slate-200">
        <h2 className="text-3xl font-bold text-red-600 mb-4">403 Forbidden</h2>
        <p className="text-slate-600 mb-6">
          You do not have permission to access the Platform Administrator Dashboard. Your current role is {userRole || 'Guest'}.
        </p>
        <Link
          to="/"
          className="px-6 py-2 bg-slate-900 text-white rounded-lg hover:bg-slate-800 transition-colors"
        >
          Return to Home
        </Link>
      </div>
    );
  }

  return (
    <div className="max-w-2xl mx-auto py-8">
      <div className="bg-white rounded-2xl shadow-sm border border-slate-200 overflow-hidden">
        <div className="p-8 border-b border-slate-100 bg-slate-50/50">
          <div className="flex items-center space-x-4">
            <div className="bg-blue-100 p-3 rounded-xl">
              <UserPlus className="w-6 h-6 text-blue-600" />
            </div>
            <div>
              <h2 className="text-xl font-bold text-slate-900">Platform Administrator Demo</h2>
              <p className="text-sm text-slate-500">Create User Account (UC55)</p>
            </div>
          </div>
        </div>

        <div className="p-8">
          {status.message && (
            <div
              className={`mb-6 p-4 rounded-xl flex items-start space-x-3 ${
                status.type === 'success'
                  ? 'bg-emerald-50 text-emerald-800 border border-emerald-200'
                  : 'bg-red-50 text-red-800 border border-red-200'
              }`}
            >
              {status.type === 'success' ? (
                <CheckCircle className="w-5 h-5 mt-0.5 text-emerald-500" />
              ) : (
                <XCircle className="w-5 h-5 mt-0.5 text-red-500" />
              )}
              <p className="font-medium text-sm">{status.message}</p>
            </div>
          )}

          <form onSubmit={handleSubmit} className="space-y-6">
            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
              <div className="space-y-2">
                <label className="text-sm font-medium text-slate-700">Username</label>
                <input
                  required
                  type="text"
                  name="username"
                  value={formData.username}
                  onChange={handleChange}
                  className="w-full px-4 py-3 rounded-xl border border-slate-200 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent transition-all"
                  placeholder="Enter username"
                />
              </div>

              <div className="space-y-2">
                <label className="text-sm font-medium text-slate-700">Email Address</label>
                <input
                  required
                  type="email"
                  name="email"
                  value={formData.email}
                  onChange={handleChange}
                  className="w-full px-4 py-3 rounded-xl border border-slate-200 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent transition-all"
                  placeholder="user@example.com"
                />
              </div>
            </div>

            <div className="space-y-2">
              <label className="text-sm font-medium text-slate-700">Password</label>
              <input
                required
                type="password"
                name="password"
                value={formData.password}
                onChange={handleChange}
                className="w-full px-4 py-3 rounded-xl border border-slate-200 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent transition-all"
                placeholder="••••••••"
              />
            </div>

            <div className="space-y-3">
              <label className="text-sm font-medium text-slate-700">Assign Roles</label>

              <div className="space-y-3 rounded-xl border border-slate-200 p-4">
                {ROLE_OPTIONS.map((role) => (
                  <label
                    key={role.id}
                    className="flex items-center gap-3 cursor-pointer"
                  >
                    <input
                      type="checkbox"
                      checked={formData.roleIds.includes(role.id)}
                      onChange={() => handleRoleChange(role.id)}
                      className="h-4 w-4 rounded border-slate-300 text-blue-600 focus:ring-blue-500"
                    />
                    <span className="text-slate-700">{role.name}</span>
                  </label>
                ))}
              </div>

              <p className="text-xs text-slate-500">
                You can assign one or multiple roles.
              </p>
            </div>

            <button
              type="submit"
              disabled={loading || formData.roleIds.length === 0}
              className="w-full bg-blue-600 hover:bg-blue-700 text-white font-semibold py-3 px-6 rounded-xl shadow-sm transition-all disabled:opacity-70 disabled:cursor-not-allowed flex justify-center items-center"
            >
              {loading ? (
                <svg
                  className="animate-spin -ml-1 mr-3 h-5 w-5 text-white"
                  xmlns="http://www.w3.org/2000/svg"
                  fill="none"
                  viewBox="0 0 24 24"
                >
                  <circle
                    className="opacity-25"
                    cx="12"
                    cy="12"
                    r="10"
                    stroke="currentColor"
                    strokeWidth="4"
                  />
                  <path
                    className="opacity-75"
                    fill="currentColor"
                    d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"
                  />
                </svg>
              ) : (
                'Create User Account'
              )}
            </button>
          </form>
        </div>
      </div>
    </div>
  );
};

export default AdminSystemPage;