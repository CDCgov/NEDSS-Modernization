import { TableCard } from 'design-system/card/table/TableCard';
import { Column } from 'design-system/table';
import { ColumnPreference } from 'design-system/table/preferences';
import { usePatientFileDocumentRequiringReview } from './usePatientFileDocumentRequiringReview';
import { usePatient } from '../../usePatient';
import { DocumentRequiringReview } from 'generated';
import { ClassicLink } from 'classic';
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
        case 'Case Report':
            return `/nbs/api/profile/${value.patient}/report/document/${value.id}`;
        case 'Morbidity Report':
            return `/nbs/api/profile/${value.patient}/report/morbidity/${value.id}`;
        default:
            return '';
    }
};

const renderEventDate = (value?: string) => {
    return internalizeDate(value);
};

const renderEventId = (value: DocumentRequiringReview) => {
    const classicUrl = resolveUrl(value);

    return (
        <ClassicLink id="eventId" url={classicUrl} destination="current">
            {value.local}
        </ClassicLink>
    );
};

const columns: Column<DocumentRequiringReview>[] = [
    { id: 'id', name: 'Event ID', render: renderEventId },
    { id: 'type', name: 'Document type', value: (value: DocumentRequiringReview) => value.type },
    {
        id: 'dateReceived',
        name: 'Date received',
        value: (value: DocumentRequiringReview) => value.dateReceived,
        render: (value: DocumentRequiringReview) => renderDateReceived(value.dateReceived)
    },
    {
        id: 'reporting',
        name: 'Reporting facility/provider',
        render: (value: DocumentRequiringReview) =>
            renderFacilityProvider(value.reportingFacility, value.orderingProvider, value.sendingFacility)
    },
    {
        id: 'eventDate',
        name: 'Event date',
        value: (value: DocumentRequiringReview) => value.eventDate,
        render: (value: DocumentRequiringReview) => renderEventDate(value.eventDate)
    },
    {
        id: 'description',
        name: 'Description',
        render: renderDescription
    }
];

const columnIDs = columns.map((column) => ({ id: column.id, name: column.name }));

const columnPreferences: ColumnPreference[] = [
    { ...columnIDs[0] },
    { ...columnIDs[1], toggleable: true },
    { ...columnIDs[2], toggleable: true },
    { ...columnIDs[3], toggleable: true },
    { ...columnIDs[4], toggleable: true },
    { ...columnIDs[5], toggleable: true }
];

export const PatientDocumentRequiringReview = () => {
    const { id } = usePatient();

    const { documents } = usePatientFileDocumentRequiringReview(id);

    return (
        <TableCard
            id="document-requiring-review"
            title="Document requiring review"
            columnPreferencesKey="document-requiring-review"
            defaultColumnPreferences={columnPreferences}
            columns={columns}
            data={documents}
            showSettings={true}
            collapsible
        />
    );
};
