import { Suspense } from 'react';
import { Await } from 'react-router';
import { MemoizedSupplier } from 'libs/supplying';
import { LoadingOverlay } from 'libs/loading';
import { TableCard } from 'design-system/card';
import { Column } from 'design-system/table';
import { ColumnPreference } from 'design-system/table/preferences';
import { internalizeDate } from 'date';
import { internalizeDateTime } from 'date/InternalizeDateTime';
import { renderFacilityProvider, renderLabReports, renderMorbidity } from '../../renderPatientFile';
import { PatientFileDocumentRequiringReview } from './drr';
import { TableCardProps } from 'design-system/card/table/TableCard';

const renderDescription = (value: PatientFileDocumentRequiringReview) => {
    return (
        <>
            {value.type === 'Case Report' && <strong>{value.condition}</strong>}
            {value.type === 'Morbidity Report' &&
                renderMorbidity(value.condition, value.resultedTests, value.treatments)}
            {value.type === 'Laboratory Report' && renderLabReports(value.resultedTests)}
        </>
    );
};

const renderDateReceived = (value?: string) => {
    return internalizeDateTime(value);
};

const resolveUrl = (value: PatientFileDocumentRequiringReview) => {
    switch (value.type) {
        case 'Morbidity Report':
            return `/nbs/api/profile/${value.patient}/report/morbidity/${value.id}`;
        case 'Laboratory Report':
            return `/nbs/api/profile/${value.patient}/report/lab/${value.id}`;
        default:
            return `/nbs/api/profile/${value.patient}/document/${value.id}`;
    }
};

const renderEventDate = (value?: string) => {
    return internalizeDate(value);
};

const renderEventId = (value: PatientFileDocumentRequiringReview) => {
    const classicUrl = resolveUrl(value);

    return <a href={classicUrl}>{value.local}</a>;
};

const EVENT_ID = { id: 'id', name: 'Event ID' };
const DOCUMENT_TYPE = { id: 'type', name: 'Document type' };
const DATE_RECEIVED = { id: 'dateReceived', name: 'Date received' };
const REPORTING = { id: 'reporting', name: 'Reporting facility/provider' };
const EVENT_DATE = { id: 'eventDate', name: 'Event date' };
const DESCRIPTION = { id: 'description', name: 'Description' };

const columns: Column<PatientFileDocumentRequiringReview>[] = [
    { ...EVENT_ID, sortable: true, value: (value) => value.local, render: renderEventId },
    { ...DOCUMENT_TYPE, sortable: true, value: (value) => value.type },
    {
        ...DATE_RECEIVED,
        sortable: true,
        sortIconType: 'numeric',
        value: (value) => value.dateReceived,
        render: (value) => renderDateReceived(value.dateReceived)
    },
    {
        ...REPORTING,
        render: (value) =>
            renderFacilityProvider(value.reportingFacility, value.orderingProvider, value.sendingFacility)
    },
    {
        ...EVENT_DATE,
        sortable: true,
        sortIconType: 'numeric',
        value: (value) => value.eventDate,
        render: (value) => renderEventDate(value.eventDate)
    },
    {
        ...DESCRIPTION,
        render: renderDescription
    }
];

const columnPreferences: ColumnPreference[] = [
    { ...EVENT_ID },
    { ...DOCUMENT_TYPE, toggleable: true },
    { ...DATE_RECEIVED, toggleable: true },
    { ...REPORTING, toggleable: true },
    { ...EVENT_DATE, toggleable: true },
    { ...DESCRIPTION, toggleable: true }
];

type InternalCardProps = {
    data?: PatientFileDocumentRequiringReview[];
} & Omit<
    TableCardProps<PatientFileDocumentRequiringReview>,
    'columnPreferencesKey' | 'defaultColumnPreferences' | 'columns' | 'data' | 'title'
>;

const InternalCard = ({ data = [], ...remaining }: InternalCardProps) => {
    return (
        <TableCard
            title="Document requiring review"
            data={data}
            columns={columns}
            columnPreferencesKey="patient.file.drr.preferences"
            defaultColumnPreferences={columnPreferences}
            {...remaining}
        />
    );
};

type PatientDocumentRequiringReviewCardProps = {
    provider: MemoizedSupplier<Promise<PatientFileDocumentRequiringReview[]>>;
} & Omit<InternalCardProps, 'data'>;

const PatientDocumentRequiringReviewCard = ({ provider, ...remaining }: PatientDocumentRequiringReviewCardProps) => (
    <Suspense
        fallback={
            <LoadingOverlay>
                <InternalCard {...remaining} />
            </LoadingOverlay>
        }>
        <Await resolve={provider.get()}>{(data) => <InternalCard data={data} {...remaining} />}</Await>
    </Suspense>
);

export { PatientDocumentRequiringReviewCard };
