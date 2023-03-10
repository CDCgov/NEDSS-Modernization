import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { DatePickerInput } from './DatePickerInput';

describe('DatePickerInput component tests', () => {
    describe('when default date value is provided', () => {
        it('should render DatePicker which has a label as Test DP Label and an input box with the provided default date value', () => {
            const { getByLabelText, getByTestId } = render(
                <DatePickerInput
                    id="test-dp-id"
                    name="test-dp-name"
                    label="Test DP Label"
                    className="test-dp-class-name"
                    htmlFor="test-dp-id"
                    defaultValue="12/31/2022"
                />
            );
            expect(getByLabelText('Test DP Label')).toBeTruthy();
            expect(getByTestId('date-picker-internal-input').getAttribute('value')).toBe('2022-12-31');
        });

        it('should not pass invalid dates to the DatePicker', () => {
            const { getByTestId } = render(
                <DatePickerInput
                    id="test-dp-id"
                    name="test-dp-name"
                    label="Test DP Label"
                    className="test-dp-class-name"
                    htmlFor="test-dp-id"
                    defaultValue="12/21/"
                />
            );

            expect(getByTestId('date-picker-internal-input').getAttribute('value')).toBe('');
        });
    });
    describe('when default date value is not provided', () => {
        it('should render DatePicker which has a label as Test DP Label and an empty input box', () => {
            const { getByLabelText, getByTestId } = render(
                <DatePickerInput
                    id="test-dp-id"
                    name="test-dp-name"
                    label="Test DP Label"
                    className="test-dp-class-name"
                    htmlFor="test-dp-id"
                />
            );
            expect(getByLabelText('Test DP Label')).toBeTruthy();
            expect(getByTestId('date-picker-internal-input').getAttribute('value')).toBe('');
        });
    });

    describe('when a valid date value is entered', () => {
        it('should alert value changes in the ISO-8601 instant format when a date is typed', async () => {
            const onChange = jest.fn();

            const { getByTestId, getByText } = render(
                <DatePickerInput
                    id="test-dp-id"
                    name="test-dp-name"
                    label="Test DP Label"
                    className="test-dp-class-name"
                    htmlFor="test-dp-id"
                    onChange={onChange}
                />
            );

            await userEvent.type(getByTestId('date-picker-external-input'), '12/15/2023');

            expect(onChange).toHaveBeenCalledWith('12/15/2023');
        });

        it('should alert value changes when a date is selected', async () => {
            const onChange = jest.fn();

            const { getByTestId, getByText } = render(
                <DatePickerInput
                    id="test-dp-id"
                    name="test-dp-name"
                    label="Test DP Label"
                    className="test-dp-class-name"
                    htmlFor="test-dp-id"
                    defaultValue="12/23/2023"
                    onChange={onChange}
                />
            );

            await userEvent.click(getByTestId('date-picker-button'));

            const dateButton = getByText('15');

            await userEvent.click(dateButton);

            expect(onChange).toHaveBeenCalledWith('12/15/2023');
        });
    });
});
