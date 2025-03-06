import { useEffect, useState } from 'react';
import { Pass } from './model/Pass';

export const useMatchConfiguration = () => {
    const [passes, setPasses] = useState<Pass[]>([]);
    const [error] = useState<string | undefined>();
    const [loading, setLoading] = useState<boolean>(false);

    const fetchConfiguration = async () => {
        setLoading(true);

        setPasses([
            {
                name: 'Lastname_Dateofbirth',
                description: 'This is my description for this pass',
                active: true
            },
            {
                name: 'IDtype_City',
                description: '',
                active: false
            }
        ]);

        setLoading(false);
    };

    useEffect(() => {
        fetchConfiguration();
    }, []);

    return { fetchConfiguration, loading, error, passes };
};
