import { render, screen } from '@testing-library/react';
import { SexBirthDemographicView } from './SexBirthDemographicView';

const noop = () => undefined;

describe('SexBirthDemographicView', () => {
    it('should display "Sex & birth information as of" when present', () => {
        render(<SexBirthDemographicView ageResolver={noop} demographic={{ asOf: '2020-03-17' }} />);

        const actual = screen.getByRole('definition', { name: 'Sex & birth information as of' });

        expect(actual).toHaveTextContent('03/17/2020');
    });

    it('should display "Date of birth" with calculated "Current age" when present', () => {
        const ageResolver = jest.fn().mockReturnValue('calculated-age');

        render(
            <SexBirthDemographicView
                ageResolver={ageResolver}
                demographic={{ asOf: '2020-03-17', bornOn: '1999-09-09' }}
            />
        );

        const bornOn = screen.getByRole('definition', { name: 'Date of birth' });

        expect(bornOn).toHaveTextContent('09/09/1999');

        const age = screen.getByRole('definition', { name: 'Current age' });

        expect(age).toHaveTextContent('calculated-age');

        expect(ageResolver).toHaveBeenCalledWith('1999-09-09');
    });

    it('should display "Current sex" when present', () => {
        render(
            <SexBirthDemographicView
                ageResolver={noop}
                demographic={{
                    asOf: '2020-03-17',
                    current: {
                        name: 'current sex name',
                        value: 'current-sex-value'
                    }
                }}
            />
        );

        const actual = screen.getByRole('definition', { name: 'Current sex' });

        expect(actual).toHaveTextContent('current sex name');
    });

    it('should display "Unknown reason" when present', () => {
        render(
            <SexBirthDemographicView
                ageResolver={noop}
                demographic={{
                    asOf: '2020-03-17',
                    unknownReason: { name: 'unknown reason name', value: 'unknown-reason-value' }
                }}
            />
        );

        const actual = screen.getByRole('definition', { name: 'Unknown reason' });

        expect(actual).toHaveTextContent('unknown reason name');
    });

    it('should display "Transgender information" when present', () => {
        render(
            <SexBirthDemographicView
                ageResolver={noop}
                demographic={{
                    asOf: '2020-03-17',
                    transgenderInformation: {
                        name: 'transgender information name',
                        value: 'transgender-information-value'
                    }
                }}
            />
        );

        const actual = screen.getByRole('definition', { name: 'Transgender information' });

        expect(actual).toHaveTextContent('transgender information name');
    });

    it('should display "Additional gender" when present', () => {
        render(
            <SexBirthDemographicView
                ageResolver={noop}
                demographic={{
                    asOf: '2020-03-17',
                    additionalGender: 'additional gender value'
                }}
            />
        );

        const actual = screen.getByRole('definition', { name: 'Additional gender' });

        expect(actual).toHaveTextContent('additional gender value');
    });

    it('should display "Birth sex" when present', () => {
        render(
            <SexBirthDemographicView
                ageResolver={noop}
                demographic={{
                    asOf: '2020-03-17',
                    sex: {
                        name: 'birth sex name',
                        value: 'birth-sex-value'
                    }
                }}
            />
        );

        const actual = screen.getByRole('definition', { name: 'Birth sex' });

        expect(actual).toHaveTextContent('birth sex name');
    });

    it('should display "Multiple birth" when present', () => {
        render(
            <SexBirthDemographicView
                ageResolver={noop}
                demographic={{
                    asOf: '2020-03-17',
                    multiple: {
                        name: 'multiple birth name',
                        value: 'multiple-birth-value'
                    }
                }}
            />
        );

        const actual = screen.getByRole('definition', { name: 'Multiple birth' });

        expect(actual).toHaveTextContent('multiple birth name');
    });

    it('should display "Birth order" when present', () => {
        render(
            <SexBirthDemographicView
                ageResolver={noop}
                demographic={{
                    asOf: '2020-03-17',
                    order: 499
                }}
            />
        );

        const actual = screen.getByRole('definition', { name: 'Birth order' });

        expect(actual).toHaveTextContent('499');
    });

    it('should display "Birth city" when present', () => {
        render(
            <SexBirthDemographicView
                ageResolver={noop}
                demographic={{
                    asOf: '2020-03-17',
                    city: 'birth city value'
                }}
            />
        );

        const actual = screen.getByRole('definition', { name: 'Birth city' });

        expect(actual).toHaveTextContent('birth city value');
    });

    it('should display "Birth state" when present', () => {
        render(
            <SexBirthDemographicView
                ageResolver={noop}
                demographic={{
                    asOf: '2020-03-17',
                    state: {
                        name: 'birth state name',
                        value: 'birth-state-value'
                    }
                }}
            />
        );

        const actual = screen.getByRole('definition', { name: 'Birth state' });

        expect(actual).toHaveTextContent('birth state name');
    });

    it('should display "Birth county" when present', () => {
        render(
            <SexBirthDemographicView
                ageResolver={noop}
                demographic={{
                    asOf: '2020-03-17',
                    county: {
                        name: 'birth county name',
                        value: 'birth-county-value'
                    }
                }}
            />
        );

        const actual = screen.getByRole('definition', { name: 'Birth county' });

        expect(actual).toHaveTextContent('birth county name');
    });

    it('should display "Birth country" when present', () => {
        render(
            <SexBirthDemographicView
                ageResolver={noop}
                demographic={{
                    asOf: '2020-03-17',
                    country: {
                        name: 'birth country name',
                        value: 'birth-country-value'
                    }
                }}
            />
        );

        const actual = screen.getByRole('definition', { name: 'Birth country' });

        expect(actual).toHaveTextContent('birth country name');
    });
});
