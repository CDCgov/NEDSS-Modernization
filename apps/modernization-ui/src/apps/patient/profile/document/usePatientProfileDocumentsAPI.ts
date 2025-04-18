import { useEffect, useState } from 'react';
import { FindDocumentsForPatientQuery, useFindDocumentsForPatientLazyQuery } from 'generated/graphql/schema';
import { usePagination, Status } from 'pagination';
import { Document } from './PatientDocuments';
import { transform } from './PatientDocumentTransformer';

/**
 * Responds to "current" state changes of the PageContext by executing an API request for Patient Documents.  Upon completion the PageContext is updated with the total count.
 *
 * @param {string} patient The unique identifier of a patient
 * @return {Document[]} The currently resolved documents for the page and patient
 */
export const usePatientProfileDocumentsAPI = (patient?: string) => {
    const [documents, setDocuments] = useState<Document[]>([]);

    const { page, ready } = usePagination();

    const handleComplete = (data: FindDocumentsForPatientQuery) => {
        const total = data?.findDocumentsForPatient?.total || 0;
        const pageNumber = data?.findDocumentsForPatient?.number || 0;
        ready(total, pageNumber + 1);

        const content = transform(data?.findDocumentsForPatient);

        setDocuments(content);
    };

    const [getDocuments] = useFindDocumentsForPatientLazyQuery({ onCompleted: handleComplete });

    useEffect(() => {
        if (patient && page.status === Status.Requested) {
            getDocuments({
                variables: {
                    patient: patient,
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
