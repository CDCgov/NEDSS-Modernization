import { useEffect, useState } from 'react';
import { DataElements } from '../data-elements/DataElement';

export const useDataElements = () => {
    const [dataElements, setDataElements] = useState<DataElements | undefined>();
    const [error, setError] = useState<string | undefined>();
    const [loading, setLoading] = useState<boolean>(false);
    const fetchDataElements = async () => {
        setLoading(true);
        await fetch('/api/deduplication/data-elements', {
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

    const save = (elements: DataElements): Promise<boolean> => {
        console.log('saving elements', elements);
        // call API persist values
        return Promise.resolve(true);
    };

    useEffect(() => {
        fetchDataElements();
    }, []);

    return { fetchDataElements, save, loading, error, dataElements };
};
