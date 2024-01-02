import { render } from '@testing-library/react';
import { WithinTableProvider } from 'components/Table/testing';
import { PageLibraryTable } from './PageLibraryTable';

describe('when rendered', () => {
    it('should display sentence cased headers', async () => {
        const { getAllByRole } = render(
            <WithinTableProvider>
                <PageLibraryTable enableEdit={true} summaries={[]} onSort={jest.fn()}></PageLibraryTable>
            </WithinTableProvider>
        );

        const tableHeads = getAllByRole('columnheader');

        expect(tableHeads[0]).toHaveTextContent('Page name');
        expect(tableHeads[1]).toHaveTextContent('Event type');
        expect(tableHeads[2]).toHaveTextContent('Related condition(s)');
        expect(tableHeads[3]).toHaveTextContent('Status');
        expect(tableHeads[4]).toHaveTextContent('Last updated');
        expect(tableHeads[5]).toHaveTextContent('Last updated by');
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
        },
        {
            conditions: [],
            eventType: { name: 'Lab Report', value: 'LAB' },
            id: 2,
            lastUpdate: '2019-09-25T13:27:16.380Z',
            lastUpdateBy: 'last-update-by-value',
            name: 'Lab report page',
            status: 'Published'
        }
    ];

    it('should display the page summaries', async () => {
        const { findAllByRole } = render(
            <WithinTableProvider>
                <PageLibraryTable enableEdit={true} summaries={summaries} onSort={jest.fn()}></PageLibraryTable>
            </WithinTableProvider>
        );

        const tableData = await findAllByRole('cell');

        expect(tableData[0]).toHaveTextContent('test page');
        expect(tableData[1]).toHaveTextContent('Investigation');
        expect(tableData[2]).toHaveTextContent('condition display');
        expect(tableData[3]).toHaveTextContent('Draft');
        expect(tableData[4]).toHaveTextContent('09/25/2019');
        expect(tableData[5]).toHaveTextContent('last-update-by-value');
    });

    it('should redirect to the preview page when an investigation page name is clicked', () => {
        const { getByRole } = render(
            <WithinTableProvider>
                <PageLibraryTable enableEdit={true} summaries={summaries} onSort={jest.fn()}></PageLibraryTable>
            </WithinTableProvider>
        );

        expect(getByRole('link', { name: 'test page' })).toHaveAttribute('href', '/page-builder/pages/1');
    });

    it('should redirect to classic when a non investigation page name is clicked', () => {
        const { getByRole } = render(
            <WithinTableProvider>
                <PageLibraryTable enableManagement={true} summaries={summaries} onSort={jest.fn()}></PageLibraryTable>
            </WithinTableProvider>
        );

        expect(getByRole('link', { name: 'Lab report page' })).toHaveAttribute(
            'href',
            '/nbs/page-builder/api/v1/pages/2/preview'
        );
    });
});

describe('when rendered with mangement disabled', () => {
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
    it('should redirect to the edit page when the page name is clicked', async () => {
        const { getByRole } = render(
            <WithinTableProvider>
                <PageLibraryTable enableEdit={false} summaries={summaries} onSort={jest.fn()}></PageLibraryTable>
            </WithinTableProvider>
        );

        expect(getByRole('link', { name: 'test page' })).toHaveAttribute(
            'href',
            '/nbs/page-builder/api/v1/pages/1/preview'
        );
    });
});
