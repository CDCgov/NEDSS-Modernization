import { useEffect, useState } from 'react';
import { DataElementsConfiguration } from '../data-elements/DataElement';

const API_BASE = '/api/deduplication/data-elements';

type DataElementsConfigurationResponse = {
    configuration: DataElementsConfiguration;
};

export const useDataElements = () => {
    const [configuration, setConfiguration] = useState<DataElementsConfiguration | undefined>();
    const [error, setError] = useState<string | undefined>();
    const [loading, setLoading] = useState<boolean>(false);

    const fetchDataElements = async () => {
        setLoading(true);
        await fetch(API_BASE, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(async (response) => {
                const configResponse: DataElementsConfigurationResponse = await response.json();
                setConfiguration(configResponse.configuration);
            })
            .catch((error) => {
                setError(error);
            })
            .finally(() => setLoading(false));
    };

    const save = async (configuration: DataElementsConfiguration, successCallback?: () => void) => {
        await fetch(API_BASE, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(configuration)
        })
            .then(async (response) => {
                const configResponse: DataElementsConfigurationResponse = await response.json();
                setConfiguration(configResponse.configuration);
                successCallback?.();
            })
            .catch((error) => {
                setError(error);
            });
    };

    useEffect(() => {
        fetchDataElements();
    }, []);

    return { fetchDataElements, save, loading, error, configuration };
};
