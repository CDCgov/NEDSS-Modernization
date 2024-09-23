import { render } from '@testing-library/react';
import { PatientSexBirthCodedValue } from 'apps/patient/profile/sexBirth/usePatientSexBirthCodedValues';
import { CountiesCodedValues } from 'location';
import { SexAndBirthCard } from './SexAndBirthCard';
import { FormProvider, useForm } from 'react-hook-form';
import { ExtendedNewPatientEntry } from '../../entry';

const mockSexBirthCodedValues: PatientSexBirthCodedValue = {
    genders: [
        { name: 'Male', value: 'M' },
        { name: 'Female', value: 'F' },
        { name: 'Unknown', value: 'U' }
    ],
    preferredGenders: [{ name: 'FTM', value: 'FTM' }],
    genderUnknownReasons: [{ name: 'Did not ask', value: 'DNA' }],
    multipleBirth: [{ name: 'Yes', value: 'Y' }],
    states: {
        all: [{ name: 'Alabama', value: 'AL', abbreviation: 'AL' }],
        byValue: jest.fn(),
        byAbbreviation: jest.fn()
    },
    counties: {
        byState: jest.fn()
    },
    countries: [{ name: 'United States of America', value: 'US' }]
};

jest.mock('apps/patient/profile/sexBirth/usePatientSexBirthCodedValues', () => ({
    usePatientSexBirthCodedValues: () => mockSexBirthCodedValues
}));

const mockCountyCodedValues: CountiesCodedValues = { counties: [{ name: 'CountyA', value: 'A', group: 'G' }] };
jest.mock('location/useCountyCodedValues', () => ({
    useCountyCodedValues: () => mockCountyCodedValues
}));

const Fixture = () => {
    const form = useForm<ExtendedNewPatientEntry>();

    return (
        <FormProvider {...form}>
            <SexAndBirthCard />
        </FormProvider>
    );
};
describe('SexAndBirthCard', () => {
    it('should display Sex & birth as header', () => {
        const { getByRole } = render(<Fixture />);

        expect(getByRole('heading')).toHaveTextContent('Sex & birth');
    });
});
