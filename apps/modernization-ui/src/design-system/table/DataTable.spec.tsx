import { render } from '@testing-library/react';
import { DataTable, Column } from './DataTable';

type TestData = {
    id: number;
    name: string;
};

const columns: Column<TestData>[] = [
    {
        id: 'id',
        name: 'ID',
        className: 'id-header',
        render: (value) => value.id
    },
    {
        id: 'name',
        name: 'Name',
        className: 'name-header',
        render: (value) => value.name
    }
];

const data: TestData[] = [
    { id: 1, name: 'John Doe' },
    { id: 2, name: 'Jane Smith' }
];

describe('DataTable', () => {
    it('renders without crashing', () => {
        render(<DataTable id="test-table" columns={columns} data={data} />);
    });

    it('renders the correct number of columns', () => {
        const { container } = render(<DataTable id="test-table" columns={columns} data={data} />);
        const headerCells = container.querySelectorAll('thead tr:nth-child(1) th');
        expect(headerCells).toHaveLength(columns.length);
    });

    it('renders the correct number of rows', () => {
        const { container } = render(<DataTable id="test-table" columns={columns} data={data} />);
        const rows = container.querySelectorAll('tbody tr');
        expect(rows).toHaveLength(data.length);
    });

    it('renders the correct data in cells', () => {
        const { container } = render(<DataTable id="test-table" columns={columns} data={data} />);
        data.forEach((row, rowIndex) => {
            columns.forEach((column, colIndex) => {
                const cell = container.querySelector(
                    `tbody tr:nth-child(${rowIndex + 1}) td:nth-child(${colIndex + 1})`
                );
                expect(cell).toHaveTextContent(String(row[column.id as keyof TestData]));
            });
        });
    });

    it('renders the correct classNames in header cells', () => {
        const { container } = render(<DataTable id="test-table" columns={columns} data={data} />);
        columns.forEach((column, index) => {
            const headerCell = container.querySelector(`thead tr th:nth-child(${index + 1})`);
            expect(headerCell).toHaveClass(column.className!);
        });
    });
});
