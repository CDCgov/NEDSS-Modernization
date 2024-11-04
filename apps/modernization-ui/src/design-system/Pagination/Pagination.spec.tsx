import { MemoryRouter } from 'react-router-dom';
import { render } from '@testing-library/react';
import { Pagination } from './Pagination';
import userEvent from '@testing-library/user-event';

const mockRequest = jest.fn();

let mockTotal: number;
let mockPageSize: number;
let mockCurrent: number;

jest.mock('page', () => ({
    usePage: () => ({
        request: mockRequest,
        page: {
            total: mockTotal,
            pageSize: mockPageSize,
            current: mockCurrent
        }
    })
}));

const Setup = () => (
    <MemoryRouter>
        <Pagination />
    </MemoryRouter>
);

describe('When Pagination renders', () => {
    it('should the total pages', () => {
        mockTotal = 17;
        mockPageSize = 11;

        const { getByText } = render(<Setup />);

        expect(getByText(/of 17/)).toBeInTheDocument();
    });

    it('should display 4 pages', () => {
        mockTotal = 17;
        mockPageSize = 5;

        const { queryByLabelText } = render(<Setup />);

        expect(queryByLabelText('Page 1')).toBeInTheDocument();
        expect(queryByLabelText('Page 2')).toBeInTheDocument();
        expect(queryByLabelText('Page 3')).toBeInTheDocument();
        expect(queryByLabelText('Page 4')).toBeInTheDocument();
        expect(queryByLabelText('Page 5')).not.toBeInTheDocument();
    });

    it('should display 4 pages', () => {
        mockTotal = 17;
        mockPageSize = 5;
        mockCurrent = 2;

        const { getByLabelText } = render(<Setup />);

        const page = getByLabelText('Page 2');

        expect(page).toHaveAttribute('aria-current', 'page');
    });

    it('should request the previous page', () => {
        mockTotal = 17;
        mockPageSize = 5;
        mockCurrent = 3;

        const { getByLabelText } = render(<Setup />);

        const page = getByLabelText('Previous page');

        userEvent.click(page);

        expect(mockRequest).toBeCalledWith(2);
    });

    it('should request the selected page', () => {
        mockTotal = 11;
        mockPageSize = 5;

        const { getByLabelText } = render(<Setup />);

        const page = getByLabelText('Page 2');

        userEvent.click(page);

        expect(mockRequest).toBeCalledWith(2);
    });

    it('should request the next page', () => {
        mockTotal = 13;
        mockPageSize = 5;
        mockCurrent = 2;

        const { getByLabelText } = render(<Setup />);

        const page = getByLabelText('Next page');

        userEvent.click(page);

        expect(mockRequest).toBeCalledWith(3);
    });

    it('should default to a page size of 10', () => {
        const { getByLabelText } = render(<Setup />);

        const toggle = getByLabelText('selected page size');

        expect(toggle).toHaveValue('10');

        expect(toggle).toBeInTheDocument();
    });

    it('should hide the pagination component when the total results are zero', () => {
        mockTotal = 0;

        const { getByText } = render(<Setup />);

        const paginationDiv = getByText('Showing', { exact: false }).parentElement;

        expect(paginationDiv).toBeInTheDocument();
        expect(paginationDiv).toHaveClass('hidden');
    });

    it('should show the pagination component when the total results are not zero', () => {
        mockTotal = 20;

        const { getByText } = render(<Setup />);

        const paginationDiv = getByText('Showing', { exact: false }).parentElement;

        expect(paginationDiv).toBeInTheDocument();
        expect(paginationDiv).toHaveClass('pagination');
    });
});
