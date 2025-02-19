import { FormProvider, useForm } from 'react-hook-form';
import { render, waitFor, act } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { PatientIdentificationCodedValues } from 'apps/patient/profile/identification/usePatientIdentificationCodedValues';
import { BasicIdentificationEntry } from '../entry';
import { BasicIdentificationFields } from './BasicIdentificationFields';

const mockPatientIdentificationCodedValues: PatientIdentificationCodedValues = {
    types: [{ name: 'Account number', value: 'AN' }],
    authorities: [{ name: 'Assigning auth', value: 'AA' }]
};

jest.mock('apps/patient/profile/identification/usePatientIdentificationCodedValues', () => ({
    usePatientIdentificationCodedValues: () => mockPatientIdentificationCodedValues
}));

const Fixture = (props: { sizing?: 'small' | 'medium' | 'large' }) => {
    const form = useForm<BasicIdentificationEntry>({
        mode: 'onBlur',
        defaultValues: {
            type: undefined,
            issuer: undefined,
            id: ''
        }
    });
    return (
        <FormProvider {...form}>
            <BasicIdentificationFields sizing={props.sizing} />
        </FormProvider>
    );
};

describe('BasicIdentificationFields', () => {
    it('Should render the proper labels', async () => {
        const { getByLabelText } = render(<Fixture />);

        expect(getByLabelText('Type')).toBeInTheDocument();
        expect(getByLabelText('Assigning authority')).toBeInTheDocument();
        expect(getByLabelText('ID value')).toBeInTheDocument();
    });

    it('Should render the proper labels small when the sizing is set to small', async () => {
        const { getByText } = render(<Fixture sizing="small" />);

        expect(getByText('Type').parentElement?.parentElement).toHaveClass('small');
        expect(getByText('Assigning authority').parentElement?.parentElement).toHaveClass('small');
        expect(getByText('ID value').parentElement?.parentElement).toHaveClass('small');
    });

    it('should require type', async () => {
        const { getByLabelText, getByText } = render(<Fixture />);

        const typeInput = getByLabelText('Type');
        act(() => {
            userEvent.click(typeInput);
            userEvent.tab();
        });
        await waitFor(() => {
            expect(getByText('The Type is required.')).toBeInTheDocument();
        });
    });

    it('should require id value', async () => {
        const { getByLabelText, getByText, findByText } = render(<Fixture />);
        await findByText('ID value');

        const valueInput = getByLabelText('ID value');
        act(() => {
            userEvent.click(valueInput);
            userEvent.tab();
        });
        await waitFor(() => {
            expect(getByText('The ID value is required.')).toBeInTheDocument();
        });
    });

    it('should be valid with type, and id value', async () => {
        const { getByLabelText, queryByText, findByText } = render(<Fixture />);

        const type = getByLabelText('Type');
        const idValue = getByLabelText('ID value');
        await findByText('ID value');

        act(() => {
            userEvent.selectOptions(type, 'AN');
            userEvent.tab();
            userEvent.type(idValue, '1234');
            userEvent.tab();
        });

        await waitFor(() => {
            expect(queryByText('The Type is required.')).not.toBeInTheDocument();
            expect(queryByText('The ID value is required.')).not.toBeInTheDocument();
        });
    });
});
