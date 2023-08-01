import {
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
import { useRef, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { CalculatedVariables } from 'pages/patientProfile/CalculatedVariables';
import { Summary } from 'pages/patientProfile/Summary';
import { Events } from 'pages/patientProfile/Events';
import { Demographics } from 'pages/patientProfile/Demographics';
import { Config } from 'config';
import { usePatientProfile } from './usePatientProfile';
import { PatientProfileSummary } from './summary/PatientProfileSummary';
import { DeletePatientMutation, useDeletePatientMutation } from 'generated/graphql/schema';
import { DeletabilityResult, resolveDeletability } from './resolveDeletability';
import { resolveDeleteMessage } from './resolveDeleteMessage';

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

    const modalRef = useRef<ModalRef>(null);

    const [activeTab, setActiveTab] = useState<ACTIVE_TAB.DEMOGRAPHICS | ACTIVE_TAB.EVENT | ACTIVE_TAB.SUMMARY>(
        ACTIVE_TAB.SUMMARY
    );

    let PatientLegalNameObject = {
        __typename: 'PatientLegalName',
        prefix: '',
        first: 'Mike',
        middle: 'Greg',
        last: 'Davis',
        suffix: ''
    };

    let PatientSummaryAddressObject = {
        __typename: 'PatientSummaryAddress',
        street: 'string',
        city: 'string',
        state: 'sting',
        zipcode: 'string',
        country: 'string '
    };


    let PatientSummaryPhoneObject = { __typename: 'PatientSummaryPhone', use: 'string', number: '555-5555555' };
    let PatientSummaryEmailObject = { __typename: 'PatientSummaryEmail', use: 'string', address: 'string' };

    let PatientSummaryObject = {
        __typename: 'PatientSummary',
        birthday: '01/01/1980',
        age: 43,
        gender: 'Female',
        ethnicity: 'non-hipanic',
        race: 'white',
        legalName: PatientLegalNameObject,
        phone: PatientSummaryPhoneObject,
        email: PatientSummaryEmailObject,
        address: PatientSummaryAddressObject,
    };

    let PatientProfileObject = {
        __typename: 'PatientProfile',
        id: 'string',
        local: 'string',
        shortId: 80201,
        version: 'number',
        status: 'string',
        deletable: false,
        summary: PatientSummaryObject,
        deceased: false
    };


    const profile = {patient: PatientProfileObject, summary: PatientSummaryObject};

    const deletability = resolveDeletability(profile?.patient);

    const navigate = useNavigate();

    const handleComplete = (data: DeletePatientMutation) => {
        if (data.deletePatient.__typename === 'PatientDeleteSuccessful') {
            navigate('/advanced-search');
        } else if (data.deletePatient.__typename === 'PatientDeleteFailed') {
            // display this message somewhere, data.deletePatient.reason
        }
    };

    const [deletePatient] = useDeletePatientMutation({ onCompleted: handleComplete });

    function handleDeletePatient() {
        if (profile?.patient) {
            deletePatient({
                variables: {
                    patient: profile.patient.id
                }
            });
        }
    }

    return (
        <div className="height-full main-banner">

            <div className="main-body">
                <div className="width-full text-bold flex-row display-flex flex-align-center flex-justify-left">
                    <img style={{ marginLeft: '5px' }} src={'/arrow-back.svg'}/>
                    <span className="light-title">Investigations / HIV</span>
                    <span style={{ marginLeft: '2px' }} className="dark-title">(CAS100030001GA01)</span>

                 </div>
                {profile && profile.summary && (
                    <PatientProfileSummary patient={profile.patient} summary={profile.summary} />
                )}

                {activeTab === ACTIVE_TAB.SUMMARY && <Summary patient={profile?.patient.id} />}
                {activeTab === ACTIVE_TAB.EVENT && <Events patient={profile?.patient.id} />}
                {activeTab === ACTIVE_TAB.DEMOGRAPHICS && <Demographics id={profile?.patient.id || ''} />}

                <div className="text-center margin-y-5">
                    <Button outline type={'button'} onClick={() => window.scrollTo({ top: 0, behavior: 'smooth' })}>
                        <Icon.ArrowUpward className="margin-right-1" />
                        Back to top
                    </Button>
                </div>
            </div>
        </div>
    );
};
