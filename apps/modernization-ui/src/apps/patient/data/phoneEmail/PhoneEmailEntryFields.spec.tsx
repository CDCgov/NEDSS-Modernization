import { FormProvider, useForm } from 'react-hook-form';
import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { PhoneEmailEntry, initial } from './entry';
import { PhoneEmailEntryFields } from './PhoneEmailEntryFields';

const mockPhoneCodedValues = {
    types: [{ name: 'Phone', value: 'PH' }],
    uses: [{ name: 'Home', value: 'H' }]
};

jest.mock('./usePhoneCodedValues', () => ({
    usePhoneCodedValues: () => mockPhoneCodedValues
}));

const Fixture = () => {
    const form = useForm<PhoneEmailEntry>({
        mode: 'onBlur',
        defaultValues: initial()
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
        const user = userEvent.setup();

        const { getByLabelText, getByText } = render(<Fixture />);

        const typeInput = getByLabelText('Type');

        await user.click(typeInput);
        await user.tab();

        expect(getByText('The Type is required.')).toBeInTheDocument();
    });

    it('should require use', async () => {
        const user = userEvent.setup();

        const { getByLabelText, getByText } = render(<Fixture />);

        const useInput = getByLabelText('Use');

        await user.click(useInput);
        await user.tab();

        expect(getByText('The Use is required.')).toBeInTheDocument();
    });

    it('should require as of', async () => {
        const user = userEvent.setup();

        const { getByLabelText, findByText } = render(<Fixture />);

        const asOf = getByLabelText('Phone & email as of');

        await user.clear(asOf);
        await user.click(asOf);
        await user.tab();

        expect(await findByText('The Phone & email as of is required.')).toBeInTheDocument();
    });

    it('should be valid with as of, type, and use', async () => {
        const user = userEvent.setup();

        const { getByLabelText, queryByText } = render(<Fixture />);

        const asOf = getByLabelText('Phone & email as of');
        const type = getByLabelText('Type');
        const use = getByLabelText('Use');

        await user.type(asOf, '01/20/2020');
        await user.tab();
        await user.selectOptions(use, 'H');
        await user.tab();
        await user.selectOptions(type, 'PH');
        await user.tab();

        expect(queryByText('The Type is required')).not.toBeInTheDocument();
        expect(queryByText('The As of date is required')).not.toBeInTheDocument();
        expect(queryByText('The Use is required')).not.toBeInTheDocument();
    });

    test.each([
        { value: '1', valid: true },
        { value: '123', valid: true }
    ])('should validate country code format for value: $value', async ({ value, valid }) => {
        const user = userEvent.setup();

        const { getByLabelText, queryByText } = render(<Fixture />);
        const countryCodeInput = getByLabelText('Country code');

        await user.clear(countryCodeInput);
        await user.type(countryCodeInput, value);
        await user.tab();

        const validationError = queryByText('A country code should be 1 to 20 digits');
        if (valid) {
            expect(validationError).not.toBeInTheDocument();
        } else {
            expect(validationError).toBeInTheDocument();
        }
    });

    test.each([
        { value: '1', valid: true },
        { value: '12', valid: true },
        { value: '123', valid: true },
        { value: '1234', valid: true }
    ])('should validate extension format for value: $value', async ({ value, valid }) => {
        const user = userEvent.setup();

        const { getByLabelText, queryByText } = render(<Fixture />);
        const extensionInput = getByLabelText('Extension');

        await user.clear(extensionInput);
        await user.type(extensionInput, value);
        await user.tab();

        const validationError = queryByText('A Extension should be 1 to 20 digits');
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
