import { Suspense } from 'react';
import { Await } from 'react-router';
import { LoadingOverlay } from 'libs/loading';
import { MemoizedSupplier } from 'libs/supplying';
import { Column } from 'design-system/table';
import { ColumnPreference } from 'design-system/table/preferences';
import { TableCard, TableCardProps } from 'design-system/card';
import { permissions, Permitted } from 'libs/permission';
import { LinkButton } from 'design-system/button';
import { Icon } from 'design-system/icon';
import { displayNotificationStatus, displayStatus, displayInvestigator } from 'libs/events/investigations';
import { PatientFileInvestigation } from './investigation';

const INVESTIGATION_ID = { id: 'investigationId', name: 'Investigation ID' };
const START_DATE = { id: 'startedOn', name: 'Start date' };
const STATUS = { id: 'status', name: 'Status' };
const CONDITION = { id: 'condition', name: 'Condition' };
const CASE_STATUS = { id: 'caseStatus', name: 'Case status' };
const NOTIFICATION = { id: 'notification', name: 'Notification' };
const JURISDICTION = { id: 'jurisdiction', name: 'Jurisdiction' };
const INVESTIGATOR = { id: 'investigator', name: 'Investigator' };
const CO_INFECTION = { id: 'coInfection', name: 'Co-infection ID' };

const columns: Column<PatientFileInvestigation>[] = [
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
        value: (row) => row.startedOn,
        sortIconType: 'numeric'
    },
    {
        ...STATUS,
        sortable: true,
        value: (row) => row.status,
        render: (row) => displayStatus(row.status)
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
        value: (row) => row.caseStatus,
        render: (row) => displayStatus(row.caseStatus)
    },
    {
        ...NOTIFICATION,
        sortable: true,
        value: (row) => row.notification,
        render: (row) => displayNotificationStatus(row.notification)
    },
    {
        ...JURISDICTION,
        sortable: true,
        value: (row) => row.jurisdiction
    },
    {
        ...INVESTIGATOR,
        sortable: true,
        value: (row) => displayInvestigator(row.investigator)
    },
    {
        ...CO_INFECTION,
        sortable: true,
        value: (row) => row.coInfection
    }
];

const columnPreferences: ColumnPreference[] = [
    { ...INVESTIGATION_ID },
    { ...START_DATE, moveable: true, toggleable: true },
    { ...STATUS, moveable: true, toggleable: true },
    { ...CONDITION, moveable: true, toggleable: true },
    { ...CASE_STATUS, moveable: true, toggleable: true },
    { ...NOTIFICATION, moveable: true, toggleable: true },
    { ...JURISDICTION, moveable: true, toggleable: true },
    { ...INVESTIGATOR, moveable: true, toggleable: true },
    { ...CO_INFECTION, moveable: true, toggleable: true }
];

type InternalCardProps = {
    patient: number;
    data?: PatientFileInvestigation[];
} & Omit<
    TableCardProps<PatientFileInvestigation>,
    'columnPreferencesKey' | 'defaultColumnPreferences' | 'columns' | 'data' | 'title'
>;

const InternalCard = ({ patient, sizing, data = [], ...remaining }: InternalCardProps) => {
    return (
        <TableCard
            title="Investigations"
            data={data}
            columns={columns}
            columnPreferencesKey={'patient.file.investigations.preferences'}
            defaultColumnPreferences={columnPreferences}
            sizing={sizing}
            actions={
                <Permitted permission={permissions.investigation.add}>
                    <LinkButton
                        secondary
                        sizing={sizing}
                        icon={<Icon name="add_circle" />}
                        href={`/nbs/api/profile/${patient}/investigation`}>
                        Add investigation
                    </LinkButton>
                </Permitted>
            }
            {...remaining}
        />
    );
};

type PatientFileInvestigationsCardProps = {
    provider: MemoizedSupplier<Promise<PatientFileInvestigation[]>>;
} & Omit<InternalCardProps, 'data'>;

const PatientFileInvestigationsCard = ({ provider, ...remaining }: PatientFileInvestigationsCardProps) => (
    <Suspense
        fallback={
            <LoadingOverlay>
                <InternalCard {...remaining} />
            </LoadingOverlay>
        }>
        <Await resolve={provider.get()}>{(data) => <InternalCard data={data} {...remaining} />}</Await>
    </Suspense>
);

export { PatientFileInvestigationsCard };
