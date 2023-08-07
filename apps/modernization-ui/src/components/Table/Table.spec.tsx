import { render, fireEvent } from '@testing-library/react';
import { TableComponent } from './Table';

describe('Table component', () => {
    it('Should renders table component', async () => {
        const { container } = render(
            <TableComponent
                tableHeader="Test Table Header"
                tableSubHeader="Test Sub Header"
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
                        checkbox: true,
                        expanded: false,
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

        const tableHeader = container.getElementsByClassName('table-header');
        const tableHead = container.getElementsByClassName('head-name');
        const tableData = container.getElementsByClassName('table-data');
        expect(tableHeader[0].innerHTML).toBe('Test Table HeaderTest Sub Header');
        expect(tableHead[0].innerHTML).toBe('Start Date');
        expect(tableData[0]).toHaveTextContent('10/05/2022');
    });

    it('table with no data', async () => {
        const { container } = render(
            <TableComponent
                tableHeader="Test Table Header"
                tableSubHeader="Test Sub Header"
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
                        checkbox: true,
                        expanded: false,
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

        const notAvailable = container.getElementsByClassName('no-data');
        expect(notAvailable[0].innerHTML).toBe('No data');
    });
});

describe('when a table has a sortable header', () => {
    it('should default to no active soriting', async () => {
        const { getAllByRole } = render(
            <TableComponent
                tableHeader="Test Table Header"
                tableSubHeader="Test Sub Header"
                tableHead={[
                    { name: 'A', sortable: true },
                    { name: 'B', sortable: false }
                ]}
                tableBody={[
                    {
                        id: 1,
                        expanded: false,
                        tableDetails: [{ id: 1, title: 'one' }]
                    },
                    {
                        id: 2,
                        expanded: false,
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
                tableSubHeader="Test Sub Header"
                tableHead={[
                    { name: 'A', sortable: true },
                    { name: 'B', sortable: false }
                ]}
                tableBody={[
                    {
                        id: 1,
                        expanded: false,
                        tableDetails: [{ id: 1, title: 'one' }]
                    },
                    {
                        id: 2,
                        expanded: false,
                        tableDetails: [{ id: 2, title: 'two' }]
                    }
                ]}
            />
        );

        const sortIcon = getByRole('button', { name: 'sort' });

        fireEvent.click(sortIcon);

        const headers = getAllByRole('columnheader');

        const sortableHeader = headers[0];

        expect(sortableHeader).toHaveClass('sort-header');
        expect(sortableHeader).toHaveAttribute('aria-sort', 'descending');

        const nonSortableHeader = headers[1];
        expect(nonSortableHeader).not.toHaveClass('sort-header');
    });

    it('should activate ascending sort when button clicked while a descending sort is active', async () => {
        const { container, getByRole, getAllByRole } = render(
            <TableComponent
                tableHeader="Test Table Header"
                tableSubHeader="Test Sub Header"
                tableHead={[
                    { name: 'A', sortable: true },
                    { name: 'B', sortable: false }
                ]}
                tableBody={[
                    {
                        id: 1,
                        expanded: false,
                        tableDetails: [{ id: 1, title: 'one' }]
                    },
                    {
                        id: 2,
                        expanded: false,
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

        expect(sortableHeader).toHaveClass('sort-header');
        expect(sortableHeader).toHaveAttribute('aria-sort', 'ascending');

        const nonSortableHeader = headers[1];
        expect(nonSortableHeader).not.toHaveClass('sort-header');
    });
});
