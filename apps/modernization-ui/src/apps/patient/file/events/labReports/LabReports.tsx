import { ClassicLink } from 'classic';
import { TableCard } from 'design-system/card/table/TableCard';
import { Column } from 'design-system/table';
import { ColumnPreference } from 'design-system/table/preferences';
import { PatientLabReport, TestResult } from 'generated';
import { Icon } from 'design-system/icon';
import { usePatient } from '../../usePatient';
// import { usePatientLabReports } from './usePatientLabReports';
import { renderFacilityProvider } from '../../renderPatientFile';
import { mockPatientLabReports } from './mockPatientLabReports';

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

const LabReports = () => {
    const patient = usePatient();
    // const { patientLabReports } = usePatientLabReports(patient.id);

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

    const columns: Column<PatientLabReport>[] = [
        {
            ...EVENT_ID,
            sortable: true,
            value: (value) => value.eventId,
            render: (value: PatientLabReport) => (
                <ClassicLink id="condition" url={`/nbs/api/profile/${patient.id}/report/lab/${value.eventId}`}>
                    {value.eventId}
                </ClassicLink>
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
                        <ClassicLink
                            url={`/nbs/api/profile/${patient}/investigation/${value.associatedInvestigation.id}`}>
                            {value.associatedInvestigation.id}
                        </ClassicLink>
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
            value: (value) => value.programArea,
            render: (value: PatientLabReport) => value.programArea
        },
        {
            ...JURISDICTION,
            sortable: true,
            value: (value) => value.jurisdiction,
            render: (value: PatientLabReport) => value.jurisdiction
        }
    ];

    return (
        <div>
            <TableCard
                id="patient-file-lab-reports-table-card"
                title="Lab reports"
                data={mockPatientLabReports || []}
                defaultCollapsed={mockPatientLabReports && mockPatientLabReports.length > 0 ? false : true}
                actions={[
                    {
                        sizing: 'small',
                        secondary: true,
                        children: 'Add lab report',
                        icon: <Icon name="add_circle" />,
                        labelPosition: 'right',
                        onClick: () => console.log('Add lab report clicked')
                    }
                ]}
                columns={columns}
                columnPreferencesKey="patient-file-lab-reports-table-card-column-preferences"
                defaultColumnPreferences={columnPreferences}
            />
        </div>
    );
};

export default LabReports;
