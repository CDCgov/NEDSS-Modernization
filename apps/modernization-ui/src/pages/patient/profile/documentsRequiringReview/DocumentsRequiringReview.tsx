import { useEffect } from 'react';
import { PageProvider, usePage } from 'page';
import { DocumentsRequiringReviewTable } from './DocumentRequiringReviewTable';
import { useDocumentsRequiringReviewApi } from './useDocumentsRequiringReviewApi';

type Props = {
    patient?: string;
};
export const DocumentRequiringReview = ({ patient }: Props) => {
    return (
        <PageProvider pageSize={10}>
            <DocumentsRequiringReviewWrapper patient={patient} />
        </PageProvider>
    );
};

const DocumentsRequiringReviewWrapper = ({ patient }: Props) => {
    const documents = useDocumentsRequiringReviewApi(patient);
    const { firstPage } = usePage();

    useEffect(() => {
        if (patient) {
            firstPage();
        }
    }, [patient]);
    return <DocumentsRequiringReviewTable documents={documents} />;
};
