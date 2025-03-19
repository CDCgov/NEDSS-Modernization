import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { DateCriteriaFieldProps, DateCriteriaField } from './DateCriteriaField';
import { DateEqualsCriteria } from './dateCriteria';

describe('DateCriteriaField Component', () => {
    const defaultProps: DateCriteriaFieldProps = {
        id: 'test-date-entry',
        value: { equals: { day: 1, month: 1, year: 1995 } } as DateEqualsCriteria,
        label: 'Test Date Entry',
        onChange: jest.fn()
    };

    it('should render with default props', () => {
        const { getByLabelText } = render(<DateCriteriaField {...defaultProps} />);
        expect(getByLabelText('Exact Date')).toBeInTheDocument();
        expect(getByLabelText('Date Range')).toBeInTheDocument();
    });

    it('should render with default options when value is null', () => {
        const { getByLabelText, getByRole } = render(<DateCriteriaField {...defaultProps} value={null} />);
        const exactDateRadio = getByLabelText('Exact Date');
        expect(exactDateRadio).toBeChecked();
        expect(getByRole('spinbutton', { name: 'Month' })).toBeInTheDocument();
        expect(getByRole('spinbutton', { name: 'Day' })).toBeInTheDocument();
        expect(getByRole('spinbutton', { name: 'Year' })).toBeInTheDocument();
    });

    it('should call onChange when a exact date is selected', async () => {
        const { getByLabelText } = render(<DateCriteriaField {...defaultProps} />);

        const exactDateRadio = getByLabelText('Exact Date');
        await userEvent.click(exactDateRadio);

        expect(exactDateRadio).toBeChecked();
    });
});
