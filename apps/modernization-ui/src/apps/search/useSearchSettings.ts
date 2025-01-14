import { useEffect, useState } from 'react';
import { useConfiguration } from 'configuration';
import { View } from './useSearchResultDisplay';

type SearchSettings = {
    defaultView: View;
    allowToggle: boolean;
};

const defaults: SearchSettings = {
    defaultView: 'table',
    allowToggle: false
};

const useSearchSettings = () => {
    const [settings, setSettings] = useState<SearchSettings>(() => {
        return {
            ...defaults,
            defaultView: defaults.defaultView
        };
    });

    const {
        features: { search }
    } = useConfiguration();

    useEffect(() => {
        if (search.view.table.enabled) {
            setSettings((current) => ({ ...current, allowToggle: true }));
        }
    }, [search.view.table.enabled]);

    return {
        settings
    };
};

export { useSearchSettings };
export type { SearchSettings };
