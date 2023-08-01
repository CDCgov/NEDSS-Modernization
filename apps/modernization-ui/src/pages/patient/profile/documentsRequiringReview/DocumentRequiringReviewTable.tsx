import { TableBody, TableComponent } from 'components/Table/Table';
import { format } from 'date-fns';
import { DocumentRequiringReview } from 'generated/graphql/schema';
import { usePage } from 'page/usePage';
import { useEffect, useState } from 'react';
import { Direction } from 'sorting/Sort';

enum Headers {
    DocumentType = 'Document type',
    DateReceived = 'Date received',
    ReportingFacilityProvider = 'Reporting facility / provider',
    EventDate = 'Event date',
    Description = 'Description with',
    EventID = 'Event #'
}

const headers = [
    { name: Headers.DocumentType, sortable: true },
    { name: Headers.DateReceived, sortable: true },
    { name: Headers.ReportingFacilityProvider, sortable: true },
    { name: Headers.EventDate, sortable: true },
    { name: Headers.Description, sortable: true },
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
            {format(new Date(document?.dateReceived), 'MM / dd / yyyy')} <br />{' '}
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
            {format(new Date(document?.eventDate), 'MM / dd / yyyy')} <br />{' '}
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

export const DocumentsRequiringReviewTable = ({ documents }: { documents: DocumentRequiringReview[] | undefined }) => {
    const { page, request } = usePage();
    const [bodies, setBodies] = useState<TableBody[]>([]);

    useEffect(() => {
        if (documents) {
            setBodies(asTableBodies(documents));
        }
    }, [documents]);

    const handleSort = (name: string, type: Direction) => {
        console.log('sort', name, type);
    };

    return (
        <div className="margin-top-6 margin-bottom-2 flex-row common-card">
            <TableComponent
                tableHeader={'Open Investigations'}
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
