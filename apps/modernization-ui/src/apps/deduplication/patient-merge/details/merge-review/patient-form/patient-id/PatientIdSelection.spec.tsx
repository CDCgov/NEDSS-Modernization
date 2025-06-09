import { FormProvider, useForm } from 'react-hook-form';
import { PatientIdSelection } from './PatientIdSelection';
import { MergeCandidate } from 'apps/deduplication/api/model/MergeCandidate';
import { PatientMergeForm } from '../../model/PatientMergeForm';
import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';

const onRemove = jest.fn();
const Fixture = () => {
    const form = useForm<PatientMergeForm>();
    const data: Partial<MergeCandidate>[] = [
        { personUid: '100', personLocalId: '001' },
        { personUid: '200', personLocalId: '002' },
        { personUid: '300', personLocalId: '003' }
    ];
    return (
        <FormProvider {...form}>
            <PatientIdSelection mergeCandidates={data as MergeCandidate[]} onRemovePatient={onRemove} />
        </FormProvider>
    );
};

describe('PatientIdSelection', () => {
    it('should render radio buttons', () => {
        const { getByLabelText } = render(<Fixture />);

        expect(getByLabelText('001')).toBeInTheDocument();
        expect(getByLabelText('001')).toHaveAttribute('type', 'radio');

        expect(getByLabelText('002')).toBeInTheDocument();
        expect(getByLabelText('002')).toHaveAttribute('type', 'radio');

        expect(getByLabelText('003')).toBeInTheDocument();
        expect(getByLabelText('003')).toHaveAttribute('type', 'radio');
    });

    it('should select when clicked', async () => {
        const user = userEvent.setup();
        const { getByLabelText } = render(<Fixture />);

        await user.click(getByLabelText('001'));
        expect(getByLabelText('001')).toBeChecked();

        await user.click(getByLabelText('002'));
        expect(getByLabelText('002')).toBeChecked();

        await user.click(getByLabelText('003'));
        expect(getByLabelText('003')).toBeChecked();

        await user.click(getByLabelText('001'));
        expect(getByLabelText('001')).toBeChecked();
    });

    it('should render delete button for non-active entries', async () => {
        const user = userEvent.setup();
        const { getAllByRole, getByLabelText } = render(<Fixture />);

        // No Id is selected, so all 3 have a delete button
        const deleteButtons = getAllByRole('button');
        expect(deleteButtons).toHaveLength(3);

        expect(deleteButtons[0]).toHaveTextContent('Remove');
        expect(deleteButtons[1]).toHaveTextContent('Remove');
        expect(deleteButtons[2]).toHaveTextContent('Remove');

        // Selecting an ID removes the delete button from it. leaving 2
        await user.click(getByLabelText('001'));
        expect(getAllByRole('button')).toHaveLength(2);
    });

    it('should call delete button when clicked', async () => {
        const user = userEvent.setup();
        const { getAllByRole } = render(<Fixture />);

        const deleteButtons = getAllByRole('button');

        await user.click(deleteButtons[0]);
        expect(onRemove).toHaveBeenLastCalledWith('100');

        await user.click(deleteButtons[1]);
        expect(onRemove).toHaveBeenLastCalledWith('200');

        await user.click(deleteButtons[2]);
        expect(onRemove).toHaveBeenLastCalledWith('300');
    });
});
