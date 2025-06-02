import { FormProvider, useForm } from 'react-hook-form';
import { IdentificationSelection } from './IdentificationSelection';
import { render } from '@testing-library/react';
import { PatientData } from 'apps/deduplication/api/model/PatientData';

const patientData: Partial<PatientData>[] = [
    {
        identifications: [
            {
                personUid: '1',
                sequence: '1',
                asOf: '2014-03-11T00:00:00.000',
                type: "Driver's license",
                value: '10001'
            }
        ]
    }
];
const Fixture = () => {
    const form = useForm();
    return (
        <FormProvider {...form}>
            <IdentificationSelection patientData={patientData as PatientData[]} />
        </FormProvider>
    );
};
describe('IdentificationSelection', () => {
    it('should have the proper section title', () => {
        const { getByText } = render(<Fixture />);

        expect(getByText('IDENTIFICATION')).toBeInTheDocument();
    });
});
