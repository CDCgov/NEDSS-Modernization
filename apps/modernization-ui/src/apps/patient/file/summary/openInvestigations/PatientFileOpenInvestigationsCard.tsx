import { Suspense } from 'react';
import { Await } from 'react-router';
import { MemoizedSupplier } from 'libs/supplying';
import { LoadingOverlay } from 'libs/loading';
import { TableCardProps } from 'design-system/card/table/TableCard';
import { Column } from 'design-system/table';
import { ColumnPreference } from 'design-system/table/preferences';
import { TableCard } from 'design-system/card';
import { displayNotificationStatus } from 'libs/events/investigations';
import { displayInvestigator } from 'libs/investigator';
import { PatientFileOpenInvestigation } from './openInvestigation';

const INVESTIGATION_ID = { id: 'investigationId', name: 'Investigation ID' };
const START_DATE = { id: 'startDate', name: 'Start date' };
const CONDITION = { id: 'condition', name: 'Condition' };
const CASE_STATUS = { id: 'caseStatus', name: 'Case status' };
const NOTIFICATION = { id: 'notification', name: 'Notification' };
const JURISDICTION = { id: 'jurisdiction', name: 'Jurisdiction' };
const INVESTIGATOR = { id: 'investigator', name: 'Investigator' };
const CO_INFECTION_ID = { id: 'coinfectionId', name: 'Co-Infection ID' };

const columnPreferences: ColumnPreference[] = [
    { ...INVESTIGATION_ID },
    { ...START_DATE, moveable: true, toggleable: true },
    { ...CONDITION, moveable: true, toggleable: true },
    { ...CASE_STATUS, moveable: true, toggleable: true },
    { ...NOTIFICATION, moveable: true, toggleable: true },
    { ...JURISDICTION, moveable: true, toggleable: true },
    { ...INVESTIGATOR, moveable: true, toggleable: true },
    { ...CO_INFECTION_ID, moveable: true, toggleable: true }
];

const columns: Column<PatientFileOpenInvestigation>[] = [
    {
        ...INVESTIGATION_ID,
        sortable: true,
        value: (row) => row.local,
        render: (value) => (
            <a href={`/nbs/api/profile/${value.patient}/investigation/${value.identifier}`}>{value.local}</a>
        )
    },
    {
        ...START_DATE,
        sortable: true,
        value: (value) => value.startedOn,
        sortIconType: 'numeric'
    },
    {
        ...CONDITION,
        sortable: true,
        value: (row) => row.condition,
        render: (value) => <b>{value.condition}</b>
    },
    {
        ...CASE_STATUS,
        sortable: true,
        value: (value) => value.caseStatus
    },
    {
        ...NOTIFICATION,
        sortable: true,
        value: (value) => value.notification,
        render: (row) => displayNotificationStatus(row.notification)
    },
    {
        ...JURISDICTION,
        sortable: true,
        value: (value) => value.jurisdiction
    },
    {
        ...INVESTIGATOR,
        sortable: true,
        value: (value) => displayInvestigator(value.investigator)
    },
    {
        ...CO_INFECTION_ID,
        sortable: true,
        value: (value) => value.coInfection
    }
];

type InternalCardProps = {
    data?: PatientFileOpenInvestigation[];
} & Omit<
    TableCardProps<PatientFileOpenInvestigation>,
    'columnPreferencesKey' | 'defaultColumnPreferences' | 'columns' | 'data' | 'title'
>;

const InternalCard = ({ data = [], ...remaining }: InternalCardProps) => {
    return (
        <TableCard
            title="Open investigations"
            data={data}
            columns={columns}
            columnPreferencesKey="patient.file.open-investigations.preferences"
            defaultColumnPreferences={columnPreferences}
            {...remaining}
        />
    );
};

type PatientFileOpenInvestigationsCardProps = {
    provider: MemoizedSupplier<Promise<PatientFileOpenInvestigation[]>>;
} & Omit<InternalCardProps, 'data'>;

const PatientFileOpenInvestigationsCard = ({ provider, ...remaining }: PatientFileOpenInvestigationsCardProps) => (
    <Suspense
        fallback={
            <LoadingOverlay>
                <InternalCard {...remaining} />
            </LoadingOverlay>
        }>
        <Await resolve={provider.get()}>{(data) => <InternalCard data={data} {...remaining} />}</Await>
    </Suspense>
);

export { PatientFileOpenInvestigationsCard };
