import { useLocalStorage } from 'storage';

const PAGE_SIZE_PREFERENCE_KEY = 'patient-search-page-size';

export const usePageSizePreference = (defaultPageSize: number) => {
    const { value: preferencePageSize, save: setPreferencePageSize } = useLocalStorage<number>({
        key: PAGE_SIZE_PREFERENCE_KEY,
        initial: defaultPageSize
    });

    return { preferencePageSize, setPreferencePageSize };
};
