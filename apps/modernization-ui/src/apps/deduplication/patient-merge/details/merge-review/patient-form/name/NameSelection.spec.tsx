import { FormProvider, useForm } from 'react-hook-form';
import { NameSelection } from './NameSelection';
import { render } from '@testing-library/react';
import { PatientData } from 'apps/deduplication/api/model/PatientData';

const patientData: Partial<PatientData>[] = [
    {
        names: [
            {
                personUid: '1',
                sequence: '1',
                asOf: '2022-06-07T14:24:44.970',
                type: 'Legal',
                first: 'John',
                last: 'Doe'
            },
            {
                personUid: '1',
                sequence: '2',
                asOf: '2020-01-01T14:24:44.970',
                type: 'Alias',
                first: 'Johnathan',
                last: 'Doer'
            }
        ]
    }
];
const Fixture = () => {
    const form = useForm();
    return (
        <FormProvider {...form}>
            <NameSelection patientData={patientData as PatientData[]} />
        </FormProvider>
    );
};
describe('NameSelection', () => {
    it('should have the proper section title', () => {
        const { getByText } = render(<Fixture />);

        expect(getByText('NAME')).toBeInTheDocument();
    });
});
