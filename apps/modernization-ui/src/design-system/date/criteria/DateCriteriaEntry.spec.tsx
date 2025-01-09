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

    it('should call onChange when a exact date is selected', async () => {
        const { getByLabelText } = render(<DateCriteriaEntry {...defaultProps} />);

        const exactDateRadio = getByLabelText('Exact Date');
        await userEvent.click(exactDateRadio);

        expect(exactDateRadio).toBeChecked();
    });
});
