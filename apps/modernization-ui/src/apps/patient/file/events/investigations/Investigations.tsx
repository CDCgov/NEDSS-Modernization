import { TableCard } from 'design-system/card/table/TableCard';
import { Headers, Investigation } from './PatientInvestigation';
import { Icon } from 'design-system/icon';
import { Column } from 'design-system/table';
import { displayName } from 'name';
import { usePatientInvestigations } from './usePatientInvestigations';
import { usePatient } from '../../usePatient';
import { Tag } from 'design-system/tag';
import { TagVariant } from 'design-system/tag/Tag';
import { ColumnPreference } from 'design-system/table/preferences';
import { ClassicLink } from 'classic';
import { internalizeDate } from 'date';
import { useMemo } from 'react';
import { mapOr } from 'utils/mapping';

const displayTag = (value: string | undefined, status: boolean, variant: TagVariant) => {
    if (!value) return '';
    return status ? (
        <Tag size="small" weight="bold" variant={variant}>
            {value}
        </Tag>
    ) : (
        value
    );
};

const INVESTIGATION_ID = { id: 'investigationId', name: Headers.Investigation };
const START_DATE = { id: 'startedOn', name: Headers.StartDate };
const STATUS = { id: 'status', name: Headers.Status };
const CONDITION = { id: 'condition', name: Headers.Condition };
const CASE_STATUS = { id: 'caseStatus', name: Headers.CaseStatus };
const NOTIFICATION = { id: 'notification', name: Headers.Notification };
const JURISDICTION = { id: 'jurisdiction', name: Headers.Jurisdiction };
const INVESTIGATOR = { id: 'investigatorName', name: Headers.Investigator };
const CO_INFECTION = { id: 'coInfection', name: Headers.CoInfection };

const maybeDisplayName = mapOr(displayName('short'), undefined);

const createColumns = (id: number): Column<Investigation>[] => [
    {
        ...INVESTIGATION_ID,
        sortable: true,
        value: (row) => row.investigationId,
        render: (value) => (
            <ClassicLink url={`/patient/${id}/investigation/${value.investigationId}`}>
                {value.investigationId}
            </ClassicLink>
        )
    },
    {
        ...START_DATE,
        sortable: true,
        value: (row) => row.startedOn,
        render: (value) => internalizeDate(value.startedOn),
        sortIconType: 'numeric'
    },
    {
        ...STATUS,
        sortable: true,
        value: (row) => row.status,
        render: (value) => displayTag(value.status, value.status === 'Open', 'success'),
        sortIconType: 'alpha'
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
        render: (value) => displayTag(value.caseStatus, value.caseStatus === 'Open', 'success'),
        sortIconType: 'alpha'
    },
    {
        ...NOTIFICATION,
        sortable: true,
        value: (row) => row.notification,
        render: (value) => displayTag(value.notification, value.notification === 'Rejected', 'error'),
        sortIconType: 'alpha'
    },
    {
        ...JURISDICTION,
        sortable: true,
        value: (row) => row.jurisdiction
    },
    {
        ...INVESTIGATOR,
        sortable: true,
        value: (row) => maybeDisplayName(row.investigatorName),
        render: (value) => (value.investigatorName ? displayName('short')(value.investigatorName) : '')
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

export const Investigations = () => {
    const { id } = usePatient();
    const { data } = usePatientInvestigations(id);

    const columns = useMemo(() => createColumns(id), [id]);

    return (
        <TableCard
            sizing="small"
            title="Investigations"
            id={'investigations'}
            columnPreferencesKey={'patient.file.open-investigations.preferences.${column.id}'}
            defaultCollapsed={!data?.length}
            columns={columns}
            actions={[
                {
                    sizing: 'small',
                    secondary: true,
                    children: 'Add investigation',
                    icon: <Icon name="add_circle" />,
                    labelPosition: 'right',
                    onClick: () => console.log('Add Person clicked')
                },
                {
                    sizing: 'small',
                    secondary: true,
                    children: 'Compare investigations'
                }
            ]}
            noDataFallback
            data={data ?? []}
            defaultColumnPreferences={columnPreferences}
        />
    );
};
