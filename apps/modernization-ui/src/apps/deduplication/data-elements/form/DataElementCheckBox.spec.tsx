import { render, screen, fireEvent } from '@testing-library/react';
import { DataElementCheckBox } from './DataElementCheckBox';

describe('DataElementCheckBox', () => {
    it('renders the checkbox with label', () => {
        render(<DataElementCheckBox id="test-checkbox" label="Test Label" />);

        const checkbox = screen.getByTestId('test-checkbox');
        const label = screen.getByText('Test Label');

        expect(checkbox).toBeInTheDocument();
        expect(label).toBeInTheDocument();
        expect(checkbox).not.toBeChecked();
    });

    it('renders the checkbox as checked when selected is true', () => {
        render(<DataElementCheckBox id="test-checkbox" label="Test Label" selected />);

        const checkbox = screen.getByTestId('test-checkbox');
        expect(checkbox).toBeChecked();
    });

    it('calls onChange when checkbox is clicked', () => {
        const onChangeMock = jest.fn();
        render(<DataElementCheckBox id="test-checkbox" label="Test Label" onChange={onChangeMock} />);

        const checkbox = screen.getByTestId('test-checkbox');
        fireEvent.click(checkbox);

        expect(onChangeMock).toHaveBeenCalledWith(true);
    });

    it('disables the checkbox when disabled prop is provided', () => {
        render(<DataElementCheckBox id="test-checkbox" label="Test Label" disabled />);

        const checkbox = screen.getByTestId('test-checkbox');
        expect(checkbox).toBeDisabled();
    });

    it('toggles state correctly when clicked', () => {
        let checked = false;
        const onChangeMock = jest.fn((newState) => (checked = newState));

        render(<DataElementCheckBox id="test-checkbox" label="Test Label" selected={checked} onChange={onChangeMock} />);

        const checkbox = screen.getByTestId('test-checkbox');
        fireEvent.click(checkbox);

        expect(onChangeMock).toHaveBeenCalledWith(true);
    });
});
