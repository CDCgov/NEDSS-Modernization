import { useConfiguration } from 'configuration';
import { View } from './useSearchResultDisplay';
import { useEffect, useState } from 'react';

type SearchSettings = {
    defaultView: View;
    allowToggle: boolean;
};

const defaults: SearchSettings = {
    defaultView: 'list',
    allowToggle: false
};

const useSearchSettings = (): SearchSettings => {
    const [settings, setSettings] = useState<SearchSettings>(defaults);
    const defaultView = localStorage.getItem('defaultSearchView') as View;

    const {
        features: { search }
    } = useConfiguration();

    useEffect(() => {
        if (search.view.table.enabled) {
            setSettings((current) => ({ ...current, defaultView: defaultView || 'table', allowToggle: true }));
        }
    }, [search.view.table.enabled]);
    return settings;
};

export { useSearchSettings };

export type { SearchSettings };
