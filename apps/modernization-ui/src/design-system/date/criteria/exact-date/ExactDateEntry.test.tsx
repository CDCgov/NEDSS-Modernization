import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { ExactDateEntry } from './ExactDateEntry';

describe('ExactDateEntry Component', () => {
    const mockOnChange = jest.fn();

    it('should render inputs with correct default month', () => {
        const { getByRole } = render(
            <ExactDateEntry id="test-id" value={{ equals: { month: 1 } }} onChange={mockOnChange} />
        );
        const monthInput = getByRole('spinbutton', { name: 'Month' }) as HTMLInputElement;

        expect(monthInput.value).toBe('1');
    });

    it('should render inputs with correct default values', () => {
        const { getByRole } = render(
            <ExactDateEntry id="test-id" value={{ equals: { month: 1, day: 1, year: 1995 } }} onChange={mockOnChange} />
        );
        const monthInput = getByRole('spinbutton', { name: 'Month' }) as HTMLInputElement;
        const dayInput = getByRole('spinbutton', { name: 'Day' }) as HTMLInputElement;
        const yearInput = getByRole('spinbutton', { name: 'Year' }) as HTMLInputElement;

        expect(monthInput.value).toBe('1');
        expect(dayInput.value).toBe('1');
        expect(yearInput.value).toBe('1995');
    });

    it('should call onChange with updated month value', async () => {
        const { getByRole } = render(
            <ExactDateEntry id="test-id" value={{ equals: { month: 1 } }} onChange={mockOnChange} />
        );
        const dayInput = getByRole('spinbutton', { name: 'Day' }) as HTMLInputElement;
        await userEvent.paste(dayInput, '12');
        expect(mockOnChange).toHaveBeenCalledWith({ equals: { month: 1, day: 12 } });
    });
});
