import { ReactNode } from 'react';
import { Outlet } from 'react-router-dom';
import { PageProvider, PagingSettings } from 'page';
import { SortingProvider, SortingSettings } from 'sorting';
import { SearchResultDisplayProvider } from './useSearchResultDisplay';

const SEARCH_PAGE_SIZE = 20;

type SearchPageProviderProps = { sorting?: SortingSettings; paging?: PagingSettings; children: ReactNode };

const SearchPageProvider = ({ sorting, paging, children }: SearchPageProviderProps) => (
    <SortingProvider {...sorting} appendToUrl={sorting?.appendToUrl === undefined ? false : sorting.appendToUrl}>
        <PageProvider
            {...paging}
            pageSize={paging?.pageSize || SEARCH_PAGE_SIZE}
            appendToUrl={paging?.appendToUrl === undefined ? false : paging.appendToUrl}>
            <SearchResultDisplayProvider>{children}</SearchResultDisplayProvider>
        </PageProvider>
    </SortingProvider>
);

const SearchPage = (props: Omit<SearchPageProviderProps, 'children'>) => (
    <SearchPageProvider {...props}>
        <Outlet />
    </SearchPageProvider>
);

export { SearchPage, SearchPageProvider };
