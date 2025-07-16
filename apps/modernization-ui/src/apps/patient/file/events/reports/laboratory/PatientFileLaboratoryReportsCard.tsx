import { Suspense } from 'react';
import { Await } from 'react-router';
import { LoadingOverlay } from 'libs/loading';
import { MemoizedSupplier } from 'libs/supplying';
import { Column } from 'design-system/table';
import { ColumnPreference } from 'design-system/table/preferences';
import { TableCard, TableCardProps } from 'design-system/card';
import { PatientFileLaboratoryReport } from './laboratory-report';
import { internalizeDateTime } from 'date';
import { displayProvider } from 'libs/provider';
import { MaybeLabeledValue } from 'design-system/value';
import { Associations } from 'libs/events/investigations/associated';
import { ResultedTests } from 'libs/events/tests';

import styles from './lab-reports.module.scss';
import { permissions, Permitted } from 'libs/permission';
import { LinkButton } from 'design-system/button';

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

const columns: Column<PatientFileLaboratoryReport>[] = [
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
        className: styles['local-header'],
        sortable: true,
        value: (value) => value.associations?.[0]?.local,
        render: (value) => <Associations patient={value.patient}>{value.associations}</Associations>
    },
    {
        ...PROGRAM_AREA,
        sortable: true,
        className: styles['coded-header'],
        value: (value) => value.programArea
    },
    {
        ...JURISDICTION,
        sortable: true,
        className: styles['long-coded-header'],
        value: (value) => value.jurisdiction
    }
];

type InternalCardProps = {
    patient: number;
    data?: PatientFileLaboratoryReport[];
} & Omit<
    TableCardProps<PatientFileLaboratoryReport>,
    'columnPreferencesKey' | 'defaultColumnPreferences' | 'columns' | 'data' | 'title'
>;

const InternalCard = ({ patient, sizing, data = [], ...remaining }: InternalCardProps) => (
    <TableCard
        title="Lab reports"
        sizing={sizing}
        data={data}
        columns={columns}
        columnPreferencesKey="patient.file.laboratory-report.preferences"
        defaultColumnPreferences={columnPreferences}
        actions={
            <Permitted permission={permissions.labReport.add}>
                <LinkButton secondary sizing={sizing} icon="add_circle" href={`/nbs/api/profile/${patient}/report/lab`}>
                    Add lab report
                </LinkButton>
            </Permitted>
        }
        {...remaining}
    />
);

type PatientFileLaboratoryReportsCardProps = {
    provider: MemoizedSupplier<Promise<PatientFileLaboratoryReport[]>>;
} & Omit<InternalCardProps, 'data'>;

const PatientFileLaboratoryReportsCard = ({ provider, ...remaining }: PatientFileLaboratoryReportsCardProps) => (
    <Suspense
        fallback={
            <LoadingOverlay>
                <InternalCard {...remaining} />
            </LoadingOverlay>
        }>
        <Await resolve={provider.get()}>{(data) => <InternalCard data={data} {...remaining} />}</Await>
    </Suspense>
);

export { PatientFileLaboratoryReportsCard };
