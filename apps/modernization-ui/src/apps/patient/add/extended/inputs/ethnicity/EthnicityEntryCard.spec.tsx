import { render } from '@testing-library/react';
import { PatientEthnicityCodedValue } from 'apps/patient/profile/ethnicity';
import { FormProvider, useForm } from 'react-hook-form';
import { EthnicityEntryCard } from './EthnicityEntryCard';

const mockEthnicityValues: PatientEthnicityCodedValue = {
    ethnicGroups: [{ name: 'Hispanic or Latino', value: '2135-2' }],
    ethnicityUnknownReasons: [{ name: 'Not asked', value: '6' }],
    detailedEthnicities: [{ name: 'Central American', value: '2155-0' }]
};

jest.mock('apps/patient/profile/ethnicity/usePatientEthnicityCodedValues', () => ({
    usePatientEthnicityCodedValues: () => mockEthnicityValues
}));

const Fixture = () => {
    const form = useForm();
    return (
        <FormProvider {...form}>
            <EthnicityEntryCard />
        </FormProvider>
    );
};

describe('EthnicityEntryCard', () => {
    it('should display Ethnicity header on card', () => {
        const { getByRole } = render(<Fixture />);
        expect(getByRole('heading')).toHaveTextContent('Ethnicity');
    });
});
