import { render } from '@testing-library/react';
import { TableCard, TableCardProps } from './TableCard';
import { Column } from 'design-system/table';

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

const Fixture = (props: Partial<TableCardProps<TestData>>) => {
    return <TableCard id="tablecard" title="Test Title" columns={columns} data={data} {...props} />;
};

describe('TableCard', () => {
    it('renders without crashing', () => {
        const { container } = render(<Fixture />);
        expect(container.querySelector('section#tablecard')).toBeInTheDocument();
    });

    it('renders header with correct title', () => {
        const { getByRole } = render(<Fixture />);
        expect(getByRole('heading')).toHaveTextContent('Test Title');
    });

    it('renders DataTable with correct props', () => {
        const { getByRole } = render(<Fixture />);
        const table = getByRole('table');
        expect(table).toBeInTheDocument();
        const headerCells = table.querySelectorAll('thead tr:nth-child(1) th');
        expect(headerCells).toHaveLength(columns.length);
        const rows = table.querySelectorAll('tbody tr');
        expect(rows).toHaveLength(data.length);
    });

    it('renders with custom className', () => {
        const { container } = render(<Fixture className="custom-class" />);
        expect(container.querySelector('section')).toHaveClass('custom-class');
    });

    it('renders table with custom tableClassName', () => {
        const { getByRole } = render(<Fixture tableClassName="custom-table-class" />);
        expect(getByRole('table')).toBeInTheDocument();
        expect(getByRole('table')).toHaveClass('custom-table-class');
    });
});
