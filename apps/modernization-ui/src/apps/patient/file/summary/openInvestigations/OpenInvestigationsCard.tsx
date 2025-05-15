import { TableCard } from 'design-system/card/table/TableCard';
import { Column } from 'design-system/table';
import { ColumnPreference } from 'design-system/table/preferences';
// import { useFindOpenInvestigations } from '../../patientData/useFindOpenInvestigations';
// import { usePatient } from '../../usePatient';

type Investigation = {
    investigationId: string;
    startDate: Date;
    condition: string;
    caseStatus: string;
    notification: string;
    jurisdiction: string;
    investigator: string;
    coinfectionId: string;
};

const sampleData: Investigation[] = [
    {
        investigationId: 'INV001',
        startDate: new Date('2023-01-01'),
        condition: 'Influenza',
        caseStatus: 'Confirmed',
        notification: 'Sent',
        jurisdiction: 'New York',
        investigator: 'Dr. Smith',
        coinfectionId: 'CO001'
    },
    {
        investigationId: 'INV002',
        startDate: new Date('2023-02-15'),
        condition: 'COVID-19',
        caseStatus: 'Probable',
        notification: 'Pending',
        jurisdiction: 'California',
        investigator: 'Dr. Johnson',
        coinfectionId: 'CO002'
    },
    {
        investigationId: 'INV003',
        startDate: new Date('2023-03-10'),
        condition: 'Measles',
        caseStatus: 'Suspected',
        notification: 'Not Sent',
        jurisdiction: 'Texas',
        investigator: 'Dr. Lee',
        coinfectionId: 'CO003'
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

    const columns: Column<Investigation>[] = [
        {
            ...INVESTIGATION_ID,
            render: (value: Investigation) => <a href={`#${value.investigationId}`}>{value.investigationId}</a> // render link
        },
        {
            ...START_DATE,
            render: (value: Investigation) => value.startDate.toLocaleDateString()
        },
        {
            ...CONDITION,
            render: (value: Investigation) => value.condition
        },
        {
            ...CASE_STATUS,
            render: (value: Investigation) => value.caseStatus
        },
        {
            ...NOTIFICATION,
            render: (value: Investigation) => value.notification
        },
        {
            ...JURISDICTION,
            render: (value: Investigation) => value.jurisdiction
        },
        {
            ...INVESTIGATOR,
            render: (value: Investigation) => value.investigator
        },
        {
            ...COINFECTION_ID,
            render: (value: Investigation) => value.coinfectionId
        }
    ];

    return (
        <div>
            <TableCard
                id="patient-file-open-investigations-table-card"
                title="Open investigations"
                data={sampleData}
                columns={columns}
                columnPreferencesKey="patient-file-open-investigations-table-card-column-preferences"
                defaultColumnPreferences={columnPreferences}
            />
        </div>
    );
};

export default OpenInvestigationsCard;
