import { useEffect, useState } from 'react';
import { MatchingConfiguration } from '../match-configuration/model/Pass';

const API_BASE = '/api/deduplication/configuration';

type MatchingConfigurationResponse = {
    configuration: MatchingConfiguration;
};

export const useMatchingConfiguration = () => {
    const [matchConfiguration, setMatchConfiguration] = useState<MatchingConfiguration>({
        passes: []
    });
    const [error, setError] = useState<string | undefined>();
    const [loading, setLoading] = useState<boolean>(false);

    const fetchConfiguration = async () => {
        setLoading(true);
        await fetch(API_BASE, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(async (response) => {
                const configResponse: MatchingConfigurationResponse = await response.json();
                setMatchConfiguration(configResponse.configuration);
            })
            .catch((error) => {
                setError(error);
            })
            .finally(() => setLoading(false));
    };

    const save = async (configuration: MatchingConfiguration, successCallback?: () => void) => {
        await fetch(API_BASE, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(configuration)
        })
            .then(async (response) => {
                const configResponse: MatchingConfigurationResponse = await response.json();
                setMatchConfiguration(configResponse.configuration);
                successCallback?.();
            })
            .catch((error) => {
                setError(error);
            });
    };

    useEffect(() => {
        // fetchConfiguration();
    }, []);

    return { fetchConfiguration, save, loading, error, matchConfiguration };
};
