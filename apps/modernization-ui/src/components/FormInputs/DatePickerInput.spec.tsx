import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { DatePickerInput } from './DatePickerInput';

describe('DatePickerInput component tests', () => {
    describe('when default date value is provided', () => {
        it('should render DatePicker which has a label as Test DP Label and an input box with the provided default date value', () => {
            const { container, getByTestId } = render(
                <DatePickerInput
                    id="test-dp-id"
                    name="test-dp-name"
                    label="Test DP Label"
                    className="test-dp-class-name"
                    htmlFor="test-dp-id"
                    defaultValue="12/31/2022"
                />
            );

            const component = container.firstChild;

            expect(component).toBeTruthy();
            expect(component).not.toHaveClass('error');

            const label = getByTestId('label');
            expect(label).toHaveTextContent('Test DP Label');

            const input = getByTestId('date-picker-internal-input');
            expect(input).toHaveValue('2022-12-31');
        });

        it('should not pass invalid dates to the DatePicker', () => {
            const { container, getByTestId } = render(
                <DatePickerInput
                    id="test-dp-id"
                    name="test-dp-name"
                    label="Test DP Label"
                    className="test-dp-class-name"
                    htmlFor="test-dp-id"
                    defaultValue="12/21/1"
                />
            );

            const component = container.firstChild;

            expect(component).toHaveClass('error');

            const input = getByTestId('date-picker-internal-input');
            expect(input).toHaveValue('');
        });
    });
    describe('when default date value is not provided', () => {
        it('should render DatePicker which has a label as Test DP Label and an empty input box', () => {
            const { container, getByTestId } = render(
                <DatePickerInput
                    id="test-dp-id"
                    name="test-dp-name"
                    label="Test DP Label"
                    className="test-dp-class-name"
                    htmlFor="test-dp-id"
                />
            );
            const component = container.firstChild;

            expect(component).not.toHaveClass('error');

            const input = getByTestId('date-picker-internal-input');
            expect(input).toHaveValue('');
        });
    });

    describe('when a valid date value is entered', () => {
        it('should alert value changes in the ISO-8601 instant format when a date is typed', async () => {
            const onChange = jest.fn();

            const { getByTestId } = render(
                <DatePickerInput
                    id="test-dp-id"
                    name="test-dp-name"
                    label="Test DP Label"
                    className="test-dp-class-name"
                    htmlFor="test-dp-id"
                    onChange={onChange}
                />
            );

            const input = getByTestId('date-picker-external-input');
            await userEvent.type(input, '2/15/2023');

            expect(onChange).toHaveBeenCalledWith('2/15/2023');
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

            const input = getByTestId('date-picker-button');
            await userEvent.click(input);

            const dateButton = getByText('15');

            await userEvent.click(dateButton);

            expect(onChange).toHaveBeenCalledWith('12/15/2023');
        });

        it('should clear the previous error state', async () => {
            const { getByTestId, container } = render(
                <DatePickerInput
                    id="test-dp-id"
                    name="test-dp-name"
                    label="Test DP Label"
                    className="test-dp-class-name"
                    htmlFor="test-dp-id"
                    defaultValue="invalid"
                />
            );

            const input = getByTestId('date-picker-external-input');
            await userEvent.type(input, '2/1/2022');

            const component = container.firstChild;

            expect(component).not.toHaveClass('error');
        });
    });

    describe('when an invalid date value is entered', () => {
        it('should show an error state', async () => {
            const { getByTestId, container } = render(
                <DatePickerInput
                    id="test-dp-id"
                    name="test-dp-name"
                    label="Test DP Label"
                    className="test-dp-class-name"
                    htmlFor="test-dp-id"
                />
            );

            const input = getByTestId('date-picker-external-input');
            await userEvent.type(input, '12/');

            const component = container.firstChild;

            expect(component).toHaveClass('error');
        });
    });
});
