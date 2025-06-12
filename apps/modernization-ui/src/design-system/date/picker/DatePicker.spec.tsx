import { render, waitFor } from '@testing-library/react';
import { axe } from 'vitest-axe';
import { DatePicker, DatePickerProps } from './DatePicker';

const Fixture = ({ id = 'testing-date-picker', ...remaining }: Partial<DatePickerProps>) => (
    <div>
        <label htmlFor={id}>Date picker input test</label>
        <DatePicker id={id} {...remaining} />
    </div>
);

describe('when picking a date value for data entry', () => {
    it('should render with no accessibility violations', async () => {
        const { container } = render(<Fixture />);

        expect(await axe(container)).toHaveNoViolations();
    });

    it('should accept a value', () => {
        const { getByRole } = render(<Fixture value="02/17/1967" />);

        const input = getByRole('textbox', { name: 'Date picker input test' });

        expect(input).toHaveValue('02/17/1967');
    });

    it('should allow specification of a minimum date', () => {
        const { container } = render(<Fixture minDate="03/11/2013" />);

        //  USWDS expects the attribute to exist on the outer most element of the date picker
        const entry = container.getElementsByClassName('usa-date-picker')[0];

        expect(entry).toHaveAttribute('data-min-date', '2013-03-11');
    });

    it('should not allow future date entry by default', () => {
        const { container } = render(<Fixture />);

        //  USWDS expects the attribute to exist on the outer most element of the date picker
        const entry = container.getElementsByClassName('usa-date-picker')[0];

        expect(entry).toHaveAttribute('data-max-date', new Date().toISOString().substring(0, 10));
    });

    it('should allow specification of a maximum date', () => {
        const { container } = render(<Fixture maxDate="03/11/2013" />);

        //  USWDS expects the attribute to exist on the outer most element of the date picker
        const entry = container.getElementsByClassName('usa-date-picker')[0];

        expect(entry).toHaveAttribute('data-max-date', '2013-03-11');
    });
});
