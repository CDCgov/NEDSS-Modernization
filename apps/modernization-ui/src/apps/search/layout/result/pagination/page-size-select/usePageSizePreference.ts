import { useState, useEffect } from 'react';

const PAGE_SIZE_PREFERENCE_KEY = 'patient-search-page-size';

export const usePageSizePreference = (defaultPageSize: number) => {
    const initPageSizePreference = () => {
        const savedPageSize = localStorage.getItem(PAGE_SIZE_PREFERENCE_KEY);
        return savedPageSize ? parseInt(savedPageSize, 10) : defaultPageSize;
    };

    const [preferencePageSize, setPreferencePageSize] = useState(initPageSizePreference);

    useEffect(() => {
        localStorage.setItem(PAGE_SIZE_PREFERENCE_KEY, preferencePageSize.toString());
    }, [preferencePageSize]);

    return { preferencePageSize, setPreferencePageSize };
};
