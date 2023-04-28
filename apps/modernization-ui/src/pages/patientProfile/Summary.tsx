import { useEffect, useState } from 'react';
import { useFindDocumentsRequiringReviewForPatientLazyQuery } from '../../generated/graphql/schema';
import { TOTAL_TABLE_DATA } from '../../utils/util';
import { Config } from 'config';
import { DocumentsReview } from './components/SummaryTables/DocumentsReview';
import { PatientOpenInvestigationsTable } from 'pages/patient/profile/investigation/PatientOpenInvestigationsTable';

type SummaryProp = {
    profileData: any;
};

export const Summary = ({ profileData }: SummaryProp) => {
    const [getDcouments, { data: documentData }] = useFindDocumentsRequiringReviewForPatientLazyQuery();

    const [currentPage] = useState<number>(1);
    const [documentCurrentPage] = useState<number>(1);

    useEffect(() => {
        if (profileData) {
            getDcouments({
                variables: {
                    patientId: parseInt(profileData.id),
                    page: {
                        pageNumber: documentCurrentPage - 1,
                        pageSize: TOTAL_TABLE_DATA
                    }
                }
            });
        }
    }, [profileData, currentPage]);

    return (
        <>
            <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                <PatientOpenInvestigationsTable
                    patient={profileData?.id}
                    pageSize={TOTAL_TABLE_DATA}
                    nbsBase={Config.nbsUrl}
                />
            </div>
            <DocumentsReview
                documents={documentData?.findDocumentsRequiringReviewForPatient?.content}
                totalDocuments={documentData?.findDocumentsRequiringReviewForPatient?.total}
            />
        </>
    );
};
