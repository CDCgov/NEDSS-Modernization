import { DocumentRequiringReviewSortableField, SortDirection } from 'generated/graphql/schema';
import { PaginationProvider, usePagination } from 'pagination';
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
        <PaginationProvider pageSize={10}>
            <DocumentsRequiringReviewContainer patient={patient} />
        </PaginationProvider>
    );
};

const DocumentsRequiringReviewContainer = ({ patient }: Props) => {
    const documents = useDocumentsRequiringReviewApi(patient);
    const { firstPage } = usePagination();

    useEffect(() => {
        if (patient) {
            firstPage();
        }
    }, [patient]);
    return <DocumentsRequiringReviewTable patient={patient} documents={documents} />;
};
