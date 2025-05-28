import { render, within } from '@testing-library/react';
import { PatientAddress } from 'apps/deduplication/api/model/PatientData';
import { AddressDetails } from './AddressDetails';

const defaultAddress: PatientAddress = {
    id: '123',
    asOf: '2022-06-07T14:24:44.970',
    type: 'House',
    use: 'Home',
    address: '123 Main st',
    address2: 'Building 2',
    city: 'Atlanta',
    state: 'Georgia',
    zipcode: '12345',
    county: 'Fulton county',
    censusTract: '0224',
    country: 'United States',
    comments: 'Comment'
};

const Fixture = ({ address = defaultAddress }: { address?: PatientAddress }) => {
    return <AddressDetails address={address} />;
};

describe('AddressDetails', () => {
    it('should render the proper labels', () => {
        const { getByText } = render(<Fixture />);

        expect(getByText('As of date')).toBeInTheDocument();
        expect(getByText('Type')).toBeInTheDocument();
        expect(getByText('Use')).toBeInTheDocument();
        expect(getByText('Street address 1')).toBeInTheDocument();
        expect(getByText('Street address 2')).toBeInTheDocument();
        expect(getByText('City')).toBeInTheDocument();
        expect(getByText('State')).toBeInTheDocument();
        expect(getByText('Zip')).toBeInTheDocument();
        expect(getByText('County')).toBeInTheDocument();
        expect(getByText('Census tract')).toBeInTheDocument();
        expect(getByText('Country')).toBeInTheDocument();
        expect(getByText('Address comments')).toBeInTheDocument();
    });

    it('should render "---" for missing optional fields', () => {
        const sparseAddress: PatientAddress = {
            id: '1',
            asOf: '2022-06-07T14:24:44.970',
            type: 'Type',
            use: 'Use'
        };
        const { getByText } = render(<Fixture address={sparseAddress} />);
        expect(within(getByText('Street address 1').parentElement!).getByText('---')).toBeInTheDocument();
        expect(within(getByText('Street address 2').parentElement!).getByText('---')).toBeInTheDocument();
        expect(within(getByText('City').parentElement!).getByText('---')).toBeInTheDocument();
        expect(within(getByText('State').parentElement!).getByText('---')).toBeInTheDocument();
        expect(within(getByText('Zip').parentElement!).getByText('---')).toBeInTheDocument();
        expect(within(getByText('County').parentElement!).getByText('---')).toBeInTheDocument();
        expect(within(getByText('Census tract').parentElement!).getByText('---')).toBeInTheDocument();
        expect(within(getByText('Country').parentElement!).getByText('---')).toBeInTheDocument();
        expect(within(getByText('Address comments').parentElement!).getByText('---')).toBeInTheDocument();
    });

    it('should display as of date in proper format', () => {
        const { getByText } = render(<Fixture />);

        expect(getByText('06/07/2022')).toBeInTheDocument();
    });
});
