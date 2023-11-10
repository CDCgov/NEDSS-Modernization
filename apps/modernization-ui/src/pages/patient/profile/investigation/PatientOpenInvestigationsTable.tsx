import { useEffect, useState } from 'react';
import format from 'date-fns/format';
import { FindInvestigationsForPatientQuery, useFindInvestigationsForPatientLazyQuery } from 'generated/graphql/schema';

import { Headers, Investigation } from './PatientInvestigation';
import { transform } from './PatientInvestigationTransformer';
import { sort } from './PatientInvestigationSorter';
import { TableBody, TableComponent } from 'components/Table/Table';
import { Direction } from 'sorting';
import { ClassicLink } from 'classic';

const asTableBody =
    (patient?: string) =>
    (investigation: Investigation): TableBody => ({
        id: investigation.investigation,
        tableDetails: [
            {
                id: 1,
                title: investigation?.startedOn && format(investigation.startedOn, 'MM/dd/yyyy')
            },
            { id: 2, title: investigation?.condition || null },
            { id: 3, title: investigation?.caseStatus || null },
            { id: 4, title: investigation?.notification || null },
            { id: 5, title: investigation?.jurisdiction || null },
            { id: 6, title: investigation?.investigator || null },
            {
                id: 7,
                title: investigation?.event && (
                    <ClassicLink url={`/nbs/api/profile/${patient}/investigation/${investigation.investigation}`}>
                        {investigation?.event}
                    </ClassicLink>
                )
            },
            { id: 8, title: investigation?.coInfection || null }
        ]
    });

const asTableBodies = (investigations: Investigation[], patient?: string): TableBody[] =>
    investigations?.map(asTableBody(patient)) || [];

const headers = [
    { name: Headers.StartDate, sortable: true },
    { name: Headers.Condition, sortable: true },
    { name: Headers.CaseStatus, sortable: true },
    { name: Headers.Notification, sortable: true },
    { name: Headers.Jurisdiction, sortable: true },
    { name: Headers.Investigator, sortable: true },
    { name: Headers.Investigation, sortable: true },
    { name: Headers.CoInfection, sortable: true }
];

type Props = {
    patient?: string;
    pageSize: number;
};

export const PatientOpenInvestigationsTable = ({ patient, pageSize }: Props) => {
    const [currentPage, setCurrentPage] = useState<number>(1);
    const [total, setTotal] = useState<number>(0);
    const [items, setItems] = useState<any>([]);
    const [bodies, setBodies] = useState<TableBody[]>([]);

    const handleComplete = (data: FindInvestigationsForPatientQuery) => {
        setTotal(data?.findInvestigationsForPatient?.total || 0);

        const content = transform(data?.findInvestigationsForPatient);

        setItems(content);

        const sorted = sort(content, {});
        setBodies(asTableBodies(sorted, patient));
    };

    const [getInvestigation, { called, loading }] = useFindInvestigationsForPatientLazyQuery({
        onCompleted: handleComplete
    });

    useEffect(() => {
        if (patient) {
            getInvestigation({
                variables: {
                    patient: patient,
                    openOnly: true,
                    page: {
                        pageNumber: currentPage - 1,
                        pageSize
                    }
                }
            });
        }
    }, [patient, currentPage]);

    const handleSort = (name: string, direction: string): void => {
        const criteria = { name: name as Headers, type: direction as Direction };
        const sorted = sort(items, criteria);
        setBodies(asTableBodies(sorted, patient));
    };

    return (
        <TableComponent
            isLoading={!called || loading}
            tableHeader={'Open investigations'}
            tableHead={headers}
            tableBody={bodies}
            isPagination={true}
            pageSize={pageSize}
            totalResults={total}
            currentPage={currentPage}
            handleNext={setCurrentPage}
            sortData={handleSort}
        />
    );
};
