import { asSelectable } from 'options';
import { AddressDemographic } from './address';
import { render, screen } from '@testing-library/react';
import { AddressDemographicView } from './AddressDemographicView';

const entry: AddressDemographic = {
    asOf: '2000-01-01',
    type: asSelectable('H', 'Home'),
    use: asSelectable('PH', 'Primary address'),
    address1: 'address 1 value',
    address2: 'address 2 value',
    city: 'new york city',
    county: asSelectable('AK', 'Ark county'),
    country: asSelectable('US', 'United states'),
    censusTract: 'census tract value',
    zipcode: '12345'
};

describe('AddressDemographicView', () => {
    it('should display as of', () => {
        render(<AddressDemographicView entry={entry} />);

        const actual = screen.getByRole('definition', { name: 'Address as of' });

        expect(actual).toHaveTextContent('01/01/2000');
    });
});
