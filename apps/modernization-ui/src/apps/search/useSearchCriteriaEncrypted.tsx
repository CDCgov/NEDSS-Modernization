import { useMemo } from 'react';
import { useSearchParams } from 'react-router';

const CRITERIA_PARAMETER = 'q';

export const useSearchCriteriaEncrypted = () => {
    const [searchParams] = useSearchParams();
    const found = useMemo(() => searchParams.get(CRITERIA_PARAMETER), [searchParams]);

    return { found };
};
