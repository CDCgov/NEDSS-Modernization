import { useCallback, useEffect, useState } from 'react';

import { useConfiguration } from 'configuration';
import { useNavigate } from 'react-router';

type Interaction = {
    path: string;
    go: () => void;
};

const useSearchNavigation = (): Interaction => {
    const {
        features: { search },
    } = useConfiguration();

    const [path, setPath] = useState('/advanced-search');

    useEffect(() => {
        if (search.view.enabled) {
            setPath('/search');
        }
    }, [search.view.enabled]);

    const navigate = useNavigate();

    const go = useCallback(() => navigate(path), [navigate, path]);

    return {
        path,
        go,
    };
};

export { useSearchNavigation };
