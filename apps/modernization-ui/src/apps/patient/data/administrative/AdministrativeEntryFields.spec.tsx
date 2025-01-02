import { act, render } from '@testing-library/react';
import { AdministrativeEntryFields } from './AdministrativeEntryFields';
import { FormProvider, useForm } from 'react-hook-form';
import userEvent from '@testing-library/user-event';
import { NewPatientEntry } from 'apps/patient/add';

const Fixture = (props: { sizing?: 'small' | 'medium' | 'large' }) => {
    const methods = useForm<NewPatientEntry>({ mode: 'onBlur' });
    return (
        <FormProvider {...methods}>
            <AdministrativeEntryFields sizing={props.sizing} />
        </FormProvider>
    );
};

describe('when entering patient administrative information', () => {
    it('should render all input fields', () => {
        const { getByLabelText } = render(<Fixture />);

        expect(getByLabelText('Information as of date')).toBeInTheDocument();
        expect(getByLabelText('Comments')).toBeInTheDocument();
    });

    it('should render all input fields with the correct sizing when sizing is small.', () => {
        const { getByText } = render(<Fixture sizing="small" />);

        //  This is assuming that the elements have a specific structure.
        expect(getByText('Information as of date').parentElement?.parentElement).toHaveClass('small');
        expect(getByText('Comments').parentElement?.parentElement).toHaveClass('small');
    });

    it('should require as of date', async () => {
        const { getByLabelText, queryByText } = render(<Fixture />);

        const dateInput = getByLabelText('Information as of date');

        expect(queryByText('The Information as of date is required.')).not.toBeInTheDocument();

        const user = userEvent.setup();

        await user.click(dateInput).then(() => user.tab());

        expect(queryByText('The Information as of date is required.')).toBeInTheDocument();
    });
});
