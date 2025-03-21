import { FormProvider, useForm } from 'react-hook-form';
import userEvent from '@testing-library/user-event';
import { render, waitFor, act } from '@testing-library/react';
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

const mockLocationCodedValues = {
    states: {
        all: [{ name: 'StateName', value: '1' }]
    },
    counties: {
        byState: (state: string) => [{ name: 'CountyName', value: '2' }]
    },
    countries: [{ name: 'CountryName', value: '3' }]
};

jest.mock('location/useLocationCodedValues', () => ({
    useLocationCodedValues: () => mockLocationCodedValues
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

        const asOf = getByLabelText('Address as of');
        act(() => {
            userEvent.click(asOf);
            userEvent.tab();
        });

        expect(await findByText('The Address as of is required.')).toBeInTheDocument();
    });

    it('should be valid with as of, type, and use', async () => {
        const { getByLabelText, queryByText } = render(<Fixture />);

        const asOf = getByLabelText('Address as of');
        const type = getByLabelText('Type');
        const use = getByLabelText('Use');
        await waitFor(() => expect(use).toBeInTheDocument());

        act(() => {
            userEvent.paste(asOf, '01/20/2020');
            userEvent.tab();
            userEvent.selectOptions(use, 'HM');
            userEvent.tab();
            userEvent.selectOptions(type, 'H');
            userEvent.tab();
        });

        await waitFor(() => {
            expect(queryByText('Type is required.')).not.toBeInTheDocument();
            expect(queryByText('As of date is required.')).not.toBeInTheDocument();
            expect(queryByText('Use is required.')).not.toBeInTheDocument();
        });
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
        const { getByLabelText, queryByText } = render(<Fixture />);
        const censusTractInput = getByLabelText('Census tract');

        userEvent.clear(censusTractInput);
        userEvent.paste(censusTractInput, value);
        userEvent.tab();

        const validationMessage =
            'The Census tract should be in numeric XXXX or XXXX.xx format where XXXX is the basic tract and xx is the suffix. XXXX ranges from 0001 to 9999. The suffix is limited to a range between .01 and .98.';

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
        { value: '12345', valid: true },
        { value: '1234', valid: false },
        { value: '123456', valid: false },
        { value: '12345-6789', valid: true },
        { value: '12345 6789', valid: true },
        { value: '12345-678', valid: false },
        { value: '12345 678', valid: false },
        { value: '1234-5678', valid: false }
    ])('should validate ZIP code format for value: $value', async ({ value, valid }) => {
        const { getByLabelText, queryByText } = render(<Fixture />);
        const zipCodeInput = getByLabelText('Zip');

        userEvent.clear(zipCodeInput);
        userEvent.paste(zipCodeInput, value);
        userEvent.tab();

        const validationMessage =
            'Please enter a valid Zip (XXXXX or XXXXX-XXXX ) using only numeric characters (0-9).';

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
