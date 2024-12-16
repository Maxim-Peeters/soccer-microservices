import { useEffect, useState } from 'react';
import { useAuth } from '../contexts/AuthContext';

export function useAuthenticatedFetch<T>(fetchFunction: (token: string) => Promise<T>) {
  const [data, setData] = useState<T | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<Error | null>(null);
  const { token } = useAuth();

  useEffect(() => {
    if (!token) {
      setLoading(false);
      return;
    }

    const fetchData = async () => {
      try {
        const result = await fetchFunction(token);
        setData(result);
        setError(null);
      } catch (err) {
        setError(err instanceof Error ? err : new Error('An error occurred'));
        setData(null);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [token, fetchFunction]);

  return { data, loading, error };
}

