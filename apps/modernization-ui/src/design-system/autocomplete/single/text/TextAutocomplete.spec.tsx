import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';

import { AutocompleteOptionsResolver } from 'options/autocompete';
import { TextAutocomplete } from './TextAutocomplete';

const mockResolver: AutocompleteOptionsResolver = async (criteria: string) => {
    const options = [
        { name: 'Value 1', value: 'val-1', label: 'label-1' },
        { name: 'Value 2', value: 'val-2', label: 'label-2' },
        { name: 'Value 3', value: 'val-3', label: 'label-3' }
    ];
    return options.filter((option) => option.name.toLowerCase().includes(criteria.toLowerCase()));
};

describe('TextAutocomplete', () => {
    const defaultProps = {
        id: 'test-autocomplete',
        label: 'Test Autocomplete',
        resolver: mockResolver,
        onBlur: jest.fn()
    };

    it('renders with label and input', () => {
        const { getByLabelText, getByRole } = render(<TextAutocomplete {...defaultProps} />);
        expect(getByLabelText('Test Autocomplete')).toBeInTheDocument();
        expect(getByRole('textbox')).toBeInTheDocument();
    });

    it('displays suggestions when typing', async () => {
        const { getByRole, findByText } = render(<TextAutocomplete {...defaultProps} />);
        const input = getByRole('textbox');

        const user = userEvent.setup();
        await user.type(input, 'a');

        const suggestion = await findByText('Value 1');
        expect(suggestion).toBeInTheDocument();
    });

    it('calls onChange when a suggestion is selected', async () => {
        const onChange = jest.fn();
        const { getByRole, findByText } = render(<TextAutocomplete {...defaultProps} onChange={onChange} />);

        const input = getByRole('textbox');
        await userEvent.type(input, 'a');

        const select = await findByText('Value 1');
        expect(select).toBeInTheDocument();

        const user = userEvent.setup();
        await user.click(select);

        expect(onChange).toHaveBeenCalledWith('Value 1');
        expect(input).toHaveValue('Value 1');
    });

    it('displays error message when error prop is provided', () => {
        const { getByText } = render(<TextAutocomplete {...defaultProps} error="This is an error message" />);
        expect(getByText('This is an error message')).toBeInTheDocument();
    });

    it('marks input as required when required prop is true', () => {
        const { getByRole } = render(<TextAutocomplete {...defaultProps} required={true} />);
        expect(getByRole('textbox')).toHaveAttribute('required');
    });

    it('calls onChange when input loses focus', async () => {
        const onChange = jest.fn();
        const { getByRole } = render(<TextAutocomplete {...defaultProps} onChange={onChange} />);

        const input = getByRole('textbox');

        const user = userEvent.setup();
        await user.type(input, 'test');

        expect(onChange).toHaveBeenCalledWith('test');
    });
});
