import { useMemo } from 'react';
import { Column } from 'design-system/table';
import { ColumnPreference } from 'design-system/table/preferences';
import { TableCard } from 'design-system/card/table/TableCard';
import { Tag } from 'design-system/tag';
import { TagVariant } from 'design-system/tag/Tag';
import { mapOr } from 'utils/mapping';
import { ClassicLink } from 'classic';
import { displayName } from 'name';
import { PatientInvestigation } from './PatientInvestigation';
import { usePatientInvestigations } from './usePatientInvestigations';

const INVESTIGATION_ID = { id: 'investigationId', name: 'Investigation ID' };
const START_DATE = { id: 'startedOn', name: 'Start date' };
const STATUS = { id: 'status', name: 'Status' };
const CONDITION = { id: 'condition', name: 'Condition' };
const CASE_STATUS = { id: 'caseStatus', name: 'Case status' };
const NOTIFICATION = { id: 'notification', name: 'Notification' };
const JURISDICTION = { id: 'jurisdiction', name: 'Jurisdiction' };
const INVESTIGATOR = { id: 'investigator', name: 'Investigator' };
const CO_INFECTION = { id: 'coInfection', name: 'Co-infection ID' };

const maybeDisplayName = mapOr(displayName('short'), undefined);

const asTag = (value: string, variant: TagVariant) => (
    <Tag size="small" weight="bold" variant={variant}>
        {value}
    </Tag>
);

const maybeDisplayStatus = (value?: string) => {
    if (value === 'Open') {
        return asTag(value, 'success');
    } else if (value === 'Rejected') {
        return asTag(value, 'error');
    }
};

const createColumns = (id: number): Column<PatientInvestigation>[] => [
    {
        ...INVESTIGATION_ID,
        sortable: true,
        value: (row) => row.investigationId,
        render: (value) => (
            <ClassicLink url={`/nbs/api/profile/${id}/investigation/${value.identifier}`}>
                {value.investigationId}
            </ClassicLink>
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
        render: (row) => maybeDisplayStatus(row.status),
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
        render: (row) => maybeDisplayStatus(row.caseStatus),
        sortIconType: 'alpha'
    },
    {
        ...NOTIFICATION,
        sortable: true,
        value: (row) => row.notification,
        render: (row) => maybeDisplayStatus(row.notification),
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
        value: (row) => maybeDisplayName(row.investigatorName)
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

type InvestigationsCardProps = {
    patient: number;
};

const InvestigationsCard = ({ patient }: InvestigationsCardProps) => {
    const { data } = usePatientInvestigations(patient);

    const columns = useMemo(() => createColumns(patient), [patient]);

    return (
        <TableCard
            sizing="small"
            title="Investigations"
            id={'investigations'}
            columnPreferencesKey={'patient.file.investigations.preferences'}
            defaultCollapsed={data.length === 0}
            columns={columns}
            data={data}
            defaultColumnPreferences={columnPreferences}
        />
    );
};

export { InvestigationsCard };
