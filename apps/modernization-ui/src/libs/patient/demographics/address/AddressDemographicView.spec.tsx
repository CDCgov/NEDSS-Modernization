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
    state: asSelectable('state-value', 'state-name'),
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
    it('should display type', () => {
        render(<AddressDemographicView entry={entry} />);

        const actual = screen.getByRole('definition', { name: 'Type' });

        expect(actual).toHaveTextContent('Home');
    });
    it('should display use', () => {
        render(<AddressDemographicView entry={entry} />);

        const actual = screen.getByRole('definition', { name: 'Use' });

        expect(actual).toHaveTextContent('Primary address');
    });
    it('should display address 1', () => {
        render(<AddressDemographicView entry={entry} />);

        const actual = screen.getByRole('definition', { name: 'Street address 1' });

        expect(actual).toHaveTextContent('address 1 value');
    });
    it('should display address 2', () => {
        render(<AddressDemographicView entry={entry} />);

        const actual = screen.getByRole('definition', { name: 'Street address 2' });

        expect(actual).toHaveTextContent('address 2 value');
    });
    it('should display city', () => {
        render(<AddressDemographicView entry={entry} />);

        const actual = screen.getByRole('definition', { name: 'City' });

        expect(actual).toHaveTextContent('new york city');
    });
    it('should display county', () => {
        render(<AddressDemographicView entry={entry} />);

        const actual = screen.getByRole('definition', { name: 'County' });

        expect(actual).toHaveTextContent('Ark county');
    });
    it('should display country', () => {
        render(<AddressDemographicView entry={entry} />);

        const actual = screen.getByRole('definition', { name: 'Country' });

        expect(actual).toHaveTextContent('United states');
    });
    it('should display census tract', () => {
        render(<AddressDemographicView entry={entry} />);

        const actual = screen.getByRole('definition', { name: 'Census tract' });

        expect(actual).toHaveTextContent('census tract value');
    });
    it('should display zipcode', () => {
        render(<AddressDemographicView entry={entry} />);

        const actual = screen.getByRole('definition', { name: 'Zip' });

        expect(actual).toHaveTextContent('12345');
    });
});
