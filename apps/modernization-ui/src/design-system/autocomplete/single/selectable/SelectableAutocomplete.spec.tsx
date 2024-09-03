import { act, render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';

import { AutocompleteOptionsResolver } from 'options/autocompete';
import { SelectableAutocomplete } from './SelectableAutocomplete';

const mockResolver: AutocompleteOptionsResolver = async (criteria: string) => {
    const options = [
        { name: 'Value 1', value: 'val-1', label: 'label-1' },
        { name: 'Value 2', value: 'val-2', label: 'label-2' },
        { name: 'Value 3', value: 'val-3', label: 'label-3' }
    ];
    return options.filter((option) => option.name.toLowerCase().includes(criteria.toLowerCase()));
};

describe('SelectableAutocomplete', () => {
    const defaultProps = {
        id: 'test-autocomplete',
        label: 'Test Autocomplete',
        resolver: mockResolver,
        onBlur: jest.fn()
    };

    it('renders with label and input', () => {
        const { getByLabelText, getByRole } = render(<SelectableAutocomplete {...defaultProps} />);
        expect(getByLabelText('Test Autocomplete')).toBeInTheDocument();
        expect(getByRole('textbox')).toBeInTheDocument();
    });

    it('displays suggestions when typing', async () => {
        const { getByRole, findByText } = render(<SelectableAutocomplete {...defaultProps} />);
        const input = getByRole('textbox');

        await act(async () => userEvent.type(input, 'a'));

        const appleSuggestion = await findByText('label-1');
        expect(appleSuggestion).toBeInTheDocument();
    });

    it('calls onChange when a suggestion is selected', async () => {
        const onChange = jest.fn();
        const { getByRole, findByText } = render(<SelectableAutocomplete {...defaultProps} onChange={onChange} />);

        const input = getByRole('textbox');
        await userEvent.type(input, 'a');

        const appleSuggestion = await findByText('label-1');
        expect(appleSuggestion).toBeInTheDocument();

        act(() => userEvent.click(appleSuggestion));

        expect(onChange).toHaveBeenCalledWith({ name: 'Value 1', value: 'val-1', label: 'label-1' });
        expect(input).toHaveValue('Value 1');
    });

    it('displays error message when error prop is provided', () => {
        const { getByText } = render(<SelectableAutocomplete {...defaultProps} error="This is an error message" />);
        expect(getByText('This is an error message')).toBeInTheDocument();
    });

    it('marks input as required when required prop is true', () => {
        const { getByRole } = render(<SelectableAutocomplete {...defaultProps} required={true} />);
        expect(getByRole('textbox')).toHaveAttribute('required');
    });
});
