import { FormProvider, useForm } from 'react-hook-form';
import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { BasicPhoneEmailFields } from './BasicPhoneEmailFields';
import { BasicPhoneEmail } from '../entry';

const Fixture = (props: { sizing?: 'small' | 'medium' | 'large' }) => {
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
            <BasicPhoneEmailFields sizing={props.sizing} />
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

    it('should render all input fields with small size when sizing is set to small', () => {
        const { getByText } = render(<Fixture sizing="small" />);

        expect(getByText('Home phone').parentElement?.parentElement).toHaveClass('small');
        expect(getByText('Work phone').parentElement?.parentElement).toHaveClass('small');
        expect(getByText('Work phone extension').parentElement?.parentElement).toHaveClass('small');
        expect(getByText('Cell phone').parentElement?.parentElement).toHaveClass('small');
        expect(getByText('Email').parentElement?.parentElement).toHaveClass('small');
    });

    it('should validates cell phone field', async () => {
        const user = userEvent.setup();
        const { getByLabelText, queryByText } = render(<Fixture />);

        const cellInput = getByLabelText('Cell phone');
        await user.clear(cellInput);
        await user.type(cellInput, '233');
        await user.tab();

        const validationError = queryByText(
            'Please enter a valid Cell phone (XXX-XXX-XXXX) using only numeric characters (0-9).'
        );
        expect(validationError).toBeInTheDocument();
    });

    test.each([
        { value: '1', valid: true },
        { value: '12', valid: true },
        { value: '123', valid: true },
        { value: '1234', valid: true }
    ])('should validate extension format for value: $value', async ({ value, valid }) => {
        const user = userEvent.setup();
        const { getByLabelText, queryByText } = render(<Fixture />);
        const extensionInput = getByLabelText('Work phone extension');

        await user.clear(extensionInput);
        await user.type(extensionInput, value);
        await user.tab();

        const validationError = queryByText('A Extension should be 1 to 4 digits');
        if (valid) {
            expect(validationError).not.toBeInTheDocument();
        } else {
            expect(validationError).toBeInTheDocument();
        }
    });

    it('should verify email field', async () => {
        const user = userEvent.setup();
        const { getByLabelText, getByText } = render(<Fixture />);
        const email = getByLabelText('Email');

        await user.type(email, 'invalid-email');
        await user.tab();

        const validationError = getByText('Please enter Email as an email address (example: youremail@website.com).');
        expect(validationError).toBeInTheDocument();
    });
});
