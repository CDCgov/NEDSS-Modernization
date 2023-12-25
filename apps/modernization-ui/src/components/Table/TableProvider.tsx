import { ReactNode } from 'react';
import { PageProvider, PagingSettings } from 'page';
import { SortingProvider, SortingSettings } from 'sorting';

type TableProviderProps = {
    children: ReactNode;
    sorting?: SortingSettings;
    paging?: PagingSettings;
};

const TableProvider = ({ sorting, paging, children }: TableProviderProps) => {
    return (
        <SortingProvider {...sorting} appendToUrl={sorting?.appendToUrl === undefined ? true : sorting.appendToUrl}>
            <PageProvider {...paging} appendToUrl={paging?.appendToUrl === undefined ? true : paging.appendToUrl}>
                {children}
            </PageProvider>
        </SortingProvider>
    );
};

export { TableProvider };
