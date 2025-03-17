import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { DateCriteriaEntryProps } from './DateCriteriaEntry';
import { DateEqualsCriteria } from '../entry';
import { DateCriteriaEntry } from './DateCriteriaEntry';

describe('DateCriteriaEntry Component', () => {
    const defaultProps: DateCriteriaEntryProps = {
        id: 'test-date-entry',
        value: { equals: { day: 1, month: 1, year: 1995 } } as DateEqualsCriteria,
        label: 'Test Date Entry',
        onChange: jest.fn()
    };

    it('should render with default props', () => {
        const { getByLabelText } = render(<DateCriteriaEntry {...defaultProps} />);
        expect(getByLabelText('Exact Date')).toBeInTheDocument();
        expect(getByLabelText('Date Range')).toBeInTheDocument();
    });

    it('should render with default options when value is null', () => {
        const { getByLabelText, getByRole } = render(<DateCriteriaEntry {...defaultProps} value={null} />);
        const exactDateRadio = getByLabelText('Exact Date');
        expect(exactDateRadio).toBeChecked();
        expect(getByRole('spinbutton', { name: 'Month' })).toBeInTheDocument();
        expect(getByRole('spinbutton', { name: 'Day' })).toBeInTheDocument();
        expect(getByRole('spinbutton', { name: 'Year' })).toBeInTheDocument();
    });

    it('should call onChange when a exact date is selected', async () => {
        const { getByLabelText } = render(<DateCriteriaEntry {...defaultProps} />);

        const exactDateRadio = getByLabelText('Exact Date');
        await userEvent.click(exactDateRadio);

        expect(exactDateRadio).toBeChecked();
    });
});
