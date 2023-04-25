import {
    FindInvestigationsByFilterQuery,
    FindLabReportsByFilterQuery,
    FindMorbidityReportsForPatientQuery
} from '../../generated/graphql/schema';
import { Config } from 'config';
import { PatientTreatmentTable } from 'patient/profile/treatment';
import { PatientNamedByContactTable, ContactNamedByPatientTable } from 'patient/profile/contact';
import { PatientDocumentTable } from 'patient/profile/document';
import { TOTAL_TABLE_DATA } from 'utils/util';
import { PatientInvestigationsTable } from 'patient/profile/investigation';
import { MorbidityTable } from 'patient/profile/morbidity';
import { LabReportTable } from 'patient/profile/labReport';
import { VaccinationTable } from 'patient/profile/vaccination';

type EventTabProp = {
    patient: string | undefined;
    investigationData?: FindInvestigationsByFilterQuery['findInvestigationsByFilter'];
    labReports?: FindLabReportsByFilterQuery['findLabReportsByFilter'] | undefined;
    morbidityData?: FindMorbidityReportsForPatientQuery['findMorbidityReportsForPatient'] | undefined;
    profileData?: any;
};

export const Events = ({ patient }: EventTabProp) => {
    const NBS_URL = Config.nbsUrl;

    return (
        <>
            <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                <PatientInvestigationsTable patient={patient} pageSize={TOTAL_TABLE_DATA} nbsBase={NBS_URL} />
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
            <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                <PatientDocumentTable patient={patient} pageSize={TOTAL_TABLE_DATA} nbsBase={NBS_URL} />
            </div>

            <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                <ContactNamedByPatientTable patient={patient} />
            </div>

            <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                <PatientNamedByContactTable patient={patient} />
            </div>
        </>
    );
};
