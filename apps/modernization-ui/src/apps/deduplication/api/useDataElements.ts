import { useEffect, useState } from 'react';

export const useDataElements = () => {
    const [dataElements, setDataElements] = useState<string | undefined>();
    const [error] = useState<string | undefined>();
    const [loading, setLoading] = useState<boolean>(false);

    const fetchDataElements = async () => {
        setLoading(true);

        setDataElements('fake');

        setLoading(false);
    };

    useEffect(() => {
        fetchDataElements();
    }, []);

    return { fetchDataElements, loading, error, dataElements };
};
