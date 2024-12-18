import React from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

const HomePage: React.FC = () => {
  const navigate = useNavigate();
  const { logout } = useAuth(); // Destructure logout from AuthContext

  const navigationButtons = [
    { 
      label: 'Matches', 
      route: '/matches',
      bgColor: 'bg-green-500 hover:bg-green-600'
    },
    { 
      label: 'Teams', 
      route: '/teams',
      bgColor: 'bg-red-500 hover:bg-red-600'
    },
    { 
      label: 'Supporters', 
      route: '/supporters',
      bgColor: 'bg-purple-500 hover:bg-purple-600'
    }
  ];

  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-gray-100 p-4 space-y-6">
      <h1 className="text-4xl font-bold mb-8 text-gray-800">Sports Management Dashboard</h1>
      <div className="grid grid-cols-3 gap-6 w-full max-w-2xl">
        {navigationButtons.map((button) => (
          <button
            key={button.label}
            onClick={() => navigate(button.route)}
            className={` 
              ${button.bgColor}
              text-white 
              font-bold 
              py-6 
              px-4 
              rounded-lg 
              text-2xl 
              shadow-lg 
              transform 
              transition-all 
              duration-300 
              hover:scale-105 
              focus:outline-none 
              focus:ring-2 
              focus:ring-opacity-50
              w-full
            `}
          >
            {button.label}
          </button>
        ))}
      </div>
      
      {/* Logout Button */}
      <button
        onClick={() => {
          logout(); // Call the logout function
          navigate('/');
        }}
        className="bg-gray-800 hover:bg-gray-900 text-white font-bold py-4 px-8 rounded-lg text-lg shadow-lg transform transition-all duration-300 hover:scale-105 focus:outline-none focus:ring-2 focus:ring-gray-800 focus:ring-opacity-50 mt-8"
      >
        Logout
      </button>
    </div>
  );
};

export default HomePage;