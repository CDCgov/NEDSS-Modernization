import { ReactNode } from 'react';

import { SortingSettings } from 'libs/sorting';
import { PaginationSettings } from 'pagination';
import { MemoryRouter } from 'react-router';

import { TableProvider } from '../TableProvider';

type WithinTableProviderProps = {
    children: ReactNode;
    sorting?: SortingSettings;
    paging?: PaginationSettings;
};

const WithinTableProvider = ({ sorting, paging, children }: WithinTableProviderProps) => (
    <MemoryRouter>
        <TableProvider sorting={sorting} paging={paging}>
            {children}
        </TableProvider>
    </MemoryRouter>
);

export { WithinTableProvider };
