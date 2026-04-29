import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { axe } from 'jest-axe';
import { YearRangeField } from './YearRangeField';

describe('YearRangeField Component', () => {
    it('should render with no accessibility violations', async () => {
        const { container } = render(
            <YearRangeField id="testing-year-range-accessibility" onChange={vi.fn()} startYear={2000} endYear={2020} />
        );

        expect(await axe(container)).toHaveNoViolations();
    });

    it('should render the component with initial values', () => {
        const { getByRole } = render(
            <YearRangeField
                id="testing-date-range"
                startYear={2000}
                endYear={2020}
                value={{
                    between: {
                        from: '2004',
                        to: '2009',
                    },
                }}
                onChange={vi.fn()}
            />
        );
        const from = getByRole('combobox', { name: 'From' });
        const to = getByRole('combobox', { name: 'To' });

        expect(from).toHaveValue('2004');
        expect(to).toHaveValue('2009');
    });

    it('should call from input change handler when the from date is entered', async () => {
        const mockOnChange = vi.fn();
        const { getByRole } = render(
            <YearRangeField
                id="testing-date-range-from-entered"
                onChange={mockOnChange}
                startYear={2000}
                endYear={2020}
            />
        );

        const from = getByRole('combobox', { name: 'From' });

        const user = userEvent.setup();
        await user.selectOptions(from, '2010');

        expect(mockOnChange).toHaveBeenCalledWith({ between: expect.objectContaining({ from: '2010' }) });
    });

    it('should call from input change handler when the from date is changed', async () => {
        const mockOnChange = vi.fn();
        const { getByRole } = render(
            <YearRangeField
                id="testing-date-range-from-change"
                startYear={2000}
                endYear={2020}
                value={{
                    between: {
                        from: '2004',
                    },
                }}
                onChange={mockOnChange}
            />
        );

        const from = getByRole('combobox', { name: 'From' });

        const user = userEvent.setup();
        await user.selectOptions(from, '2010');

        expect(mockOnChange).toHaveBeenCalledWith({ between: expect.objectContaining({ from: '2010' }) });
    });

    it('should call to input change handler when the to date is entered', async () => {
        const mockOnChange = vi.fn();
        const { getByRole } = render(
            <YearRangeField
                id="testing-date-range-to-entered"
                onChange={mockOnChange}
                startYear={2000}
                endYear={2020}
            />
        );

        const to = getByRole('combobox', { name: 'To' });

        const user = userEvent.setup();
        await user.selectOptions(to, '2010');

        expect(mockOnChange).toHaveBeenCalledWith({ between: expect.objectContaining({ to: '2010' }) });
    });

    it('should call to input change handler when the to date is changed', async () => {
        const mockOnChange = vi.fn();
        const { getByRole } = render(
            <YearRangeField
                id="testing-date-range-to-change"
                startYear={2000}
                endYear={2020}
                value={{
                    between: {
                        from: '2004',
                        to: '2009',
                    },
                }}
                onChange={mockOnChange}
            />
        );

        const to = getByRole('combobox', { name: 'To' });

        const user = userEvent.setup();
        await user.selectOptions(to, '2010');

        expect(mockOnChange).toHaveBeenCalledWith({ between: expect.objectContaining({ to: '2010' }) });
    });
});
