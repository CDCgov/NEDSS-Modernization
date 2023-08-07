import { TableBody, TableComponent } from 'components/Table/Table';
import { format } from 'date-fns';
import { DocumentRequiringReview, DocumentRequiringReviewSortableField, SortDirection } from 'generated/graphql/schema';
import { usePage } from 'page/usePage';
import { Dispatch, SetStateAction, useEffect, useState } from 'react';
import { Direction } from 'sorting/Sort';
import { Sort } from './DocumentsRequiringReview';

enum Headers {
    DocumentType = 'Document type',
    DateReceived = 'Date received',
    ReportingFacilityProvider = 'Reporting facility / provider',
    EventDate = 'Event date',
    Description = 'Description',
    EventID = 'Event #'
}

const headers = [
    { name: Headers.DocumentType, sortable: true },
    { name: Headers.DateReceived, sortable: true },
    { name: Headers.ReportingFacilityProvider, sortable: false },
    { name: Headers.EventDate, sortable: true },
    { name: Headers.Description, sortable: false },
    { name: Headers.EventID, sortable: true }
];

const renderType = (document: DocumentRequiringReview) => {
    return (
        <>
            <span>{document.type}</span>
            {document.isElectronic ? (
                <>
                    <br />
                    (Electronic)
                </>
            ) : null}
        </>
    );
};

const renderDateReceived = (document: DocumentRequiringReview) => {
    return (
        <span>
            {format(new Date(document?.dateReceived), 'MM/dd/yyyy')} <br />
            {format(new Date(document?.dateReceived), 'hh:mm a').toLowerCase()}
        </span>
    );
};

const renderReportingFacility = (document: DocumentRequiringReview) => {
    return (
        <>
            {document.facilityProviders.length === 0 ? (
                <>
                    <span className="no-data">No data</span>
                </>
            ) : (
                <>
                    {document.facilityProviders.map((fp, key) => (
                        <div key={key}>
                            <strong>{fp?.title}</strong>
                            <br />
                            <span>{fp?.name}</span>
                        </div>
                    ))}
                </>
            )}
        </>
    );
};

const renderEventDate = (document: DocumentRequiringReview) => {
    return (
        <span>
            {format(new Date(document?.eventDate), 'MM/dd/yyyy')} <br />
            {format(new Date(document?.eventDate), 'hh:mm a').toLowerCase()}
        </span>
    );
};

const renderDescriptions = (document: DocumentRequiringReview) => {
    return (
        <>
            {document.descriptions.length === 0 ? (
                <>
                    <span className="no-data">No data</span>
                </>
            ) : (
                <>
                    {document.descriptions.map((d, key) => (
                        <div key={key}>
                            <strong>{d?.title}</strong>
                            <br />
                            <span>{d?.value}</span>
                        </div>
                    ))}
                </>
            )}
        </>
    );
};

const renderIdLink = (document: DocumentRequiringReview) => {
    return (
        <>
            <span>{document.localId}</span>
        </>
    );
};

const asTableBody = (document: DocumentRequiringReview): TableBody => ({
    id: document.id,
    checkbox: false,
    tableDetails: [
        {
            id: 1,
            title: renderType(document)
        },
        {
            id: 2,
            title: renderDateReceived(document)
        },
        {
            id: 3,
            title: renderReportingFacility(document)
        },
        {
            id: 4,
            title: renderEventDate(document)
        },
        {
            id: 5,
            title: renderDescriptions(document)
        },
        {
            id: 6,
            title: renderIdLink(document)
        }
    ]
});

const asTableBodies = (documents: DocumentRequiringReview[]): TableBody[] =>
    documents?.map((d) => asTableBody(d)) || [];

export const DocumentsRequiringReviewTable = ({
    documents,
    setSort
}: {
    documents: DocumentRequiringReview[] | undefined;
    setSort: Dispatch<SetStateAction<Sort | undefined>>;
}) => {
    const { page, request } = usePage();
    const [bodies, setBodies] = useState<TableBody[]>([]);

    useEffect(() => {
        if (documents) {
            setBodies(asTableBodies(documents));
        }
    }, [documents]);

    const handleSort = (field: string, direction: Direction) => {
        let sortField: DocumentRequiringReviewSortableField;
        switch (field) {
            case Headers.DocumentType:
                sortField = DocumentRequiringReviewSortableField.Type;
                break;
            case Headers.DateReceived:
                sortField = DocumentRequiringReviewSortableField.DateReceived;
                break;
            case Headers.EventDate:
                sortField = DocumentRequiringReviewSortableField.EventDate;
                break;
            case Headers.EventID:
                sortField = DocumentRequiringReviewSortableField.LocalId;
                break;
            default:
                return;
        }
        const sortDirection = direction === Direction.Ascending ? SortDirection.Asc : SortDirection.Desc;
        setSort({ field: sortField, direction: sortDirection });
    };

    return (
        <div className="margin-top-6 margin-bottom-2 flex-row common-card">
            <TableComponent
                tableHeader={'Documents requiring review'}
                tableHead={headers}
                tableBody={bodies}
                isPagination={true}
                pageSize={page.pageSize}
                totalResults={page.total}
                currentPage={page.current}
                handleNext={request}
                sortData={handleSort}
            />
        </div>
    );
};
