import { TableCard } from 'design-system/card/table/TableCard';
import { Column } from 'design-system/table';
import { ColumnPreference } from 'design-system/table/preferences';
import { PatientInvestigation } from 'generated/graphql/schema';
// import { useFindOpenInvestigations } from '../../patientData/useFindOpenInvestigations';
// import { usePatient } from '../../usePatient';

const sampleData: PatientInvestigation[] = [
    {
        investigation: 'INV001',
        startedOn: new Date('2023-01-01'),
        condition: 'Influenza',
        caseStatus: 'Confirmed',
        notification: 'Sent',
        jurisdiction: 'New York',
        investigator: 'Dr. Smith',
        coInfection: 'CO001',
        comparable: true,
        event: 'EVENT001',
        status: 'Open'
    },
    {
        investigation: 'INV002',
        startedOn: new Date('2023-02-15'),
        condition: 'COVID-19',
        caseStatus: '',
        notification: 'Pending',
        jurisdiction: 'California',
        investigator: 'Dr. Johnson',
        coInfection: 'CO002',
        comparable: true,
        event: 'EVENT002',
        status: 'Open'
    },
    {
        investigation: 'INV003',
        startedOn: new Date('2023-03-10'),
        condition: 'Measles',
        caseStatus: 'Suspected',
        notification: 'Not Sent',
        jurisdiction: 'Texas',
        investigator: 'Dr. Lee',
        coInfection: 'CO003',
        comparable: true,
        event: 'EVENT003',
        status: 'Open'
    }
];

const OpenInvestigationsCard = () => {
    // const patient = usePatient();
    // const { response } = useFindOpenInvestigations(patient.id.toString());

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
            render: (value: PatientInvestigation) => <a href={`#${value.investigation}`}>{value.investigation}</a> // render link
        },
        {
            ...START_DATE,
            render: (value: PatientInvestigation) => value.startedOn.toLocaleDateString()
        },
        {
            ...CONDITION,
            render: (value: PatientInvestigation) => <b>{value.condition}</b>
        },
        {
            ...CASE_STATUS,
            render: (value: PatientInvestigation) => value.caseStatus
        },
        {
            ...NOTIFICATION,
            render: (value: PatientInvestigation) => value.notification
        },
        {
            ...JURISDICTION,
            render: (value: PatientInvestigation) => value.jurisdiction
        },
        {
            ...INVESTIGATOR,
            render: (value: PatientInvestigation) => value.investigator
        },
        {
            ...COINFECTION_ID,
            render: (value: PatientInvestigation) => value.coInfection
        }
    ];

    console.log(sampleData);

    return (
        <div>
            <TableCard
                id="patient-file-open-investigations-table-card"
                title="Open investigations"
                data={sampleData}
                defaultCollapsed={sampleData.length > 0 ? false : true}
                columns={columns}
                columnPreferencesKey="patient-file-open-investigations-table-card-column-preferences"
                defaultColumnPreferences={columnPreferences}
                noDataFallback
            />
        </div>
    );
};

export default OpenInvestigationsCard;
