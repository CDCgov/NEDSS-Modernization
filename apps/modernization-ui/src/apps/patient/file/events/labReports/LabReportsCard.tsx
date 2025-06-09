import { TableCard } from 'design-system/card';
import { Column } from 'design-system/table';
import { ColumnPreference } from 'design-system/table/preferences';
import { PatientLabReport, TestResult } from 'generated';
import { usePatientLabReports } from './usePatientLabReports';
import { renderFacilityProvider } from 'apps/patient/file/renderPatientFile';

const EVENT_ID = { id: 'patient-file-lab-reports-eventId', name: 'Event ID' };
const DATE_RECEIVED = { id: 'patient-file-lab-reports-dateReceived', name: 'Date received' };
const FACILITY_PROVIDER = { id: 'patient-file-lab-reports-facilityProvider', name: 'Facility/provider' };
const DATE_COLLECTED = { id: 'patient-file-lab-reports-dateCollected', name: 'Date collected' };
const TEST_RESULTS = { id: 'patient-file-lab-reports-testResults', name: 'Test results' };
const ASSOCIATED_WITH = { id: 'patient-file-lab-reports-associatedWith', name: 'Associated with' };
const PROGRAM_AREA = { id: 'patient-file-lab-reports-programArea', name: 'Program area' };
const JURISDICTION = { id: 'patient-file-lab-reports-jurisdiction', name: 'Jurisdiction' };

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

const displayTestResults = (testResults?: TestResult[] | undefined) => {
    if (!testResults || testResults.length < 1) {
        return undefined;
    }

    const testResultAssignMapKays = testResults.map((v, i) => ({ ...v, renderKey: i }));

    return (
        <div>
            {testResultAssignMapKays.map((testResult) => {
                const { resultedTest, codedResult, numericResult, units, highRange, lowRange, statusDetails } =
                    testResult;
                return (
                    <div key={testResult.renderKey}>
                        {resultedTest && (
                            <div>
                                <b>{resultedTest}:</b>
                            </div>
                        )}
                        {codedResult && <div>{codedResult}</div>}
                        {numericResult && units && (
                            <div>
                                {numericResult} {units}
                            </div>
                        )}
                        {highRange && lowRange && (
                            <div>
                                <b>Reference Range:</b>
                                <br />
                                From {lowRange} to {highRange}
                            </div>
                        )}
                        {statusDetails && <div>{statusDetails}</div>}
                        <br />
                    </div>
                );
            })}
        </div>
    );
};

type LabReportsCardProps = {
    patient: number;
};

const LabReportsCard = ({ patient }: LabReportsCardProps) => {
    const { patientLabReports } = usePatientLabReports(patient);

    const columns: Column<PatientLabReport>[] = [
        {
            ...EVENT_ID,
            sortable: true,
            value: (value) => value.eventId,
            render: (value: PatientLabReport) => (
                <a href={`/nbs/api/profile/${patient}/report/lab/${value.id}`}>{value.eventId}</a>
            )
        },
        {
            ...DATE_RECEIVED,
            sortable: true,
            value: (value) => value.receivedDate,
            render: (value: PatientLabReport) =>
                value.receivedDate && (
                    <div>
                        <span>{new Date(value.receivedDate).toLocaleDateString()}</span>
                        <br />
                        <span>{new Date(value.receivedDate).toLocaleTimeString()}</span>
                    </div>
                )
        },
        {
            ...FACILITY_PROVIDER,
            sortable: true,
            value: (value) => value.facilityProviders?.reportingFacility,
            render: (value: PatientLabReport) =>
                renderFacilityProvider(
                    value.facilityProviders?.reportingFacility,
                    value.facilityProviders?.orderingProvider,
                    value.facilityProviders?.sendingFacility
                )
        },
        {
            ...DATE_COLLECTED,
            sortable: true,
            value: (value) => value.collectedDate,
            render: (value: PatientLabReport) =>
                value.collectedDate && new Date(value.collectedDate).toLocaleDateString()
        },
        {
            ...TEST_RESULTS,
            sortable: true,
            value: (value) => value?.testResults?.at(0)?.resultedTest,
            render: (value: PatientLabReport) => displayTestResults(value.testResults)
        },
        {
            ...ASSOCIATED_WITH,
            sortable: true,
            value: (value) => value.associatedInvestigation?.id,
            render: (value: PatientLabReport) =>
                value.associatedInvestigation && (
                    <div>
                        <a href={`/nbs/api/profile/${patient}/investigation/${value.associatedInvestigation.id}`}>
                            {value.associatedInvestigation.id}
                        </a>
                        <p className="margin-0">
                            <b>{value.associatedInvestigation.condition}</b>
                        </p>
                        <p className="margin-0">{value.associatedInvestigation.status}</p>
                    </div>
                )
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

    return (
        <TableCard
            id="patient-file-lab-reports-table-card"
            title="Lab reports"
            sizing="small"
            data={patientLabReports || []}
            columns={columns}
            columnPreferencesKey="patient.file.laboratory-report.preferences"
            defaultColumnPreferences={columnPreferences}
        />
    );
};

export { LabReportsCard };
