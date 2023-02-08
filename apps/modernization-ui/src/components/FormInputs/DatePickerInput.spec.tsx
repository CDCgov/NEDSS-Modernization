import { render } from '@testing-library/react';
import { DatePickerInput } from './DatePickerInput';

describe('DatePickerInput component tests', () => {
    describe('when default date value is provided', () => {
        it('should render DatePicker which has a label as Test DP Label and an input box with the provided default date value', () => {
            const { 
                getByLabelText,
                getByTestId
             } = render(<DatePickerInput id="test-dp-id" name="test-dp-name" label="Test DP Label" className="test-dp-class-name" htmlFor="test-dp-id" defaultValue="12/31/2022"/>);
            expect(getByLabelText('Test DP Label')).toBeTruthy();
            expect(getByTestId('date-picker-internal-input').getAttribute('value')).toBe('2022-12-31');
        });
    });
    describe('when default date value is not provided', () => {
        it('should render DatePicker which has a label as Test DP Label and an empty input box', () => {
            const { 
                getByLabelText,
                getByTestId
             } = render(<DatePickerInput id="test-dp-id" name="test-dp-name" label="Test DP Label" className="test-dp-class-name" htmlFor="test-dp-id" />);
            expect(getByLabelText('Test DP Label')).toBeTruthy();
            expect(getByTestId('date-picker-internal-input').getAttribute('value')).toBe('');
        });
    });
});