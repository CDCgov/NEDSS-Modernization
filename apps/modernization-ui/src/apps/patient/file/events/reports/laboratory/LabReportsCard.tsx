import { displayProvider } from 'libs/provider';
import { internalizeDateTime } from 'date/InternalizeDateTime';
import { TableCard } from 'design-system/card';
import { Column } from 'design-system/table';
import { MaybeLabeledValue } from 'design-system/value';
import { ColumnPreference } from 'design-system/table/preferences';
import { Associations } from 'libs/events/investigations/associated';
import { ResultedTests } from 'libs/events/tests';
import { PatientLabReport } from './patientLabReport';
import { usePatientLabReports } from './usePatientLabReports';

import styles from './lab-reports.module.scss';

const EVENT_ID = { id: 'local', name: 'Event ID' };
const DATE_RECEIVED = { id: 'received-on', name: 'Date received' };
const FACILITY_PROVIDER = { id: 'facility-provider', name: 'Facility/provider' };
const DATE_COLLECTED = { id: 'collected-on', name: 'Date collected' };
const TEST_RESULTS = { id: 'resulted-test', name: 'Test results' };
const ASSOCIATED_WITH = { id: 'associated-with', name: 'Associated with' };
const PROGRAM_AREA = { id: 'program-ara', name: 'Program area' };
const JURISDICTION = { id: 'jurisdiction', name: 'Jurisdiction' };

const columnPreferences: ColumnPreference[] = [
    { ...EVENT_ID },
    { ...DATE_RECEIVED, moveable: true, toggleable: true },
    { ...FACILITY_PROVIDER, moveable: true, toggleable: true },
    { ...DATE_COLLECTED, moveable: true, toggleable: true },
    { ...TEST_RESULTS, moveable: true, toggleable: true },
    { ...ASSOCIATED_WITH, moveable: true, toggleable: true },
    { ...PROGRAM_AREA, moveable: true, toggleable: true },
    { ...JURISDICTION, moveable: true, toggleable: true }
];

type LabReportsCardProps = {
    patient: number;
};

const columns: Column<PatientLabReport>[] = [
    {
        ...EVENT_ID,
        className: styles['local-header'],
        sortable: true,
        value: (value) => value.local,
        render: (value) => <a href={`/nbs/api/profile/${value.patient}/report/lab/${value.id}`}>{value.local}</a>
    },
    {
        ...DATE_RECEIVED,
        className: styles['date-header'],
        sortable: true,
        value: (value) => value.receivedDate,
        render: (value) => internalizeDateTime(value.receivedDate),
        sortIconType: 'numeric'
    },
    {
        ...FACILITY_PROVIDER,
        className: styles['text-header'],
        sortable: true,
        value: (value) => value.reportingFacility,
        render: (value) => (
            <>
                <MaybeLabeledValue orientation="vertical" label="Reporting facility:">
                    {value.reportingFacility}
                </MaybeLabeledValue>
                <MaybeLabeledValue orientation="vertical" label="Ordering provider:">
                    {displayProvider(value.orderingProvider)}
                </MaybeLabeledValue>
                <MaybeLabeledValue orientation="vertical" label="Ordering facility:">
                    {value.orderingFacility}
                </MaybeLabeledValue>
            </>
        )
    },
    {
        ...DATE_COLLECTED,
        className: styles['date-header'],
        sortable: true,
        value: (value) => value.collectedDate,
        sortIconType: 'numeric'
    },
    {
        ...TEST_RESULTS,
        className: styles['text-header'],
        sortable: true,
        value: (value) => value.resultedTests?.[0]?.name,
        render: (value) => (
            <>
                <ResultedTests>{value.resultedTests}</ResultedTests>
                <MaybeLabeledValue label="Specimen Source:">{value?.specimen?.source}</MaybeLabeledValue>
            </>
        )
    },
    {
        ...ASSOCIATED_WITH,
        className: styles['text-header'],
        sortable: true,
        value: (value) => value.associations?.[0]?.local,
        render: (value) => <Associations patient={value.patient}>{value.associations}</Associations>
    },
    {
        ...PROGRAM_AREA,
        sortable: true,
        value: (value) => value.programArea
    },
    {
        ...JURISDICTION,
        sortable: true,
        value: (value) => value.jurisdiction
    }
];

const LabReportsCard = ({ patient }: LabReportsCardProps) => {
    const { data } = usePatientLabReports(patient);

    return (
        <TableCard
            id="patient-file-lab-reports-table-card"
            title="Lab reports"
            sizing="small"
            data={data}
            columns={columns}
            columnPreferencesKey="patient.file.laboratory-report.preferences"
            defaultColumnPreferences={columnPreferences}
        />
    );
};

export { LabReportsCard };
