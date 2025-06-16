import { useLocalStorage } from 'storage';

export type PageSizePreferenceKeyOptions =
    | 'patients-search-page-size'
    | 'lab-reports-search-page-size'
    | 'investigations-search-page-size'
    | 'simple-search-page-size';

export const usePageSizePreference = (defaultPageSize: number, pageSizePreferenceKey: PageSizePreferenceKeyOptions) => {
    const { value: preferencePageSize, save: setPreferencePageSize } = useLocalStorage<number>({
        key: pageSizePreferenceKey,
        initial: defaultPageSize
    });

    return { preferencePageSize, setPreferencePageSize };
};
