import { useMemo } from 'react';
import { displayName } from 'name';
import { maybeMap } from 'utils/mapping';
import { equalsIgnoreCase } from 'utils/predicate';
import { Column } from 'design-system/table';
import { ColumnPreference } from 'design-system/table/preferences';
import { TableCard } from 'design-system/card';
import { Tag } from 'design-system/tag';
import { TagVariant } from 'design-system/tag/Tag';
import { LinkButton } from 'design-system/button';
import { Icon } from 'design-system/icon';
import { Sizing } from 'design-system/field';
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

const maybeDisplayName = maybeMap(displayName('short'));

const asTag = (value: string, variant: TagVariant) => (
    <Tag size="small" weight="bold" variant={variant}>
        {value}
    </Tag>
);

const isRequiredStatus = equalsIgnoreCase('Rejected');

const maybeDisplayStatus = maybeMap((value: string) => {
    if (value === 'Open') {
        return asTag(value, 'success');
    } else if (isRequiredStatus(value)) {
        return asTag(value, 'error');
    }
});

const createColumns = (id: number): Column<PatientInvestigation>[] => [
    {
        ...INVESTIGATION_ID,
        sortable: true,
        value: (row) => row.investigationId,
        render: (value) => (
            <a href={`/nbs/api/profile/${id}/investigation/${value.identifier}`}>{value.investigationId}</a>
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
        render: (row) => maybeDisplayStatus(row.status)
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
        render: (row) => maybeDisplayStatus(row.caseStatus)
    },
    {
        ...NOTIFICATION,
        sortable: true,
        value: (row) => row.notification,
        render: (row) => maybeDisplayStatus(row.notification)
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
    sizing?: Sizing;
};

const InvestigationsCard = ({ patient, sizing = 'small' }: InvestigationsCardProps) => {
    const { data } = usePatientInvestigations(patient);

    const columns = useMemo(() => createColumns(patient), [patient]);

    return (
        <TableCard
            sizing={sizing}
            title="Investigations"
            id="investigations"
            columns={columns}
            data={data}
            columnPreferencesKey={'patient.file.investigations.preferences'}
            defaultColumnPreferences={columnPreferences}
            actions={
                <LinkButton
                    secondary
                    sizing={sizing}
                    icon={<Icon name="add_circle" sizing={sizing} />}
                    href={`/nbs/api/profile/${patient}/investigation`}>
                    Add Investigation
                </LinkButton>
            }
        />
    );
};

export { InvestigationsCard };
