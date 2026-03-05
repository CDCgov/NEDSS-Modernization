import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { axe } from 'jest-axe';
import { SearchResultPageSizeSelect } from './SearchResultPageSizeSelect';

describe('when changing the number of results per page', () => {
    it('should render with no accessibility violations', async () => {
        const { container } = render(
            <SearchResultPageSizeSelect
                id={'testing'}
                value={20}
                selections={[20, 30, 50, 100]}
                onPageSizeChanged={jest.fn()}
            />
        );

        expect(await axe(container)).toHaveNoViolations();
    });

    it('should default to the minimum size', () => {
        const { getByLabelText } = render(
            <SearchResultPageSizeSelect id={'testing'} selections={[100, 50, 30, 20]} onPageSizeChanged={jest.fn()} />
        );

        const toggle = getByLabelText('Results per page');

        expect(toggle).toHaveValue('20');

        expect(toggle).toBeInTheDocument();
    });

    it('should invoke onPageSizeChanged when page size changed', async () => {
        const mockOnPageSizeChanged = jest.fn();

        const { getByLabelText } = render(
            <SearchResultPageSizeSelect
                id={'testing'}
                value={30}
                selections={[20, 30, 50, 100]}
                onPageSizeChanged={mockOnPageSizeChanged}
            />
        );

        const toggle = getByLabelText('Results per page');

        const user = userEvent.setup();
        await user.selectOptions(toggle, '50');

        expect(mockOnPageSizeChanged).toBeCalledWith(50);
    });
});
