import { render, waitFor, screen } from '@testing-library/react';
import { act } from 'react-dom/test-utils';
import { FormProvider, useForm } from 'react-hook-form';
import { AddressEntry } from '../entry';
import { AddressEntryFields } from './AddressEntryFields';
import userEvent from '@testing-library/user-event';

const mockPatientAddressCodedValues = {
    types: [{ name: 'House', value: 'H' }],
    uses: [{ name: 'Home', value: 'HM' }]
};

jest.mock('apps/patient/profile/addresses/usePatientAddressCodedValues', () => ({
    usePatientAddressCodedValues: () => mockPatientAddressCodedValues
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

describe('AddressEntryFields', () => {
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

        const asOf = getByLabelText('Address as of');
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

        const asOf = getByLabelText('Address as of');
        const type = getByLabelText('Type');
        const use = getByLabelText('Use');
        await screen.findByText('Use');

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

    it('should invalidate Census Tract format', async () => {
        const { getByLabelText, getByText } = render(<Fixture />);

        const censusTractInput = getByLabelText('Census tract');

        act(() => {
            userEvent.clear(censusTractInput);
            userEvent.type(censusTractInput, '12345');
            userEvent.tab();
        });
        await waitFor(() => {
            expect(
                getByText(
                    'Census Tract should be in numeric XXXX or XXXX.xx format where XXXX is the basic tract and xx is the suffix. XXXX ranges from 0001 to 9999. The suffix is limited to a range between .01 and .98.'
                )
            ).toBeInTheDocument();
        });
    });
});
