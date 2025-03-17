import { useEffect, useState } from 'react';
import { BlockingAttribute, MatchingAttribute, MatchMethod, Pass } from './model/Pass';

export const useMatchConfiguration = () => {
    const [passes, setPasses] = useState<Pass[]>([]);
    const [error] = useState<string | undefined>();
    const [loading, setLoading] = useState<boolean>(false);

    const fetchConfiguration = async () => {
        setLoading(true);

        setPasses([
            {
                id: 0,
                name: 'My first pass',
                description: 'This is my description for this pass',
                blockingCriteria: [BlockingAttribute.FIRST_NAME],
                matchingCriteria: [{ attribute: MatchingAttribute.LAST_NAME, method: MatchMethod.EXACT }],
                lowerBound: 0.7,
                upperBound: 0.99,
                active: true
            }
        ]);

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
