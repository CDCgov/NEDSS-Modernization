import { useConditionalRender } from 'conditional-render';
import { displayName } from 'name';
import { useAlert } from 'libs/alert';
import { Permitted, permissions } from 'libs/permission';
import { Button } from 'design-system/button';
import { Hint } from 'design-system/hint';
import { Icon } from 'design-system/icon';
import { Confirmation } from 'design-system/modal';
import { LabeledValue } from 'design-system/value';
import { useSearchNavigation } from 'apps/search';
import { Patient } from '../patient';
import { DeletePatientResponse, useDeletePatient } from './useDeletePatient';

const INACTIVE_MESSAGE = 'This patient file is inactive and cannot be deleted.';
const HAS_ASSOCIATIONS_MESSAGE =
    'This patient file has associated event records. The file cannot be deleted until all associated event records have been deleted. If you are unable to see the associated event records due to your user permission settings, please contact your system administrator.';

type DeleteActionProps = {
    patient: Patient;
};

/**
 * Represents the delete action for a patient, including the confirmation dialog and handling of the delete operation.
 * @param {Props} props - The props for the component.
 * @return {JSX.Element} The rendered component.
 */
const DeleteAction = ({ patient }: DeleteActionProps) => {
    const { showSuccess, showError } = useAlert();
    const { go } = useSearchNavigation();
    const { show, hide, render } = useConditionalRender();

    const handleDeleteComplete = (response: DeletePatientResponse) => {
        if (response.success) {
            const name = patient.name ? displayName('fullLastFirst')(patient.name) : 'patient';
            showSuccess(
                <span>
                    You have successfully deleted <strong>{`${name} (Patient ID: ${patient.patientId})`}</strong>.
                </span>
            );
            go();
        } else {
            showError('Delete failed. Please try again later.');
        }
    };

    const deletePatient = useDeletePatient(handleDeleteComplete);

    const handleDeletePatient = () => {
        deletePatient(patient.id);
    };

    const showHint = patient.deletability === 'Has_Associations' || patient.deletability === 'Is_Inactive';

    return (
        <Permitted permission={permissions.patient.delete}>
            <Hint
                id="delete-patient-hint"
                position="center"
                enabled={showHint}
                target={
                    <Button
                        secondary
                        destructive
                        disabled={patient.deletability !== 'Deletable'}
                        aria-label="Delete"
                        data-tooltip-position="top"
                        data-tooltip-offset="center"
                        icon={<Icon name="delete" />}
                        sizing={'medium'}
                        onClick={show}
                    />
                }>
                <LabeledValue label="Delete disabled" orientation="vertical" aria-describedby="delete-patient-hint">
                    {patient.deletability === 'Has_Associations' ? HAS_ASSOCIATIONS_MESSAGE : INACTIVE_MESSAGE}
                </LabeledValue>
            </Hint>
            {render(
                <Confirmation
                    title="Delete patient file"
                    cancelText="Cancel"
                    onCancel={hide}
                    confirmText="Delete"
                    destructive
                    forceAction
                    onConfirm={handleDeletePatient}>
                    You have indicated that you would like to delete this patient file. Select Delete to continue or
                    Cancel to return to the patient file.
                </Confirmation>
            )}
        </Permitted>
    );
};

export { DeleteAction };
