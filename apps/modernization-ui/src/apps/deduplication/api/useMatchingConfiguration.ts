import { useEffect, useState } from 'react';
import { MatchingConfiguration } from '../match-configuration/model/Pass';

const KEY = 'deduplication-configuration';

export const useMatchingConfiguration = () => {
    const [matchConfiguration, setMatchConfiguration] = useState<MatchingConfiguration>({
        passes: []
    });
    const [error] = useState<string | undefined>();
    const [loading, setLoading] = useState<boolean>(false);

    const fetchConfiguration = async () => {
        setLoading(true);

        const configurationString: string | null = localStorage.getItem(KEY);
        if (configurationString != null) {
            const configuration: MatchingConfiguration = JSON.parse(configurationString);
            setMatchConfiguration(configuration);
        }

        setLoading(false);
    };

    const save = async (configuration: MatchingConfiguration, successCallback?: () => void) => {
        localStorage.setItem(KEY, JSON.stringify(configuration));
        setMatchConfiguration(configuration);

        successCallback?.();
    };

    useEffect(() => {
        fetchConfiguration();
    }, []);

    return { fetchConfiguration, save, loading, error, matchConfiguration };
};
