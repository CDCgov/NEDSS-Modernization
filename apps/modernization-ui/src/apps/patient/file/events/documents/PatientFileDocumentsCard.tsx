import { MemoizedSupplier } from 'libs/supplying';
import { PatientFileDocument } from './documents';
import { TableCard, TableCardProps } from 'design-system/card';
import { Suspense } from 'react';
import { LoadingOverlay } from 'libs/loading';
import { Await } from 'react-router';
import { ColumnPreference } from 'design-system/table/preferences';
import { Column } from 'design-system/table';

import styles from './document-card.module.scss';
import { internalizeDate, internalizeDateTime } from 'date';
import { Associations } from 'libs/events/investigations/associated';

const EVENT_ID = { id: 'local', name: 'Event ID' };
const DATE_RECEIVED = { id: 'created-on', name: 'Date received' };
const SENDING_FACILILTY = { id: 'sending-facility', name: 'Sending facility' };
const DATE_REPORTED = { id: 'date-reported', name: 'Date reported' };
const CONDITION = { id: 'condition', name: 'Condition' };
const ASSOCIATED_WITH = { id: 'associated-with', name: 'Associated with' };

const columnPreferences: ColumnPreference[] = [
    { ...EVENT_ID },
    { ...DATE_RECEIVED, moveable: true, toggleable: true },
    { ...SENDING_FACILILTY, moveable: true, toggleable: true },
    { ...DATE_REPORTED, moveable: true, toggleable: true },
    { ...CONDITION, moveable: true, toggleable: true },
    { ...ASSOCIATED_WITH, moveable: true, toggleable: true }
];

const columns: Column<PatientFileDocument>[] = [
    {
        ...EVENT_ID,
        className: styles['local-header'],
        sortable: true,
        value: (value) => value.local,
        render: (value) => (
            <>
                <a href="">{value.local}</a>
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
        ...SENDING_FACILILTY,
        sortable: true,
        className: styles['text-header'],
        value: (value) => value.sendingFacility
    },
    {
        ...DATE_REPORTED,
        className: styles['report-header'],
        sortable: true,
        value: (value) => value.reportedOn,
        render: (value) => internalizeDate(value.reportedOn),
        sortIconType: 'numeric'
    },
    {
        ...CONDITION,
        sortable: true,
        value: (value) => value.condition
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
    data?: PatientFileDocument[];
} & Omit<
    TableCardProps<PatientFileDocument>,
    'columnPreferencesKey' | 'defaultColumnPreferences' | 'columns' | 'data' | 'title'
>;

const InternalCard = ({ sizing, data = [], ...remaining }: InternalCardProps) => {
    return (
        <TableCard
            title="Documents"
            sizing={sizing}
            data={data}
            columns={columns}
            columnPreferencesKey="patient.file.documents.preferences"
            defaultColumnPreferences={columnPreferences}
            {...remaining}
        />
    );
};

type PatientFileDocumentsCardProps = {
    provider: MemoizedSupplier<Promise<PatientFileDocument[]>>;
} & Omit<InternalCardProps, 'data'>;

const PatientFileDocumentsCard = ({ provider, ...remaining }: PatientFileDocumentsCardProps) => {
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

export { PatientFileDocumentsCard };
