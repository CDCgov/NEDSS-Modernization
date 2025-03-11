import { useEffect, useState } from 'react';
import { MatchingAttribute, MatchMethod, Pass } from './model/Pass';

export const useMatchConfiguration = () => {
    const [passes, setPasses] = useState<Pass[]>([]);
    const [error] = useState<string | undefined>();
    const [loading, setLoading] = useState<boolean>(false);

    const fetchConfiguration = async () => {
        setLoading(true);

        setPasses([
            {
                name: 'Lastname_Dateofbirth longer name',
                description: 'This is my description for this pass',
                blockingCriteria: [],
                matchingCriteria: [{ attribute: MatchingAttribute.FIRST_NAME, method: MatchMethod.EXACT }],
                active: true
            },
            {
                name: 'IDtype_City',
                description: '',
                blockingCriteria: [],
                matchingCriteria: [],
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
