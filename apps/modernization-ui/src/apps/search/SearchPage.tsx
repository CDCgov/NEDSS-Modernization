import { ReactNode } from 'react';
import { Outlet } from 'react-router';
import { PaginationProvider, PaginationSettings } from 'pagination';
import { SortingProvider, SortingSettings } from 'libs/sorting';
import { SearchResultDisplayProvider } from './useSearchResultDisplay';
import { ComponentSizingProvider } from 'design-system/sizing';
import { FilterProvider } from 'design-system/filter';
import { useLocalStorage } from 'storage';
// const PAGE_SIZE_PREFERENCE_KEY = 'patient-search-page-size';

const SEARCH_PAGE_SIZE = 20;

type SearchPageProviderProps = { sorting?: SortingSettings; paging?: PaginationSettings; children: ReactNode };

const SearchPageProvider = ({ sorting, paging, children }: SearchPageProviderProps) => {
    const { value: preferencePageSize } = useLocalStorage<number>({
        key: PAGE_SIZE_PREFERENCE_KEY,
        initial: SEARCH_PAGE_SIZE
    });

    return (
        <ComponentSizingProvider>
            <SortingProvider
                {...sorting}
                appendToUrl={sorting?.appendToUrl === undefined ? false : sorting.appendToUrl}>
                <PaginationProvider
                    {...paging}
                    pageSize={preferencePageSize}
                    appendToUrl={paging?.appendToUrl === undefined ? false : paging.appendToUrl}>
                    <FilterProvider>
                        <SearchResultDisplayProvider>{children}</SearchResultDisplayProvider>
                    </FilterProvider>
                </PaginationProvider>
            </SortingProvider>
        </ComponentSizingProvider>
    );
};

const SearchPage = (props: Omit<SearchPageProviderProps, 'children'>) => (
    <SearchPageProvider {...props}>
        <Outlet />
    </SearchPageProvider>
);

export { SearchPage, SearchPageProvider };
