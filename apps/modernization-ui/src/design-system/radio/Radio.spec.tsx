import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { Radio } from './Radio';

const mockOnChange = jest.fn();

describe('Radio Component', () => {
    it('should render the radio button with the correct label', () => {
        const { getByText } = render(<Radio value="radio-1" name="test" label="Test Label" onChange={mockOnChange} />);
        expect(getByText('Test Label')).toBeInTheDocument();
    });

    it('should apply the correct class names based on props', () => {
        const { getByRole } = render(
            <Radio value="radio-1" name="test" className="custom-class" sizing="large" onChange={mockOnChange} />
        );
        const radioElement = getByRole('radio');
        expect(radioElement.parentElement).toHaveClass('custom-class');
        expect(radioElement.parentElement).toHaveClass('large');
    });

    it('should call onChange handler when clicked', async () => {
        const { getByRole } = render(<Radio value="radio-1" name="test" onChange={mockOnChange} />);
        const radioElement = getByRole('radio');

        const user = userEvent.setup();

        await user.click(radioElement);
        expect(mockOnChange).toHaveBeenCalledTimes(1);
    });

    it('should be checked when the checked prop is true', () => {
        const { getByRole } = render(<Radio name="test" value="radio-1" checked={true} onChange={mockOnChange} />);
        const radioElement = getByRole('radio');
        expect(radioElement).toBeChecked();
    });

    it('should not be checked when the checked prop is false', () => {
        const { getByRole } = render(<Radio name="test" value="radio-1" checked={false} onChange={mockOnChange} />);
        const radioElement = getByRole('radio');
        expect(radioElement).not.toBeChecked();
    });

    it('should display the label as disabled when input is disabled', () => {
        const { getByText } = render(
            <Radio name="test" value="radio-1" label="Test Label" disabled onChange={mockOnChange} />
        );
        const labelElement = getByText('Test Label');
        expect(labelElement).toHaveClass('disabled');
    });
});
