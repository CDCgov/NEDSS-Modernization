import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { DatePickerInput } from './DatePickerInput';

describe('DatePickerInput component tests', () => {
    describe('when default date value is provided', () => {
        it('should render DatePicker which has a label as Test DP Label and an input box with the provided default date value', () => {
            const { container, getByTestId, getByText } = render(
                <DatePickerInput
                    name="test-dp-name"
                    label="Test DP Label"
                    className="test-dp-class-name"
                    defaultValue="12/31/2022"
                />
            );

            const component = container.firstChild;

            expect(component).toBeTruthy();
            expect(component).not.toHaveClass('error');

            expect(getByText('Test DP Label')).toBeInTheDocument();

            const input = getByTestId('date-picker-internal-input');
            expect(input).toHaveValue('2022-12-31');
        });

        it('should not pass invalid dates to the DatePicker', () => {
            const { getByText } = render(
                <DatePickerInput
                    name="test-dp-name"
                    label="Test DP Label"
                    className="test-dp-class-name"
                    errorMessage="test error message"
                />
            );

            expect(getByText('test error message')).toBeInTheDocument();
        });
    });
    describe('when default date value is not provided', () => {
        it('should render DatePicker which has a label as Test DP Label and an empty input box', () => {
            const { container, getByTestId } = render(
                <DatePickerInput name="test-dp-name" label="Test DP Label" className="test-dp-class-name" />
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
                    name="test-dp-name"
                    label="Test DP Label"
                    className="test-dp-class-name"
                    onChange={onChange}
                />
            );

            const input = getByTestId('date-picker-external-input');

            const user = userEvent.setup();

            await user.type(input, '02/15/2023');

            expect(onChange).toHaveBeenCalledWith('02/15/2023');
        });

        it('should alert value changes when a date is selected', async () => {
            const onChange = jest.fn();

            const { getByTestId, getByText } = render(
                <DatePickerInput
                    name="test-dp-name"
                    label="Test DP Label"
                    className="test-dp-class-name"
                    defaultValue="12/23/2023"
                    onChange={onChange}
                />
            );

            const input = getByTestId('date-picker-button');
            const user = userEvent.setup();

            await user.click(input);

            const dateButton = getByText('15');

            await user.click(dateButton);

            expect(onChange).toHaveBeenCalledWith('12/15/2023');
        });

        it('should clear the previous error state', async () => {
            const { getByTestId, container } = render(
                <DatePickerInput
                    name="test-dp-name"
                    label="Test DP Label"
                    className="test-dp-class-name"
                    defaultValue="invalid"
                />
            );

            const input = getByTestId('date-picker-external-input');
            const user = userEvent.setup();

            await user.type(input, '02/01/2022{tab}');

            const component = container.firstChild;

            expect(component).not.toHaveClass('error');
        });

        it('should add slashes automatically while user types', async () => {
            const { getByTestId } = render(
                <DatePickerInput
                    name="test-dp-name"
                    label="Test DP Label"
                    className="test-dp-class-name"
                    defaultValue="invalid"
                />
            );

            const input = getByTestId('date-picker-external-input');

            const user = userEvent.setup();

            await user.type(input, '02012022{tab}');

            expect(input).toHaveAttribute('value', '02/01/2022');
        });
    });

    describe('when an invalid date value is entered', () => {
        it('should show an error state', async () => {
            const { getByTestId, container } = render(
                <DatePickerInput name="test-dp-name" label="Test DP Label" className="test-dp-class-name" />
            );

            const input = getByTestId('date-picker-external-input');
            const user = userEvent.setup();

            await user.type(input, '12/{tab}');

            const component = container.firstChild;

            expect(component).toHaveTextContent(
                'Test DP LabelPlease enter a valid date (mm/dd/yyyy) using only numeric characters (0-9) or choose a date from the calendar by clicking on the calendar icon.'
            );
        });
    });

    describe('when required is provided', () => {
        it('should render DatePicker which has a label as Test DP Label*', () => {
            const { getByText } = render(
                <DatePickerInput
                    name="test-dp-name"
                    label="Test DP Label"
                    className="test-dp-class-name"
                    defaultValue="12/31/2022"
                    required
                />
            );
            const label = getByText('Test DP Label');
            expect(label).toHaveClass('required');
        });
    });
});
