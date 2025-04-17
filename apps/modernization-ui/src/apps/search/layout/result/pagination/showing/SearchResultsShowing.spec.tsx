import { render } from '@testing-library/react';
import { axe } from 'jest-axe';
import { SearchResultsShowing } from './SearchResultsShowing';
import { Page } from 'pagination';

describe('when displaying search results totals', () => {
    it('should render with no accessibility violations', async () => {
        const page: Page = {
            status: 0,
            pageSize: 20,
            total: 27,
            current: 1
        };

        const { container } = render(<SearchResultsShowing page={page} />);

        expect(await axe(container)).toHaveNoViolations();
    });

    it('should display the results of the first page', () => {
        const page: Page = {
            status: 0,
            pageSize: 20,
            total: 27,
            current: 1
        };

        const { getByText } = render(<SearchResultsShowing page={page} />);

        expect(getByText('Showing 1 to 20 of 27')).toBeInTheDocument();
    });

    it('should display the results of the second page', () => {
        const page: Page = {
            status: 0,
            pageSize: 20,
            total: 47,
            current: 2
        };

        const { getByText } = render(<SearchResultsShowing page={page} />);

        expect(getByText('Showing 21 to 40 of 47')).toBeInTheDocument();
    });

    it('should display the results of a partial page', () => {
        const page: Page = {
            status: 0,
            pageSize: 20,
            total: 7,
            current: 1
        };

        const { getByText } = render(<SearchResultsShowing page={page} />);

        expect(getByText('Showing 1 to 7 of 7')).toBeInTheDocument();
    });
});
