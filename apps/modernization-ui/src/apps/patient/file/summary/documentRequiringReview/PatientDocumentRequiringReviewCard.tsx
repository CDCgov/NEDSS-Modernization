import { Suspense } from 'react';
import { Await } from 'react-router';
import { MemoizedSupplier } from 'libs/supplying';
import { LoadingOverlay } from 'libs/loading';
import { TableCard } from 'design-system/card';
import { Column } from 'design-system/table';
import { ColumnPreference } from 'design-system/table/preferences';
import { internalizeDate } from 'date';
import { internalizeDateTime } from 'date/InternalizeDateTime';
import { renderFacilityProvider, renderMorbidity } from '../../renderPatientFile';
import { PatientFileDocumentRequiringReview } from './drr';
import { TableCardProps } from 'design-system/card/table/TableCard';
import { MaybeLabeledValue } from 'design-system/value';
import { ResultedTests } from 'libs/events/tests';

import styles from './drr.module.scss';
import { displayNoData } from 'design-system/data';
import { Tag } from 'design-system/tag';
import { Shown } from 'conditional-render';
import { Tooltip } from 'design-system/tooltip';

const renderDescription = (value: PatientFileDocumentRequiringReview) => {
    return (
        <>
            {value.type === 'Case Report' && <strong>{value.condition}</strong>}
            {value.type === 'Morbidity Report' &&
                renderMorbidity(value.condition, value.resultedTests, value.treatments)}
            {value.type === 'Laboratory Report' && renderLabReport(value)}
        </>
    );
};

const renderLabReport = (value: PatientFileDocumentRequiringReview) => {
    return (
        <>
            <ResultedTests>{value.resultedTests}</ResultedTests>
            <MaybeLabeledValue label="Specimen Source:">{value?.specimen?.source}</MaybeLabeledValue>
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

const renderEventDate = (value?: PatientFileDocumentRequiringReview) => {
    if (value?.eventDate && (value?.type === 'Morbidity Report' || value?.type === 'Case Report')) {
        return (
            <MaybeLabeledValue label="Report date" orientation="vertical">
                {internalizeDate(value.eventDate)}
            </MaybeLabeledValue>
        );
    } else if (value?.eventDate && value?.type === 'Laboratory Report') {
        return (
            <MaybeLabeledValue label="Date collected" orientation="vertical">
                {internalizeDate(value.eventDate)}
            </MaybeLabeledValue>
        );
    }
    return displayNoData();
};

const renderEventId = (value: PatientFileDocumentRequiringReview) => {
    const classicUrl = resolveUrl(value);

    return <a href={classicUrl}>{value.local}</a>;
};

const renderType = (value: PatientFileDocumentRequiringReview) => {
    return (
        <>
            {value.type}
            <Shown when={value.isElectronic}>
                <br />
                <Tooltip message="Electronic indicator">
                    {(id) => (
                        <Tag variant="accent" size="small" aria-describedby={id}>
                            E
                        </Tag>
                    )}
                </Tooltip>
            </Shown>
        </>
    );
};

const EVENT_ID = { id: 'id', name: 'Event ID' };
const DOCUMENT_TYPE = { id: 'type', name: 'Document type' };
const DATE_RECEIVED = { id: 'dateReceived', name: 'Date received' };
const REPORTING = { id: 'reporting', name: 'Reporting facility/provider' };
const EVENT_DATE = { id: 'eventDate', name: 'Event date' };
const DESCRIPTION = { id: 'description', name: 'Description' };

const columns: Column<PatientFileDocumentRequiringReview>[] = [
    {
        ...EVENT_ID,
        className: styles['local-header'],
        sortable: true,
        value: (value) => value.local,
        render: renderEventId
    },
    {
        ...DOCUMENT_TYPE,
        className: styles['text-header'],
        sortable: true,
        value: (value) => value.type,
        render: (value) => renderType(value)
    },
    {
        ...DATE_RECEIVED,
        className: styles['date-time-header'],
        sortable: true,
        sortIconType: 'numeric',
        value: (value) => value.dateReceived,
        render: (value) => renderDateReceived(value.dateReceived)
    },
    {
        ...REPORTING,
        sortable: true,

        value: (value) =>
            value.reportingFacility ?? value.orderingProvider?.first ?? value.sendingFacility ?? value.orderingFacility,
        className: styles['reporting-header'],
        render: (value) =>
            renderFacilityProvider(
                value.reportingFacility,
                value.orderingProvider,
                value.sendingFacility,
                value.orderingFacility
            )
    },
    {
        ...EVENT_DATE,
        className: styles['date-time-header'],
        sortable: true,
        sortIconType: 'numeric',
        value: (value) => value.eventDate,
        render: (value) => renderEventDate(value)
    },
    {
        ...DESCRIPTION,
        sortable: true,
        value: (value) => value.condition ?? value.resultedTests?.at(0)?.name,
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
            title="Documents requiring review"
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
