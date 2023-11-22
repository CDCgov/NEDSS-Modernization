import { PageLibraryTable } from './PageLibraryTable';
import { render } from '@testing-library/react';
import { PageSummary } from 'apps/page-builder/generated';
import { BrowserRouter } from 'react-router-dom';
import { PageProvider } from 'page';
import { ReactNode } from 'react';

type WithinContextProps = { pageSize?: number; children: ReactNode };

const WithinContext = ({ pageSize = 10, children }: WithinContextProps) => (
    <BrowserRouter>
        <PageProvider pageSize={pageSize}>
            <PageProvider>{children}</PageProvider>
        </PageProvider>
    </BrowserRouter>
);

describe('when rendered', () => {
    it('should display sentence cased headers', async () => {
        const { getByRole } = render(
            <WithinContext>
                <PageLibraryTable summaries={[]} onSort={jest.fn()}></PageLibraryTable>
            </WithinContext>
        );

        expect(getByRole('columnheader', { name: 'Page name' })).toBeInTheDocument();
        expect(getByRole('columnheader', { name: 'Event name' })).toBeInTheDocument();
        expect(getByRole('columnheader', { name: 'Related condition(s)' })).toBeInTheDocument();
        expect(getByRole('columnheader', { name: 'Status' })).toBeInTheDocument();
        expect(getByRole('columnheader', { name: 'Last updated' })).toBeInTheDocument();
        expect(getByRole('columnheader', { name: 'Last updated by' })).toBeInTheDocument();
    });
});

describe('when at least one summary is available', () => {
    const summaries = [
        {
            conditions: [{ id: 'Some condition', name: 'condition display' }],
            eventType: { name: 'Investigation', value: 'INV' },
            id: 1,
            lastUpdate: '2019-09-25T13:27:16.380Z',
            lastUpdateBy: 'last-update-by-value',
            name: 'test page',
            status: 'Draft'
        }
    ];

    it('should display the page summaries', async () => {
        const { getByRole } = render(
            <WithinContext>
                <PageLibraryTable summaries={summaries} onSort={jest.fn()}></PageLibraryTable>
            </WithinContext>
        );

        expect(getByRole('cell', { name: 'test page' })).toBeInTheDocument();
        expect(getByRole('cell', { name: 'Investigation' })).toBeInTheDocument();
        expect(getByRole('cell', { name: 'condition display' })).toBeInTheDocument();
        expect(getByRole('cell', { name: 'Draft' })).toBeInTheDocument();
        expect(getByRole('cell', { name: '09/25/2019' })).toBeInTheDocument();
        expect(getByRole('cell', { name: 'last-update-by-value' })).toBeInTheDocument();
    });

    it('should redirect to the edit page when the page name is clicked', async () => {
        const { getByRole } = render(
            <WithinContext>
                <PageLibraryTable summaries={summaries} onSort={jest.fn()}></PageLibraryTable>
            </WithinContext>
        );

        expect(getByRole('link', { name: 'test page' })).toHaveAttribute('href', '/page-builder/edit/page/1');
    });
});
