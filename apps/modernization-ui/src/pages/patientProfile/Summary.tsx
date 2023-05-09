import { useEffect, useState } from 'react';
import { useFindDocumentsRequiringReviewForPatientLazyQuery } from '../../generated/graphql/schema';
import { TOTAL_TABLE_DATA } from '../../utils/util';
import { DocumentsReview } from './components/SummaryTables/DocumentsReview';
import { PatientOpenInvestigationsTable } from 'pages/patient/profile/investigation/PatientOpenInvestigationsTable';

type SummaryProp = {
    patient: string | undefined;
};

export const Summary = ({ patient }: SummaryProp) => {
    const [getDcouments, { data: documentData }] = useFindDocumentsRequiringReviewForPatientLazyQuery();

    const [currentPage] = useState<number>(1);
    const [documentCurrentPage] = useState<number>(1);

    useEffect(() => {
        if (patient) {
            getDcouments({
                variables: {
                    patientId: parseInt(patient),
                    page: {
                        pageNumber: documentCurrentPage - 1,
                        pageSize: TOTAL_TABLE_DATA
                    }
                }
            });
        }
    }, [patient, currentPage]);

    return (
        <>
            <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                <PatientOpenInvestigationsTable patient={patient} pageSize={TOTAL_TABLE_DATA} />
            </div>
            <DocumentsReview
                documents={documentData?.findDocumentsRequiringReviewForPatient?.content}
                totalDocuments={documentData?.findDocumentsRequiringReviewForPatient?.total}
            />
        </>
    );
};
