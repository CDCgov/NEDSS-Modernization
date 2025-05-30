import { MergePatient } from 'apps/deduplication/api/model/MergePatient';
import { FormProvider, useForm } from 'react-hook-form';
import { PatientMergeForm } from '../../model/PatientMergeForm';
import { AdminCommentsSelection } from './AdminCommentsSelection';
import { render } from '@testing-library/react';

const Fixture = () => {
    const form = useForm<PatientMergeForm>();
    const data: Partial<MergePatient>[] = [
        { personUid: '100', adminComments: { date: '2025-05-01T00:00', comment: 'First comment' } },
        { personUid: '200', adminComments: { date: '2005-01-21T00:00', comment: 'Second comment' } },
        { personUid: '300', adminComments: { date: '1995-04-23T00:00', comment: 'Third comment' } }
    ];
    return (
        <FormProvider {...form}>
            <AdminCommentsSelection mergePatients={data as MergePatient[]} />
        </FormProvider>
    );
};
describe('AdminCommentsSelection', () => {
    it('should display sections for each patient', () => {
        const { getByText, getAllByText } = render(<Fixture />);

        expect(getAllByText('ADMINISTRATIVE COMMENTS')).toHaveLength(3);

        // First
        expect(getByText('First comment')).toBeInTheDocument();
        expect(getByText('05/01/2025')).toBeInTheDocument();

        // Second
        expect(getByText('Second comment')).toBeInTheDocument();
        expect(getByText('01/21/2005')).toBeInTheDocument();

        // Third
        expect(getByText('Third comment')).toBeInTheDocument();
        expect(getByText('04/23/1995')).toBeInTheDocument();
    });
});
