import { useEffect, useState } from 'react';
import { format } from 'date-fns';
import { Direction } from 'sorting';
import { usePagination } from 'pagination';
import { ClassicLink } from 'classic';
import { TableBody, TableComponent } from 'components/Table/Table';
import { Document, AssociatedWith, Headers } from './PatientDocuments';
import { SortCriteria, sort } from './PatientDocumentSorter';

const headers = [
    { name: Headers.DateReceived, sortable: true },
    { name: Headers.Type, sortable: true },
    { name: Headers.SendingFacility, sortable: true },
    { name: Headers.DateReported, sortable: true },
    { name: Headers.Condition, sortable: true },
    { name: Headers.AssociatedWith, sortable: true },
    { name: Headers.EventID, sortable: true }
];

const displayReceivedOn = (patient: string, document: Document) => (
    <ClassicLink url={`/nbs/api/profile/${patient}/document/${document.document}`}>
        {format(document.receivedOn, 'MM/dd/yyyy')} <br /> {format(document.receivedOn, 'hh:mm a')}
    </ClassicLink>
);

const association = (patient: string, association: AssociatedWith) => (
    <div>
        <ClassicLink url={`/nbs/api/profile/${patient}/document/${association.id}`}>{association?.local}</ClassicLink>
    </div>
);

const asTableBody =
    (patient: string) =>
    (document: Document): TableBody => ({
        id: document?.event,
        tableDetails: [
            {
                id: 1,
                title: displayReceivedOn(patient, document)
            },
            {
                id: 2,
                title: document?.type
            },
            { id: 3, title: document?.sendingFacility || null },
            { id: 4, title: format(new Date(document?.reportedOn), 'MM/dd/yyyy') },
            { id: 5, title: document?.condition || null },
            {
                id: 6,
                title: document?.associatedWith && association(patient, document.associatedWith)
            },
            { id: 7, title: document?.event || null }
        ]
    });

const asTableBodies = (patient: string, documents: Document[]): TableBody[] =>
    documents?.map(asTableBody(patient)) || [];

type Props = {
    patient?: string;
    documents: Document[];
};

export const PatientDocumentTable = ({ patient, documents }: Props) => {
    const { page, request } = usePagination();
    const [criteria, setCriteria] = useState<SortCriteria>({});

    const [bodies, setBodies] = useState<TableBody[]>([]);

    useEffect(() => {
        if (patient) {
            const sorted = sort(documents, criteria);
            setBodies(asTableBodies(patient, sorted));
        }
    }, [documents, criteria]);

    const handleSort = (name: string, direction: string): void => {
        setCriteria({ name: name as Headers, type: direction as Direction });
    };

    return (
        <TableComponent
            tableHeader={'Documents'}
            tableHead={headers}
            tableBody={bodies}
            isPagination={true}
            pageSize={page.pageSize}
            totalResults={page.total}
            currentPage={page.current}
            handleNext={request}
            sortData={handleSort}
        />
    );
};
