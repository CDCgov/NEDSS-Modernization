import { ReactNode } from 'react';
import { PaginationProvider, PaginationSettings } from 'pagination';
import { SortingProvider, SortingSettings } from 'sorting';

type TableProviderProps = {
    children: ReactNode;
    sorting?: SortingSettings;
    paging?: PaginationSettings;
};

const TableProvider = ({ sorting, paging, children }: TableProviderProps) => {
    return (
        <SortingProvider {...sorting} appendToUrl={sorting?.appendToUrl === undefined ? true : sorting.appendToUrl}>
            <PaginationProvider {...paging} appendToUrl={paging?.appendToUrl === undefined ? true : paging.appendToUrl}>
                {children}
            </PaginationProvider>
        </SortingProvider>
    );
};

export { TableProvider };
