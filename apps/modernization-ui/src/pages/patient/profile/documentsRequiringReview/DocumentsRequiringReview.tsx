import { DocumentRequiringReviewSortableField, SortDirection } from 'generated/graphql/schema';
import { PageProvider, usePage } from 'page';
import { useEffect, useState } from 'react';
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
    const [sort, setSort] = useState<Sort>();
    const documents = useDocumentsRequiringReviewApi(patient, sort);
    const { firstPage } = usePage();

    useEffect(() => {
        if (patient) {
            firstPage();
        }
    }, [patient, sort]);
    return <DocumentsRequiringReviewTable setSort={setSort} documents={documents} />;
};
