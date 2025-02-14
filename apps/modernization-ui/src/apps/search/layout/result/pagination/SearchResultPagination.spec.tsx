import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { SearchResultPagination } from './SearchResultPagination';

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

const Setup = () => <SearchResultPagination id="search-pagination-testing" />;

describe('When paginating search results', () => {
    it('should the total pages', () => {
        mockTotal = 27;
        mockPageSize = 20;
        mockCurrent = 1;

        const { getByText } = render(<Setup />);

        expect(getByText(/Showing 1 to 20 of 27/)).toBeInTheDocument();
    });

    it('should display 4 pages', () => {
        mockTotal = 80;
        mockPageSize = 20;
        mockCurrent = 1;

        const { queryByLabelText } = render(<Setup />);

        expect(queryByLabelText('Page 1')).toBeInTheDocument();
        expect(queryByLabelText('Page 2')).toBeInTheDocument();
        expect(queryByLabelText('Page 3')).toBeInTheDocument();
        expect(queryByLabelText('Page 4')).toBeInTheDocument();
        expect(queryByLabelText('Page 5')).not.toBeInTheDocument();
    });

    it('should display results for the current page', () => {
        mockTotal = 47;
        mockPageSize = 20;
        mockCurrent = 2;

        const { getByLabelText, getByText } = render(<Setup />);

        const page = getByLabelText('Page 2');

        expect(page).toHaveAttribute('aria-current', 'page');

        expect(getByText(/Showing 21 to 40 of 47/)).toBeInTheDocument();
    });

    it('should request the previous page when the "Previous page" button is clicked', () => {
        mockTotal = 179;
        mockPageSize = 30;
        mockCurrent = 3;

        const { getByLabelText } = render(<Setup />);

        const page = getByLabelText('Previous page');

        userEvent.click(page);

        expect(mockRequest).toBeCalledWith(2);
    });

    it('should request the selected page when a page is clicked.', () => {
        mockTotal = 137;
        mockPageSize = 20;
        mockCurrent = 1;

        const { getByLabelText, debug } = render(<Setup />);

        const page = getByLabelText('Page 2');

        userEvent.click(page);

        expect(mockRequest).toBeCalledWith(2);
    });

    it('should request the next page when the "Next page" button is clicked', () => {
        mockTotal = 137;
        mockPageSize = 20;
        mockCurrent = 2;

        const { getByLabelText } = render(<Setup />);

        const page = getByLabelText('Next page');

        userEvent.click(page);

        expect(mockRequest).toBeCalledWith(3);
    });

    it('should default to a page size of 20', () => {
        mockTotal = 21;
        mockPageSize = 20;
        mockCurrent = 1;

        const { getByLabelText } = render(<Setup />);

        const toggle = getByLabelText('Results per page');

        expect(toggle).toHaveValue('20');

        expect(toggle).toBeInTheDocument();
    });

    it('should now allow pagination of results when no results are returned', () => {
        mockTotal = 0;
        mockPageSize = 20;
        mockCurrent = 1;

        const { queryByText, queryByLabelText } = render(<Setup />);

        const showing = queryByText('Showing');
        expect(showing).not.toBeInTheDocument();

        const toggle = queryByLabelText('Results per page');
        expect(toggle).not.toBeInTheDocument();
    });

    it('should now allow pagination of results when the total results are less than the smallest page size of 20.', () => {
        mockTotal = 19;
        mockPageSize = 20;
        mockCurrent = 1;

        const { queryByText, queryByLabelText } = render(<Setup />);

        const showing = queryByText('Showing');
        expect(showing).not.toBeInTheDocument();

        const toggle = queryByLabelText('Results per page');
        expect(toggle).not.toBeInTheDocument();
    });

    it('should allow pagination of results when the total results is greater than the smallest page size of 20.', () => {
        mockTotal = 21;
        mockPageSize = 20;
        mockCurrent = 1;

        const { getByText, getByLabelText } = render(<Setup />);

        const showing = getByText(/Showing/);

        expect(showing).toBeInTheDocument();

        const toggle = getByLabelText('Results per page');
        expect(toggle).toBeInTheDocument();
    });
});
