import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { ExactDateEntry } from './ExactDateEntry';

describe('ExactDateEntry Component', () => {
    const mockOnChange = jest.fn();

    it('should render inputs with correct default month', () => {
        const { getByRole } = render(<ExactDateEntry value={{ equals: { month: 1 } }} onChange={mockOnChange} />);
        const monthInput = getByRole('textbox', { name: 'month' }) as HTMLInputElement;

        expect(monthInput.value).toBe('1');
    });

    it('should render inputs with correct default values', () => {
        const { getByRole } = render(
            <ExactDateEntry value={{ equals: { month: 1, day: 1, year: 1995 } }} onChange={mockOnChange} />
        );
        const monthInput = getByRole('textbox', { name: 'month' }) as HTMLInputElement;
        const dayInput = getByRole('textbox', { name: 'day' }) as HTMLInputElement;
        const yearInput = getByRole('textbox', { name: 'year' }) as HTMLInputElement;

        expect(monthInput.value).toBe('1');
        expect(dayInput.value).toBe('1');
        expect(yearInput.value).toBe('1995');
    });

    it('should call onChange with updated month value', async () => {
        const { getByRole } = render(<ExactDateEntry value={{ equals: { month: 1 } }} onChange={mockOnChange} />);
        const dayInput = getByRole('textbox', { name: 'day' }) as HTMLInputElement;
        await userEvent.paste(dayInput, '12');
        expect(mockOnChange).toHaveBeenCalledWith({ equals: { month: 1, day: 12 } });
    });
});
