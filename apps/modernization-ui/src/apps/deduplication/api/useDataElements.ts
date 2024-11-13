import { useEffect, useState } from 'react';
import { DataElementsConfiguration } from '../data-elements/DataElement';

const API_BASE = '/api/deduplication/data-elements';

export const useDataElements = () => {
    const [dataElements, setDataElements] = useState<DataElementsConfiguration | undefined>();
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
                setDataElements(await response.json());
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
                setDataElements(await response.json());
                successCallback?.();
            })
            .catch((error) => {
                setError(error);
            });
    };

    useEffect(() => {
        fetchDataElements();
    }, []);

    return { fetchDataElements, save, loading, error, dataElements };
};
