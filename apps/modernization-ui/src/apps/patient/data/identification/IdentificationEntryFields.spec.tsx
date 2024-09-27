import { render } from '@testing-library/react';
import { PatientIdentificationCodedValues } from 'apps/patient/profile/identification/usePatientIdentificationCodedValues';
import { FormProvider, useForm } from 'react-hook-form';
import { IdentificationEntry } from '../entry';
import { IdentificationEntryFields } from './IdentificationEntryFields';

const mockPatientIdentificationCodedValues: PatientIdentificationCodedValues = {
    types: [{ name: 'Account number', value: 'AN' }],
    authorities: [{ name: 'Assigning auth', value: 'AA' }]
};

jest.mock('apps/patient/profile/identification/usePatientIdentificationCodedValues', () => ({
    usePatientIdentificationCodedValues: () => mockPatientIdentificationCodedValues
}));

const Fixture = () => {
    const form = useForm<IdentificationEntry>({
        mode: 'onBlur',
        defaultValues: {
            asOf: undefined,
            type: undefined,
            issuer: undefined,
            id: ''
        }
    });
    return (
        <FormProvider {...form}>
            <IdentificationEntryFields />
        </FormProvider>
    );
};

describe('IdentificationEntryFields', () => {
    it('should render the proper labels', async () => {
        const { getByLabelText, findByText } = render(<Fixture />);
        await findByText('ID value');

        expect(getByLabelText('Identification as of')).toBeInTheDocument();
        expect(getByLabelText('Type')).toBeInTheDocument();
        expect(getByLabelText('Assigning authority')).toBeInTheDocument();
        expect(getByLabelText('ID value')).toBeInTheDocument();
    });
});
