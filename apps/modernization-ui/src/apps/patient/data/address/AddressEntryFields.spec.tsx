import { FormProvider, useForm } from 'react-hook-form';
import userEvent from '@testing-library/user-event';
import { render } from '@testing-library/react';
import { AddressEntry } from './entry';
import { AddressEntryFields } from './AddressEntryFields';
import { AddressCodedValues } from './useAddressCodedValues';

const mockAddressCodedValues: AddressCodedValues = {
    types: [{ name: 'House', value: 'H' }],
    uses: [{ name: 'Home', value: 'HM' }]
};

jest.mock('./useAddressCodedValues', () => ({
    useAddressCodedValues: () => mockAddressCodedValues
}));

const mockStateCodedValues = [{ name: 'StateName', value: '1' }];

const mockCountryCodedValues = [{ name: 'CountryName', value: '3' }];

const mockCountyCodedValues = [{ name: 'CountyName', value: '2' }];

jest.mock('options/location', () => ({
    useCountyOptions: () => mockCountyCodedValues,
    useCountryOptions: () => mockCountryCodedValues,
    useStateOptions: () => mockStateCodedValues
}));

const Fixture = () => {
    const form = useForm<AddressEntry>({
        mode: 'onBlur',
        defaultValues: {
            asOf: undefined,
            type: undefined,
            use: undefined,
            address1: '',
            address2: '',
            city: '',
            state: undefined,
            zipcode: '',
            county: undefined,
            country: undefined,
            censusTract: '',
            comment: ''
        }
    });
    return (
        <FormProvider {...form}>
            <AddressEntryFields />
        </FormProvider>
    );
};

describe('when entering patient address demographics', () => {
    it('should render the proper labels', () => {
        const { getByLabelText } = render(<Fixture />);

        expect(getByLabelText('Address as of')).toBeInTheDocument();
        expect(getByLabelText('Type')).toBeInTheDocument();
        expect(getByLabelText('Use')).toBeInTheDocument();
        expect(getByLabelText('Street address 1')).toBeInTheDocument();
        expect(getByLabelText('Street address 2')).toBeInTheDocument();
        expect(getByLabelText('City')).toBeInTheDocument();
        expect(getByLabelText('State')).toBeInTheDocument();
        expect(getByLabelText('Zip')).toBeInTheDocument();
        expect(getByLabelText('County')).toBeInTheDocument();
        expect(getByLabelText('Census tract')).toBeInTheDocument();
        expect(getByLabelText('Country')).toBeInTheDocument();
        expect(getByLabelText('Address comments')).toBeInTheDocument();
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

        const asOf = getByLabelText('Address as of');

        await user.click(asOf);
        await user.tab();

        expect(await findByText('The Address as of is required.')).toBeInTheDocument();
    });

    it('should be valid with as of, type, and use', async () => {
        const user = userEvent.setup();
        const { getByLabelText, queryByText } = render(<Fixture />);

        const asOf = getByLabelText('Address as of');
        const type = getByLabelText('Type');
        const use = getByLabelText('Use');
        expect(use).toBeInTheDocument();

        await user.type(asOf, '01/20/2020');
        await user.tab();
        await user.selectOptions(use, 'HM');
        await user.tab();
        await user.selectOptions(type, 'H');
        await user.tab();

        expect(queryByText('Type is required.')).not.toBeInTheDocument();
        expect(queryByText('As of date is required.')).not.toBeInTheDocument();
        expect(queryByText('Use is required.')).not.toBeInTheDocument();
    });

    test.each([
        { value: '0000.00', valid: false },
        { value: '0001.00', valid: false },
        { value: '0001.01', valid: true },
        { value: '1000.00', valid: false },
        { value: '9999.99', valid: false },
        { value: '9999.98', valid: true },
        { value: '0001', valid: true },
        { value: '9999', valid: true },
        { value: '0000', valid: false },
        { value: '9999.00', valid: false },
        { value: '0001.99', valid: false },
        { value: '1234.56', valid: true }
    ])('should validate Census Tract format for value: $value', async ({ value, valid }) => {
        const user = userEvent.setup();
        const { getByLabelText, queryByText } = render(<Fixture />);
        const censusTractInput = getByLabelText('Census tract');

        await user.clear(censusTractInput);
        await user.type(censusTractInput, value);
        await userEvent.tab();

        const validationError = queryByText(
            'The Census tract should be in numeric XXXX or XXXX.xx format where XXXX is the basic tract and xx is the suffix. XXXX ranges from 0001 to 9999. The suffix is limited to a range between .01 and .98.'
        );
        if (valid) {
            expect(validationError).not.toBeInTheDocument();
        } else {
            expect(validationError).toBeInTheDocument();
        }
    });

    test.each([
        { value: '12345', valid: true },
        { value: '1234', valid: false },
        { value: '123456', valid: false },
        { value: '12345-6789', valid: true },
        { value: '12345 6789', valid: true },
        { value: '12345-678', valid: false },
        { value: '12345 678', valid: false },
        { value: '1234-5678', valid: false }
    ])('should validate ZIP code format for value: $value', async ({ value, valid }) => {
        const user = userEvent.setup();
        const { getByLabelText, queryByText } = render(<Fixture />);
        const zipCodeInput = getByLabelText('Zip');

        await user.clear(zipCodeInput);
        await user.type(zipCodeInput, value);
        await user.tab();

        const validationError = queryByText(
            'Please enter a valid Zip (XXXXX or XXXXX-XXXX ) using only numeric characters (0-9).'
        );
        if (valid) {
            expect(validationError).not.toBeInTheDocument();
        } else {
            expect(validationError).toBeInTheDocument();
        }
    });
});
