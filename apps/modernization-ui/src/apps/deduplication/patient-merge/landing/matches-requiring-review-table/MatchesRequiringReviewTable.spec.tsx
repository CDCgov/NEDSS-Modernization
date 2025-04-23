import { MatchRequiringReviewResponse } from 'apps/deduplication/api/model/MatchRequiringReview';
import { MatchesRequiringReviewTable } from './MatchesRequiringReviewTable';
import { render, within } from '@testing-library/react';
import { PaginationProvider } from 'pagination';
import { MemoryRouter } from 'react-router';

let mockReturnValue: MatchRequiringReviewResponse | undefined = {
    matches: [
        {
            patientId: '1234',
            patientName: 'John Smith',
            createdDate: '2014-02-11T11:30:30',
            identifiedDate: '2024-02-11T12:30:30',
            numOfMatchingRecords: 2
        }
    ],
    page: 0,
    total: 0
};
jest.mock('apps/deduplication/api/useMatchesRequiringReview', () => ({
    useMatchesRequiringReview: () => {
        return { response: mockReturnValue };
    }
}));
const Fixture = () => {
    return (
        <MemoryRouter>
            <PaginationProvider>
                <MatchesRequiringReviewTable />
            </PaginationProvider>
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

    it('should display the proper match data', () => {
        const { getAllByRole } = render(<Fixture />);

        const tableData = getAllByRole('cell');

        expect(tableData[0]).toHaveTextContent('1234');
        expect(tableData[1]).toHaveTextContent('John Smith');
        expect(tableData[2]).toHaveTextContent('02/11/2014 11:30 AM');
        expect(tableData[3]).toHaveTextContent('02/11/2024 12:30 PM');
        expect(tableData[4]).toHaveTextContent('2');
        expect(tableData[5]).toHaveTextContent('Review');
        expect(within(tableData[5]).getByRole('button')).toBeInTheDocument();
    });
});
