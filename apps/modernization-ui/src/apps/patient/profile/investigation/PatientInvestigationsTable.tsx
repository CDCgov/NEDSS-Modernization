import { useEffect, useState } from 'react';
import { Icon } from '@trussworks/react-uswds';
import { format } from 'date-fns';
import { FindInvestigationsForPatientQuery, useFindInvestigationsForPatientLazyQuery } from 'generated/graphql/schema';

import { Headers, Investigation } from './PatientInvestigation';
import { transform } from './PatientInvestigationTransformer';
import { sort } from './PatientInvestigationSorter';
import { SelectionHandler, SelectionMode, TableBody, TableComponent } from 'components/Table';
import { Direction } from 'sorting';
import { ClassicButton, ClassicLink } from 'classic';
import { usePatientProfilePermissions } from '../permission';
import { useInvestigationCompare } from './useInvestigationCompare';

type InvestigationSelectionHandler = (investigation: Investigation) => SelectionHandler;

const asTableBody =
    (handleSelect: InvestigationSelectionHandler, patient?: string) =>
    (investigation: Investigation): TableBody => ({
        id: investigation.investigation,
        selectable: investigation.comparable,
        onSelect: handleSelect(investigation),
        tableDetails: [
            {
                id: 1,
                title: investigation?.event && (
                    <ClassicLink url={`/nbs/api/profile/${patient}/investigation/${investigation.investigation}`}>
                        {investigation?.event}
                    </ClassicLink>
                )
            },
            {
                id: 2,
                title: investigation?.startedOn && format(investigation.startedOn, 'MM/dd/yyyy')
            },
            { id: 3, title: investigation?.condition },
            { id: 4, title: investigation?.status },
            { id: 5, title: investigation?.caseStatus },
            { id: 6, title: investigation?.notification },
            { id: 7, title: investigation?.jurisdiction },
            { id: 8, title: investigation?.investigator },
            { id: 9, title: investigation?.coInfection || null }
        ]
    });

const asTableBodies = (
    investigations: Investigation[],
    handleSelect: InvestigationSelectionHandler,
    patient?: string
): TableBody[] => investigations?.map(asTableBody(handleSelect, patient)) || [];

const headers = [
    { name: Headers.Investigation, sortable: true },
    { name: Headers.StartDate, sortable: true },
    { name: Headers.Condition, sortable: true },
    { name: Headers.Status, sortable: true },
    { name: Headers.CaseStatus, sortable: true },
    { name: Headers.Notification, sortable: true },
    { name: Headers.Jurisdiction, sortable: true },
    { name: Headers.Investigator, sortable: true },
    { name: Headers.CoInfection, sortable: true }
];

type Props = {
    patient: string | undefined;
    pageSize: number;
    allowAdd?: boolean;
};

export const PatientInvestigationsTable = ({ patient, pageSize, allowAdd = false }: Props) => {
    const [currentPage, setCurrentPage] = useState<number>(1);
    const [total, setTotal] = useState<number>(0);
    const [items, setItems] = useState<Investigation[]>([]);
    const [bodies, setBodies] = useState<TableBody[]>([]);
    const permissions = usePatientProfilePermissions();

    const { comparable, selected, select, deselect } = useInvestigationCompare();

    const handleSelect = (investigation: Investigation) => (mode: SelectionMode) => {
        if (mode === 'select') {
            select(investigation);
        } else {
            deselect(investigation);
        }
    };

    const handleComplete = (data: FindInvestigationsForPatientQuery) => {
        setTotal(data?.findInvestigationsForPatient?.total || 0);

        const content = transform(data?.findInvestigationsForPatient);

        setItems(content);

        const sorted = sort(content, {});
        setBodies(asTableBodies(sorted, handleSelect, patient));
    };

    const [getInvestigation, { called, loading }] = useFindInvestigationsForPatientLazyQuery({
        onCompleted: handleComplete
    });

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
        setBodies(asTableBodies(sorted, handleSelect, patient));
    };

    return (
        <TableComponent
            buttons={
                allowAdd && (
                    <div className="grid-row">
                        {permissions.compareInvestigation && (
                            <ClassicButton
                                disabled={!comparable}
                                type="button"
                                className="grid-row"
                                url={`/nbs/api/profile/${patient}/investigation/${selected?.[0]?.investigation}/compare/${selected?.[1]?.investigation}`}>
                                <Icon.Topic className="margin-right-05" />
                                Compare investigations
                            </ClassicButton>
                        )}
                        <ClassicButton url={`/nbs/api/profile/${patient}/investigation`}>
                            <Icon.Add className="margin-right-05" />
                            Add investigation
                        </ClassicButton>
                    </div>
                )
            }
            isLoading={!called || loading}
            tableHeader={'Investigations'}
            tableHead={headers}
            tableBody={bodies}
            isPagination={true}
            pageSize={pageSize}
            totalResults={total}
            currentPage={currentPage}
            handleNext={setCurrentPage}
            sortData={handleSort}
            selectable
        />
    );
};
