import { vi } from 'vitest';
import { MatchRequiringReviewResponse } from 'apps/deduplication/api/model/MatchRequiringReview';
import { MatchesRequiringReviewTable } from './MatchesRequiringReviewTable';
import { render, waitFor, within } from '@testing-library/react';
import { PaginationProvider } from 'pagination';
import { MemoryRouter } from 'react-router';
import userEvent from '@testing-library/user-event';
import { SortingProvider } from 'libs/sorting';

let mockReturnValue: MatchRequiringReviewResponse;
beforeEach(() => {
    vi.clearAllMocks();
    mockReturnValue = {
        matches: [
            {
                matchId: 1,
                patientId: '1234',
                patientLocalId: '4321',
                patientName: 'John Smith',
                createdDate: '2014-02-11T11:30:30',
                identifiedDate: '2024-02-11T12:30:30',
                numOfMatchingRecords: 2
            }
        ],
        page: 0,
        total: 0
    };
});

vi.mock('pagination', async () => {
    const original = await vi.importActual<any>('pagination');
    return {
        ...original,
        PaginationProvider: original.PaginationProvider, // Ensure PaginationProvider is exported
        usePagination: () => ({
            page: {
                current: 1,
                pageSize: 20,
                status: 'Requested',
                total: 1
            },
            ready: vi.fn(),
            request: vi.fn(),
            resize: vi.fn(),
            firstPage: vi.fn()
        })
    };
});

const mockFetch = vi.fn();
vi.mock('apps/deduplication/api/useMatchesRequiringReview', () => ({
    useMatchesRequiringReview: () => ({
        response: mockReturnValue,
        fetchMatchesRequiringReview: mockFetch
    })
}));

const Fixture = () => {
    return (
        <MemoryRouter>
            <SortingProvider>
                <PaginationProvider>
                    <MatchesRequiringReviewTable />
                </PaginationProvider>
            </SortingProvider>
        </MemoryRouter>
    );
};
describe('MatchesRequiringReviewTable', () => {
    it('should display the proper columns', () => {
        const { getAllByRole } = render(<Fixture />);

        const tableHeads = getAllByRole('columnheader');
        expect(tableHeads).toHaveLength(6);
        expect(tableHeads[0]).toHaveTextContent('Patient ID');
        expect(tableHeads[1]).toHaveTextContent('Person name');
        expect(tableHeads[2]).toHaveTextContent('Date created');
        expect(tableHeads[3]).toHaveTextContent('Date identified');
        expect(tableHeads[4]).toHaveTextContent('Number of matching records');
        expect(tableHeads[5]).toHaveTextContent('');
    });

    it('should display sorting icons', () => {
        const { getByText } = render(<Fixture />);

        const patientId = getByText('Patient ID');
        expect(patientId).toHaveClass('sortable');
        expect(patientId.children[0].children[0].getAttribute('xlink:href')).toContain('sprite.svg#sort_arrow');

        const personName = getByText('Person name');
        expect(personName).toHaveClass('sortable');
        expect(personName.children[0].children[0].getAttribute('xlink:href')).toContain('sprite.svg#sort_arrow');

        const dateCreated = getByText('Date created');
        expect(dateCreated).toHaveClass('sortable');
        expect(dateCreated.children[0].children[0].getAttribute('xlink:href')).toContain('sprite.svg#sort_arrow');

        const dateIdentified = getByText('Date identified');
        expect(dateIdentified).toHaveClass('sortable');
        expect(dateIdentified.children[0].children[0].getAttribute('xlink:href')).toContain(
            'sprite.svg#sort_des_numeric'
        );

        const numberOfMatching = getByText('Number of matching records');
        expect(numberOfMatching).toHaveClass('sortable');
        expect(numberOfMatching.children[0].children[0].getAttribute('xlink:href')).toContain('sprite.svg#sort_arrow');
    });

    it('should sort on click', async () => {
        const user = userEvent.setup();
        const { getByText } = render(<Fixture />);

        // default sort
        await waitFor(() => {
            expect(mockFetch).lastCalledWith(0, 20, 'identified,desc');
        });

        await user.click(getByText('Patient ID').children[0]); // sort on patient Id asc
        expect(mockFetch).lastCalledWith(0, 20, 'patient-id,asc');

        await user.click(getByText('Patient ID').children[0]); // sort on patient Id desc
        expect(mockFetch).lastCalledWith(0, 20, 'patient-id,desc');

        await user.click(getByText('Person name').children[0]); // sort on Person name
        expect(mockFetch).lastCalledWith(0, 20, 'name,asc');

        await user.click(getByText('Person name').children[0]); // sort on Person name desc
        expect(mockFetch).lastCalledWith(0, 20, 'name,desc');

        await user.click(getByText('Date created').children[0]); // sort on Date created
        expect(mockFetch).lastCalledWith(0, 20, 'created,asc');

        await user.click(getByText('Date created').children[0]); // sort on Date created desc
        expect(mockFetch).lastCalledWith(0, 20, 'created,desc');

        await user.click(getByText('Date identified').children[0]); // sort on Date identified
        expect(mockFetch).lastCalledWith(0, 20, 'identified,asc');

        await user.click(getByText('Date identified').children[0]); // sort on Date identified desc
        expect(mockFetch).lastCalledWith(0, 20, 'identified,desc');

        await user.click(getByText('Number of matching records').children[0]); // sort on Number of matching records
        expect(mockFetch).lastCalledWith(0, 20, 'count,asc');

        await user.click(getByText('Number of matching records').children[0]); // sort on Number of matching records desc
        expect(mockFetch).lastCalledWith(0, 20, 'count,desc');

        await user.click(getByText('Number of matching records').children[0]); // clear sort on Number
        expect(mockFetch).lastCalledWith(0, 20, 'identified,desc'); // back to default
    });

    it('should display the proper match data', () => {
        const { getAllByRole } = render(<Fixture />);

        const tableData = getAllByRole('cell');

        expect(tableData[0]).toHaveTextContent('4321');
        expect(tableData[1]).toHaveTextContent('John Smith');
        expect(tableData[2]).toHaveTextContent('02/11/2014 11:30 AM');
        expect(tableData[3]).toHaveTextContent('02/11/2024 12:30 PM');
        expect(tableData[4]).toHaveTextContent('2');
        expect(tableData[5]).toHaveTextContent('Review');
        expect(within(tableData[5]).getByRole('button')).toBeInTheDocument();
    });
});
