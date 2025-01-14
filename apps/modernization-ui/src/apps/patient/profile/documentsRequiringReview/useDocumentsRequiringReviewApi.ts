import { useEffect, useState } from 'react';
import {
    FindDocumentsRequiringReviewForPatientQuery,
    useFindDocumentsRequiringReviewForPatientLazyQuery
} from 'generated/graphql/schema';
import { usePage, Status } from 'page';
import { transform } from './DocumentRequiringReviewTransformer';
import { DocumentReview } from './ReviewDocuments';

export const useDocumentsRequiringReviewApi = (patient?: string) => {
    const [documents, setDocuments] = useState<DocumentReview[]>();

    const { page, ready } = usePage();

    const handleComplete = (data: FindDocumentsRequiringReviewForPatientQuery) => {
        const total = data?.findDocumentsRequiringReviewForPatient?.total || 0;
        const pageNumber = data?.findDocumentsRequiringReviewForPatient?.number || 0;
        ready(total, pageNumber + 1);

        const content = transform(data?.findDocumentsRequiringReviewForPatient);

        setDocuments(content);
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
