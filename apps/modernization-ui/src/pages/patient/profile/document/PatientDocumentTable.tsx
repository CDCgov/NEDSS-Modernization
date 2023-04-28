import { TableBody, TableComponent } from 'components/Table/Table';
import { useEffect, useState } from 'react';
import format from 'date-fns/format';
import { Document, AssociatedWith, Headers } from './PatientDocuments';
import { sort } from './PatientDocumentSorter';
import { Direction } from 'sorting';
import { usePage } from 'page';
import { goToPage } from 'page/page';

const headers = [
    { name: Headers.DateReceived, sortable: true },
    { name: Headers.Type, sortable: true },
    { name: Headers.SendingFacility, sortable: true },
    { name: Headers.DateReported, sortable: true },
    { name: Headers.Condition, sortable: true },
    { name: Headers.AssociatedWith, sortable: true },
    { name: Headers.EventID, sortable: true }
];

const association = (association?: AssociatedWith | null) =>
    association && (
        <>
            <div>
                <p className="margin-0 text-primary text-bold link" style={{ wordBreak: 'break-word' }}>
                    {association.local}
                </p>
            </div>
        </>
    );

const asTableBody =
    (nbsBase: string) =>
    (document: Document): TableBody => ({
        id: document?.event,
        checkbox: false,
        tableDetails: [
            {
                id: 1,
                title: (
                    <>
                        {format(document?.receivedOn, 'MM/dd/yyyy')} <br /> {format(document?.receivedOn, 'hh:mm a')}
                    </>
                ),
                class: 'link',
                link: `${nbsBase}/ViewFile1.do?ContextAction=DocumentIDOnEvents&nbsDocumentUid=${document?.document}`
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
                title: association(document?.associatedWith)
            },
            { id: 7, title: document?.event || null }
        ]
    });

const asTableBodies = (nbsBase: string, documents: Document[]): TableBody[] =>
    documents?.map(asTableBody(nbsBase)) || [];

type Props = {
    nbsBase: string;
    documents: Document[];
};

export const PatientDocumentTable = ({ nbsBase, documents }: Props) => {
    const { page, dispatch } = usePage();

    const [bodies, setBodies] = useState<TableBody[]>([]);

    useEffect(() => {
        const sorted = sort(documents, {});
        setBodies(asTableBodies(nbsBase, sorted));
    }, [documents]);

    const handleSort = (name: string, direction: string): void => {
        const criteria = { name: name as Headers, type: direction as Direction };
        const sorted = sort(documents, criteria);
        setBodies(asTableBodies(nbsBase, sorted));
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
            handleNext={goToPage(dispatch)}
            sortData={handleSort}
        />
    );
};
