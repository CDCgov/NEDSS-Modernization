import { vi } from 'vitest';
import { FormProvider, useForm } from 'react-hook-form';
import { render, waitFor, act } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { IdentificationCodedValues } from 'apps/patient/data/identification/useIdentificationCodedValues';
import { BasicIdentificationEntry } from '../entry';
import { BasicIdentificationFields } from './BasicIdentificationFields';

const mockIdentificationCodedValues: IdentificationCodedValues = {
    types: [{ name: 'Account number', value: 'AN' }],
    authorities: [{ name: 'Assigning auth', value: 'AA' }]
};

vi.mock('apps/patient/data/identification/useIdentificationCodedValues', () => ({
    useIdentificationCodedValues: () => mockIdentificationCodedValues
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

        await userEvent.click(typeInput);
        await userEvent.tab();

        expect(getByText('The Type is required.')).toBeInTheDocument();
    });

    it('should require id value', async () => {
        const { getByLabelText, getByText, findByText } = render(<Fixture />);
        await findByText('ID value');

        const valueInput = getByLabelText('ID value');

        await userEvent.click(valueInput);
        await userEvent.tab();

        expect(getByText('The ID value is required.')).toBeInTheDocument();
    });

    it('should be valid with type, and id value', async () => {
        const { getByLabelText, queryByText, findByText } = render(<Fixture />);

        const type = getByLabelText('Type');
        const idValue = getByLabelText('ID value');
        await findByText('ID value');

        await act(async () => {
            await userEvent.selectOptions(type, 'AN');
            await userEvent.tab();
            await userEvent.type(idValue, '1234');
            await userEvent.tab();
        });

        await waitFor(() => {
            expect(queryByText('The Type is required.')).not.toBeInTheDocument();
            expect(queryByText('The ID value is required.')).not.toBeInTheDocument();
        });
    });
});
