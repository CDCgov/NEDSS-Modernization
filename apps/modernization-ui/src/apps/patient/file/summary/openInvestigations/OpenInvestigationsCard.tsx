import { ClassicLink } from 'classic';
import { TableCard } from 'design-system/card/table/TableCard';
import { Column } from 'design-system/table';
import { ColumnPreference } from 'design-system/table/preferences';
import { InvestigatorName, PatientInvestigation } from 'generated';
import { usePatientFileOpenInvestigations } from './usePatientFileOpenInvestigations';
import { usePatient } from '../../usePatient';

const maybeInvestigatorName = (investigatorName: InvestigatorName | undefined) => {
    if (!investigatorName) {
        return undefined;
    }
    const { first, last } = investigatorName;
    if (
        typeof first === 'string' &&
        typeof last === 'string' &&
        first.length > 0 &&
        last.length > 0 &&
        first !== 'null' &&
        last !== 'null'
    ) {
        return `${first} ${last}`;
    }
    return undefined;
};

const OpenInvestigationsCard = () => {
    const patient = usePatient();
    const { patientOpenInvestigations } = usePatientFileOpenInvestigations(patient.id);

    const INVESTIGATION_ID = { id: 'patient-file-open-investigations-investigationId', name: 'Investigation ID' };
    const START_DATE = { id: 'patient-file-open-investigations-startDate', name: 'Start date' };
    const CONDITION = { id: 'patient-file-open-investigations-condition', name: 'Condition' };
    const CASE_STATUS = { id: 'patient-file-open-investigations-caseStatus', name: 'Case status' };
    const NOTIFICATION = { id: 'patient-file-open-investigations-notification', name: 'Notification' };
    const JURISDICTION = { id: 'patient-file-open-investigations-jurisdiction', name: 'Jurisdiction' };
    const INVESTIGATOR = { id: 'patient-file-open-investigations-investigator', name: 'Investigator' };
    const COINFECTION_ID = { id: 'patient-file-open-investigations-coinfectionId', name: 'Co-Infection ID' };

    const columnPreferences: ColumnPreference[] = [
        { ...INVESTIGATION_ID },
        { ...START_DATE, moveable: true, toggleable: true },
        { ...CONDITION, moveable: true, toggleable: true },
        { ...CASE_STATUS, moveable: true, toggleable: true },
        { ...NOTIFICATION, moveable: true, toggleable: true },
        { ...JURISDICTION, moveable: true, toggleable: true },
        { ...INVESTIGATOR, moveable: true, toggleable: true },
        { ...COINFECTION_ID, moveable: true, toggleable: true }
    ];

    const columns: Column<PatientInvestigation>[] = [
        {
            ...INVESTIGATION_ID,
            sortable: true,
            render: (value: PatientInvestigation) => (
                <ClassicLink
                    id="condition"
                    url={`/nbs/api/profile/${patient.id.toString()}/investigation/${value.identifier}`}>
                    {value.investigationId}
                </ClassicLink>
            )
        },
        {
            ...START_DATE,
            sortable: true,
            render: (value: PatientInvestigation) =>
                value.startedOn ? new Date(value.startedOn).toLocaleDateString() : ''
        },
        {
            ...CONDITION,
            sortable: true,
            render: (value: PatientInvestigation) => <b>{value.condition}</b>
        },
        {
            ...CASE_STATUS,
            sortable: true,
            render: (value: PatientInvestigation) => value.caseStatus
        },
        {
            ...NOTIFICATION,
            sortable: true,
            render: (value: PatientInvestigation) => value.notification
        },
        {
            ...JURISDICTION,
            sortable: true,
            render: (value: PatientInvestigation) => value.jurisdiction
        },
        {
            ...INVESTIGATOR,
            sortable: true,
            render: (value: PatientInvestigation) => maybeInvestigatorName(value.investigatorName)
        },
        {
            ...COINFECTION_ID,
            sortable: true,
            render: (value: PatientInvestigation) => value.coInfection
        }
    ];

    return (
        <div>
            <TableCard
                id="patient-file-open-investigations-table-card"
                title="Open investigations"
                data={patientOpenInvestigations || []}
                defaultCollapsed={patientOpenInvestigations && patientOpenInvestigations.length > 0 ? false : true}
                columns={columns}
                columnPreferencesKey="patient-file-open-investigations-table-card-column-preferences"
                defaultColumnPreferences={columnPreferences}
                noDataFallback
            />
        </div>
    );
};

export default OpenInvestigationsCard;
