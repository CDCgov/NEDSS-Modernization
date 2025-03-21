import { ReactNode } from 'react';
import { PagingSettings } from 'page';
import { SortingSettings } from 'sorting';
import { MemoryRouter } from 'react-router';
import { TableProvider } from '../TableProvider';

type WithinTableProviderProps = {
    children: ReactNode;
    sorting?: SortingSettings;
    paging?: PagingSettings;
};

const WithinTableProvider = ({ sorting, paging, children }: WithinTableProviderProps) => (
    <MemoryRouter>
        <TableProvider sorting={sorting} paging={paging}>
            {children}
        </TableProvider>
    </MemoryRouter>
);

export { WithinTableProvider };
