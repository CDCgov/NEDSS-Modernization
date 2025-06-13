import { render, screen } from '@testing-library/react';
import { MergePreviewTableCard } from './MergePreviewTableCard';
import type { Column } from 'design-system/table';
import { MemoryRouter } from 'react-router';

type TestData = {
    id: number;
    name: string;
    email: string;
};

const mockData: TestData[] = [
    { id: 1, name: 'Alice', email: 'alice@example.com' },
    { id: 2, name: 'Bob', email: 'bob@example.com' }
];

const mockColumns: Column<TestData>[] = [
    {
        id: 'name',
        name: 'Name',
        sortable: true,
        value: (row) => row.name
    },
    {
        id: 'email',
        name: 'Email',
        sortable: true,
        value: (row) => row.email
    }
];

describe('SortableTableCard', () => {
    it('renders card title, tag, and table with data', () => {
        render(
            <MemoryRouter>
                <MergePreviewTableCard id="test-address" title="Test Addresses" columns={mockColumns} data={mockData} />
            </MemoryRouter>
        );

        // Card title
        expect(screen.getByRole('heading', { name: /Test Addresses/i })).toBeInTheDocument();

        // Tag count
        expect(screen.getByText(String(mockData.length))).toBeInTheDocument();

        // Table headers
        expect(screen.getByText('Name')).toBeInTheDocument();
        expect(screen.getByText('Email')).toBeInTheDocument();

        // Table data
        expect(screen.getByText('Alice')).toBeInTheDocument();
        expect(screen.getByText('alice@example.com')).toBeInTheDocument();
        expect(screen.getByText('Bob')).toBeInTheDocument();
        expect(screen.getByText('bob@example.com')).toBeInTheDocument();
    });
});
