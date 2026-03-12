import React from 'react';
import { HomePage } from './pages/HomePage';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

function App() {
  return (
    <div className="min-h-screen bg-background text-foreground antialiased">
        <div className="flex-1 space-y-4 p-8 pt-6">
          <HomePage />
        </div>
        <ToastContainer position="bottom-right" theme="dark" />
    </div>
  );
}

export default App;
