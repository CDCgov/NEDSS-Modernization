import {
    Alert,
    Button,
    ButtonGroup,
    Grid,
    Icon,
    Modal,
    ModalFooter,
    ModalHeading,
    ModalRef,
    ModalToggleButton
} from '@trussworks/react-uswds';
import './style.scss';
import { useEffect, useRef, useState } from 'react';
import { useParams } from 'react-router-dom';
import {
    FindPatientsByFilterQuery,
    RecordStatus,
    useFindContactsForPatientLazyQuery,
    useFindDocumentsForPatientLazyQuery,
    useFindInvestigationsByFilterLazyQuery,
    useFindLabReportsByFilterLazyQuery,
    useFindMorbidtyReportForPatientLazyQuery,
    useFindPatientsByFilterLazyQuery,
    useFindTreatmentsForPatientLazyQuery
} from '../../generated/graphql/schema';
import { calculateAge } from '../../utils/util';
import { Summary } from './Summary';
import { Events } from './Events';
import { Demographics } from './Demographics';

enum ACTIVE_TAB {
    SUMMARY = 'Summary',
    EVENT = 'Events',
    DEMOGRAPHICS = 'Demographics'
}

export const PatientProfile = () => {
    const { id } = useParams();

    const modalRef = useRef<ModalRef>(null);

    const [getPatientInvestigationData, { data: investigationData }] = useFindInvestigationsByFilterLazyQuery();
    const [getPatientLabReportData, { data: labReportData }] = useFindLabReportsByFilterLazyQuery();
    const [getPatientProfileData, { data: patientProfileData }] = useFindPatientsByFilterLazyQuery();
    const [getMorbidityData, { data: morbidityData }] = useFindMorbidtyReportForPatientLazyQuery();
    const [getTreatmentsData, { data: treatmentsData }] = useFindTreatmentsForPatientLazyQuery();
    const [getDocumentsData, { data: documentsData }] = useFindDocumentsForPatientLazyQuery();
    const [getContactsData, { data: contactsData }] = useFindContactsForPatientLazyQuery();

    const [activeTab, setActiveTab] = useState<ACTIVE_TAB.DEMOGRAPHICS | ACTIVE_TAB.EVENT | ACTIVE_TAB.SUMMARY>(
        ACTIVE_TAB.SUMMARY
    );
    const [profileData, setProfileData] = useState<FindPatientsByFilterQuery['findPatientsByFilter']['content'][0]>();

    useEffect(() => {
        if (id) {
            getPatientProfileData({
                variables: {
                    filter: {
                        id: id,
                        recordStatus: [RecordStatus.Active, RecordStatus.LogDel, RecordStatus.Superceded]
                    }
                }
            });
        }
    }, []);

    useEffect(() => {
        if (patientProfileData?.findPatientsByFilter.content) {
            setProfileData(patientProfileData?.findPatientsByFilter.content[0]);
            if (
                patientProfileData?.findPatientsByFilter.content?.length > 0 &&
                patientProfileData.findPatientsByFilter.content[0].id
            ) {
                getPatientInvestigationData({
                    variables: {
                        filter: {
                            patientId: +patientProfileData.findPatientsByFilter.content[0].id
                        }
                    }
                });
                getPatientLabReportData({
                    variables: {
                        filter: {
                            patientId: +patientProfileData.findPatientsByFilter.content[0].id
                        }
                    }
                });
                getMorbidityData({
                    variables: {
                        patientId: +patientProfileData.findPatientsByFilter.content[0].id
                    }
                });
                getTreatmentsData({
                    variables: {
                        patient: patientProfileData.findPatientsByFilter.content[0].id
                    }
                });
                getDocumentsData({
                    variables: {
                        patient: patientProfileData.findPatientsByFilter.content[0].id
                    }
                });
                getContactsData({
                    variables: {
                        patient: patientProfileData.findPatientsByFilter.content[0].id
                    }
                });
            }
        }
    }, [patientProfileData]);

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
                    <Button className="display-inline-flex print-btn" type={'submit'}>
                        <Icon.Print className="margin-right-05" />
                        Print
                    </Button>
                    <ModalToggleButton
                        modalRef={modalRef}
                        opener
                        className="delete-btn display-inline-flex"
                        type={'submit'}>
                        <Icon.Delete className="margin-right-05" />
                        Delete Patient
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
                                Would you like to permenantly delete patient record {profileData?.localId},{' '}
                                {`${profileData?.lastNm}, ${profileData?.firstNm}`}?
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
                <div className="margin-y-2 flex-row common-card">
                    <div className="grid-row flex-align-center flex-justify padding-2 border-bottom border-base-lighter">
                        <p className="font-sans-xl text-bold margin-0">
                            {`${profileData?.lastNm}, ${profileData?.firstNm}`}
                        </p>
                        <h5 className="font-sans-md text-medium margin-0">Patient ID: {profileData?.localId}</h5>
                    </div>
                    <Grid row gap={3} className="padding-3">
                        <Grid row col={3}>
                            <Grid col={12}>
                                <h5 className="margin-0 text-normal font-sans-1xs text-gray-50 margin-right-1">SEX</h5>
                                <p className="margin-0 font-sans-1xs text-normal">
                                    {profileData?.currSexCd === 'M'
                                        ? 'Male'
                                        : profileData?.currSexCd === 'F'
                                        ? 'Female'
                                        : 'Unknown'}
                                </p>
                            </Grid>
                            <Grid col={12} className="margin-top-3">
                                <h5 className="margin-0 text-normal font-sans-1xs text-gray-50 margin-right-1">
                                    DATE OF BIRTH
                                </h5>
                                <p className="margin-0 font-sans-1xs text-normal">
                                    {new Date(profileData?.birthTime).toLocaleDateString('en-US', {
                                        timeZone: 'UTC'
                                    })}{' '}
                                    ({calculateAge(new Date(profileData?.birthTime))})
                                </p>
                            </Grid>
                        </Grid>

                        <Grid row col={3}>
                            <Grid col={12}>
                                <h5 className="margin-0 text-normal font-sans-1xs text-gray-50 margin-right-1">
                                    PHONE
                                </h5>
                                <p className="margin-0 font-sans-1xs text-normal">(555) 555-5555</p>
                            </Grid>
                            <Grid col={12} className="margin-top-3">
                                <h5 className="margin-0 text-normal font-sans-1xs text-gray-50 margin-right-1">
                                    EMAIL
                                </h5>
                                <p className="margin-0 font-sans-1xs text-normal">sjohn@helloworld.com</p>
                            </Grid>
                        </Grid>

                        <Grid row col={3}>
                            <Grid col={12}>
                                <h5 className="margin-0 text-normal font-sans-1xs text-gray-50 margin-right-1">
                                    ADDRESS
                                </h5>
                                <p className="margin-0 font-sans-1xs text-normal">
                                    12 Main St, Apt 12 Atlanta, GA, 30342
                                </p>
                            </Grid>
                        </Grid>

                        <Grid row col={3}>
                            <Grid col={12}>
                                <h5 className="margin-0 text-normal font-sans-1xs text-gray-50 margin-right-1">RACE</h5>
                                <p className="margin-0 font-sans-1xs text-normal">White</p>
                            </Grid>
                            <Grid col={12} className="margin-top-3">
                                <h5 className="margin-0 text-normal font-sans-1xs text-gray-50 margin-right-1">
                                    ETHNICITY
                                </h5>
                                <p className="margin-0 font-sans-1xs text-normal">Not Hispanic or Latino</p>
                            </Grid>
                        </Grid>
                    </Grid>
                </div>

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

                {activeTab === ACTIVE_TAB.SUMMARY && <Summary profileData={profileData} />}
                {activeTab === ACTIVE_TAB.EVENT && (
                    <Events
                        investigationData={investigationData?.findInvestigationsByFilter}
                        labReports={labReportData?.findLabReportsByFilter}
                        morbidityData={morbidityData?.findMorbidtyReportForPatient}
                        treatmentsData={treatmentsData?.findTreatmentsForPatient}
                        documentsData={documentsData?.findDocumentsForPatient}
                        contactsData={contactsData?.findContactsForPatient}
                    />
                )}
                {activeTab === ACTIVE_TAB.DEMOGRAPHICS && (
                    <Demographics
                        handleFormSubmission={(type: 'error' | 'success' | 'warning' | 'info', message: string) => {
                            setSubmittedSuccess(true);
                            setAddedItem(message);
                            setAlertType(type);
                        }}
                        patientProfileData={patientProfileData?.findPatientsByFilter}
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
