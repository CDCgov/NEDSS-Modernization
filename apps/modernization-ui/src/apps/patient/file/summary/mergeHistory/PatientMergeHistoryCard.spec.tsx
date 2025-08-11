import { render, screen } from '@testing-library/react';
import { MemoryRouter } from 'react-router';
import { PatientMergeHistoryCard } from './PatientMergeHistoryCard';
import type { PatientFileMergeHistory } from './mergeHistory';
import { MemoizedSupplier } from 'libs/supplying';

let mockPermissions: string[] = [];

vi.mock('user', () => ({
    useUser: () => ({
        state: { user: { permissions: mockPermissions } }
    })
}));

const mockData: PatientFileMergeHistory[] = [
    {
        supersededPersonLocalId: '12345',
        supersededPersonLegalName: 'John Doe',
        mergeTimestamp: '2023-01-01T12:00:00Z',
        mergedByUser: 'admin'
    }
];

const provider = new MemoizedSupplier(() => Promise.resolve(mockData));

const Fixture = () => (
    <MemoryRouter>
        <PatientMergeHistoryCard id="merge-history" provider={provider} />
    </MemoryRouter>
);

describe('PatientMergeHistoryCard', () => {
    beforeEach(() => {
        mockPermissions = [];
    });

    it('renders link when user has permission', async () => {
        mockPermissions = ['FINDINACTIVE-PATIENT'];

        render(<Fixture />);
        const link = await screen.findByRole('link', { name: '12345' });
        expect(link).toHaveAttribute('href', '/patient/12345/summary');
    });

    it('renders plain text when user lacks permission', async () => {
        mockPermissions = ['ADD-PATIENT'];
        render(<Fixture />);
        const text = await screen.findByText('12345');
        expect(text.tagName.toLowerCase()).toBe('span');
    });
});
