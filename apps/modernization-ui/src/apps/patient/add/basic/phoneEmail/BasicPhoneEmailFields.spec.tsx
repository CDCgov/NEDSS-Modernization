import { render, waitFor } from '@testing-library/react';
import { FormProvider, useForm } from 'react-hook-form';
import { BasicPhoneEmailFields } from './BasicPhoneEmailFields';
import userEvent from '@testing-library/user-event';
import { BasicPhoneEmail } from '../entry';

const Fixture = () => {
    const form = useForm<BasicPhoneEmail>({
        mode: 'onBlur',
        defaultValues: {
            home: undefined,
            work: undefined,
            cell: undefined,
            email: undefined
        }
    });
    return (
        <FormProvider {...form}>
            <BasicPhoneEmailFields />
        </FormProvider>
    );
};

describe('PhoneEmailEntryFields', () => {
    it('should render all input fields', () => {
        const { getByLabelText } = render(<Fixture />);

        expect(getByLabelText('Home phone')).toBeInTheDocument();
        expect(getByLabelText('Work phone')).toBeInTheDocument();
        expect(getByLabelText('Work phone extension')).toBeInTheDocument();
        expect(getByLabelText('Cell phone')).toBeInTheDocument();
        expect(getByLabelText('Email')).toBeInTheDocument();
    });

    it('should validates cell phone field', async () => {
        const { getByLabelText, queryByText } = render(<Fixture />);

        const cellInput = getByLabelText('Cell phone');
        userEvent.clear(cellInput);
        userEvent.paste(cellInput, '233');
        userEvent.tab();

        const validationMessage = 'Please enter a valid Cell phone (XXX-XXX-XXXX) using only numeric characters (0-9).';

        await waitFor(() => {
            const validationError = queryByText(validationMessage);
            expect(validationError).toBeInTheDocument();
        });
    });

    test.each([
        { value: '1', valid: true },
        { value: '12', valid: true },
        { value: '123', valid: true },
        { value: '1234', valid: true }
    ])('should validate extension format for value: $value', async ({ value, valid }) => {
        const { getByLabelText, queryByText } = render(<Fixture />);
        const extensionInput = getByLabelText('Work phone extension');

        userEvent.clear(extensionInput);
        userEvent.paste(extensionInput, value);
        userEvent.tab();

        const validationMessage = 'A Extension should be 1 to 4 digits';

        await waitFor(() => {
            const validationError = queryByText(validationMessage);
            if (valid) {
                expect(validationError).not.toBeInTheDocument();
            } else {
                expect(validationError).toBeInTheDocument();
            }
        });
    });
});
