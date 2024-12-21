import React, { useState, useEffect } from 'react';
import { SupporterResponse } from '../dto/SupporterResponse';
import { LoadingSpinner } from '../components/LoadingSpinner';

import { useNavigate } from 'react-router-dom';
import SupporterService from '../services/supporterService';
import SupporterCard from '../components/supporters/SupporterCard';
import { useAuth } from '../context/AuthContext';
const SupportersPage: React.FC = () => {
    const {isAuthenticated} = useAuth();
    const [supporters, setSupporters] = useState<SupporterResponse[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);
    const navigate = useNavigate();

    const fetchSupporters = async () => {
        try {
            setLoading(true);
            setError(null);
            const fetchedSupporters = await SupporterService.getAllSupporters();
            setSupporters(fetchedSupporters);
        } catch (err) {
            setError('Failed to fetch supporters');
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchSupporters();
    }, []);

    if (loading) return <LoadingSpinner />;

    if (error) {
        return (
            <div className="flex flex-col items-center justify-center min-h-[400px] space-y-4">
                <p className="text-red-500 text-lg">{error}</p>
                <button 
                    onClick={() => fetchSupporters()}
                    className="px-4 py-2 bg-blue-500 text-white rounded-lg hover:bg-blue-600 transition-colors"
                >
                    Retry
                </button>
            </div>
        );
    }

    return (
        <div className="container mx-auto px-4 py-8">
            <h1 className="text-4xl font-bold mb-4 text-center">Supporters</h1>
            <div className="flex justify-center mb-4">
                {isAuthenticated && (
                    <button
                    onClick={() => navigate('/manage-supporters')}
                    className="px-4 py-2 bg-green-500 text-white rounded-lg hover:bg-green-600 transition-colors"
                >
                    Manage Supporters
                </button>)}
            </div>
            <div className="grid grid-cols-1 md:grid-cols-2 xl:grid-cols-3 gap-6">
                {supporters.map(supporter => (
                    <SupporterCard key={supporter.supporterCode} supporter={supporter} />
                ))}
            </div>
        </div>
    );
}

export default SupportersPage;