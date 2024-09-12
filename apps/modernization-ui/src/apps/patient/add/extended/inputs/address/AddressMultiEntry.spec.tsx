import { render } from '@testing-library/react';
import { internalizeDate } from 'date';
import { AddressMultiEntry } from './AddressMultiEntry';

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

const onChange = jest.fn();
const isDirty = jest.fn();

describe('RaceMultiEntry', () => {
    it('should display correct table headers', async () => {
        const { getAllByRole } = render(<AddressMultiEntry onChange={onChange} isDirty={isDirty} />);

        const headers = getAllByRole('columnheader');
        expect(headers[0]).toHaveTextContent('As of');
        expect(headers[1]).toHaveTextContent('Type');
        expect(headers[2]).toHaveTextContent('Address');
        expect(headers[3]).toHaveTextContent('City');
        expect(headers[4]).toHaveTextContent('State');
        expect(headers[5]).toHaveTextContent('Zip');
    });

    it('should display proper defaults', async () => {
        const { getByLabelText, getAllByRole, getAllByText } = render(
            <AddressMultiEntry onChange={onChange} isDirty={isDirty} />
        );

        const dateInput = getByLabelText('Address as of');
        expect(dateInput).toHaveValue(internalizeDate(new Date()));

        const race = getByLabelText('Type');
        expect(race).toHaveValue('');

        const use = getByLabelText('Use');
        expect(use).toHaveValue('');

        const street1 = getByLabelText('Street address 1');
        expect(street1).toHaveValue('');

        const street2 = getByLabelText('Street address 2');
        expect(street2).toHaveValue('');

        const city = getByLabelText('City');
        expect(city).toHaveValue('');

        const state = getByLabelText('State');
        expect(state).toHaveValue('');

        const zip = getByLabelText('Zip');
        expect(zip).toHaveValue('');

        const county = getByLabelText('County');
        expect(county).toHaveValue('');

        const censusTract = getByLabelText('Census tract');
        expect(censusTract).toHaveValue('');

        const country = getByLabelText('Country');
        expect(country).toHaveValue('');

        const comments = getByLabelText('Address comments');
        expect(comments).toHaveValue('');
    });
});
