import { act, render, waitFor } from '@testing-library/react';
import { AdministrativeEntryFields } from './AdministrativeEntryFields';
import { FormProvider, useForm } from 'react-hook-form';
import userEvent from '@testing-library/user-event';
import { NewPatientEntry } from 'apps/patient/add';

const Fixture = () => {
    const methods = useForm<NewPatientEntry>({ mode: 'onBlur' });
    return (
        <FormProvider {...methods}>
            <AdministrativeEntryFields />
        </FormProvider>
    );
};

describe('when entering patient administrative information', () => {
    it('should render all input fields', () => {
        const { getByLabelText } = render(<Fixture />);

        expect(getByLabelText('Information as of date')).toBeInTheDocument();
        expect(getByLabelText('Comments')).toBeInTheDocument();
    });

    it('should require as of date', async () => {
        const { getByLabelText, queryByText, findByText } = render(<Fixture />);

        const dateInput = getByLabelText('Information as of date');

        expect(queryByText('The Information as of date is required.')).not.toBeInTheDocument();

        act(() => {
            userEvent.click(dateInput);
            userEvent.tab();
        });

        expect(await findByText('The Information as of date is required.')).toBeInTheDocument();
    });
});
