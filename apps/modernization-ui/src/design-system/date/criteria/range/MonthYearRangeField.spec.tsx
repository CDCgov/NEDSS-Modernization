import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { axe } from 'jest-axe';
import { MonthYearRangeField } from './MonthYearRangeField';

describe('MonthYearRangeField Component', () => {
    it('should render with no accessibility violations', async () => {
        const { container } = render(
            <MonthYearRangeField
                id="testing-month-year-range-accessibility"
                onChange={vi.fn()}
                startYear={2000}
                endYear={2020}
            />
        );

        expect(await axe(container)).toHaveNoViolations();
    });

    it('should render the component with initial values', () => {
        const { getByRole } = render(
            <MonthYearRangeField
                id="testing-date-range"
                startYear={2000}
                endYear={2020}
                value={{
                    between: {
                        from: '01/2004',
                        to: '12/2009',
                    },
                }}
                onChange={vi.fn()}
            />
        );
        const fromMonth = getByRole('combobox', { name: 'From Month' });
        const fromYear = getByRole('combobox', { name: 'From Year' });
        const toMonth = getByRole('combobox', { name: 'To Month' });
        const toYear = getByRole('combobox', { name: 'To Year' });

        expect(fromMonth).toHaveValue('1');
        expect(fromYear).toHaveValue('2004');
        expect(toMonth).toHaveValue('12');
        expect(toYear).toHaveValue('2009');
    });

    it('should call from input change handler when the from month is entered', async () => {
        const mockOnChange = vi.fn();
        const { getByRole } = render(
            <MonthYearRangeField
                id="testing-date-range-from-entered"
                onChange={mockOnChange}
                startYear={2000}
                endYear={2020}
            />
        );

        const from = getByRole('combobox', { name: 'From Month' });

        const user = userEvent.setup();
        await user.selectOptions(from, '2');

        expect(mockOnChange).toHaveBeenCalledWith({ between: expect.objectContaining({ from: '02/0' }) });
    });

    it('should call from input change handler when the from year is entered', async () => {
        const mockOnChange = vi.fn();
        const { getByRole } = render(
            <MonthYearRangeField
                id="testing-date-range-from-entered"
                onChange={mockOnChange}
                startYear={2000}
                endYear={2020}
            />
        );

        const from = getByRole('combobox', { name: 'From Year' });

        const user = userEvent.setup();
        await user.selectOptions(from, '2018');

        expect(mockOnChange).toHaveBeenCalledWith({ between: expect.objectContaining({ from: '00/2018' }) });
    });

    it('should call from input change handler when the from month is changed', async () => {
        const mockOnChange = vi.fn();
        const { getByRole } = render(
            <MonthYearRangeField
                id="testing-date-range-from-change"
                startYear={2000}
                endYear={2020}
                value={{
                    between: {
                        from: '01/2004',
                    },
                }}
                onChange={mockOnChange}
            />
        );

        const from = getByRole('combobox', { name: 'From Month' });

        const user = userEvent.setup();
        await user.selectOptions(from, '8');

        expect(mockOnChange).toHaveBeenCalledWith({ between: expect.objectContaining({ from: '08/2004' }) });
    });

    it('should call from input change handler when the from year is changed', async () => {
        const mockOnChange = vi.fn();
        const { getByRole } = render(
            <MonthYearRangeField
                id="testing-date-range-from-change"
                startYear={2000}
                endYear={2020}
                value={{
                    between: {
                        from: '01/2004',
                    },
                }}
                onChange={mockOnChange}
            />
        );

        const from = getByRole('combobox', { name: 'From Year' });

        const user = userEvent.setup();
        await user.selectOptions(from, '2010');

        expect(mockOnChange).toHaveBeenCalledWith({ between: expect.objectContaining({ from: '01/2010' }) });
    });

    it('should call to input change handler when the to month is entered', async () => {
        const mockOnChange = vi.fn();
        const { getByRole } = render(
            <MonthYearRangeField
                id="testing-date-range-to-entered"
                onChange={mockOnChange}
                startYear={2000}
                endYear={2020}
            />
        );

        const to = getByRole('combobox', { name: 'To Month' });

        const user = userEvent.setup();
        await user.selectOptions(to, '2');

        expect(mockOnChange).toHaveBeenCalledWith({ between: expect.objectContaining({ to: '02/0' }) });
    });

    it('should call to input change handler when the to year is entered', async () => {
        const mockOnChange = vi.fn();
        const { getByRole } = render(
            <MonthYearRangeField
                id="testing-date-range-to-entered"
                onChange={mockOnChange}
                startYear={2000}
                endYear={2020}
            />
        );

        const to = getByRole('combobox', { name: 'To Year' });

        const user = userEvent.setup();
        await user.selectOptions(to, '2018');

        expect(mockOnChange).toHaveBeenCalledWith({ between: expect.objectContaining({ to: '00/2018' }) });
    });

    it('should call to input change handler when the to month is changed', async () => {
        const mockOnChange = vi.fn();
        const { getByRole } = render(
            <MonthYearRangeField
                id="testing-date-range-to-change"
                startYear={2000}
                endYear={2020}
                value={{
                    between: {
                        to: '01/2004',
                    },
                }}
                onChange={mockOnChange}
            />
        );

        const to = getByRole('combobox', { name: 'To Month' });

        const user = userEvent.setup();
        await user.selectOptions(to, '8');

        expect(mockOnChange).toHaveBeenCalledWith({ between: expect.objectContaining({ to: '08/2004' }) });
    });

    it('should call to input change handler when the to year is changed', async () => {
        const mockOnChange = vi.fn();
        const { getByRole } = render(
            <MonthYearRangeField
                id="testing-date-range-to-change"
                startYear={2000}
                endYear={2020}
                value={{
                    between: {
                        to: '01/2004',
                    },
                }}
                onChange={mockOnChange}
            />
        );

        const to = getByRole('combobox', { name: 'To Year' });

        const user = userEvent.setup();
        await user.selectOptions(to, '2010');

        expect(mockOnChange).toHaveBeenCalledWith({ between: expect.objectContaining({ to: '01/2010' }) });
    });
});
