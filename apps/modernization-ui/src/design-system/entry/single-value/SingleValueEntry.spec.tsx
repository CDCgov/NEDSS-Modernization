import { render, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { Input } from 'components/FormInputs/Input';
import { Control, Controller } from 'react-hook-form';
import { SingleValueEntry } from './SingleValueEntry';

type TestType = {
    testInput: string;
};

const onChange = jest.fn();
const isDirty = jest.fn();

const defaultValues: TestType = {
    testInput: 'default value'
};

const renderForm = (control: Control<TestType>) => {
    return (
        <Controller
            name="testInput"
            control={control}
            render={({ field: { onBlur, onChange, value, name } }) => (
                <Input
                    flexBox
                    onBlur={onBlur}
                    onChange={onChange}
                    defaultValue={value}
                    type="text"
                    label="Test Input"
                    id={name}
                />
            )}
        />
    );
};

const UnderTest = ({ isFooter = true }: { isFooter?: boolean }) => {
    return (
        <SingleValueEntry<TestType>
            title="Test"
            defaultValues={defaultValues}
            onChange={onChange}
            isDirty={isDirty}
            formRenderer={renderForm}
            isFooter={isFooter}
        />
    );
};

describe('SingleValueEntry', () => {
    it('should display provided title', async () => {
        const { findByText } = render(<UnderTest />);
        const heading = await findByText('Test');
        expect(heading).toBeInTheDocument();
        expect(heading.className).toBe('heading two');
    });

    it('should display provided form', async () => {
        const { findByLabelText } = render(<UnderTest />);
        const input = await findByLabelText('Test Input');
        expect(input).toBeInTheDocument();
    });

    it('should display default value', async () => {
        const { findByLabelText } = render(<UnderTest />);
        const input = await findByLabelText('Test Input');
        expect(input).toHaveValue('default value');
    });

    it('should display save and reset buttons when isFooter is true', async () => {
        const { findByText } = render(<UnderTest />);
        const saveButton = await findByText('Save test');
        const resetButton = await findByText('Reset');
        expect(saveButton).toBeInTheDocument();
        expect(resetButton).toBeInTheDocument();
    });

    it('should not display buttons when isFooter is false', async () => {
        const { queryByText } = render(<UnderTest isFooter={false} />);
        const saveButton = queryByText('Save test');
        const resetButton = queryByText('Reset');
        expect(saveButton).not.toBeInTheDocument();
        expect(resetButton).not.toBeInTheDocument();
    });

    it('should trigger onChange when data is submitted', async () => {
        const { findByLabelText, getByText } = render(<UnderTest />);
        const input = await findByLabelText('Test Input');
        const saveButton = getByText('Save test');

        userEvent.clear(input);
        userEvent.type(input, 'new value');
        userEvent.click(saveButton);

        await waitFor(() => {
            expect(onChange).toHaveBeenCalledWith({ testInput: 'new value' });
        });
    });

    it('should trigger isDirty when form is changed', async () => {
        const { findByLabelText } = render(<UnderTest />);
        const input = await findByLabelText('Test Input');

        userEvent.clear(input);
        userEvent.type(input, 'new value');

        await waitFor(() => {
            expect(isDirty).toHaveBeenCalledWith(true);
        });
    });

    it('should reset form when reset button is clicked', async () => {
        const { findByLabelText, getByText } = render(<UnderTest />);
        const input = await findByLabelText('Test Input');
        const resetButton = getByText('Reset');

        userEvent.clear(input);
        userEvent.type(input, 'new value');
        userEvent.click(resetButton);

        await waitFor(() => {
            expect(input).toHaveValue('default value');
        });
    });

    it('should display error messages when provided', async () => {
        const { findByText, getByText } = render(
            <SingleValueEntry<TestType>
                title="Test"
                defaultValues={defaultValues}
                onChange={onChange}
                isDirty={isDirty}
                formRenderer={renderForm}
                errors={['Error 1', 'Error 2']}
            />
        );

        const errorHeading = await findByText('Please fix the following errors:');
        expect(errorHeading).toBeInTheDocument();

        const error1 = getByText('Error 1');
        const error2 = getByText('Error 2');
        expect(error1).toBeInTheDocument();
        expect(error2).toBeInTheDocument();
    });
});
