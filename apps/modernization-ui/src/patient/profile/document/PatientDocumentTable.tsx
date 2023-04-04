import { TableBody, TableComponent } from 'components/Table/Table';
import { useEffect, useState } from 'react';
import format from 'date-fns/format';
import { Document, AssociatedWith } from './PatientDocuments';
import { FindDocumentsForPatientQuery, useFindDocumentsForPatientLazyQuery } from 'generated/graphql/schema';
import { Config } from 'config';

const NBS_URL = Config.nbsUrl;

export type Result = FindDocumentsForPatientQuery['findDocumentsForPatient'];

const headers = [
    { name: 'Date received', sortable: true },
    { name: 'Type', sortable: true },
    { name: 'Sending facility', sortable: true },
    { name: 'Date reported', sortable: true },
    { name: 'Condition', sortable: true },
    { name: 'Associated with', sortable: true },
    { name: 'Event ID', sortable: true }
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

const asTableBody = (document: Document | null): TableBody => ({
    id: document?.event,
    checkbox: false,
    tableDetails: [
        {
            id: 1,
            title: (
                <>
                    {format(new Date(document?.receivedOn), 'MM/dd/yyyy')} <br />{' '}
                    {format(new Date(document?.receivedOn), 'hh:mm a')}
                </>
            ),
            class: 'link',
            link: `${NBS_URL}/nbs/ViewFile1.do?ContextAction=DocumentIDOnEvents&nbsDocumentUid=${document?.document}`
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

const asTableBodies = (documents: Result): TableBody[] => documents?.content?.map(asTableBody) || [];

type Props = {
    patient?: string;
    pageSize: number;
};

export const PatientDocumentTable = ({ patient, pageSize }: Props) => {
    const [currentPage, setCurrentPage] = useState<number>(1);
    const [total, setTotal] = useState<number>(0);
    const [tableBodies, setTableBodies] = useState<TableBody[]>([]);

    const handleComplete = (data: FindDocumentsForPatientQuery) => {
        const total = data?.findDocumentsForPatient?.total || 0;
        setTotal(total);

        const bodies = asTableBodies(data.findDocumentsForPatient);
        setTableBodies(bodies);
    };

    const [getDocuments] = useFindDocumentsForPatientLazyQuery({ onCompleted: handleComplete });

    useEffect(() => {
        if (patient) {
            getDocuments({
                variables: {
                    patient: patient,
                    page: {
                        pageNumber: currentPage - 1,
                        pageSize
                    }
                }
            });
        }
    }, [patient, currentPage]);

    return (
        <TableComponent
            tableHeader={'Documents'}
            tableHead={headers}
            tableBody={tableBodies}
            isPagination={true}
            pageSize={pageSize}
            totalResults={total}
            currentPage={currentPage}
            handleNext={setCurrentPage}
        />
    );
};
