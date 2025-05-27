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

const displayTag = (value: string | undefined, status: boolean, variant: TagVariant) => {
    if (!value) return '';
    return status ? <Tag variant={variant}>{value}</Tag> : value;
};

const columns: Column<Investigation>[] = [
    {
        id: Headers.Investigation,
        name: Headers.Investigation,
        sortable: true,
        render: (value) => value.investigationId
    },
    {
        id: Headers.StartDate,
        name: Headers.StartDate,
        sortable: true,
        render: (value) => value?.startedOn && format(value.startedOn, 'MM/dd/yyyy'),
        sortIconType: 'numeric'
    },
    {
        id: Headers.Condition,
        name: Headers.Condition,
        sortable: true,
        render: (value) => value.condition,
        sortIconType: 'alpha'
    },
    {
        id: Headers.Status,
        name: Headers.Status,
        sortable: true,
        render: (value) => displayTag(value.status, value.status === 'Open', 'success'),
        sortIconType: 'alpha'
    },
    {
        id: Headers.CaseStatus,
        name: Headers.CaseStatus,
        sortable: true,
        render: (value) => value.caseStatus,
        sortIconType: 'alpha'
    },
    {
        id: Headers.Notification,
        name: Headers.Notification,
        sortable: true,
        render: (value) => displayTag(value.notification, value.notification === 'Rejected', 'error'),
        sortIconType: 'alpha'
    },
    {
        id: Headers.Jurisdiction,
        name: Headers.Jurisdiction,
        sortable: true,
        render: (value) => value.jurisdiction,
        sortIconType: 'alpha'
    },
    {
        id: Headers.Investigator,
        name: Headers.Investigator,
        sortable: true,
        render: (value) => (value.investigatorName ? displayName('short')(value.investigatorName) : ''),
        sortIconType: 'alpha'
    },
    {
        id: Headers.CoInfection,
        name: Headers.CoInfection,
        sortable: true,
        render: (value) => value.coInfection,
        sortIconType: 'numeric'
    }
];

export const Investigations = () => {
    const { id } = usePatient();
    const { data } = usePatientInvestigations(id);

    return (
        <TableCard
            title="Investigations"
            id={'investigations'}
            columnPreferencesKey={'investigations'}
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
            data={data ?? []}
        />
    );
};
