import { PageLibraryTable } from './PageLibraryTable';
import { render } from '@testing-library/react';
import { WithinTableProvider } from 'components/Table/testing';
import * as config from 'configuration';

jest.mock('configuration', () => ({
    ...jest.requireActual('configuration'),
    useConfiguration: jest.fn()
}));

afterEach(() => {
    jest.resetAllMocks();
});

const setEnabled = (enabled: boolean) => {
    beforeEach(() => {
        jest.spyOn(config, 'useConfiguration').mockReturnValue({
            settings: {
                smarty: {
                    key: '123'
                }
            },
            features: {
                address: {
                    autocomplete: false,
                    verification: false
                },
                pageBuilder: {
                    enabled: enabled,
                    page: {
                        library: {
                            enabled: enabled
                        },
                        management: {
                            enabled: enabled
                        }
                    }
                }
            },
            loading: false
        });
    });
};

describe('when rendered', () => {
    setEnabled(true);
    it('should display sentence cased headers', async () => {
        const { getAllByRole } = render(
            <WithinTableProvider>
                <PageLibraryTable summaries={[]} onSort={jest.fn()}></PageLibraryTable>
            </WithinTableProvider>
        );

        const tableHeads = getAllByRole('columnheader');

        expect(tableHeads[0]).toHaveTextContent('Page name');
        expect(tableHeads[1]).toHaveTextContent('Event name');
        expect(tableHeads[2]).toHaveTextContent('Related condition(s)');
        expect(tableHeads[3]).toHaveTextContent('Status');
        expect(tableHeads[4]).toHaveTextContent('Last updated');
        expect(tableHeads[5]).toHaveTextContent('Last updated by');
    });
});

describe('when at least one summary is available', () => {
    setEnabled(true);

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
        const { findAllByRole } = render(
            <WithinTableProvider>
                <PageLibraryTable summaries={summaries} onSort={jest.fn()}></PageLibraryTable>
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

    it('should redirect to the edit page when the page name is clicked', async () => {
        const { getByRole } = render(
            <WithinTableProvider>
                <PageLibraryTable summaries={summaries} onSort={jest.fn()}></PageLibraryTable>
            </WithinTableProvider>
        );

        expect(getByRole('link', { name: 'test page' })).toHaveAttribute('href', '/page-builder/pages/1');
    });
});

describe('when rendered with mangement disabled', () => {
    setEnabled(false);

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
                <PageLibraryTable summaries={summaries} onSort={jest.fn()}></PageLibraryTable>
            </WithinTableProvider>
        );

        expect(getByRole('link', { name: 'test page' })).toHaveAttribute(
            'href',
            '/nbs/page-builder/api/v1/pages/1/preview'
        );
    });
});
