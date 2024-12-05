import { render } from '@testing-library/react';
import { AddressEntry } from 'apps/patient/data';
import { AddressView } from './AddressView';

const entry: AddressEntry = {
    asOf: '12/25/2020',
    type: { name: 'House', value: 'H' },
    use: { name: 'Home', value: 'HM' },
    address1: '123 main st',
    address2: '2nd floor',
    city: 'city',
    state: { name: 'StateName', value: '1' },
    zipcode: '12345',
    county: { name: 'CountyName', value: '2' },
    country: { name: 'CountryName', value: '3' },
    censusTract: '22222',
    comment: 'comment'
};

describe('PhoneEntryView', () => {
    it('should render label with values', () => {
        const { getByText } = render(<AddressView entry={entry} />);
        const asOf = getByText('Address as of');
        expect(asOf.parentElement).toContainElement(getByText('12/25/2020'));

        const type = getByText('Type');
        expect(type.parentElement).toContainElement(getByText('House'));

        const use = getByText('Use');
        expect(use.parentElement).toContainElement(getByText('Home'));

        const street1 = getByText('Street address 1');
        expect(street1.parentElement).toContainElement(getByText('123 main st'));

        const street2 = getByText('Street address 2');
        expect(street2.parentElement).toContainElement(getByText('2nd floor'));

        const city = getByText('City');
        expect(city.parentElement).toContainElement(getByText('city'));

        const state = getByText('State');
        expect(state.parentElement).toContainElement(getByText('StateName'));

        const zip = getByText('Zip');
        expect(zip.parentElement).toContainElement(getByText('12345'));

        const county = getByText('County');
        expect(county.parentElement).toContainElement(getByText('CountyName'));

        const censusTract = getByText('Census tract');
        expect(censusTract.parentElement).toContainElement(getByText('22222'));

        const country = getByText('Country');
        expect(country.parentElement).toContainElement(getByText('CountryName'));

        const comments = getByText('Address comments');
        expect(comments.parentElement).toContainElement(getByText('comment'));
    });
});
