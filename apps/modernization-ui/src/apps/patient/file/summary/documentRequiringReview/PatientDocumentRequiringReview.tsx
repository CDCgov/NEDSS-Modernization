import { TableCard } from 'design-system/card';
import { Column } from 'design-system/table';
import { ColumnPreference } from 'design-system/table/preferences';
import { usePatientFileDocumentRequiringReview } from './usePatientFileDocumentRequiringReview';
import { DocumentRequiringReview } from 'generated';
import { internalizeDate } from 'date';
import { internalizeDateTime } from 'date/InternalizeDateTime';
import { renderFacilityProvider, renderLabReports, renderMorbidity } from '../../renderPatientFile';

const renderDescription = (value: DocumentRequiringReview) => {
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

const resolveUrl = (value: DocumentRequiringReview) => {
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

const renderEventId = (value: DocumentRequiringReview) => {
    const classicUrl = resolveUrl(value);

    return <a href={classicUrl}>{value.local}</a>;
};

const EVENT_ID = { id: 'id', name: 'Event ID' };
const DOCUMENT_TYPE = { id: 'type', name: 'Document type' };
const DATE_RECEIVED = { id: 'dateReceived', name: 'Date received' };
const REPORTING = { id: 'reporting', name: 'Reporting facility/provider' };
const EVENT_DATE = { id: 'eventDate', name: 'Event date' };
const DESCRIPTION = { id: 'description', name: 'Description' };

const columns: Column<DocumentRequiringReview>[] = [
    { ...EVENT_ID, sortable: true, value: (value) => value.local, render: renderEventId },
    { ...DOCUMENT_TYPE, sortable: true, value: (value) => value.type },
    {
        ...DATE_RECEIVED,
        sortable: true,
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

type PatientDocumentRequiringReviewCardProps = {
    patient: number;
};

export const PatientDocumentRequiringReview = ({ patient }: PatientDocumentRequiringReviewCardProps) => {
    const { documents } = usePatientFileDocumentRequiringReview(patient);

    return (
        <TableCard
            id="document-requiring-review"
            title="Document requiring review"
            sizing="small"
            columnPreferencesKey="patient.file.drr.preferences"
            defaultColumnPreferences={columnPreferences}
            columns={columns}
            data={documents}
            collapsible
        />
    );
};
