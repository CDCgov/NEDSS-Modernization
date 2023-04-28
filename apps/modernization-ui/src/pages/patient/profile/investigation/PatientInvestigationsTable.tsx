import { useContext, useEffect, useState } from 'react';
import { Button, Icon } from '@trussworks/react-uswds';
import format from 'date-fns/format';
import { FindInvestigationsForPatientQuery, useFindInvestigationsForPatientLazyQuery } from 'generated/graphql/schema';

import { RedirectControllerService } from 'generated';
import { UserContext } from 'providers/UserContext';
import { Headers, Investigation } from './PatientInvestigation';
import { transform } from './PatientInvestigationTransformer';
import { sort } from './PatientInvestigationSorter';
import { TableBody, TableComponent } from 'components/Table/Table';
import { Direction } from 'sorting';

const asTableBody =
    (nbsBase: string) =>
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
                title: investigation?.event,
                link: `${nbsBase}/ViewFile1.do?ContextAction=InvestigationIDOnEvents&publicHealthCaseUID=${investigation.investigation}`
            },
            { id: 9, title: investigation?.coInfection || null }
        ]
    });

const asTableBodies = (nbsBase: string, investigations: Investigation[]): TableBody[] =>
    investigations?.map(asTableBody(nbsBase)) || [];

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
    patient?: string;
    pageSize: number;
    nbsBase: string;
};

export const PatientInvestigationsTable = ({ patient, pageSize, nbsBase }: Props) => {
    const { state } = useContext(UserContext);

    const [currentPage, setCurrentPage] = useState<number>(1);
    const [total, setTotal] = useState<number>(0);
    const [items, setItems] = useState<any>([]);
    const [bodies, setBodies] = useState<TableBody[]>([]);

    const handleComplete = (data: FindInvestigationsForPatientQuery) => {
        setTotal(data?.findInvestigationsForPatient?.total || 0);

        const content = transform(data?.findInvestigationsForPatient);

        setItems(content);

        const sorted = sort(content, {});
        setBodies(asTableBodies(nbsBase, sorted));
    };

    const [getInvestigation] = useFindInvestigationsForPatientLazyQuery({ onCompleted: handleComplete });

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
        setBodies(asTableBodies(nbsBase, sorted));
    };

    return (
        <TableComponent
            buttons={
                <div className="grid-row">
                    <Button disabled type="button" className="grid-row">
                        <Icon.Topic className="margin-right-05" />
                        Compare investigations
                    </Button>
                    <Button
                        type="button"
                        className="grid-row"
                        onClick={() => {
                            RedirectControllerService.preparePatientDetailsUsingGet({
                                authorization: 'Bearer ' + state.getToken()
                            }).then(() => {
                                window.location.href = `${nbsBase}/LoadSelectCondition1.do?ContextAction=AddInvestigation`;
                            });
                        }}>
                        <Icon.Add className="margin-right-05" />
                        Add investigation
                    </Button>
                </div>
            }
            tableHeader={'Investigations'}
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
