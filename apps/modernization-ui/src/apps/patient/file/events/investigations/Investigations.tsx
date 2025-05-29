import { TableCard } from 'design-system/card/table/TableCard';
import { Headers, Investigation } from './PatientInvestigation';
import { Icon } from 'design-system/icon';
import { Column } from 'design-system/table';
import { format } from 'date-fns';
import { displayName } from 'name';
import { usePatientInvestigations } from './usePatientInvestigations';
import { usePatient } from '../../usePatient';
import { Tag } from 'design-system/tag';
import { TagVariant } from 'design-system/tag/Tag';
import { ColumnPreference } from 'design-system/table/preferences';
import { ClassicLink } from 'classic';

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

const columns = (id: number): Column<Investigation>[] => [
    {
        id: 'investigationId',
        name: Headers.Investigation,
        sortable: true,
        render: (value) => (
            <ClassicLink url={`/patient/${id}/investigation/${value.investigationId}`}>
                {value.investigationId}
            </ClassicLink>
        )
    },
    {
        id: 'startedOn',
        name: Headers.StartDate,
        sortable: true,
        render: (value) => value?.startedOn && format(value.startedOn, 'MM/dd/yyyy'),
        sortIconType: 'numeric'
    },
    {
        id: 'status',
        name: Headers.Status,
        sortable: true,
        render: (value) => displayTag(value.status, value.status === 'Open', 'success'),
        sortIconType: 'alpha'
    },
    {
        id: 'condition',
        name: Headers.Condition,
        sortable: true,
        render: (value) => <b>{value.condition}</b>,
        sortIconType: 'alpha'
    },

    {
        id: 'caseStatus',
        name: Headers.CaseStatus,
        sortable: true,
        render: (value) => value.caseStatus,
        sortIconType: 'alpha'
    },
    {
        id: 'notification',
        name: Headers.Notification,
        sortable: true,
        render: (value) => displayTag(value.notification, value.notification === 'Rejected', 'error'),
        sortIconType: 'alpha'
    },
    {
        id: 'jurisdiction',
        name: Headers.Jurisdiction,
        sortable: true,
        render: (value) => value.jurisdiction,
        sortIconType: 'alpha'
    },
    {
        id: 'investigatorName',
        name: Headers.Investigator,
        sortable: true,
        render: (value) => (value.investigatorName ? displayName('short')(value.investigatorName) : ''),
        sortIconType: 'alpha'
    },
    {
        id: 'coInfection',
        name: Headers.CoInfection,
        sortable: true,
        render: (value) => value.coInfection,
        sortIconType: 'numeric'
    }
];

export const Investigations = () => {
    const { id } = usePatient();
    const { data } = usePatientInvestigations(id);

    const columnPreferences: ColumnPreference[] = columns(id).map((column, index) => ({
        id: column.id,
        name: column.name,
        moveable: index !== 0,
        toggleable: index !== 0
    }));

    return (
        <TableCard
            sizing="small"
            title="Investigations"
            id={'investigations'}
            columnPreferencesKey={'investigations'}
            columns={columns(id)}
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
            data={data ?? []}
            defaultColumnPreferences={columnPreferences}
        />
    );
};
