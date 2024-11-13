import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { DateEntryCriteriaProps } from './DateEntryCriteria';
import { DateEqualsCriteria } from '../entry';
import { DateEntryCriteria } from './DateEntryCriteria';

describe('DateEntryCriteria Component', () => {
    const defaultProps: DateEntryCriteriaProps = {
        id: 'test-date-entry',
        value: { equals: { day: 1, month: 1, year: 1995 } } as DateEqualsCriteria,
        label: 'Test Date Entry',
        onChange: jest.fn()
    };

    it('should render with default props', () => {
        const { getByLabelText } = render(<DateEntryCriteria {...defaultProps} />);
        expect(getByLabelText('Exact Date')).toBeInTheDocument();
        expect(getByLabelText('Date Range')).toBeInTheDocument();
    });

    it('should switch to Date Range when Date Range radio is selected', async () => {
        const { getByLabelText } = render(<DateEntryCriteria {...defaultProps} />);

        const dateRangeRadio = getByLabelText('Date Range');
        await userEvent.click(dateRangeRadio);

        expect(dateRangeRadio).toBeChecked();
    });

    it('should call onChange when a exact date is selected', async () => {
        const { getByLabelText } = render(<DateEntryCriteria {...defaultProps} />);

        const exactDateRadio = getByLabelText('Exact Date');
        await userEvent.click(exactDateRadio);

        expect(exactDateRadio).toBeChecked();
    });
});
