import React, { useState } from 'react';
import TeamList from '../components/teams/TeamList';
import TeamCrud from '../components/teams/TeamCrud';

const TeamsPage: React.FC = () => {
  const [showCrud, setShowCrud] = useState(false);

  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-gray-100 p-4 space-y-6">
      <h1 className="text-4xl font-bold mb-8 text-gray-800">Teams</h1>
      {showCrud ? <TeamCrud /> : <TeamList />}
      <button
        onClick={() => setShowCrud(!showCrud)}
        className="bg-blue-500 hover:bg-blue-600 text-white font-bold py-4 px-8 rounded-lg text-lg shadow-lg transform transition-all duration-300 hover:scale-105 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-opacity-50 mt-8"
      >
        {showCrud ? 'View Teams List' : 'Manage Teams'}
      </button>
    </div>
  );
};

export default TeamsPage;