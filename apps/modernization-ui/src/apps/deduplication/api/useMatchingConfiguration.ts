import { useEffect, useState } from 'react';
import axios from 'axios';
import { MatchingConfiguration } from '../match-configuration/model/Pass';
import { Config } from '../../../config';

interface UseMatchingConfigurationReturn {
    matchConfiguration: MatchingConfiguration | null;
    save: (configuration: MatchingConfiguration, successCallback?: () => void) => void;
    error: string | null;
    loading: boolean;
}

export const useMatchingConfiguration = (): UseMatchingConfigurationReturn => {
    const [matchConfiguration, setMatchConfiguration] = useState<MatchingConfiguration | null>(null);
    const [error, setError] = useState<string | null>(null);
    const [loading, setLoading] = useState<boolean>(false);

    const fetchConfiguration = async () => {
        setLoading(true);
        try {
            const response = await axios.get(Config.matchingConfigApiUrl);

            if (response.data && Object.keys(response.data).length > 0) {
                setMatchConfiguration(response.data);
            } else {
                console.warn('No matching configuration found. Setting empty config.');
                setMatchConfiguration({ passes: [] }); // Prevent JSON errors
            }
        } catch (err) {
            setError('Failed to fetch configuration');
            console.error('API Error:', err);
        } finally {
            setLoading(false);
        }
    };

    const save = async (configuration: MatchingConfiguration, successCallback?: () => void) => {
        setLoading(true);
        try {
            await axios.post(Config.saveConfigApiUrl, configuration);
            setMatchConfiguration(configuration);

            successCallback?.();
        } catch (err) {
            setError('Failed to save matching configuration');
            console.error('Error saving configuration:', err); // Log error details
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchConfiguration();
    }, []);

    return { matchConfiguration, save, error, loading };
};
