import {
    FindInvestigationsByFilterQuery,
    FindLabReportsByFilterQuery,
    FindMorbidityReportsForPatientQuery
} from '../../generated/graphql/schema';
import { PatientTreatmentTable } from 'pages/patient/profile/treatment';
import { PatientNamedByContactTable, ContactNamedByPatientTable } from 'pages/patient/profile/contact';
import { PatientProfileDocuments } from 'pages/patient/profile/document';
import { TOTAL_TABLE_DATA } from 'utils/util';
import { PatientInvestigationsTable } from 'pages/patient/profile/investigation';
import { MorbidityTable } from 'pages/patient/profile/morbidity';
import { LabReportTable } from 'pages/patient/profile/labReport';
import { VaccinationTable } from 'pages/patient/profile/vaccination';

type EventTabProp = {
    patient: string | undefined;
    investigationData?: FindInvestigationsByFilterQuery['findInvestigationsByFilter'];
    labReports?: FindLabReportsByFilterQuery['findLabReportsByFilter'] | undefined;
    morbidityData?: FindMorbidityReportsForPatientQuery['findMorbidityReportsForPatient'] | undefined;
    profileData?: any;
};

export const Events = ({ patient }: EventTabProp) => {
    return (
        <>
            <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                <PatientInvestigationsTable patient={patient} pageSize={TOTAL_TABLE_DATA} />
            </div>
            <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                <LabReportTable patient={patient} />
            </div>

            <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                <MorbidityTable patient={patient} />
            </div>

            <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                <VaccinationTable patient={patient} />
            </div>
            <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                <PatientTreatmentTable patient={patient} />
            </div>

            <PatientProfileDocuments patient={patient} pageSize={TOTAL_TABLE_DATA} />

            <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                <ContactNamedByPatientTable patient={patient} />
            </div>

            <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                <PatientNamedByContactTable patient={patient} />
            </div>
        </>
    );
};
