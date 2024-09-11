import { render } from '@testing-library/react';
import { AddressView } from './AddressView';
import { AddressFields } from 'apps/patient/profile/addresses/AddressEntry';

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

const entry: AddressFields = {
    asOf: '12/25/2020',
    type: 'H',
    use: 'HM',
    address1: '123 main st',
    address2: '2nd floor',
    city: 'city',
    state: '1',
    zipcode: '12345',
    county: '2',
    country: '3',
    censusTract: '22222',
    comment: 'comment'
};

describe('PhoneEntryView', () => {
    it('should render label with values', () => {
        const { getByText } = render(<AddressView entry={entry} />);
        const asOf = getByText('Address as of');
        expect(asOf.parentElement?.children[1]).toHaveTextContent('12/25/2020');

        const type = getByText('Type');
        expect(type.parentElement?.children[1]).toHaveTextContent('House');

        const use = getByText('Use');
        expect(use.parentElement?.children[1]).toHaveTextContent('Home');

        const street1 = getByText('Street address 1');
        expect(street1.parentElement?.children[1]).toHaveTextContent('123 main st');

        const street2 = getByText('Street address 2');
        expect(street2.parentElement?.children[1]).toHaveTextContent('2nd floor');

        const city = getByText('City');
        expect(city.parentElement?.children[1]).toHaveTextContent('city');

        const state = getByText('State');
        expect(state.parentElement?.children[1]).toHaveTextContent('StateName');

        const zip = getByText('Zip');
        expect(zip.parentElement?.children[1]).toHaveTextContent('12345');

        const county = getByText('County');
        expect(county.parentElement?.children[1]).toHaveTextContent('CountyName');

        const censusTract = getByText('Census tract');
        expect(censusTract.parentElement?.children[1]).toHaveTextContent('22222');

        const country = getByText('Country');
        expect(country.parentElement?.children[1]).toHaveTextContent('CountryName');

        const comments = getByText('Address comments');
        expect(comments.parentElement?.children[1]).toHaveTextContent('comment');
    });
});
