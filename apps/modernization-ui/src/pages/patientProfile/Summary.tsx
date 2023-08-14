import { PatientOpenInvestigationsTable } from 'pages/patient/profile/investigation/PatientOpenInvestigationsTable';
import { TOTAL_TABLE_DATA } from '../../utils/util';
import { DocumentRequiringReview } from '../patient/profile/documentsRequiringReview/DocumentsRequiringReview';

type SummaryProp = {
    patient: string | undefined;
};

export const Summary = ({ patient }: SummaryProp) => {
    return (
        <>
            <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                <PatientOpenInvestigationsTable patient={patient} pageSize={TOTAL_TABLE_DATA} />
            </div>
            <DocumentRequiringReview patient={patient} />
        </>
    );
};
