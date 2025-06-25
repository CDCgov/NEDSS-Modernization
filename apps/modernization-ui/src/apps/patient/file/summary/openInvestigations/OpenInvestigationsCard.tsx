import { Column } from 'design-system/table';
import { ColumnPreference } from 'design-system/table/preferences';
import { TableCard } from 'design-system/card';
import { usePatientOpenInvestigations, PatientInvestigation } from 'libs/patient/events/investigations';
import { displayInvestigator, displayNotificationStatus } from 'libs/events/investigations';

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

type OpenInvestigationsCardProps = {
    patient: number;
};

const OpenInvestigationsCard = ({ patient }: OpenInvestigationsCardProps) => {
    const { data } = usePatientOpenInvestigations(patient);

    return (
        <TableCard
            id="patient-file-open-investigations-table-card"
            title="Open investigations"
            sizing="small"
            data={data}
            columns={columns}
            columnPreferencesKey="patient.file.open-investigations.preferences"
            defaultColumnPreferences={columnPreferences}
        />
    );
};

export { OpenInvestigationsCard };
