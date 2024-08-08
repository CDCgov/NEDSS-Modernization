import { useConfiguration } from 'configuration';
import { useEffect, useState } from 'react';
import { View } from './useSearchResultDisplay';

type SearchSettings = {
    defaultView: View;
    allowToggle: boolean;
};

const defaults: SearchSettings = {
    defaultView: 'list',
    allowToggle: false
};

const useSearchSettings = () => {
    const [settings, setSettings] = useState<SearchSettings>(() => {
        const storedView = localStorage.getItem('defaultSearchView') as View | null;
        return {
            ...defaults,
            defaultView: storedView || defaults.defaultView
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

    const updateDefaultView = (newView: View) => {
        localStorage.setItem('defaultSearchView', newView);
        setSettings((current) => ({ ...current, defaultView: newView }));
    };

    return {
        settings,
        updateDefaultView
    };
};

export { useSearchSettings };
export type { SearchSettings };
