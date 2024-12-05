import { useEffect, useMemo, useState } from 'react';
import { useSearchParams } from 'react-router-dom';

const CRITERIA_PARAMETER = 'q';

export const useSearchCriteriaEncrypted = () => {
    const [searchParams] = useSearchParams();
    const found = useMemo(() => searchParams.get(CRITERIA_PARAMETER), [searchParams]);
    const [criteria, setCriteria] = useState<string | null>(null);

    useEffect(() => {
        if (found !== null) {
            console.log({ found });
            setCriteria(found);
        }
    }, [found]);

    return { criteria };
};
