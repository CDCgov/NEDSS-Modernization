import { render, fireEvent } from '@testing-library/react';
import { TableComponent } from './Table';

describe('Table component', () => {
    it('Should render table component', async () => {
        const { getByRole, getAllByRole } = render(
            <TableComponent
                tableHeader="Test Table Header"
                tableHead={[
                    { name: 'Start Date', sortable: true },
                    { name: 'Condition', sortable: true },
                    { name: 'Case status', sortable: true },
                    { name: 'Notification', sortable: true },
                    { name: 'Jurisdiction', sortable: true },
                    { name: 'Investigator', sortable: true },
                    { name: 'Investigation #', sortable: false },
                    { name: 'Co-infection #', sortable: false }
                ]}
                tableBody={[
                    {
                        id: 1,
                        tableDetails: [
                            { id: 1, title: '10/05/2022' },
                            { id: 2, title: 'Test Desc Text' },
                            { id: 3, title: 'Record Status' },
                            { id: 4, title: 'Notification Status' },
                            { id: 5, title: 'jurisdictionCodeDescTxt' },
                            {
                                id: 6,
                                title: 'John Doe'
                            },
                            { id: 7, title: '100023' },
                            { id: 8, title: 'COIN1000XX01' }
                        ]
                    }
                ]}
            />
        );

        expect(getByRole('heading', { name: 'Test Table Header' })).toBeInTheDocument();

        const tableHead = getAllByRole('columnheader');
        expect(tableHead[0]).toHaveTextContent('Start Date');
        expect(tableHead[1]).toHaveTextContent('Condition');
        expect(tableHead[2]).toHaveTextContent('Case status');
        expect(tableHead[3]).toHaveTextContent('Notification');
        expect(tableHead[4]).toHaveTextContent('Jurisdiction');
        expect(tableHead[5]).toHaveTextContent('Investigator');
        expect(tableHead[6]).toHaveTextContent('Investigation #');
        expect(tableHead[7]).toHaveTextContent('Co-infection #');

        const tableData = getAllByRole('cell');

        expect(tableData[0]).toHaveTextContent('10/05/2022');
        expect(tableData[1]).toHaveTextContent('Test Desc Text');
        expect(tableData[2]).toHaveTextContent('Record Status');
        expect(tableData[3]).toHaveTextContent('Notification Status');
        expect(tableData[4]).toHaveTextContent('jurisdictionCodeDescTxt');
        expect(tableData[5]).toHaveTextContent('John Doe');
        expect(tableData[6]).toHaveTextContent('100023');
        expect(tableData[7]).toHaveTextContent('COIN1000XX01');
    });

    it('table with no data', async () => {
        const { getByRole } = render(
            <TableComponent
                tableHeader="Test Table Header"
                tableHead={[
                    { name: 'Start Date', sortable: true },
                    { name: 'Condition', sortable: true },
                    { name: 'Case status', sortable: true },
                    { name: 'Notification', sortable: true },
                    { name: 'Jurisdiction', sortable: true },
                    { name: 'Investigator', sortable: true },
                    { name: 'Investigation #', sortable: false },
                    { name: 'Co-infection #', sortable: false }
                ]}
                tableBody={[
                    {
                        id: 1,

                        tableDetails: [
                            { id: 1, title: null },
                            { id: 2, title: 'Test Desc Text' },
                            { id: 3, title: 'Record Status' },
                            { id: 4, title: 'Notification Status' },
                            { id: 5, title: 'jurisdictionCodeDescTxt' },
                            {
                                id: 6,
                                title: 'John Doe'
                            },
                            { id: 7, title: '100023' },
                            { id: 8, title: 'COIN1000XX01' }
                        ]
                    }
                ]}
            />
        );

        expect(getByRole('cell', { name: '---' })).toBeInTheDocument();
    });
});

describe('when a table has a sortable header', () => {
    it('should default to no active sorting', async () => {
        const { getAllByRole } = render(
            <TableComponent
                tableHeader="Test Table Header"
                tableHead={[
                    { name: 'A', sortable: true },
                    { name: 'B', sortable: false }
                ]}
                tableBody={[
                    {
                        id: 1,
                        tableDetails: [{ id: 1, title: 'one' }]
                    },
                    {
                        id: 2,
                        tableDetails: [{ id: 2, title: 'two' }]
                    }
                ]}
            />
        );

        const headers = getAllByRole('columnheader');

        const sortableHeader = headers[0];

        expect(sortableHeader).not.toHaveClass('sort-header');
        expect(sortableHeader).not.toHaveAttribute('aria-sort');

        const sortIcons = getAllByRole('button', { name: 'sort' });
        expect(sortIcons[0]).not.toBeNull();
        expect(sortIcons[1]).toBeUndefined();
    });

    it('should activate descending sort when button clicked', async () => {
        const { getByRole, getAllByRole } = render(
            <TableComponent
                tableHeader="Test Table Header"
                tableHead={[
                    { name: 'A', sortable: true },
                    { name: 'B', sortable: false }
                ]}
                tableBody={[
                    {
                        id: 1,
                        tableDetails: [{ id: 1, title: 'one' }]
                    },
                    {
                        id: 2,
                        tableDetails: [{ id: 2, title: 'two' }]
                    }
                ]}
            />
        );

        const sortIcon = getByRole('button', { name: 'sort' });

        fireEvent.click(sortIcon);

        const headers = getAllByRole('columnheader');

        const sortableHeader = headers[0];

        expect(sortableHeader).toHaveClass('sorted');
        expect(sortableHeader).toHaveAttribute('aria-sort', 'descending');

        const nonSortableHeader = headers[1];
        expect(nonSortableHeader).not.toHaveClass('sorted');
    });

    it('should activate ascending sort when button clicked while a descending sort is active', async () => {
        const { getByRole, getAllByRole } = render(
            <TableComponent
                tableHeader="Test Table Header"
                tableHead={[
                    { name: 'A', sortable: true },
                    { name: 'B', sortable: false }
                ]}
                tableBody={[
                    {
                        id: 1,
                        tableDetails: [{ id: 1, title: 'one' }]
                    },
                    {
                        id: 2,
                        tableDetails: [{ id: 2, title: 'two' }]
                    }
                ]}
            />
        );

        const sortIcon = getByRole('button', { name: 'sort' });

        fireEvent.click(sortIcon);
        fireEvent.click(sortIcon);

        const headers = getAllByRole('columnheader');

        const sortableHeader = headers[0];

        expect(sortableHeader).toHaveClass('sorted');
        expect(sortableHeader).toHaveAttribute('aria-sort', 'ascending');

        const nonSortableHeader = headers[1];
        expect(nonSortableHeader).not.toHaveClass('sorted');
    });

    it('should disable sorting if there is only 1 total result', () => {
        const { getByRole } = render(
            <TableComponent
                tableHeader="Test Table Header"
                tableHead={[
                    { name: 'A', sortable: true },
                    { name: 'B', sortable: false }
                ]}
                tableBody={[
                    {
                        id: 1,
                        tableDetails: [{ id: 1, title: 'one' }]
                    }
                ]}
                totalResults={1}
            />
        );
        const sortIcon = getByRole('button', { name: 'sort' });

        expect(sortIcon).toBeDisabled();
    });
});
