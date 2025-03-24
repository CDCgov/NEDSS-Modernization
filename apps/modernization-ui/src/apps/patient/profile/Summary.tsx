import { PatientOpenInvestigationsTable } from 'apps/patient/profile/investigation/PatientOpenInvestigationsTable';
import { TOTAL_TABLE_DATA } from 'utils/util';
import { DocumentRequiringReview } from 'apps/patient/profile/documentsRequiringReview/DocumentsRequiringReview';
import { useParams } from 'react-router';
import { usePatientProfile } from './usePatientProfile';

export const Summary = () => {
    const { id } = useParams();
    const { patient } = usePatientProfile(id);

    return (
        <div role="tabpanel" id="summary-tabpanel">
            <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                <PatientOpenInvestigationsTable patient={patient?.id} pageSize={TOTAL_TABLE_DATA} />
            </div>
            <DocumentRequiringReview patient={patient?.id} />
        </div>
    );
};
