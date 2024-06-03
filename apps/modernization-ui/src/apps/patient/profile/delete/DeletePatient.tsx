import { useNavigate } from 'react-router-dom';
import { Button, Icon } from '@trussworks/react-uswds';
import { Patient } from '../Patient';
import { useAlert } from 'alert';
import { formattedName } from 'utils';

import { Confirmation, Warning } from 'design-system/modal';
import { Note } from 'components/Note';
import { DeletePatientMutation, PatientSummary, useDeletePatientMutation } from 'generated/graphql/schema';
import { DeletabilityResult, resolveDeletability } from './resolveDeletability';

import styles from './delete-patient.module.scss';
import { useConditionalRender } from 'conditional-render';
import { displayName } from 'name';

type Props = {
    patient: Patient;
    summary: PatientSummary;
};

const DeletePatient = ({ patient, summary }: Props) => {
    const { showSuccess, showError } = useAlert();

    const deletability = resolveDeletability(patient);

    const navigate = useNavigate();

    const handleDeleteComplete = (data: DeletePatientMutation) => {
        if (data.deletePatient.__typename === 'PatientDeleteSuccessful') {
            showSuccess({
                message: `Deleted patient ${formattedName(summary?.legalName?.last, summary?.legalName?.first)}`
            });
            navigate('/advanced-search');
        } else if (data.deletePatient.__typename === 'PatientDeleteFailed') {
            showError({
                message: 'Delete failed. Please try again later.'
            });
        }
    };

    const [deletePatient] = useDeletePatientMutation({ onCompleted: handleDeleteComplete });

    const handleDeletePatient = () => {
        if (patient) {
            deletePatient({
                variables: {
                    patient: patient.id
                }
            });
        }
    };

    const { show, hide, render } = useConditionalRender();

    return (
        <>
            <Button className={styles.destructive} type={'button'} onClick={show}>
                <Icon.Delete size={3} />
                Delete patient
            </Button>
            {render(
                <>
                    {deletability === DeletabilityResult.Deletable && (
                        <Confirmation
                            title="Permanently delete patient?"
                            cancelText="No, go back"
                            onCancel={hide}
                            confirmText="Yes, delete"
                            onConfirm={handleDeletePatient}>
                            Would you like to permanently delete patient record <strong>{patient?.shortId}</strong>
                            {summary.legalName && (
                                <>
                                    , <strong>{displayName()(summary.legalName)}</strong>
                                </>
                            )}
                            ,
                        </Confirmation>
                    )}
                    {deletability === DeletabilityResult.Has_Associations && (
                        <Warning title="The patient can not be deleted" onClose={hide}>
                            <Note>
                                The file cannot be deleted until all associated event records have been deleted. If you
                                are unable to see the associated event records due to your user permission settings,
                                please contact your system administrator.
                            </Note>
                        </Warning>
                    )}
                    {deletability === DeletabilityResult.Is_Inactive && (
                        <Warning title="The patient can not be deleted" onClose={hide}>
                            <Note>This patient file is inactive and cannot be deleted.</Note>
                        </Warning>
                    )}
                </>
            )}
        </>
    );
};

export { DeletePatient };
