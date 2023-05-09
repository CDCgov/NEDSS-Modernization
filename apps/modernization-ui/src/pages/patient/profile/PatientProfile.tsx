import {
    Alert,
    Button,
    ButtonGroup,
    Icon,
    Modal,
    ModalFooter,
    ModalHeading,
    ModalRef,
    ModalToggleButton
} from '@trussworks/react-uswds';
import 'pages/patientProfile/style.scss';
import { useContext, useEffect, useRef, useState } from 'react';
import { useParams } from 'react-router-dom';
import { RedirectControllerService } from 'generated';
import { UserContext } from 'providers/UserContext';

import { Summary } from 'pages/patientProfile/Summary';
import { Events } from 'pages/patientProfile/Events';
import { Demographics } from 'pages/patientProfile/Demographics';
import { Config } from 'config';
import { usePatientProfile } from './usePatientProfile';
import { PatientProfileSummary } from './summary/PatientProfileSummary';

enum ACTIVE_TAB {
    SUMMARY = 'Summary',
    EVENT = 'Events',
    DEMOGRAPHICS = 'Demographics'
}

export const PatientProfile = () => {
    const { id } = useParams();
    const { state } = useContext(UserContext);
    const NBS_URL = Config.nbsUrl;

    const modalRef = useRef<ModalRef>(null);

    const [activeTab, setActiveTab] = useState<ACTIVE_TAB.DEMOGRAPHICS | ACTIVE_TAB.EVENT | ACTIVE_TAB.SUMMARY>(
        ACTIVE_TAB.SUMMARY
    );

    const profile = usePatientProfile(id);

    const openPrintableView = () => {
        RedirectControllerService.preparePatientDetailsUsingGet({
            authorization: 'Bearer ' + state.getToken()
        }).then(() => {
            window.open(
                `${NBS_URL}/LoadViewFile1.do?method=ViewFile&ContextAction=print&uid=${id}`,
                '_blank',
                'noreferrer'
            );
        });
    };

    const [submittedSuccess, setSubmittedSuccess] = useState<boolean>(false);
    const [addedItem, setAddedItem] = useState<string>('');
    const [alertType, setAlertType] = useState<'error' | 'success' | 'warning' | 'info'>('success');

    useEffect(() => {
        if (submittedSuccess) {
            setTimeout(() => {
                setSubmittedSuccess(false);
            }, 5000);
        }
    }, [submittedSuccess]);

    return (
        <div className="height-full main-banner">
            <div className="bg-white grid-row flex-align-center flex-justify border-bottom-style">
                <h1 className="font-sans-xl text-medium">Patient Profile</h1>
                <div>
                    <Button type={'button'} className="display-inline-flex print-btn" onClick={openPrintableView}>
                        <Icon.Print className="margin-right-05" />
                        Print
                    </Button>
                    <ModalToggleButton
                        modalRef={modalRef}
                        opener
                        className="delete-btn display-inline-flex"
                        type={'submit'}>
                        <Icon.Delete className="margin-right-05" />
                        Delete patient
                    </ModalToggleButton>
                    <Modal
                        ref={modalRef}
                        id="example-modal-1"
                        aria-labelledby="modal-1-heading"
                        className="padding-0"
                        aria-describedby="modal-1-description">
                        <ModalHeading
                            id="modal-1-heading"
                            className="border-bottom border-base-lighter font-sans-lg padding-2">
                            Permanently delete patient?
                        </ModalHeading>
                        <div className="margin-2 grid-row flex-no-wrap border-left-1 border-accent-warm flex-align-center">
                            <Icon.Warning className="font-sans-2xl margin-x-2" />
                            <p id="modal-1-description">
                                Would you like to permenantly delete patient record {profile?.patient?.shortId},{' '}
                                {`${profile?.summary?.legalName?.last}, ${profile?.summary?.legalName?.first}`}
                            </p>
                        </div>
                        <ModalFooter className="border-top border-base-lighter padding-2 margin-left-auto">
                            <ButtonGroup>
                                <ModalToggleButton outline modalRef={modalRef} closer>
                                    No, go back
                                </ModalToggleButton>
                                <ModalToggleButton modalRef={modalRef} closer className="padding-105 text-center">
                                    Yes, delete
                                </ModalToggleButton>
                            </ButtonGroup>
                        </ModalFooter>
                    </Modal>
                </div>
            </div>
            <div className="main-body">
                {profile && profile.summary && (
                    <PatientProfileSummary patient={profile.patient} summary={profile.summary} />
                )}

                <div className="grid-row flex-align-center">
                    <h6
                        className={`${
                            activeTab === ACTIVE_TAB.SUMMARY && 'active'
                        } text-normal type margin-y-3 font-sans-md padding-bottom-1 cursor-pointer margin-top-2 margin-bottom-0`}
                        onClick={() => setActiveTab(ACTIVE_TAB.SUMMARY)}>
                        {ACTIVE_TAB.SUMMARY}
                    </h6>
                    <h6
                        className={`${
                            activeTab === ACTIVE_TAB.EVENT && 'active'
                        } padding-bottom-1 type text-normal margin-y-3 font-sans-md margin-x-3 cursor-pointer margin-top-2 margin-bottom-0`}
                        onClick={() => setActiveTab(ACTIVE_TAB.EVENT)}>
                        {ACTIVE_TAB.EVENT}
                    </h6>
                    <h6
                        className={`${
                            activeTab === ACTIVE_TAB.DEMOGRAPHICS && 'active'
                        } text-normal type margin-y-3 font-sans-md padding-bottom-1 cursor-pointer margin-top-2 margin-bottom-0`}
                        onClick={() => setActiveTab(ACTIVE_TAB.DEMOGRAPHICS)}>
                        {ACTIVE_TAB.DEMOGRAPHICS}
                    </h6>
                </div>

                {activeTab === ACTIVE_TAB.SUMMARY && <Summary profileData={profile} />}
                {activeTab === ACTIVE_TAB.EVENT && <Events patient={id} />}
                {activeTab === ACTIVE_TAB.DEMOGRAPHICS && (
                    <Demographics
                        handleFormSubmission={(type: 'error' | 'success' | 'warning' | 'info', message: string) => {
                            setSubmittedSuccess(true);
                            setAddedItem(message);
                            setAlertType(type);
                        }}
                        id={id || ''}
                    />
                )}

                <div className="text-center margin-y-5">
                    <Button outline type={'button'} onClick={() => window.scrollTo({ top: 0, behavior: 'smooth' })}>
                        <Icon.ArrowUpward className="margin-right-1" />
                        Back to top
                    </Button>
                </div>
            </div>

            {submittedSuccess && (
                <Alert
                    type={alertType}
                    heading="Success"
                    headingLevel="h4"
                    cta={
                        <Button type="button" unstyled>
                            <Icon.Close />
                        </Button>
                    }>
                    Added new name, {addedItem}
                </Alert>
            )}
        </div>
    );
};
