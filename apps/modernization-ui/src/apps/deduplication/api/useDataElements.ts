import { useEffect, useState } from 'react';
import { DataElements } from '../data-elements/types/DataElement';

const KEY = 'deduplication-dataElements';

export const useDataElements = () => {
    const [configuration, setConfiguration] = useState<DataElements | undefined>();
    const [error] = useState<string | undefined>();
    const [loading, setLoading] = useState<boolean>(false);

    const fetchDataElements = async () => {
        setLoading(true);
        const dataElementsString: string | null = localStorage.getItem(KEY);
        if (dataElementsString != null) {
            const dataElements: DataElements = JSON.parse(dataElementsString);
            setConfiguration(dataElements);
        } else {
            setConfiguration(undefined);
        }

        setLoading(false);
    };

    const save = async (configuration: DataElements, successCallback?: () => void) => {
        localStorage.setItem(KEY, JSON.stringify(configuration));
        setConfiguration(configuration);

        successCallback?.();
    };

    useEffect(() => {
        fetchDataElements();
    }, []);

    return { fetchDataElements, save, loading, error, configuration };
};
