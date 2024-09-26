import { Icon } from '@trussworks/react-uswds';
import { DeletePatientMutation, PatientSummary, useDeletePatientMutation } from 'generated/graphql/schema';
import { useAlert } from 'alert';
import { displayName } from 'name';
import { Patient } from 'apps/patient/profile/Patient';

import { Confirmation, Warning } from 'design-system/modal';
import { Shown, useConditionalRender } from 'conditional-render';
import { Note } from 'components/Note';
import { DeletabilityResult, resolveDeletability } from './resolveDeletability';

import { useSearchNavigation } from 'apps/search';
import { Button } from 'components/button';

type Props = {
    patient: Patient;
    summary: PatientSummary;
};

const DeletePatient = ({ patient, summary }: Props) => {
    const { showSuccess, showError } = useAlert();

    const { go } = useSearchNavigation();

    const deletability = resolveDeletability(patient);

    const handleDeleteComplete = (data: DeletePatientMutation) => {
        if (data.deletePatient.__typename === 'PatientDeleteSuccessful') {
            showSuccess({
                message: `Deleted patient ${(summary.legalName && displayName('short')(summary.legalName)) || patient.shortId}`
            });
            go();
        } else if (data.deletePatient.__typename === 'PatientDeleteFailed') {
            showError({
                message: 'Delete failed. Please try again later.'
            });
        }
    };

    const [deletePatient] = useDeletePatientMutation({ onCompleted: handleDeleteComplete });

    const handleDeletePatient = () => {
        deletePatient({
            variables: {
                patient: patient.id
            }
        });
    };

    const { show, hide, render } = useConditionalRender();

    return (
        <>
            <Button destructive icon={<Icon.Delete size={3} />} onClick={show}>
                Delete patient
            </Button>
            {render(
                <>
                    <Shown when={deletability === DeletabilityResult.Deletable}>
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
                    </Shown>
                    <Shown when={deletability === DeletabilityResult.Has_Associations}>
                        <Warning title="The patient can not be deleted" onClose={hide}>
                            <Note>
                                The file cannot be deleted until all associated event records have been deleted. If you
                                are unable to see the associated event records due to your user permission settings,
                                please contact your system administrator.
                            </Note>
                        </Warning>
                    </Shown>
                    <Shown when={deletability === DeletabilityResult.Is_Inactive}>
                        <Warning title="The patient can not be deleted" onClose={hide}>
                            <Note>This patient file is inactive and cannot be deleted.</Note>
                        </Warning>
                    </Shown>
                </>
            )}
        </>
    );
};

export { DeletePatient };
