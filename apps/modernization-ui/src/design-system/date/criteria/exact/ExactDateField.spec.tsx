import { useState } from 'react';

import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { axe } from 'jest-axe';

import { DateEqualsCriteria, initialDateEqualsCriteria } from '../dateCriteria';

import { ExactDateField, ExactDateFieldProps } from './ExactDateField';

const Fixture = ({ value, onChange }: Partial<ExactDateFieldProps>) => {
    const [val, setVal] = useState<DateEqualsCriteria>(value ?? initialDateEqualsCriteria);
    const handleOnChange = (v: DateEqualsCriteria) => {
        setVal(v);
        onChange?.(v);
    };

    return <ExactDateField id="testing-exact-date-entry" value={val} onChange={handleOnChange} />;
};

describe('ExactDateField Component', () => {
    it('should render with no accessibility violations', async () => {
        const { container } = render(<Fixture value={initialDateEqualsCriteria} onChange={vi.fn()} />);

        expect(await axe(container)).toHaveNoViolations();
    });

    it('should render inputs with correct default month', () => {
        render(<Fixture value={{ equals: { month: 1, day: null, year: null } }} />);
        const monthInput = screen.getByRole('spinbutton', { name: 'Month' });

        expect(monthInput).toHaveValue(1);
    });

    it('should render inputs with correct default values', () => {
        const { getByRole } = render(<Fixture value={{ equals: { month: 1, day: 1, year: 1995 } }} />);
        const monthInput = getByRole('spinbutton', { name: 'Month' });
        const dayInput = getByRole('spinbutton', { name: 'Day' });
        const yearInput = getByRole('spinbutton', { name: 'Year' });

        expect(monthInput).toHaveValue(1);
        expect(dayInput).toHaveValue(1);
        expect(yearInput).toHaveValue(1995);
    });

    it('should call onChange when day value is changed', async () => {
        const mockOnChange = vi.fn();

        const { getByRole } = render(<Fixture onChange={mockOnChange} value={initialDateEqualsCriteria} />);

        const user = userEvent.setup();

        const day = getByRole('spinbutton', { name: 'Day' });

        await user.type(day, '12{tab}');

        expect(mockOnChange).toHaveBeenCalledWith({ equals: expect.objectContaining({ day: 12 }) });
    });

    it('should call onChange when month value is changed', async () => {
        const mockOnChange = vi.fn();

        const { getByRole } = render(<Fixture onChange={mockOnChange} value={initialDateEqualsCriteria} />);

        const user = userEvent.setup();

        const month = getByRole('spinbutton', { name: 'Month' });

        await user.type(month, '4{tab}');

        expect(mockOnChange).toHaveBeenCalledWith({ equals: expect.objectContaining({ month: 4 }) });
    });

    it('should call onChange when year value is changed', async () => {
        const mockOnChange = vi.fn();

        const { getByRole } = render(<Fixture onChange={mockOnChange} value={initialDateEqualsCriteria} />);
        const user = userEvent.setup();

        const year = getByRole('spinbutton', { name: 'Year' });

        await user.type(year, '1908{tab}');

        expect(mockOnChange).toHaveBeenCalledWith({ equals: expect.objectContaining({ year: 1908 }) });
    });
});
