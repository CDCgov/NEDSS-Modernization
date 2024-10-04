import { render, waitFor, screen } from '@testing-library/react';
import { act } from 'react-dom/test-utils';
import { FormProvider, useForm } from 'react-hook-form';
import { PhoneEmailEntry } from '../entry';
import { PhoneEmailEntryFields } from './PhoneEmailEntryFields';
import userEvent from '@testing-library/user-event';

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

describe('PhoneEmailEntryFields', () => {
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
            expect(getByText('Type is required.')).toBeInTheDocument();
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
            expect(getByText('Use is required.')).toBeInTheDocument();
        });
    });

    it('should require as of', async () => {
        const { getByLabelText, getByText } = render(<Fixture />);

        const asOf = getByLabelText('Phone & email as of');
        act(() => {
            userEvent.click(asOf);
            userEvent.tab();
        });
        await waitFor(() => {
            expect(getByText('As of date is required.')).toBeInTheDocument();
        });
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
            expect(queryByText('Type is required.')).not.toBeInTheDocument();
            expect(queryByText('As of date is required.')).not.toBeInTheDocument();
            expect(queryByText('Use is required.')).not.toBeInTheDocument();
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

        const validationMessage = 'A country code should be 1 to 3 digits';

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
    ])('should validate extensionformat for value: $value', async ({ value, valid }) => {
        const { getByLabelText, queryByText } = render(<Fixture />);
        const extensionInput = getByLabelText('Extension');

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
