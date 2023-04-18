import { useEffect, useState } from 'react';
import {
    useFindDocumentsRequiringReviewForPatientLazyQuery,
    useFindOpenInvestigationsForPatientLazyQuery
} from '../../generated/graphql/schema';
import { TOTAL_TABLE_DATA } from '../../utils/util';
import { OpenInvestigations } from './components/SummaryTables/OpenInvestigations';
import { DocumentsReview } from './components/SummaryTables/DocumentsReview';

type SummaryProp = {
    profileData: any;
};

export const Summary = ({ profileData }: SummaryProp) => {
    const [getAllInvestigations, { data: openInvestigationData }] = useFindOpenInvestigationsForPatientLazyQuery();
    const [getDcouments, { data: documentData }] = useFindDocumentsRequiringReviewForPatientLazyQuery();

    const [currentPage] = useState<number>(1);
    const [documentCurrentPage] = useState<number>(1);

    useEffect(() => {
        if (profileData) {
            getAllInvestigations({
                variables: {
                    patientId: parseInt(profileData.id),
                    page: {
                        pageNumber: currentPage - 1,
                        pageSize: TOTAL_TABLE_DATA
                    }
                }
            });
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
            <OpenInvestigations
                investigations={openInvestigationData?.findOpenInvestigationsForPatient?.content}
                totalInvestigations={openInvestigationData?.findOpenInvestigationsForPatient?.total}
            />

            <DocumentsReview
                documents={documentData?.findDocumentsRequiringReviewForPatient?.content}
                totalDocuments={documentData?.findDocumentsRequiringReviewForPatient?.total}
            />
        </>
    );
};
