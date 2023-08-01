import { useEffect, useState } from 'react';
import {
    DocumentRequiringReview,
    FindDocumentsRequiringReviewForPatientQuery,
    useFindDocumentsRequiringReviewForPatientLazyQuery
} from 'generated/graphql/schema';
import { usePage, Status } from 'page';

export const useDocumentsRequiringReviewApi = (patient?: string) => {
    const [documents, setDocuments] = useState<DocumentRequiringReview[]>();

    const { page, ready } = usePage();

    const handleComplete = (data: FindDocumentsRequiringReviewForPatientQuery) => {
        const total = data?.findDocumentsRequiringReviewForPatient?.total || 0;
        const pageNumber = data?.findDocumentsRequiringReviewForPatient?.number || 0;
        ready(total, pageNumber + 1);

        setDocuments(data.findDocumentsRequiringReviewForPatient.content as DocumentRequiringReview[]);
    };

    const [getDocuments] = useFindDocumentsRequiringReviewForPatientLazyQuery({ onCompleted: handleComplete });

    useEffect(() => {
        if (patient && page.status === Status.Requested) {
            getDocuments({
                variables: {
                    patient: parseInt(patient),
                    page: {
                        pageNumber: page.current - 1,
                        pageSize: page.pageSize
                    }
                }
            });
        }
    }, [patient, page]);

    return documents;
};
