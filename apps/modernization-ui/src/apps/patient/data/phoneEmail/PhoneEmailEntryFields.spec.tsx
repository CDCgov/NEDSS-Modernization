import { render, waitFor, screen, act } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { FormProvider, useForm } from 'react-hook-form';
import { PhoneEmailEntry } from './entry';
import { PhoneEmailEntryFields } from './PhoneEmailEntryFields';

const mockPatientPhoneCodedValues = {
    types: [{ name: 'Phone', value: 'PH' }],
    uses: [{ name: 'Home', value: 'H' }]
};

jest.mock('apps/patient/profile/phoneEmail/usePatientPhoneCodedValues', () => ({
    usePatientPhoneCodedValues: () => mockPatientPhoneCodedValues
}));

const Fixture = () => {
    const form = useForm<PhoneEmailEntry>({
        mode: 'onBlur',
        defaultValues: {
            asOf: undefined,
            type: undefined,
            use: undefined,
            countryCode: '',
            phoneNumber: '',
            extension: '',
            email: '',
            url: '',
            comment: ''
        }
    });
    return (
        <FormProvider {...form}>
            <PhoneEmailEntryFields />
        </FormProvider>
    );
};

describe('when entering patient phone & email demographics', () => {
    it('should render the proper labels', () => {
        const { getByLabelText } = render(<Fixture />);

        expect(getByLabelText('Phone & email as of')).toBeInTheDocument();
        expect(getByLabelText('Type')).toBeInTheDocument();
        expect(getByLabelText('Use')).toBeInTheDocument();
        expect(getByLabelText('Country code')).toBeInTheDocument();
        expect(getByLabelText('Phone number')).toBeInTheDocument();
        expect(getByLabelText('Extension')).toBeInTheDocument();
        expect(getByLabelText('Email')).toBeInTheDocument();
        expect(getByLabelText('Phone & email comments')).toBeInTheDocument();
    });

    it('should require type', async () => {
        const { getByLabelText, getByText } = render(<Fixture />);

        const typeInput = getByLabelText('Type');
        act(() => {
            userEvent.click(typeInput);
            userEvent.tab();
        });
        await waitFor(() => {
            expect(getByText('The Type is required.')).toBeInTheDocument();
        });
    });

    it('should require use', async () => {
        const { getByLabelText, getByText } = render(<Fixture />);

        const useInput = getByLabelText('Use');
        act(() => {
            userEvent.click(useInput);
            userEvent.tab();
        });
        await waitFor(() => {
            expect(getByText('The Use is required.')).toBeInTheDocument();
        });
    });

    it('should require as of', async () => {
        const { getByLabelText, findByText } = render(<Fixture />);

        const asOf = getByLabelText('Phone & email as of');
        act(() => {
            userEvent.click(asOf);
            userEvent.tab();
        });

        expect(await findByText('The Phone & email as of is required.')).toBeInTheDocument();
    });

    it('should be valid with as of, type, and use', async () => {
        const { getByLabelText, queryByText } = render(<Fixture />);

        const asOf = getByLabelText('Phone & email as of');
        const type = getByLabelText('Type');
        const use = getByLabelText('Use');
        await screen.findByText('Use');
        act(() => {
            userEvent.paste(asOf, '01/20/2020');
            userEvent.tab();
            userEvent.selectOptions(use, 'H');
            userEvent.tab();
            userEvent.selectOptions(type, 'PH');
            userEvent.tab();
        });

        await waitFor(() => {
            expect(queryByText('The Type is required')).not.toBeInTheDocument();
            expect(queryByText('The As of date is required')).not.toBeInTheDocument();
            expect(queryByText('The Use is required')).not.toBeInTheDocument();
        });
    });

    test.each([
        { value: '1', valid: true },
        { value: '123', valid: true }
    ])('should validate country code format for value: $value', async ({ value, valid }) => {
        const { getByLabelText, queryByText } = render(<Fixture />);
        const countryCodeInput = getByLabelText('Country code');

        userEvent.clear(countryCodeInput);
        userEvent.paste(countryCodeInput, value);
        userEvent.tab();

        const validationMessage = 'A country code should be 1 to 20 digits!';

        await waitFor(() => {
            const validationError = queryByText(validationMessage);
            if (valid) {
                expect(validationError).not.toBeInTheDocument();
            } else {
                expect(validationError).toBeInTheDocument();
            }
        });
    });

    test.each([
        { value: '1', valid: true },
        { value: '12', valid: true },
        { value: '123', valid: true },
        { value: '1234', valid: true }
    ])('should validate extension format for value: $value', async ({ value, valid }) => {
        const { getByLabelText, queryByText } = render(<Fixture />);
        const extensionInput = getByLabelText('Extension');

        userEvent.clear(extensionInput);
        userEvent.paste(extensionInput, value);
        userEvent.tab();

        const validationMessage = 'An Extension should be 1 to 20 digits.';

        await waitFor(() => {
            const validationError = queryByText(validationMessage);
            if (valid) {
                expect(validationError).not.toBeInTheDocument();
            } else {
                expect(validationError).toBeInTheDocument();
            }
        });
    });

    it('should verify email field', async () => {
        const { getByLabelText, getByText } = render(<Fixture />);
        const email = getByLabelText('Email');

        userEvent.paste(email, 'invalid-email');
        userEvent.tab();

        await waitFor(() => {
            const validationError = getByText(
                'Please enter Email as an email address (example: youremail@website.com).'
            );
            expect(validationError).toBeInTheDocument();
        });
    });
});
