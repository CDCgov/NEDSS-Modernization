import { ClassicLink } from 'classic';
import { TableCard } from 'design-system/card/table/TableCard';
import { Column } from 'design-system/table';
import { ColumnPreference } from 'design-system/table/preferences';
import { FacilityProviders, OrderingProvider, PatientLabReport } from 'generated';
// import { usePatientLabReports } from './usePatientLabReports';
import { usePatient } from '../../usePatient';
import { mockPatientLabReports } from './mockPatientLabReports';

const maybeOrderingProviderName = (orderingProvider: OrderingProvider | undefined) => {
    if (!orderingProvider) {
        return undefined;
    }
    const { first, last } = orderingProvider;
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

const displayFacilityProviders = (facilityProviders: FacilityProviders | undefined) => {
    if (!facilityProviders) {
        return undefined;
    }
    return (
        <div>
            <b>Reporting Facility:</b>
            <br />
            {facilityProviders.reportingFacility}
            <br />
            <b>Ordering Facility:</b>
            <br />
            {facilityProviders.sendingFacility}
            <br />
            <b>Ordering Provider:</b>
            <br />
            {maybeOrderingProviderName(facilityProviders.orderingProvider)}
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
            render: (value: PatientLabReport) => (
                <ClassicLink id="condition" url={`/nbs/api/profile/${patient.id}/report/lab/${value.eventId}`}>
                    {value.eventId}
                </ClassicLink>
            )
        },
        {
            ...DATE_RECEIVED,
            sortable: true,
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
            render: (value: PatientLabReport) => displayFacilityProviders(value.facilityProviders)
        },
        {
            ...DATE_COLLECTED,
            sortable: true,
            render: (value: PatientLabReport) => value.collectedDate?.toString()
        },
        {
            ...TEST_RESULTS,
            sortable: true,
            render: (value: PatientLabReport) => value.testResults?.map((v) => v.codedResult)
        },
        {
            ...ASSOCIATED_WITH,
            sortable: true,
            render: (value: PatientLabReport) => value.associatedInvestigation?.id
        },
        {
            ...PROGRAM_AREA,
            sortable: true,
            render: (value: PatientLabReport) => value.programArea
        },
        {
            ...JURISDICTION,
            sortable: true,
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
                columns={columns}
                columnPreferencesKey="patient-file-lab-reports-table-card-column-preferences"
                defaultColumnPreferences={columnPreferences}
                noDataFallback
            />
        </div>
    );
};

export default LabReports;
