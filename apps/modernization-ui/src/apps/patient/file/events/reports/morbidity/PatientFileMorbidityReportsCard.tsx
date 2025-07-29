import { Suspense } from 'react';
import { Await } from 'react-router';
import { LoadingOverlay } from 'libs/loading';
import { MemoizedSupplier } from 'libs/supplying';
import { Column } from 'design-system/table';
import { ColumnPreference } from 'design-system/table/preferences';
import { Shown } from 'conditional-render';
import { permissions, Permitted } from 'libs/permission';
import { TableCard, TableCardProps } from 'design-system/card';
import { internalizeDateTime } from 'date';
import { displayProvider } from 'libs/provider';
import { LinkButton } from 'design-system/button';
import { MaybeLabeledValue } from 'design-system/value';
import { PatientFileMorbidityReport } from './morbidity-report';
import { ResultedTests } from 'libs/events/tests';
import { Associations } from 'libs/events/investigations/associated';
import { TreatmentList } from 'libs/events/reports/morbidity';

import styles from './morbidity-reports.module.scss';

const EVENT_ID = { id: 'local', name: 'Event ID' };
const DATE_RECEIVED = { id: 'received-on', name: 'Date received' };
const DATE_ADDED = { id: 'added-on', name: 'Date added' };
const FACILITY_PROVIDER = { id: 'facility-provider', name: 'Facility/provider' };
const REPORT_DATE = { id: 'reported-on', name: 'Report date' };
const CONDITION = { id: 'condition', name: 'Condition' };
const TREATMENT_INFO = { id: 'treatment-info', name: 'Treatment info' };
const ASSOCIATED_WITH = { id: 'associated-with', name: 'Associated with' };
const JURISDICTION = { id: 'jurisdiction', name: 'Jurisdiction' };

const columnPreferences: ColumnPreference[] = [
    { ...EVENT_ID },
    { ...DATE_RECEIVED, moveable: true, toggleable: true },
    { ...DATE_ADDED, moveable: true, toggleable: true, hidden: true },
    { ...FACILITY_PROVIDER, moveable: true, toggleable: true },
    { ...REPORT_DATE, moveable: true, toggleable: true },
    { ...CONDITION, moveable: true, toggleable: true },
    { ...TREATMENT_INFO, moveable: true, toggleable: true },
    { ...ASSOCIATED_WITH, moveable: true, toggleable: true },
    { ...JURISDICTION, moveable: true, toggleable: true }
];

const columns: Column<PatientFileMorbidityReport>[] = [
    {
        ...EVENT_ID,
        className: styles['local-header'],
        sortable: true,
        value: (value) => value.local,
        render: (value) => (
            <>
                <a href={`/nbs/api/profile/${value.patient}/report/morbidity/${value.id}`}>{value.local}</a>
                <Shown when={Boolean(value.processingDecision)}>
                    <br />
                    {value.processingDecision}
                </Shown>
            </>
        )
    },
    {
        ...DATE_RECEIVED,
        className: styles['date-time-header'],
        sortable: true,
        value: (value) => value.receivedOn,
        render: (value) => internalizeDateTime(value.receivedOn),
        sortIconType: 'numeric'
    },
    {
        ...DATE_ADDED,
        className: styles['date-header'],
        sortable: true,
        value: (value) => value.addedOn,
        render: (value) => internalizeDateTime(value.addedOn),
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
                <MaybeLabeledValue orientation="vertical" label="Reporting provider:">
                    {displayProvider(value.reportingProvider)}
                </MaybeLabeledValue>
            </>
        )
    },
    {
        ...REPORT_DATE,
        className: styles['date-time-header'],
        sortable: true,
        value: (value) => value.reportedOn,
        sortIconType: 'numeric'
    },
    {
        ...CONDITION,
        sortable: true,
        value: (value) => value.condition,
        render: (value) => (
            <>
                <strong>{value.condition}</strong>
                <ResultedTests>{value.resultedTests}</ResultedTests>
            </>
        )
    },
    {
        ...TREATMENT_INFO,
        sortable: true,
        value: (value) => value.treatments?.join(),
        render: (value) => <TreatmentList>{value.treatments}</TreatmentList>
    },
    {
        ...JURISDICTION,
        sortable: true,
        className: styles['long-coded-header'],
        value: (value) => value.jurisdiction
    },
    {
        ...ASSOCIATED_WITH,
        className: styles['local-header'],
        sortable: true,
        value: (value) => value.associations?.[0]?.local,
        render: (value) => <Associations patient={value.patient}>{value.associations}</Associations>
    }
];

type InternalCardProps = {
    patient: number;
    data?: PatientFileMorbidityReport[];
} & Omit<
    TableCardProps<PatientFileMorbidityReport>,
    'columnPreferencesKey' | 'defaultColumnPreferences' | 'columns' | 'data' | 'title'
>;

const InternalCard = ({ patient, sizing, data = [], ...remaining }: InternalCardProps) => (
    <TableCard
        title="Morbidity reports"
        sizing={sizing}
        data={data}
        columns={columns}
        columnPreferencesKey="patient.file.morbidity-report.preferences"
        defaultColumnPreferences={columnPreferences}
        actions={
            <Permitted permission={permissions.morbidityReport.add}>
                <LinkButton
                    secondary
                    sizing={sizing}
                    icon="add_circle"
                    href={`/nbs/api/profile/${patient}/report/morbidity`}>
                    Add morbidity report
                </LinkButton>
            </Permitted>
        }
        {...remaining}
    />
);

type PatientFileMorbidityReportsCardProps = {
    provider: MemoizedSupplier<Promise<PatientFileMorbidityReport[]>>;
} & Omit<InternalCardProps, 'data'>;

const PatientFileMorbidityReportsCard = ({ provider, ...remaining }: PatientFileMorbidityReportsCardProps) => (
    <Suspense
        fallback={
            <LoadingOverlay>
                <InternalCard {...remaining} />
            </LoadingOverlay>
        }>
        <Await resolve={provider.get()}>{(data) => <InternalCard data={data} {...remaining} />}</Await>
    </Suspense>
);

export { PatientFileMorbidityReportsCard };
