import { Button, Icon, ModalRef, ModalToggleButton } from '@trussworks/react-uswds';
import 'apps/patient/profile/style.scss';
import { useRef, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

import { Summary } from 'apps/patient/profile/Summary';
import { Events } from 'apps/patient/profile/Events';
import { Demographics } from 'apps/patient/profile/Demographics';
import { Config } from 'config';
import { usePatientProfile } from './usePatientProfile';
import { PatientProfileSummary } from './summary/PatientProfileSummary';
import { DeletePatientMutation, useDeletePatientMutation } from 'generated/graphql/schema';
import { DeletabilityResult, resolveDeletability } from './resolveDeletability';
import { MessageModal } from 'messageModal';
import { usePatientProfilePermissions } from './permission';
import { useAlert } from 'alert';
import { formattedName } from 'utils';
import { ProfileProvider } from './ProfileContext';
import styles from './patient-profile.module.scss';
import TabButton from 'components/TabButton/TabButton';
import { ConfirmationModal } from 'confirmation';

const openPrintableView = (patient: string | undefined) => () => {
    if (patient) {
        window.open(
            `${Config.nbsUrl}/LoadViewFile1.do?method=ViewFile&ContextAction=print&uid=${patient}`,
            '_blank',
            'noreferrer'
        );
    }
};

enum ACTIVE_TAB {
    SUMMARY = 'Summary',
    EVENT = 'Events',
    DEMOGRAPHICS = 'Demographics'
}

export const PatientProfile = () => {
    const { id } = useParams();
    const { showAlert } = useAlert();

    const modalRef = useRef<ModalRef>(null);

    const [activeTab, setActiveTab] = useState<ACTIVE_TAB.DEMOGRAPHICS | ACTIVE_TAB.EVENT | ACTIVE_TAB.SUMMARY>(
        ACTIVE_TAB.SUMMARY
    );

    const permissions = usePatientProfilePermissions();

    const { patient, summary } = usePatientProfile(id);

    const deletability = resolveDeletability(patient);

    const navigate = useNavigate();

    const handleComplete = (data: DeletePatientMutation) => {
        if (data.deletePatient.__typename === 'PatientDeleteSuccessful') {
            showAlert({
                type: 'success',
                header: 'success',
                message: `Deleted patient ${formattedName(summary?.legalName?.last, summary?.legalName?.first)}`
            });
            navigate('/advanced-search');
        } else if (data.deletePatient.__typename === 'PatientDeleteFailed') {
            showAlert({
                type: 'error',
                header: 'failed',
                message: 'Delete failed. Please try again later.'
            });
        }
    };

    const [deletePatient] = useDeletePatientMutation({ onCompleted: handleComplete });

    function handleDeletePatient() {
        if (patient) {
            deletePatient({
                variables: {
                    patient: patient.id
                }
            });
        }
    }

    return (
        <ProfileProvider id={id}>
            <div className="height-full main-banner">
                <div className="padding-left-2 padding-right-1 bg-white grid-row flex-align-center flex-justify border-bottom-style">
                    <h1 className="font-sans-xl text-medium">Patient profile</h1>
                    <div>
                        <Button type={'button'} onClick={openPrintableView(patient?.id)}>
                            <Icon.Print size={3} />
                            Print
                        </Button>
                        {permissions.delete && (
                            <ModalToggleButton
                                modalRef={modalRef}
                                opener
                                className={styles.destructive}
                                type={'submit'}>
                                <Icon.Delete size={3} />
                                Delete patient
                            </ModalToggleButton>
                        )}
                        {deletability === DeletabilityResult.Deletable && (
                            <ConfirmationModal
                                modal={modalRef}
                                title="Permanently delete patient?"
                                message={`Would you like to permanently delete patient record ${patient?.shortId}, ${summary?.legalName?.last}, ${summary?.legalName?.first}`}
                                cancelText="No, go back"
                                onCancel={() => {
                                    modalRef.current?.toggleModal(undefined, false);
                                }}
                                confirmText="Yes, delete"
                                onConfirm={handleDeletePatient}
                            />
                        )}
                        {deletability === DeletabilityResult.Has_Associations && (
                            <MessageModal
                                modal={modalRef}
                                title={`The patient can not be deleted`}
                                message="This patient file has associated event records."
                                detail="The file cannot be deleted until all associated event records have been deleted. If you are unable to see the associated event records due to your user permission settings, please contact your system administrator."
                            />
                        )}
                        {deletability === DeletabilityResult.Is_Inactive && (
                            <MessageModal
                                modal={modalRef}
                                title={`The patient can not be deleted`}
                                message="This patient file is inactive and cannot be deleted."
                            />
                        )}
                    </div>
                </div>
                <div className="main-body">
                    {patient && summary && <PatientProfileSummary summary={summary} patient={patient} />}

                    <div role="tablist" className="grid-row flex-align-center margin-y-3">
                        <TabButton
                            className="margin-left-0 font-sans-md"
                            title={ACTIVE_TAB.SUMMARY}
                            active={activeTab === ACTIVE_TAB.SUMMARY}
                            onClick={() => setActiveTab(ACTIVE_TAB.SUMMARY)}
                        />
                        <TabButton
                            className="font-sans-md"
                            title={ACTIVE_TAB.EVENT}
                            active={activeTab === ACTIVE_TAB.EVENT}
                            onClick={() => setActiveTab(ACTIVE_TAB.EVENT)}
                        />
                        <TabButton
                            className="font-sans-md"
                            title={ACTIVE_TAB.DEMOGRAPHICS}
                            active={activeTab === ACTIVE_TAB.DEMOGRAPHICS}
                            onClick={() => setActiveTab(ACTIVE_TAB.DEMOGRAPHICS)}
                        />
                    </div>

                    {activeTab === ACTIVE_TAB.SUMMARY && <Summary patient={patient?.id} />}
                    {activeTab === ACTIVE_TAB.EVENT && (
                        <Events patient={patient?.id} addEventsAllowed={patient?.status === 'ACTIVE'} />
                    )}
                    {activeTab === ACTIVE_TAB.DEMOGRAPHICS && <Demographics patient={patient} />}

                    <div className="text-center margin-y-5">
                        <Button outline type={'button'} onClick={() => window.scrollTo({ top: 0, behavior: 'smooth' })}>
                            <Icon.ArrowUpward className="margin-right-1" />
                            Back to top
                        </Button>
                    </div>
                </div>
            </div>
        </ProfileProvider>
    );
};
