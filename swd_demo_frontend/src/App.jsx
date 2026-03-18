import React from 'react';
import { BrowserRouter, Routes, Route, Link } from 'react-router-dom';
import HomePage from './pages/HomePage';
import AdminSystemPage from './pages/AdminSystemPage';
import ContentAdminDashboard from './pages/ContentAdminDashboard';

function App() {
  return (
    <BrowserRouter>
      <div className="min-h-screen flex flex-col font-sans">
        <header className="bg-white shadow">
          <div className="max-w-7xl mx-auto px-4 py-6 sm:px-6 lg:px-8 flex justify-between items-center">
            <h1 className="text-3xl font-bold text-gray-900 tracking-tight">SWD Demo</h1>
            <nav className="flex space-x-4">
              <Link to="/" className="text-gray-500 hover:text-gray-900 font-medium">Home</Link>
            </nav>
          </div>
        </header>

        <main className="flex-1 w-full max-w-7xl mx-auto py-6 sm:px-6 lg:px-8">
          <Routes>
            <Route path="/" element={<HomePage />} />
            <Route path="/admin-system" element={<AdminSystemPage />} />
            <Route path="/content-admin" element={<ContentAdminDashboard />} />
          </Routes>
        </main>

        <footer className="bg-white py-4 mt-auto">
          <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
            <p className="text-center text-sm text-gray-500">© 2026 SWD Demo. All rights reserved.</p>
          </div>
        </footer>
      </div>
    </BrowserRouter>
  );
}

export default App;
