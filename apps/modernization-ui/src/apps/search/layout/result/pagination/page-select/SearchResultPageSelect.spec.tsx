import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { Page } from 'page';
import { SearchResultPageSelect } from './SearchResultPageSelect';

describe('when displaying the pages search results', () => {
    it('should display 4 pages', () => {
        const page: Page = {
            status: 0,
            pageSize: 20,
            total: 80,
            current: 1
        };

        const { queryByLabelText } = render(<SearchResultPageSelect page={page} onSelected={jest.fn()} />);

        expect(queryByLabelText('Page 1')).toBeInTheDocument();
        expect(queryByLabelText('Page 2')).toBeInTheDocument();
        expect(queryByLabelText('Page 3')).toBeInTheDocument();
        expect(queryByLabelText('Page 4')).toBeInTheDocument();
        expect(queryByLabelText('Page 5')).not.toBeInTheDocument();
    });

    it('should display results for the current page', () => {
        const page: Page = {
            status: 0,
            pageSize: 20,
            total: 47,
            current: 2
        };

        const { getByLabelText } = render(<SearchResultPageSelect page={page} onSelected={jest.fn()} />);

        const current = getByLabelText('Page 2');

        expect(current).toHaveAttribute('aria-current', 'page');
    });

    it('should request the previous page when the "Previous page" button is clicked', () => {
        const page: Page = {
            status: 0,
            pageSize: 30,
            total: 179,
            current: 3
        };

        const mockOnSelected = jest.fn();

        const { getByLabelText } = render(<SearchResultPageSelect page={page} onSelected={mockOnSelected} />);

        const previous = getByLabelText('Previous page');

        userEvent.click(previous);

        expect(mockOnSelected).toBeCalledWith(2);
    });

    it('should request the selected page when a page is clicked.', () => {
        const page: Page = {
            status: 0,
            pageSize: 20,
            total: 137,
            current: 1
        };

        const mockOnSelected = jest.fn();

        const { getByLabelText } = render(<SearchResultPageSelect page={page} onSelected={mockOnSelected} />);

        const current = getByLabelText('Page 2');

        userEvent.click(current);

        expect(mockOnSelected).toBeCalledWith(2);
    });

    it('should request the next page when the "Next page" button is clicked', () => {
        const page: Page = {
            status: 0,
            pageSize: 20,
            total: 137,
            current: 2
        };

        const mockOnSelected = jest.fn();

        const { getByLabelText } = render(<SearchResultPageSelect page={page} onSelected={mockOnSelected} />);
        const next = getByLabelText('Next page');

        userEvent.click(next);

        expect(mockOnSelected).toBeCalledWith(3);
    });
});
