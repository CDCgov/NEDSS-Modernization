import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { FormProvider, useForm } from 'react-hook-form';
import { AdministrativeEntryFields } from './AdministrativeEntryFields';
import { AdministrativeEntry } from 'apps/patient/data/entry';

const Fixture = (props: { sizing?: 'small' | 'medium' | 'large' }) => {
    const methods = useForm<AdministrativeEntry>({ mode: 'onBlur' });
    return (
        <FormProvider {...methods}>
            <AdministrativeEntryFields sizing={props.sizing} />
        </FormProvider>
    );
};

describe('when entering patient administrative information', () => {
    it('should render all input fields', () => {
        const { getByLabelText, getByRole } = render(<Fixture />);

        expect(getByRole('textbox', { name: /Information as of date/i })).toBeInTheDocument();
        expect(getByLabelText('Comments')).toBeInTheDocument();
    });

    it('should render all input fields with the correct sizing when sizing is small.', () => {
        const { getByText } = render(<Fixture sizing="small" />);

        //  This is assuming that the elements have a specific structure.
        expect(getByText('Information as of date').parentElement?.parentElement).toHaveClass('small');
        expect(getByText('Comments').parentElement?.parentElement).toHaveClass('small');
    });

    it('should require as of date', async () => {
        const { getByLabelText, queryByText, getByRole } = render(<Fixture />);

        const dateInput = getByRole('textbox', { name: /Information as of date/i });

        expect(queryByText('The Information as of date is required.')).not.toBeInTheDocument();

        const user = userEvent.setup();

        await user.click(dateInput).then(() => user.tab());

        expect(queryByText('The Information as of date is required.')).toBeInTheDocument();
    });
});
