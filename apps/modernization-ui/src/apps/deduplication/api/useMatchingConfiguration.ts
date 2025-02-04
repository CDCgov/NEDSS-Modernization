import { useEffect, useState } from 'react';
import axios from 'axios';
import { MatchingConfiguration } from '../match-configuration/model/Pass';

// Correctly typing the return value of the hook
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
        console.log('Fetching matching configuration...');

        try {
            const response = await axios.get('http://localhost:8083/api/deduplication/matching-configuration');

            if (response.data) {
                setMatchConfiguration(response.data);
                console.log('Configuration fetched successfully:', response.data);
            } else {
                console.warn('No matching configuration found. Prompting user to input settings.');
                setMatchConfiguration({ passes: [] }); // Provide an empty structure instead of null
            }
        } catch (err) {
            setError('Failed to fetch matching configuration');
            console.error('Error fetching configuration:', err);
        } finally {
            setLoading(false);
        }
    };

    const save = async (configuration: MatchingConfiguration, successCallback?: () => void) => {
        setLoading(true);
        console.log('Saving matching configuration...', configuration); // Log the data to be saved

        try {
            await axios.post('http://localhost:8083/api/deduplication/configure-matching', configuration);
            setMatchConfiguration(configuration);
            console.log('Configuration saved successfully:', configuration); // Log success

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
