import { FormProvider, useForm } from 'react-hook-form';
import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { PhoneEmailEntry, initial } from './entry';
import { PhoneEmailEntryFields } from './PhoneEmailEntryFields';

const mockPhoneCodedValues = {
    types: [{ name: 'Phone', value: 'PH' }],
    uses: [{ name: 'Home', value: 'H' }]
};

vi.mock('./usePhoneCodedValues', () => ({
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

        await user.click(typeInput).then(() => user.tab());

        expect(getByText('The Type is required.')).toBeInTheDocument();
    });

    it('should require use', async () => {
        const user = userEvent.setup();

        const { getByLabelText, getByText } = render(<Fixture />);

        const useInput = getByLabelText('Use');

        await user.click(useInput).then(() => user.tab());

        expect(getByText('The Use is required.')).toBeInTheDocument();
    });

    it('should require as of', async () => {
        const user = userEvent.setup();

        const { getByLabelText, findByText } = render(<Fixture />);

        const asOf = getByLabelText('Phone & email as of');

        await user
            .clear(asOf)
            .then(() => user.click(asOf))
            .then(() => user.tab());

        expect(await findByText('The Phone & email as of is required.')).toBeInTheDocument();
    });

    it('should be valid with as of, type, and use', async () => {
        const user = userEvent.setup();

        const { getByLabelText, queryByText } = render(<Fixture />);

        const asOf = getByLabelText('Phone & email as of');
        const type = getByLabelText('Type');
        const use = getByLabelText('Use');

        await user
            .type(asOf, '01/20/2020')
            .then(() => user.selectOptions(use, 'H'))
            .then(() => user.selectOptions(type, 'PH'))
            .then(() => user.tab());

        expect(queryByText('The Type is required')).not.toBeInTheDocument();
        expect(queryByText('The As of date is required')).not.toBeInTheDocument();
        expect(queryByText('The Use is required')).not.toBeInTheDocument();
    });

    test.each([
        { value: '0' },
        { value: '01' },
        { value: '0123' },
        { value: '01234' },
        { value: '012345' },
        { value: '0123456' },
        { value: '01234567' },
        { value: '012345678' },
        { value: '012345679' },
        { value: '0123456790' },
        { value: '01234567901' },
        { value: '0123456790123' },
        { value: '01234567901234' },
        { value: '012345679012345' },
        { value: '0123456790123456' },
        { value: '01234567901234567' },
        { value: '012345679012345678' },
        { value: '012345679012345679' },
        { value: '0123456790123456790' }
    ])('should allow country codes up to 20 characters: $value', async ({ value }) => {
        const { getByLabelText, queryByText } = render(<Fixture />);
        const user = userEvent.setup();
        const country = getByLabelText('Country code');

        await user.type(country, `${value}{tab}`);

        expect(queryByText('A country code should be 1 to 20 digits')).not.toBeInTheDocument();
    });

    it('should not allow country codes over 20 characters', async () => {
        const { getByLabelText, getByText } = render(<Fixture />);
        const user = userEvent.setup();
        const country = getByLabelText('Country code');

        await user.type(country, '012345679012345679012{tab}');

        expect(country).toHaveValue('01234567901234567901');
    });

    test.each([
        { value: '0' },
        { value: '01' },
        { value: '0123' },
        { value: '01234' },
        { value: '012345' },
        { value: '0123456' },
        { value: '01234567' },
        { value: '012345678' },
        { value: '012345679' },
        { value: '0123456790' },
        { value: '01234567901' },
        { value: '0123456790123' },
        { value: '01234567901234' },
        { value: '012345679012345' },
        { value: '0123456790123456' },
        { value: '01234567901234567' },
        { value: '012345679012345678' },
        { value: '012345679012345679' },
        { value: '0123456790123456790' }
    ])('should allow extensions up to 20 characters: $value', async ({ value }) => {
        const { getByLabelText, queryByText } = render(<Fixture />);
        const user = userEvent.setup();
        const extension = getByLabelText('Extension');

        await user.type(extension, `${value}{tab}`);

        expect(queryByText('A Extension should be 1 to 20 digits')).not.toBeInTheDocument();
    });

    it('should not allow extensions over 20 characters', async () => {
        const { getByLabelText, getByText } = render(<Fixture />);
        const user = userEvent.setup();
        const extension = getByLabelText('Extension');

        await user.type(extension, '012345679012345679012{tab}');

        expect(extension).toHaveValue('01234567901234567901');
    });

    it('should verify email field', async () => {
        const { getByLabelText, getByText } = render(<Fixture />);
        const user = userEvent.setup();
        const email = getByLabelText('Email');

        await user.type(email, 'invalid-email{tab}');

        expect(
            getByText('Please enter Email as an email address (example: youremail@website.com).')
        ).toBeInTheDocument();
    });
});
