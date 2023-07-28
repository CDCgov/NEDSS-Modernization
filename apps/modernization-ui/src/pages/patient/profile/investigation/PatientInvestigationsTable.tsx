import React, { useEffect, useState } from 'react';
import { Button, Icon } from '@trussworks/react-uswds';
import format from 'date-fns/format';
import { FindInvestigationsForPatientQuery, useFindInvestigationsForPatientLazyQuery } from 'generated/graphql/schema';

import { Headers, Investigation } from './PatientInvestigation';
import { transform } from './PatientInvestigationTransformer';
import { sort } from './PatientInvestigationSorter';
import { TableBody, TableComponent } from 'components/Table/Table';
import { Direction } from 'sorting';
import { ClassicButton, ClassicLink } from 'classic';

const asTableBody =
    (patient?: string) =>
    (investigation: Investigation): TableBody => ({
        id: investigation.investigation,
        checkbox: true,
        tableDetails: [
            {
                id: 1,
                title: investigation?.startedOn && format(investigation.startedOn, 'MM/dd/yyyy')
            },
            { id: 2, title: investigation?.condition },
            { id: 3, title: investigation?.status },
            { id: 4, title: investigation?.caseStatus },
            { id: 5, title: investigation?.notification },
            { id: 6, title: investigation?.jurisdiction },
            { id: 7, title: investigation?.investigator },
            {
                id: 8,
                title: investigation?.event && (
                    <ClassicLink url={`/nbs/api/profile/${patient}/investigation/${investigation.investigation}`}>
                        {investigation?.event}
                    </ClassicLink>
                )
            },
            { id: 9, title: investigation?.coInfection || null }
        ]
    });

const asTableBodies = (investigations: Investigation[], patient?: string): TableBody[] =>
    investigations?.map(asTableBody(patient)) || [];

const headers = [
    { name: Headers.StartDate, sortable: true },
    { name: Headers.Condition, sortable: true },
    { name: Headers.Status, sortable: true },
    { name: Headers.CaseStatus, sortable: true },
    { name: Headers.Notification, sortable: true },
    { name: Headers.Jurisdiction, sortable: true },
    { name: Headers.Investigator, sortable: true },
    { name: Headers.Investigation, sortable: true },
    { name: Headers.CoInfection, sortable: true }
];

type Props = {
    patient: string | undefined;
    pageSize: number;
};

export const PatientInvestigationsTable = ({ patient, pageSize }: Props) => {
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

    const [getInvestigation, { loading }] = useFindInvestigationsForPatientLazyQuery({ onCompleted: handleComplete });

    useEffect(() => {
        if (patient) {
            getInvestigation({
                variables: {
                    patient: patient,
                    openOnly: false,
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

    const [checkedItems, setCheckedItems] = useState<{ id: string; value: string | undefined }[]>([]);
    const handleSelected = (e: React.ChangeEvent<HTMLInputElement>, row: TableBody) => {
        if (e.target.checked) {
            if (e.target.value === row.tableDetails[1].title) {
                if (checkedItems?.[0]?.value === e.target.value) {
                    setCheckedItems((old) => [
                        ...old,
                        { id: row.id as string, value: row.tableDetails[1].title as string }
                    ]);
                }
                if (checkedItems?.length === 0) {
                    setCheckedItems((old) => [
                        ...old,
                        { id: row.id as string, value: row.tableDetails[1].title as string }
                    ]);
                }
            }
        } else {
            setCheckedItems(checkedItems.filter((item) => item.id !== row.id));
        }
    };

    return (
        <TableComponent
            buttons={
                <div className="grid-row">
                    <Button disabled type="button" className="grid-row">
                        <Icon.Topic className="margin-right-05" />
                        Compare investigations
                    </Button>
                    <ClassicButton url={`/nbs/api/profile/${patient}/investigation`}>
                        <Icon.Add className="margin-right-05" />
                        Add investigation
                    </ClassicButton>
                </div>
            }
            isLoading={loading}
            tableHeader={'Investigations'}
            tableHead={headers}
            tableBody={bodies}
            isPagination={true}
            pageSize={pageSize}
            totalResults={total}
            currentPage={currentPage}
            handleNext={setCurrentPage}
            sortData={handleSort}
            handleSelected={handleSelected}
        />
    );
};
