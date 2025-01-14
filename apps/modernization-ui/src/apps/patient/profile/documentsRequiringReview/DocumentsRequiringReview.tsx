import { DocumentRequiringReviewSortableField, SortDirection } from 'generated/graphql/schema';
import { PageProvider, usePage } from 'page';
import { useEffect } from 'react';
import { DocumentsRequiringReviewTable } from './DocumentRequiringReviewTable';
import { useDocumentsRequiringReviewApi } from './useDocumentsRequiringReviewApi';

type Props = {
    patient?: string;
};

export type Sort = {
    field: DocumentRequiringReviewSortableField;
    direction: SortDirection;
};
export const DocumentRequiringReview = ({ patient }: Props) => {
    return (
        <PageProvider pageSize={10}>
            <DocumentsRequiringReviewContainer patient={patient} />
        </PageProvider>
    );
};

const DocumentsRequiringReviewContainer = ({ patient }: Props) => {
    const documents = useDocumentsRequiringReviewApi(patient);
    const { firstPage } = usePage();

    useEffect(() => {
        if (patient) {
            firstPage();
        }
    }, [patient]);
    return <DocumentsRequiringReviewTable patient={patient} documents={documents} />;
};
