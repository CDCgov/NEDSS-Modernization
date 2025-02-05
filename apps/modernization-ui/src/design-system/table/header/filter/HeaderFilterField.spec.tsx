import { render, act, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { FilterInteraction, FilterProvider, FilterType } from 'design-system/filter';
import { HeaderFilterField } from './HeaderFilterField';

const mockApply = jest.fn();
const mockClear = jest.fn();

const mockInteraction: FilterInteraction = {
    active: false,
    filter: undefined,
    show: jest.fn(),
    hide: jest.fn(),
    toggle: jest.fn(),
    apply: mockApply,
    clear: mockClear,
    clearAll: jest.fn(),
    reset: jest.fn()
};

const Fixture = ({ id, type = 'text' }: { id: string; type?: FilterType }) => {
    return <HeaderFilterField descriptor={{ id, type }} label="Testing" filtering={mockInteraction} />;
};

describe('when filtering table data from the header', () => {
    it('should render the input for text filters', () => {
        const { getByRole } = render(<Fixture id="test-id" />);
        const input = getByRole('textbox', { name: 'filter by Testing' });
        expect(input).toBeInTheDocument();
    });

    it('should default to an empty filter', () => {
        const { getByRole } = render(<Fixture id="test-id" />);
        const input = getByRole('textbox');
        expect(input).toHaveValue('');
    });

    it('should apply the filter when enter is pressed', () => {
        const { getByRole } = render(<Fixture id="applying-value" />);
        const input = getByRole('textbox');

        act(() => {
            userEvent.type(input, 't');
        });

        act(() => {
            userEvent.type(input, '{Enter}');
        });

        expect(mockApply).toHaveBeenCalledWith('applying-value', 't');
    });

    it('should clear the filter when the clear button is pressed', () => {
        const { getByRole } = render(<Fixture id="clearing-value" />);
        const input = getByRole('textbox');

        act(() => {
            userEvent.clear(input);
            userEvent.type(input, '{Enter}');
        });

        expect(mockClear).toHaveBeenCalledWith('clearing-value');

        expect(input).toHaveValue('');
    });
});
