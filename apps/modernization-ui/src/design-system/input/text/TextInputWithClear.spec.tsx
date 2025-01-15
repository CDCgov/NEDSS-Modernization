import { render } from '@testing-library/react';
import { TextInputWithClear } from './TextInputWithClear';
import userEvent from '@testing-library/user-event';

describe('TextInputWithClear Component', () => {
    const mockOnChange = jest.fn();
    const mockOnClear = jest.fn();
    const mockOnKeyPress = jest.fn();

    const wrapper = (value = '') => {
        return render(
            <TextInputWithClear
                id="test-input"
                value={value}
                onChange={mockOnChange}
                onClear={mockOnClear}
                onKeyPress={mockOnKeyPress}
            />
        );
    };

    test('renders correctly with initial value', () => {
        const { getByDisplayValue } = wrapper('initial value');
        expect(getByDisplayValue('initial value')).toBeInTheDocument();
    });

    test('updates value when prop changes', () => {
        const { rerender, getByDisplayValue } = wrapper('initial value');
        rerender(
            <TextInputWithClear
                id="test-input"
                value="new value"
                onChange={mockOnChange}
                onClear={mockOnClear}
                onKeyPress={mockOnKeyPress}
            />
        );
        expect(getByDisplayValue('new value')).toBeInTheDocument();
    });

    test('clears input and calls onClear when icon is clicked', () => {
        const { getByTestId } = wrapper('clear me');
        const icon = getByTestId('clear-icon');
        userEvent.click(icon);
        expect(mockOnClear).toHaveBeenCalled();
    });
});
