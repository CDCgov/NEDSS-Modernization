import { render, screen, waitFor } from '@testing-library/react';
import { PatientIdentificationCodedValues } from 'apps/patient/profile/identification/usePatientIdentificationCodedValues';
import { act } from 'react-dom/test-utils';
import { FormProvider, useForm } from 'react-hook-form';
import { IdentificationEntry } from '../entry';
import { IdentificationEntryFields } from './IdentificationEntryFields';
import userEvent from '@testing-library/user-event';

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
        const { getByLabelText } = render(<Fixture />);
        await screen.findByText('ID value');

        expect(getByLabelText('Identification as of')).toBeInTheDocument();
        expect(getByLabelText('Type')).toBeInTheDocument();
        expect(getByLabelText('Assigning authority')).toBeInTheDocument();
        expect(getByLabelText('ID value')).toBeInTheDocument();
    });

    it('should require as of', async () => {
        const { getByLabelText, getByText } = render(<Fixture />);
        await screen.findByText('ID value');

        const asOf = getByLabelText('Identification as of');
        act(() => {
            userEvent.click(asOf);
            userEvent.tab();
        });
        await waitFor(() => {
            expect(getByText('As of date is required.')).toBeInTheDocument();
        });
    });

    it('should require type', async () => {
        const { getByLabelText, getByText } = render(<Fixture />);
        await screen.findByText('ID value');

        const typeInput = getByLabelText('Type');
        act(() => {
            userEvent.click(typeInput);
            userEvent.tab();
        });
        await waitFor(() => {
            expect(getByText('Type is required.')).toBeInTheDocument();
        });
    });

    it('should require id value', async () => {
        const { getByLabelText, getByText } = render(<Fixture />);
        await screen.findByText('ID value');

        const valueInput = getByLabelText('ID value');
        act(() => {
            userEvent.click(valueInput);
            userEvent.tab();
        });
        await waitFor(() => {
            expect(getByText('ID value is required.')).toBeInTheDocument();
        });
    });

    it('should be valid with as of, type, and id value', async () => {
        const { getByLabelText, queryByText } = render(<Fixture />);

        const asOf = getByLabelText('Identification as of');
        const type = getByLabelText('Type');
        const idValue = getByLabelText('ID value');
        await screen.findByText('ID value');

        act(() => {
            userEvent.paste(asOf, '01/20/2020');
            userEvent.tab();
            userEvent.selectOptions(type, 'AN');
            userEvent.tab();
            userEvent.type(idValue, '1234');
            userEvent.tab();
        });

        await waitFor(() => {
            expect(queryByText('Type is required.')).not.toBeInTheDocument();
            expect(queryByText('As of date is required.')).not.toBeInTheDocument();
            expect(queryByText('ID value is required.')).not.toBeInTheDocument();
        });
    });
});
