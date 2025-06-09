import { Column } from 'design-system/table';
import { ColumnPreference } from 'design-system/table/preferences';
import { TableCard } from 'design-system/card';
import { LinkButton } from 'design-system/button';
import { Icon } from 'design-system/icon';
import { Sizing } from 'design-system/field';
import { PatientInvestigation, usePatientInvestigations } from 'libs/patient/events/investigations';
import { displayInvestigator, displayNotificationStatus, displayStatus } from 'libs/events/investigations';
import { permissions, Permitted } from 'libs/permission';

const INVESTIGATION_ID = { id: 'investigationId', name: 'Investigation ID' };
const START_DATE = { id: 'startedOn', name: 'Start date' };
const STATUS = { id: 'status', name: 'Status' };
const CONDITION = { id: 'condition', name: 'Condition' };
const CASE_STATUS = { id: 'caseStatus', name: 'Case status' };
const NOTIFICATION = { id: 'notification', name: 'Notification' };
const JURISDICTION = { id: 'jurisdiction', name: 'Jurisdiction' };
const INVESTIGATOR = { id: 'investigator', name: 'Investigator' };
const CO_INFECTION = { id: 'coInfection', name: 'Co-infection ID' };

const columns: Column<PatientInvestigation>[] = [
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

type InvestigationsCardProps = {
    patient: number;
    sizing?: Sizing;
};

const InvestigationsCard = ({ patient, sizing = 'small' }: InvestigationsCardProps) => {
    const { data } = usePatientInvestigations(patient);

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
                <Permitted permission={permissions.investigation.add}>
                    <LinkButton
                        secondary
                        sizing={sizing}
                        icon={<Icon name="add_circle" sizing={sizing} />}
                        href={`/nbs/api/profile/${patient}/investigation`}>
                        Add investigation
                    </LinkButton>
                </Permitted>
            }
        />
    );
};

export { InvestigationsCard };
