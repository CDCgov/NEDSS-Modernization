import { MemoryRouter, Route, Routes } from 'react-router';
import { MergeDetails } from './MergeDetails';
import { render } from '@testing-library/react';

const mockFetch = jest.fn();
let mockLoading = false;
jest.mock('apps/deduplication/api/useMergeDetails', () => ({
    useMergeDetails: () => {
        return { fetchPatientMergeDetails: mockFetch, loading: mockLoading };
    }
}));

const Fixture = () => {
    return (
        <MemoryRouter initialEntries={['/deduplication/merge/1234']}>
            <Routes>
                <Route path="/deduplication/merge/:patientId" element={<MergeDetails />} />
            </Routes>
        </MemoryRouter>
    );
};
describe('MergeDetails', () => {
    beforeEach(() => {
        mockLoading = false;
    });

    it('should fetch the patient merge details specified in the path', () => {
        render(<Fixture />);
        expect(mockFetch).toHaveBeenCalledWith('1234');
    });

    it('should render a loading indicator', () => {
        mockLoading = true;
        const { getByText } = render(<Fixture />);
        expect(getByText('Loading')).toBeInTheDocument();
    });

    it('should default to review display', () => {
        const { getByText } = render(<Fixture />);

        expect(getByText('Patient matches requiring review')).toBeInTheDocument();
    });
});
