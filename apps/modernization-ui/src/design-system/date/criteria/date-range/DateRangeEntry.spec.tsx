import { render } from '@testing-library/react';
import { DateBetweenCriteria } from 'design-system/date/entry';
import { DateRangeEntry } from './DateRangeEntry';
import userEvent from '@testing-library/user-event';

describe('DateRangeEntry Component', () => {
    const mockOnChange = jest.fn();
    const initialValue: DateBetweenCriteria = {
        between: {
            from: '02/17/1990',
            to: '02/17/2000'
        }
    };

    beforeEach(() => {
        jest.clearAllMocks();
    });

    it('should render the component with initial values', () => {
        const { getByRole } = render(
            <DateRangeEntry id="test-date-range" value={initialValue} onChange={mockOnChange} />
        );
        const fromInput = getByRole('textbox', { name: 'test-date-range-from' });
        const toInput = getByRole('textbox', { name: 'test-date-range-to' });

        expect(fromInput).toHaveValue('02/17/1990');
        expect(toInput).toHaveValue('02/17/2000');
    });

    it('should call from input change handler when the from date is changed', () => {
        const { getByRole } = render(
            <DateRangeEntry id="test-date-range" value={initialValue} onChange={mockOnChange} />
        );

        const fromInput = getByRole('textbox', { name: 'test-date-range-from' });
        userEvent.clear(fromInput);
        userEvent.paste(fromInput, '02/01/2023');

        expect(mockOnChange).toHaveBeenCalled();
    });
});
