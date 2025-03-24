import { PatientTreatmentTable } from 'apps/patient/profile/treatment';
import {
    PatientProfileContactsNamedByPatient,
    PatientProfilePatientNamedByContact
} from 'apps/patient/profile/contact';
import { PatientProfileDocuments } from 'apps/patient/profile/document';
import { TOTAL_TABLE_DATA } from 'utils/util';
import { PatientInvestigationsTable } from 'apps/patient/profile/investigation';
import { MorbidityTable } from 'apps/patient/profile/morbidity';
import { LabReportTable } from 'apps/patient/profile/labReport';
import { PatientProfileVaccinations } from 'apps/patient/profile/vaccination';
import { ClassicModalProvider } from 'classic/ClassicModalContext';
import { useParams } from 'react-router';
import { usePatientProfile } from './usePatientProfile';

export const Events = () => {
    const { id } = useParams();
    const { patient } = usePatientProfile(id);
    return (
        <div role="tabpanel" id="events-tabpanel">
            <ClassicModalProvider>
                <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                    <PatientInvestigationsTable
                        patient={patient?.id}
                        pageSize={TOTAL_TABLE_DATA}
                        allowAdd={patient?.status === 'ACTIVE'}
                    />
                </div>
                <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                    <LabReportTable
                        patient={patient?.id}
                        pageSize={TOTAL_TABLE_DATA}
                        allowAdd={patient?.status === 'ACTIVE'}
                    />
                </div>

                <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                    <MorbidityTable
                        patient={patient?.id}
                        pageSize={TOTAL_TABLE_DATA}
                        allowAdd={patient?.status === 'ACTIVE'}
                    />
                </div>

                <PatientProfileVaccinations
                    patient={patient?.id}
                    pageSize={TOTAL_TABLE_DATA}
                    allowAdd={patient?.status === 'ACTIVE'}
                />
                <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                    <PatientTreatmentTable patient={patient?.id} />
                </div>

                <PatientProfileDocuments patient={patient?.id} pageSize={TOTAL_TABLE_DATA} />

                <PatientProfileContactsNamedByPatient patient={patient?.id} pageSize={TOTAL_TABLE_DATA} />

                <PatientProfilePatientNamedByContact patient={patient?.id} pageSize={TOTAL_TABLE_DATA} />
            </ClassicModalProvider>
        </div>
    );
};
