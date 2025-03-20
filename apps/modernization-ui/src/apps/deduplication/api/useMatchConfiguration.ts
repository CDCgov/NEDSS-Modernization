import { useEffect, useState } from 'react';
import { Pass } from './model/Pass';

export const useMatchConfiguration = () => {
    const [passes, setPasses] = useState<Pass[]>([]);
    const [error] = useState<string | undefined>();
    const [loading, setLoading] = useState<boolean>(false);

    const fetchConfiguration = async () => {
        setLoading(true);

        setPasses([]);

        setLoading(false);
    };

    const deletePass = (id: number) => {
        setPasses(passes.filter((p) => id !== p.id));
    };

    const savePass = (pass: Pass) => {
        console.log('save clicked for pass', pass);
    };

    useEffect(() => {
        fetchConfiguration();
    }, []);

    return { fetchConfiguration, deletePass, savePass, loading, error, passes };
};
