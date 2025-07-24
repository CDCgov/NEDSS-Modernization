import { render, screen } from '@testing-library/react';
import { MortalityDemographicView } from './MortalityDemographicView';

const noop = () => undefined;

describe('MortalityDemographicView', () => {
    it('should display "As of" when present', () => {
        render(<MortalityDemographicView demographic={{ asOf: '2020-03-17' }} />);

        const actual = screen.getByRole('definition', { name: 'As of' });

        expect(actual).toHaveTextContent('03/17/2020');
    });

    it('should display "Is the patient deceased" when present', () => {
        render(
            <MortalityDemographicView
                demographic={{
                    asOf: '2020-03-17',
                    deceased: {
                        name: 'deceased name',
                        value: 'deceased-value'
                    }
                }}
            />
        );

        const actual = screen.getByRole('definition', { name: 'Is the patient deceased?' });

        expect(actual).toHaveTextContent('deceased name');
    });

    it('should display "Date of death" when present', () => {
        render(<MortalityDemographicView demographic={{ asOf: '2020-03-17', deceasedOn: '1999-09-09' }} />);

        const bornOn = screen.getByRole('definition', { name: 'Date of death' });

        expect(bornOn).toHaveTextContent('09/09/1999');
    });

    it('should display "City of death" when present', () => {
        render(
            <MortalityDemographicView
                demographic={{
                    asOf: '2020-03-17',
                    city: 'death city value'
                }}
            />
        );

        const actual = screen.getByRole('definition', { name: 'City of death' });

        expect(actual).toHaveTextContent('death city value');
    });

    it('should display "State of death" when present', () => {
        render(
            <MortalityDemographicView
                demographic={{
                    asOf: '2020-03-17',
                    state: {
                        name: 'death state name',
                        value: 'death-state-value'
                    }
                }}
            />
        );

        const actual = screen.getByRole('definition', { name: 'State of death' });

        expect(actual).toHaveTextContent('death state name');
    });

    it('should display "County of deathy" when present', () => {
        render(
            <MortalityDemographicView
                demographic={{
                    asOf: '2020-03-17',
                    county: {
                        name: 'death county name',
                        value: 'death-county-value'
                    }
                }}
            />
        );

        const actual = screen.getByRole('definition', { name: 'County of death' });

        expect(actual).toHaveTextContent('death county name');
    });

    it('should display "Country of death" when present', () => {
        render(
            <MortalityDemographicView
                demographic={{
                    asOf: '2020-03-17',
                    country: {
                        name: 'death country name',
                        value: 'death-country-value'
                    }
                }}
            />
        );

        const actual = screen.getByRole('definition', { name: 'Country of death' });

        expect(actual).toHaveTextContent('death country name');
    });
});
