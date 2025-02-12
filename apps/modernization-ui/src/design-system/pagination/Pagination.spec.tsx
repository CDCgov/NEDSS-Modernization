import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { axe } from 'jest-axe';
import { Pagination } from './Pagination';

describe('when paginating a large set of data', () => {
    it('should render with no accessibility violations', async () => {
        const { container } = render(
            <Pagination total={5} current={3} onNext={jest.fn()} onPrevious={jest.fn()} onSelectPage={jest.fn()} />
        );

        expect(await axe(container)).toHaveNoViolations();
    });

    it('should display the given number of pages', () => {
        const { queryByLabelText } = render(
            <Pagination total={4} current={1} onNext={jest.fn()} onPrevious={jest.fn()} onSelectPage={jest.fn()} />
        );

        expect(queryByLabelText('Page 1')).toBeInTheDocument();
        expect(queryByLabelText('Page 2')).toBeInTheDocument();
        expect(queryByLabelText('Page 3')).toBeInTheDocument();
        expect(queryByLabelText('Page 4')).toBeInTheDocument();
        expect(queryByLabelText('Page 5')).not.toBeInTheDocument();
    });

    it('should only display the maximum number of pages', () => {
        const { queryByLabelText, getByText } = render(
            <Pagination
                total={11}
                maxPagesShown={7}
                current={1}
                onNext={jest.fn()}
                onPrevious={jest.fn()}
                onSelectPage={jest.fn()}
            />
        );

        expect(getByText('…')).toBeInTheDocument();

        expect(queryByLabelText('Page 1')).toBeInTheDocument();
        expect(queryByLabelText('Page 2')).toBeInTheDocument();
        expect(queryByLabelText('Page 3')).toBeInTheDocument();
        expect(queryByLabelText('Page 4')).toBeInTheDocument();
        expect(queryByLabelText('Page 5')).toBeInTheDocument();
        expect(queryByLabelText('Page 6')).not.toBeInTheDocument();
        expect(queryByLabelText('Page 7')).not.toBeInTheDocument();
        expect(queryByLabelText('Page 8')).not.toBeInTheDocument();
        expect(queryByLabelText('Page 9')).not.toBeInTheDocument();
        expect(queryByLabelText('Page 10')).not.toBeInTheDocument();
        expect(queryByLabelText('Page 11')).toBeInTheDocument();
    });

    it('should only display the maximum number of pages relative to the current page', () => {
        const { queryByLabelText, getAllByText } = render(
            <Pagination
                total={11}
                maxPagesShown={7}
                current={5}
                onNext={jest.fn()}
                onPrevious={jest.fn()}
                onSelectPage={jest.fn()}
            />
        );

        // There will be two "…" displayed
        expect(getAllByText('…')).toHaveLength(2);

        expect(queryByLabelText('Page 1')).toBeInTheDocument();
        expect(queryByLabelText('Page 2')).not.toBeInTheDocument();
        expect(queryByLabelText('Page 3')).not.toBeInTheDocument();
        expect(queryByLabelText('Page 4')).toBeInTheDocument();
        expect(queryByLabelText('Page 5')).toBeInTheDocument();
        expect(queryByLabelText('Page 6')).toBeInTheDocument();
        expect(queryByLabelText('Page 7')).not.toBeInTheDocument();
        expect(queryByLabelText('Page 8')).not.toBeInTheDocument();
        expect(queryByLabelText('Page 9')).not.toBeInTheDocument();
        expect(queryByLabelText('Page 10')).not.toBeInTheDocument();
        expect(queryByLabelText('Page 11')).toBeInTheDocument();
    });

    it('should specify the current page with aria-current', () => {
        const { getByLabelText } = render(
            <Pagination total={4} current={2} onNext={jest.fn()} onPrevious={jest.fn()} onSelectPage={jest.fn()} />
        );

        const page = getByLabelText('Page 2');

        expect(page).toHaveAttribute('aria-current', 'page');
    });

    it('should request the previous page when the "Previous page" button is clicked', () => {
        const mockOnNext = jest.fn();
        const mockOnPrevious = jest.fn();
        const mockOnSelectPage = jest.fn();

        const { getByLabelText } = render(
            <Pagination
                total={4}
                current={3}
                onNext={mockOnNext}
                onPrevious={mockOnPrevious}
                onSelectPage={mockOnSelectPage}
            />
        );

        const page = getByLabelText('Previous page');

        userEvent.click(page);

        expect(mockOnPrevious).toBeCalled();
    });

    it('should request the selected page when a page is clicked', () => {
        const mockOnNext = jest.fn();
        const mockOnPrevious = jest.fn();
        const mockOnSelectPage = jest.fn();

        const { getByLabelText } = render(
            <Pagination
                total={4}
                current={1}
                onNext={mockOnNext}
                onPrevious={mockOnPrevious}
                onSelectPage={mockOnSelectPage}
            />
        );

        const page = getByLabelText('Page 2');

        userEvent.click(page);

        expect(mockOnSelectPage).toBeCalledWith(2);
    });

    it('should request the next page when the "Next page" button is clicked', () => {
        const mockOnNext = jest.fn();
        const mockOnPrevious = jest.fn();
        const mockOnSelectPage = jest.fn();

        const { getByLabelText } = render(
            <Pagination
                total={4}
                current={2}
                onNext={mockOnNext}
                onPrevious={mockOnPrevious}
                onSelectPage={mockOnSelectPage}
            />
        );

        const page = getByLabelText('Next page');

        userEvent.click(page);

        expect(mockOnNext).toBeCalled();
    });
});
