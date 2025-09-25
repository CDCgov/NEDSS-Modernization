import { render } from '@testing-library/react';
import { Column } from 'design-system/table';
import { axe } from 'vitest-axe';
import { ReactNode } from 'react';
import { MemoryRouter } from 'react-router';
import { TableCard, TableCardProps } from './TableCard';

const mockApply = vi.fn();

vi.mock('design-system/table/preferences/useColumnPreferences', () => ({
    useColumnPreferences: () => ({
        apply: mockApply
    }),
    ColumnPreferenceProvider: ({ children }: { children: (a: { apply: () => TestData[] }) => ReactNode }) =>
        children({ apply: mockApply })
}));

vi.mock('design-system/table/preferences/withColumnPreferences', () => ({
    withColumnPreferences: (Component: any) => (props: any) => <Component {...props} />
}));

global.structuredClone = (val) => val;

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

const preferences = [
    {
        id: 'id',
        name: 'ID'
    },
    {
        id: 'name',
        name: 'Name'
    }
];

const data: TestData[] = [
    { id: 1, name: 'John Doe' },
    { id: 2, name: 'Jane Smith' }
];

const Fixture = (props: Partial<TableCardProps<TestData>>) => {
    return (
        <MemoryRouter>
            <TableCard
                id="tablecard"
                title="Test Title"
                columns={columns}
                columnPreferencesKey="test-key"
                defaultColumnPreferences={preferences}
                data={data}
                {...props}
            />
        </MemoryRouter>
    );
};

describe('TableCard', () => {
    beforeEach(() => {
        vi.resetAllMocks();
        mockApply.mockReturnValue(columns);
    });

    it('renders without crashing', () => {
        const { container } = render(<Fixture />);
        expect(container.querySelector('#tablecard')).toBeInTheDocument();
    });

    it('should render with no accessibility violations', async () => {
        const { container } = render(<Fixture />);
        expect(await axe(container)).toHaveNoViolations();
    });

    it('renders header with correct title', () => {
        const { getByRole } = render(<Fixture />);
        expect(getByRole('heading')).toHaveTextContent('Test Title');
    });

    it('renders DataTable with correct props and column length', () => {
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

    it('renders the settings button by default', () => {
        const { queryByLabelText } = render(<Fixture />);
        expect(queryByLabelText('Settings')).toBeInTheDocument();
    });

    it('renders with mocked column preferences', () => {
        mockApply.mockReturnValue(columns.slice(0, 1));

        const { getByRole } = render(<Fixture columnPreferencesKey="test-key" />);

        expect(mockApply).toHaveBeenCalledTimes(1);
        expect(mockApply).toHaveBeenCalledWith(columns);

        const table = getByRole('table');
        expect(table).toBeInTheDocument();
        const headerCells = table.querySelectorAll('thead tr:nth-child(1) th');
        expect(headerCells).toHaveLength(1);
    });
});
