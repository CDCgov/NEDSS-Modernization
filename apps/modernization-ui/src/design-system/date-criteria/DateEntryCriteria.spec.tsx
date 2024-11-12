import { render, fireEvent } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import { DateEntryCriteria, DateEntryCriteriaProps } from './DateEntryCriteria';
import { dateOperationOptions } from './options';

describe('DateEntryCriteria Component', () => {
    const defaultProps: DateEntryCriteriaProps = {
        id: 'test-id',
        label: 'Test Label',
        onChange: jest.fn()
    };

    it('should renders radio buttons for each date operation option', () => {
        const { getByLabelText } = render(<DateEntryCriteria {...defaultProps} />);
        dateOperationOptions.forEach((option) => {
            expect(getByLabelText(option.label)).toBeInTheDocument();
        });
    });

    it('should calls onChange with correct value when radio button is changed', () => {
        const handleChange = jest.fn();
        const { getByLabelText } = render(<DateEntryCriteria {...defaultProps} onChange={handleChange} />);

        const betweenRadio = getByLabelText('Date Range');
        fireEvent.click(betweenRadio);
        expect(handleChange).toHaveBeenCalledWith({ between: undefined });
    });
});
