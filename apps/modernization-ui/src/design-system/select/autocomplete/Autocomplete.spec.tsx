import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { Autocomplete } from './Autocomplete';

const mockUseAutocomplete = jest.fn();
const mockSuggest = jest.fn();
const mockReset = jest.fn();

const defaultProps = {
    id: 'test-autocomplete',
    label: 'Test Autocomplete',
    placeholder: 'Enter text',
    onChange: jest.fn(),
    onBlur: jest.fn(),
    useAutocomplete: mockUseAutocomplete
};

describe('Autocomplete', () => {
    beforeEach(() => {
        mockUseAutocomplete.mockReturnValue({
            options: [],
            suggest: mockSuggest,
            reset: mockReset
        });
    });

    it('renders with label and input', () => {
        const { getByLabelText, getByPlaceholderText } = render(<Autocomplete {...defaultProps} />);

        expect(getByLabelText('Test Autocomplete')).toBeInTheDocument();
        expect(getByPlaceholderText('Enter text')).toBeInTheDocument();
    });

    it('calls suggest on input change', async () => {
        const { getByRole } = render(<Autocomplete {...defaultProps} />);

        const input = getByRole('textbox');
        await userEvent.type(input, 'test');

        expect(mockSuggest).toHaveBeenCalledWith('test');
    });

    it('displays suggestions when options are available', async () => {
        mockUseAutocomplete.mockReturnValue({
            options: [
                { label: 'Option 1', value: 'option1' },
                { label: 'Option 2', value: 'option2' }
            ],
            suggest: mockSuggest,
            reset: mockReset
        });

        const { getByRole, getByText } = render(<Autocomplete {...defaultProps} />);

        const input = getByRole('textbox');
        await userEvent.type(input, 'test');

        expect(getByText('Option 1')).toBeInTheDocument();
        expect(getByText('Option 2')).toBeInTheDocument();
    });

    it('calls onChange and onBlur when a suggestion is selected', async () => {
        mockUseAutocomplete.mockReturnValue({
            options: [{ label: 'Option 1', value: 'option1' }],
            suggest: mockSuggest,
            reset: mockReset
        });

        const { getByRole, getByText } = render(<Autocomplete {...defaultProps} />);

        const input = getByRole('textbox');
        await userEvent.type(input, 'test');

        const option = getByText('Option 1');
        userEvent.click(option);

        expect(defaultProps.onChange).toHaveBeenCalledWith('option1');
        expect(defaultProps.onBlur).toHaveBeenCalled();
    });

    it('renders with error state', () => {
        const { getByText, container } = render(<Autocomplete {...defaultProps} error="Error message" />);

        expect(getByText('Error message')).toBeInTheDocument();
        expect(container.firstChild).toHaveClass('input--error');
    });
});
