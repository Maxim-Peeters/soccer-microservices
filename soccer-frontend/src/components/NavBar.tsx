import React from 'react';
import { Link } from 'react-router-dom';

const navigationButtons = [
    { 
        label: 'Matches', 
        route: '/matches',
        bgColor: 'hover:bg-green-600'
    },
    { 
        label: 'Teams', 
        route: '/teams',
        bgColor: 'hover:bg-red-600'
    },
    { 
        label: 'Supporters', 
        route: '/supporters',
        bgColor: 'hover:bg-purple-600'
    }
];

const NavBar: React.FC = () => {
    return (
        <nav className="bg-gray-800 p-4 shadow-md">
            <div className="container mx-auto flex justify-between items-center">
                <div className="text-white text-2xl font-bold">
                    Soccer App
                </div>
                <div className="flex space-x-4">
                    {navigationButtons.map((button) => (
                        <Link 
                            key={button.route} 
                            to={button.route} 
                            className={`text-white px-3 py-2 rounded-md text-sm font-medium transition duration-300 ${button.bgColor}`}
                        >
                            {button.label}
                        </Link>
                    ))}
                </div>
            </div>
        </nav>
    );
};

export default NavBar;