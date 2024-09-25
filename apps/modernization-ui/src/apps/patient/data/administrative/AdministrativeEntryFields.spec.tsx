import { act, render, waitFor } from '@testing-library/react';
import { AdministrativeEntryFields } from './AdministrativeEntryFields';
import { FormProvider, useForm } from 'react-hook-form';
import { ReactNode } from 'react';
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

describe('Administrative', () => {
    it('should render all input fields', () => {
        const { getByLabelText } = render(<Fixture />);

        expect(getByLabelText('Information as of date')).toBeInTheDocument();
        expect(getByLabelText('General comments')).toBeInTheDocument();
    });

    it('should require as of', () => {
        const { getByLabelText } = render(<Fixture />);

        expect(getByLabelText('Information as of date')).toBeInTheDocument();
        expect(getByLabelText('General comments')).toBeInTheDocument();
    });

    it('should require as of date', async () => {
        const { getByLabelText, queryByText } = render(<Fixture />);
        const errorMessage = 'As of date is required.';
        const dateInput = getByLabelText('Information as of date');

        expect(queryByText(errorMessage)).not.toBeInTheDocument();
        act(() => {
            userEvent.click(dateInput);
            userEvent.tab();
        });
        await waitFor(() => {
            expect(queryByText(errorMessage)).toBeInTheDocument();
        });

        act(() => {
            userEvent.click(dateInput);
            userEvent.paste(dateInput, '12/01/2020');
            userEvent.tab();
        });
        await waitFor(() => {
            expect(queryByText(errorMessage)).not.toBeInTheDocument();
        });
    });
});
