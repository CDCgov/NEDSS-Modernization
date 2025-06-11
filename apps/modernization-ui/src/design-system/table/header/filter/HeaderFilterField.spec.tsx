import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { FilterInteraction, FilterType } from 'design-system/filter';
import { HeaderFilterField } from './HeaderFilterField';

const mockValueOf = jest.fn();
const mockApply = jest.fn();
const mockAdd = jest.fn();
const mockClear = jest.fn();

const mockInteraction: FilterInteraction = {
    active: false,
    filter: undefined,
    show: jest.fn(),
    hide: jest.fn(),
    toggle: jest.fn(),
    valueOf: mockValueOf,
    apply: mockApply,
    clear: mockClear,
    clearAll: jest.fn(),
    reset: jest.fn(),
    add: mockAdd,
    pendingFilter: { 'applying-value': 't' }
};

const Fixture = ({ id, type = 'text' }: { id: string; type?: FilterType }) => {
    return <HeaderFilterField descriptor={{ id, type }} label="Testing" filtering={mockInteraction} sizing="medium" />;
};

describe('when filtering table data from the header', () => {
    beforeEach(() => {
        mockValueOf.mockReset();
        mockApply.mockClear();
        mockAdd.mockClear();
        mockClear.mockClear();
    });

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

    it('should contain the applied filter value', () => {
        mockValueOf.mockReturnValue('applied-value');

        const { getByRole } = render(<Fixture id="test-id" />);
        const input = getByRole('textbox');
        expect(input).toHaveValue('applied-value');
    });

    it('should apply the filter when enter is pressed', async () => {
        const { getByRole } = render(<Fixture id="applying-value" />);
        const input = getByRole('textbox');

        const user = userEvent.setup();

        await user.type(input, 't{Enter}');

        expect(mockAdd).toHaveBeenCalledWith('applying-value', 't');
    });

    it('should clear the filter when clearing text from an input with existing value', async () => {
        mockValueOf.mockReturnValue('existing value');

        const { getByRole } = render(<Fixture id="clearing-value" />);
        const input = getByRole('textbox');

        const user = userEvent.setup();
        await user.clear(input);

        expect(mockAdd).toHaveBeenCalledWith('clearing-value', undefined);
        expect(mockClear).toHaveBeenCalledWith('clearing-value');
    });

    it('should not clear the filter when there is no existing filter value', async () => {
        mockValueOf.mockReturnValue('');

        const { getByRole } = render(<Fixture id="clearing-value" />);
        const input = getByRole('textbox');

        mockClear.mockClear();

        const user = userEvent.setup();
        await user.clear(input);

        expect(mockClear).not.toHaveBeenCalled();
    });
});
