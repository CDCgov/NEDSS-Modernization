import { TableCard, TableCardProps } from 'design-system/card';
import { LoadingOverlay } from 'libs/loading';
import { MemoizedSupplier } from 'libs/supplying';
import { Suspense } from 'react';
import { Await } from 'react-router';
import { ColumnPreference } from 'design-system/table/preferences';
import { Column } from 'design-system/table';

import styles from './treatments.module.scss';
import { internalizeDate, internalizeDateTime } from 'date';
import { displayProvider } from 'libs/provider';
import { Associations } from 'libs/events/investigations/associated';
import { PatientFileTreatment } from '.';

const EVENT_ID = { id: 'local', name: 'Event ID' };
const DATE_RECEIVED = { id: 'created-on', name: 'Date created' };
const PROVIDER = { id: 'provider', name: 'Provider' };
const TREATMENT_DATE = { id: 'treated-on', name: 'Treatment date' };
const TREATMENT = { id: 'treatment', name: 'Treatment' };
const ASSOCIATED_WITH = { id: 'associated-with', name: 'Associated with' };

const columnPreferences: ColumnPreference[] = [
    { ...EVENT_ID },
    { ...DATE_RECEIVED, moveable: true, toggleable: true },
    { ...PROVIDER, moveable: true, toggleable: true },
    { ...TREATMENT_DATE, moveable: true, toggleable: true },
    { ...TREATMENT, moveable: true, toggleable: true },
    { ...ASSOCIATED_WITH, moveable: true, toggleable: true }
];

const columns: Column<PatientFileTreatment>[] = [
    {
        ...EVENT_ID,
        className: styles['local-header'],
        sortable: true,
        value: (value) => value.local,
        render: (value) => <a href={`/nbs/api/profile/${value.patient}/treatment/${value.id}`}>{value.local}</a>
    },
    {
        ...DATE_RECEIVED,
        className: styles['date-time-header'],
        sortable: true,
        value: (value) => value.createdOn,
        render: (value) => internalizeDateTime(value.createdOn),
        sortIconType: 'numeric'
    },
    {
        ...PROVIDER,
        sortable: true,
        className: styles['text-header'],
        value: (value) => value.provider?.first,
        render: (value) => displayProvider(value.provider),
        sortIconType: 'alpha'
    },
    {
        ...TREATMENT_DATE,
        className: styles['treatment-date-header'],
        sortable: true,
        value: (value) => value.treatedOn,
        render: (value) => internalizeDate(value.treatedOn)
    },
    {
        ...TREATMENT,
        sortable: true,
        value: (value) => value.description,
        render: (value) => <strong>{value.description}</strong>,
        sortIconType: 'alpha'
    },
    {
        ...ASSOCIATED_WITH,
        sortable: true,
        className: styles['long-coded-header'],
        value: (value) => value.associations?.[0]?.local,
        render: (value) => <Associations patient={value.patient}>{value.associations}</Associations>
    }
];

type InternalCardProps = {
    data?: PatientFileTreatment[];
} & Omit<
    TableCardProps<PatientFileTreatment>,
    'columnPreferencesKey' | 'defaultColumnPreferences' | 'columns' | 'data' | 'title'
>;

const InternalCard = ({ sizing, data = [], ...remaining }: InternalCardProps) => (
    <TableCard
        title="Treatments"
        sizing={sizing}
        data={data}
        columns={columns}
        columnPreferencesKey="patient.file.treatments.preferences"
        defaultColumnPreferences={columnPreferences}
        {...remaining}
    />
);

type PatientFileTreatmentsCardProps = {
    provider: MemoizedSupplier<Promise<PatientFileTreatment[]>>;
} & Omit<InternalCardProps, 'data'>;

const PatientFileTreatmentsCard = ({ provider, ...remaining }: PatientFileTreatmentsCardProps) => {
    return (
        <Suspense
            fallback={
                <LoadingOverlay>
                    <InternalCard {...remaining} />
                </LoadingOverlay>
            }>
            <Await resolve={provider.get()}>{(data) => <InternalCard data={data} {...remaining} />}</Await>
        </Suspense>
    );
};

export { PatientFileTreatmentsCard };
