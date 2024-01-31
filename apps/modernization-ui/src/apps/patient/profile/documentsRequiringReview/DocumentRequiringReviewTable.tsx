import { TableBody, TableComponent } from 'components/Table/Table';
import { format } from 'date-fns';
import { DocumentRequiringReview, DocumentRequiringReviewSortableField, SortDirection } from 'generated/graphql/schema';
import { usePage } from 'page/usePage';
import { Dispatch, SetStateAction, useEffect, useState } from 'react';
import { Direction } from 'sorting/Sort';
import { Sort } from './DocumentsRequiringReview';
import { ClassicLink } from 'classic';
import { NoData } from 'components/NoData';

enum Columns {
    DocumentType = 'Document type',
    DateReceived = 'Date received',
    ReportingFacilityProvider = 'Reporting facility / provider',
    EventDate = 'Event date',
    Description = 'Description',
    EventID = 'Event #'
}

const headers = [
    { name: Columns.DocumentType, sortable: true },
    { name: Columns.DateReceived, sortable: true },
    { name: Columns.ReportingFacilityProvider, sortable: false },
    { name: Columns.EventDate, sortable: true },
    { name: Columns.Description, sortable: false },
    { name: Columns.EventID, sortable: true }
];

const resolveUrl = (document: DocumentRequiringReview, patient?: string) => {
    switch (document.type) {
        case 'Morbidity Report':
            return `/nbs/api/profile/${patient}/report/morbidity/${document.id}`;
        case 'Laboratory Report':
            return `/nbs/api/profile/${patient}/report/lab/${document.id}`;
        default:
            return `/nbs/api/profile/${patient}/document/${document.id}`;
    }
};

const renderType = (document: DocumentRequiringReview, patient?: string) => {
    const url = resolveUrl(document, patient);
    return (
        <>
            {url && <ClassicLink url={url}>{document.type}</ClassicLink>}
            {document.isElectronic && (
                <>
                    <br />
                    (Electronic)
                </>
            )}
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
            {document.facilityProviders.reportingFacility ? (
                <>
                    <div>
                        <strong>Reporting facility</strong>
                        <br />
                        <span>{document.facilityProviders.reportingFacility.name}</span>
                    </div>
                </>
            ) : null}
            {document.facilityProviders.orderingProvider ? (
                <>
                    <div>
                        <strong>Ordering provider</strong>
                        <br />
                        <span>{document.facilityProviders.orderingProvider.name}</span>
                    </div>
                </>
            ) : null}
            {document.facilityProviders.sendingFacility ? (
                <>
                    <div>
                        <strong>Sending facility</strong>
                        <br />
                        <span>{document.facilityProviders.sendingFacility.name}</span>
                    </div>
                </>
            ) : null}
            {document.facilityProviders.orderingProvider === undefined &&
            document.facilityProviders.reportingFacility === undefined ? (
                <NoData />
            ) : null}
        </>
    );
};

const renderEventDate = (document: DocumentRequiringReview) => {
    return (
        <span>
            {document.eventDate ? (
                <>
                    {format(new Date(document?.eventDate), 'MM/dd/yyyy')} <br />
                    {format(new Date(document?.eventDate), 'hh:mm a').toLowerCase()}
                </>
            ) : (
                <>No date</>
            )}
        </span>
    );
};

const renderDescriptions = (document: DocumentRequiringReview) => {
    return document.descriptions.map((d, key) => (
        <div key={key}>
            <strong>{d?.title}</strong>
            <br />
            <span>{d?.value}</span>
        </div>
    ));
};

const renderIdLink = (document: DocumentRequiringReview) => {
    return (
        <>
            <span>{document.localId}</span>
        </>
    );
};

const asTableBody = (document: DocumentRequiringReview, patient: string): TableBody => ({
    id: document.id,
    tableDetails: [
        {
            id: 1,
            title: renderType(document, patient)
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

const asTableBodies = (documents: DocumentRequiringReview[], patient: string): TableBody[] =>
    documents?.map((d) => asTableBody(d, patient)) || [];

export const DocumentsRequiringReviewTable = ({
    documents,
    patient,
    setSort
}: {
    documents: DocumentRequiringReview[] | undefined;
    patient: string | undefined;
    setSort: Dispatch<SetStateAction<Sort | undefined>>;
}) => {
    const { page, request } = usePage();
    const [bodies, setBodies] = useState<TableBody[]>([]);

    useEffect(() => {
        if (documents && patient) {
            setBodies(asTableBodies(documents, patient));
        }
    }, [documents]);

    const handleSort = (field: string, direction: Direction) => {
        let sortField: DocumentRequiringReviewSortableField;
        switch (field) {
            case Columns.DocumentType:
                sortField = DocumentRequiringReviewSortableField.Type;
                break;
            case Columns.DateReceived:
                sortField = DocumentRequiringReviewSortableField.DateReceived;
                break;
            case Columns.EventDate:
                sortField = DocumentRequiringReviewSortableField.EventDate;
                break;
            case Columns.EventID:
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
