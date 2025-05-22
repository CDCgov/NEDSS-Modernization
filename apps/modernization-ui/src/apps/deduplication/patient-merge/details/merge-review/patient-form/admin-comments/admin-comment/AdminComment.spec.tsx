import { FormProvider, useForm } from 'react-hook-form';
import { PatientMergeForm } from '../../../model/PatientMergeForm';
import { AdminComment } from './AdminComment';
import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';

const Fixture = () => {
    const form = useForm<PatientMergeForm>();
    const data = { personUid: '100', adminComments: { date: '2025-05-01T00:00', comment: 'First comment' } };
    return (
        <FormProvider {...form}>
            <AdminComment personUid={data.personUid} adminComments={data.adminComments} />
        </FormProvider>
    );
};

describe('AdminComment', () => {
    it('should render radio button', () => {
        const { getByRole } = render(<Fixture />);

        expect(getByRole('radio')).toBeInTheDocument();
    });

    it('should render as of date label', () => {
        const { getByLabelText } = render(<Fixture />);

        expect(getByLabelText('As of date')).toBeInTheDocument();
    });

    it('should render date in MM/dd/yyyy format', () => {
        const { getByText } = render(<Fixture />);
        expect(getByText('05/01/2025')).toBeInTheDocument();
    });

    it('should render comments label', () => {
        const { getByText } = render(<Fixture />);

        expect(getByText('Comments:')).toBeInTheDocument();
    });

    it('should set value on click', async () => {
        const user = userEvent.setup();
        const { getByRole } = render(<Fixture />);

        expect(getByRole('radio')).not.toBeChecked();

        await user.click(getByRole('radio'));
        expect(getByRole('radio')).toBeChecked();
    });
});
