import { TableBody, TableComponent } from 'components/Table/Table';
import { format } from 'date-fns';
import { usePagination } from 'pagination/usePagination';
import { useEffect, useState } from 'react';
import { Direction } from 'sorting/Sort';
import { ClassicLink } from 'classic';
import { NoData } from 'components/NoData';
import { Columns, DocumentReview } from './ReviewDocuments';
import { SortCriteria, sort } from './DocumentRequiringReviewSorter';

const headers = [
    { name: Columns.DocumentType, sortable: true },
    { name: Columns.DateReceived, sortable: true },
    { name: Columns.ReportingFacilityProvider, sortable: false },
    { name: Columns.EventDate, sortable: true },
    { name: Columns.Description, sortable: false },
    { name: Columns.EventID, sortable: true }
];

const resolveUrl = (document: DocumentReview, patient?: string) => {
    switch (document.type) {
        case 'Morbidity Report':
            return `/nbs/api/profile/${patient}/report/morbidity/${document.id}`;
        case 'Laboratory Report':
            return `/nbs/api/profile/${patient}/report/lab/${document.id}`;
        default:
            return `/nbs/api/profile/${patient}/document/${document.id}`;
    }
};

const renderType = (document: DocumentReview, patient?: string) => {
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

const renderDateReceived = (document: DocumentReview) => {
    return (
        <span>
            {format(document?.dateReceived, 'MM/dd/yyyy')} <br />
            {format(document?.dateReceived, 'hh:mm a').toLowerCase()}
        </span>
    );
};

const renderReportingFacility = (document: DocumentReview) => {
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

const renderEventDate = (document: DocumentReview) => {
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

const renderDescriptions = (document: DocumentReview) => {
    return document.descriptions.map((d, key) => (
        <div key={key}>
            <strong>{d?.title}</strong>
            <br />
            <span>{d?.value}</span>
        </div>
    ));
};

const renderIdLink = (document: DocumentReview) => {
    return (
        <>
            <span>{document.localId}</span>
        </>
    );
};

const asTableBody = (document: DocumentReview, patient: string): TableBody => ({
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

const asTableBodies = (documents: DocumentReview[], patient: string): TableBody[] =>
    documents?.map((d) => asTableBody(d, patient)) || [];

export const DocumentsRequiringReviewTable = ({
    documents,
    patient
}: {
    documents: DocumentReview[] | undefined;
    patient: string | undefined;
}) => {
    const { page, request } = usePagination();
    const [bodies, setBodies] = useState<TableBody[]>([]);
    const [criteria, setCriteria] = useState<SortCriteria>({});

    useEffect(() => {
        if (documents && patient) {
            const sorted = sort(documents, criteria);
            setBodies(asTableBodies(sorted, patient));
        }
    }, [documents, criteria]);

    const handleSort = (name: string, direction: string): void => {
        setCriteria({ name: name as Columns, type: direction as Direction });
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
