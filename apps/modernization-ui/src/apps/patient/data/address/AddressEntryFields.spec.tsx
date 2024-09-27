import { render } from '@testing-library/react';
import { FormProvider, useForm } from 'react-hook-form';
import { AddressEntry } from '../entry';
import { AddressEntryFields } from './AddressEntryFields';

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
});
