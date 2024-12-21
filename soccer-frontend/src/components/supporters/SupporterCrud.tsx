import React, { useEffect, useState } from 'react';
import { SupporterResponse } from '../../dto/SupporterResponse';
import { LoadingSpinner } from '../LoadingSpinner';
import AddEditSupporter from './AddEditSupporter';
import SupporterService from '../../services/supporterService';
import { useAuth } from '../../context/AuthContext';
import { useNavigate } from 'react-router-dom';
const SupporterCrud: React.FC = () => {
    const { isAuthenticated } = useAuth();
    const [supporters, setSupporters] = useState<SupporterResponse[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);
    const [isAddEditOpen, setIsAddEditOpen] = useState(false);
    const [selectedSupporter, setSelectedSupporter] = useState<SupporterResponse | null>(null);
    const navigate = useNavigate();

    useEffect(() => {
        if (!isAuthenticated) {
            window.alert("You are not authenticated, please login first");
            navigate('/', { replace: true });
            return;
        }
        fetchSupporters();
    }, [isAuthenticated, navigate]);

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

    const openAddEdit = (supporter?: SupporterResponse) => {
        setSelectedSupporter(supporter || null);
        setIsAddEditOpen(true);
    };

    const closeAddEdit = () => {
        setIsAddEditOpen(false);
        setSelectedSupporter(null);
        fetchSupporters();
    };

    const handleDeleteSupporter = async (supporterCode: string) => {
        if (window.confirm('Are you sure you want to delete this supporter?')) {
            try {
                await SupporterService.deleteSupporter(supporterCode);
                fetchSupporters();
            } catch (err) {
                setError('Failed to delete supporter');
            }
        }
    };

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
        <div className="container mx-auto px-4">
            <h1 className="text-4xl font-bold mb-8 text-center">Supporter Management</h1>
            <button 
                onClick={() => openAddEdit()}
                className="mb-4 px-4 py-2 bg-green-500 text-white rounded-lg hover:bg-green-600 transition-colors"
            >
                Add New Supporter
            </button>
            <div className="overflow-x-auto">
                <table className="min-w-full bg-white">
                    <thead>
                        <tr>
                            <th className="py-2 px-4 border-b">Name</th>
                            <th className="py-2 px-4 border-b">Email</th>
                            <th className="py-2 px-4 border-b">Birth Date</th>
                            <th className="py-2 px-4 border-b">Team</th>
                            <th className="py-2 px-4 border-b">Favorite Player</th>
                            <th className="py-2 px-4 border-b">Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {supporters.map((supporter) => (
                            <tr key={supporter.supporterCode}>
                                <td className="py-2 px-4 border-b">{supporter.firstName} {supporter.lastName}</td>
                                <td className="py-2 px-4 border-b">{supporter.email}</td>
                                <td className="py-2 px-4 border-b">{new Date(supporter.birthDate).toLocaleDateString()}</td>
                                <td className="py-2 px-4 border-b">{supporter.teamName.name}</td>
                                <td className="py-2 px-4 border-b">
                                    {supporter.favoritePlayer.firstName} {supporter.favoritePlayer.lastName}
                                </td>
                                <td className="py-2 px-4 border-b">
                                    <button 
                                        onClick={() => openAddEdit(supporter)}
                                        className="px-2 py-1 bg-yellow-500 text-white rounded hover:bg-yellow-600 transition-colors mr-2"
                                    >
                                        Edit
                                    </button>
                                    <button 
                                        onClick={() => handleDeleteSupporter(supporter.supporterCode)}
                                        className="px-2 py-1 bg-red-500 text-white rounded hover:bg-red-600 transition-colors"
                                    >
                                        Delete
                                    </button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
            {isAddEditOpen && <AddEditSupporter onClose={closeAddEdit} supporter={selectedSupporter} />}
        </div>
    );
};

export default SupporterCrud;
