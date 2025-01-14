import { act, render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';

import { AutocompleteOptionsResolver } from 'options/autocompete';
import { Autocomplete } from './Autocomplete';

const mockResolver: AutocompleteOptionsResolver = async (criteria: string) => {
    const options = [
        { name: 'Option 1', value: 'opt-1', label: 'label-1' },
        { name: 'Option 2', value: 'opt-2', label: 'label-2' },
        { name: 'Option 3', value: 'opt-3', label: 'label-3' }
    ];
    return options.filter((option) => option.name.toLowerCase().includes(criteria.toLowerCase()));
};

describe('Autocomplete', () => {
    const defaultProps = {
        id: 'test-autocomplete',
        label: 'Test Autocomplete',
        resolver: mockResolver,
        onBlur: jest.fn()
    };

    it('renders with label and input', () => {
        const { getByLabelText, getByRole } = render(<Autocomplete {...defaultProps} />);
        expect(getByLabelText('Test Autocomplete')).toBeInTheDocument();
        expect(getByRole('textbox')).toBeInTheDocument();
    });

    it('displays suggestions when typing', async () => {
        const { getByRole, findByText } = render(<Autocomplete {...defaultProps} />);
        const input = getByRole('textbox');

        await act(async () => userEvent.type(input, 'o'));

        const suggestion = await findByText('Option 1');
        expect(suggestion).toBeInTheDocument();
    });

    it('calls onChange when a suggestion is selected', async () => {
        const onChange = jest.fn();
        const { getByRole, findByText } = render(<Autocomplete {...defaultProps} onChange={onChange} />);

        const input = getByRole('textbox');
        await userEvent.type(input, 'o');

        const select = await findByText('Option 1');
        expect(select).toBeInTheDocument();

        act(() => userEvent.click(select));

        expect(onChange).toHaveBeenCalled();
        expect(input).toHaveValue('Option 1');
    });

    it('displays error message when error prop is provided', () => {
        const { getByText } = render(<Autocomplete {...defaultProps} error="This is an error message" />);
        expect(getByText('This is an error message')).toBeInTheDocument();
    });

    it('marks input as required when required prop is true', () => {
        const { getByRole } = render(<Autocomplete {...defaultProps} required={true} />);
        expect(getByRole('textbox')).toHaveAttribute('required');
    });

    it('calls onEntered when input changes', async () => {
        const onEntered = jest.fn();
        const { getByRole } = render(<Autocomplete {...defaultProps} onEntered={onEntered} />);

        const input = getByRole('textbox');
        await act(async () => {
            await userEvent.type(input, 'test');
        });

        expect(onEntered).toHaveBeenCalledWith('t');
        expect(onEntered).toHaveBeenCalledWith('e');
        expect(onEntered).toHaveBeenCalledWith('s');
        expect(onEntered).toHaveBeenCalledWith('t');
        expect(onEntered).toHaveBeenCalledTimes(4);
    });

    it('calls onBlur when input loses focus', async () => {
        const onBlur = jest.fn();
        const { getByRole } = render(<Autocomplete {...defaultProps} onBlur={onBlur} />);

        const input = getByRole('textbox');
        await userEvent.type(input, 'test');
        await userEvent.tab();

        expect(onBlur).toHaveBeenCalledTimes(1);
    });
});
