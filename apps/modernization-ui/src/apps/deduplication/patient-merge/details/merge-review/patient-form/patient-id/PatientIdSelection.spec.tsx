import { FormProvider, useForm } from 'react-hook-form';
import { PatientIdSelection } from './PatientIdSelection';
import { PatientData } from 'apps/deduplication/api/model/PatientData';
import { PatientMergeForm } from '../../model/PatientMergeForm';
import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';

const onRemove = jest.fn();
const Fixture = () => {
    const form = useForm<PatientMergeForm>();
    const data: Partial<PatientData>[] = [{ personUid: '100' }, { personUid: '200' }, { personUid: '300' }];
    return (
        <FormProvider {...form}>
            <PatientIdSelection patientData={data as PatientData[]} onRemovePatient={onRemove} />
        </FormProvider>
    );
};

describe('PatientIdSelection', () => {
    it('should render radio buttons', () => {
        const { getByLabelText } = render(<Fixture />);

        expect(getByLabelText('100')).toBeInTheDocument();
        expect(getByLabelText('100')).toHaveAttribute('type', 'radio');

        expect(getByLabelText('200')).toBeInTheDocument();
        expect(getByLabelText('200')).toHaveAttribute('type', 'radio');

        expect(getByLabelText('300')).toBeInTheDocument();
        expect(getByLabelText('300')).toHaveAttribute('type', 'radio');
    });

    it('should select when clicked', async () => {
        const user = userEvent.setup();
        const { getByLabelText } = render(<Fixture />);

        await user.click(getByLabelText('100'));
        expect(getByLabelText('100')).toBeChecked();

        await user.click(getByLabelText('200'));
        expect(getByLabelText('200')).toBeChecked();

        await user.click(getByLabelText('300'));
        expect(getByLabelText('300')).toBeChecked();

        await user.click(getByLabelText('100'));
        expect(getByLabelText('100')).toBeChecked();
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
        await user.click(getByLabelText('100'));
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
