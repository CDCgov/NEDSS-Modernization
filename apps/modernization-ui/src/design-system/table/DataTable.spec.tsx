import { render, screen, within } from '@testing-library/react';
import { axe } from 'vitest-axe';
import { DataTable, Column } from './DataTable';
import { Sizing } from 'design-system/field';

type TestData = {
    id: number;
    name: string;
};

const data: TestData[] = [
    { id: 1, name: 'John Doe' },
    { id: 2, name: 'Jane Smith' }
];

describe('DataTable', () => {
    it('should render with no accessibility violations', async () => {
        const columns = [
            {
                id: 'id',
                name: 'ID',
                render: () => 'id-value'
            },
            {
                id: 'name',
                name: 'Name',
                render: () => `name-value`
            },
            {
                id: 'other',
                name: 'Other',
                render: () => 'other-value'
            }
        ];

        const { container } = render(<DataTable id="test-table" columns={columns} data={data} />);
        expect(await axe(container)).toHaveNoViolations();
    });

    it('should display the columns', () => {
        const columns = [
            {
                id: 'id',
                name: 'ID',
                render: () => 'id-value'
            },
            {
                id: 'name',
                name: 'Name',
                render: () => `name-value`
            },
            {
                id: 'other',
                name: 'Other',
                render: () => 'other-value'
            }
        ];

        render(<DataTable id="test-table" columns={columns} data={[]} />);

        const headers = screen.getAllByRole('columnheader');
        expect(headers).toHaveLength(3);

        const id = screen.getByRole('columnheader', { name: 'ID' });
        const name = screen.getByRole('columnheader', { name: 'Name' });
        const other = screen.getByRole('columnheader', { name: 'Other' });

        expect(id).toBeInTheDocument();
        expect(name).toBeInTheDocument();
        expect(other).toBeInTheDocument();
    });

    it('displays the columns with custom styles', () => {
        const columns = [
            {
                id: 'id',
                name: 'ID',
                className: 'id-class-name',
                render: () => 'id-value'
            },
            {
                id: 'name',
                name: 'Name',
                render: () => `name-value`
            },
            {
                id: 'other',
                name: 'Other',
                className: 'other-class-name',
                render: () => 'other-value'
            }
        ];

        render(<DataTable id="test-table" columns={columns} data={[]} />);

        const id = screen.getByRole('columnheader', { name: 'ID' });

        expect(id).toHaveClass('id-class-name');

        const other = screen.getByRole('columnheader', { name: 'Other' });

        expect(other).toHaveClass('other-class-name');
    });

    it('displays the data', () => {
        const columns: Column<TestData>[] = [
            {
                id: 'id',
                name: 'ID',
                value: (value) => value.id
            },
            {
                id: 'name',
                name: 'Name',
                value: (value) => value.name
            },
            {
                id: 'combined',
                name: 'Combined values',
                render: (value) => `${value.name} (${value.id})`
            }
        ];

        const data = [
            { id: 2, name: 'name-two', value: 'two-value' },
            { id: 5, name: 'name-five', value: 'five-value' },
            { id: 3, name: 'name-three', value: 'three-value' }
        ];

        render(<DataTable id="test-table" columns={columns} data={data} />);

        const two = screen.getByRole('row', { name: /2/ });
        expect(two).not.toBeNull();
        expect(within(two!).getByText('name-two'));
        expect(within(two!).getByText('name-two (2)'));

        const five = screen.getByRole('row', { name: /5/ });
        expect(five).not.toBeNull();
        expect(within(five!).getByText('name-five'));
        expect(within(five!).getByText('name-five (5)'));

        const three = screen.getByRole('row', { name: /3/ });
        expect(within(three!).getByText('name-three (3)'));
        expect(within(three!).getByText('name-three'));

        expect(screen.getAllByRole('row')).toHaveLength(4); //  header row + data rows
    });

    it('displays the data using the render function over the value function', () => {
        const columns: Column<TestData>[] = [
            {
                id: 'id',
                name: 'ID',
                value: (value) => value.id,
                render: (value) => `Rendered ${value.id}`
            },
            {
                id: 'name',
                name: 'Name',
                value: (value) => value.name,
                render: (value) => `Rendered ${value.name}`
            }
        ];

        const data = [
            { id: 2, name: 'name-two', value: 'two-value' },
            { id: 5, name: 'name-five', value: 'five-value' },
            { id: 3, name: 'name-three', value: 'three-value' }
        ];

        render(<DataTable id="test-table" columns={columns} data={data} />);

        const two = screen.getByRole('row', { name: /2/ });
        expect(within(two!).getByText('Rendered name-two'));

        const five = screen.getByRole('row', { name: /5/ });
        expect(within(five!).getByText('Rendered name-five'));

        const three = screen.getByRole('row', { name: /3/ });
        expect(within(three!).getByText('Rendered name-three'));
    });

    it('displays the default empty table message', () => {
        const columns: Column<TestData>[] = [
            {
                id: 'id',
                name: 'ID',
                value: (value) => value.id
            },
            {
                id: 'name',
                name: 'Name',
                value: (value) => value.name
            }
        ];

        render(<DataTable id="test-table" columns={columns} data={[]} />);

        expect(screen.getByText('No data has been added.')).toBeInTheDocument();
    });

    it('defers to the provided onEmpty when the table is empty', () => {
        const columns: Column<TestData>[] = [
            {
                id: 'id',
                name: 'ID',
                value: (value) => value.id
            },
            {
                id: 'name',
                name: 'Name',
                value: (value) => value.name
            },
            {
                id: 'combined',
                name: 'Combined values',
                render: (value) => `${value.name} (${value.id})`
            }
        ];

        const onEmpty = jest.fn();
        render(<DataTable id="test-table" columns={columns} data={[]} onEmpty={onEmpty} />);

        expect(onEmpty).toHaveBeenCalledWith(3);

        expect(screen.queryByText('No data has been added.')).not.toBeInTheDocument();
    });

    it.each(['small', 'medium', 'large'] as Sizing[])('applies the sizing classes for %s', (sizing) => {
        const columns: Column<TestData>[] = [
            {
                id: 'id',
                name: 'ID',
                value: (value) => value.id
            }
        ];

        render(<DataTable id="test-table" columns={columns} data={[]} sizing={sizing} />);
        const table = screen.getByRole('table').closest('div');
        expect(table).toHaveClass(sizing);
    });
});
