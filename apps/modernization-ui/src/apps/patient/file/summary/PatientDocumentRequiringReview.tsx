import { TableCard } from 'design-system/card/table/TableCard';
import { Column } from 'design-system/table';
import { ColumnPreference } from 'design-system/table/preferences';
import { usePatientFileDocumentRequiringReview } from './usePatientFileDocumentRequiringReview';
import { usePatient } from '../usePatient';
import { DocumentRequiringReview } from 'generated';
import { ClassicLink } from 'classic';
import { internalizeDate } from 'date';
import { internalizeDateTime } from 'date/InternalizeDateTime';
import { renderMorbidity } from '../renderReports';

const renderReporting = (value: DocumentRequiringReview) => {
    return (
        <>
            {value.reportingFacility && (
                <>
                    <strong>Reporting facility:</strong>
                    <br />
                    {value.reportingFacility}
                    <br />
                </>
            )}

            {value.orderingProvider && (
                <>
                    <strong>Ordering provider:</strong>
                    <br />
                    {value.orderingProvider.prefix}
                    {value.orderingProvider.first} {value.orderingProvider.last}
                    <br />
                </>
            )}
            {value.sendingFacility && (
                <>
                    <strong>Sending facility:</strong>
                    <br />
                    {value.sendingFacility}
                </>
            )}
        </>
    );
};

const renderDescription = (value: DocumentRequiringReview) => {
    return (
        <>
            {value.type === 'Case Report' && <strong>{value.condition}</strong>}
            {value.type === 'Morbidity Report' && renderMorbidity(value)}
        </>
    );
};

const renderDateReceived = (value: string) => {
    return internalizeDateTime(value);
};

const resolveUrl = (value: DocumentRequiringReview) => {
    const { id } = usePatient();
    switch (value.type) {
        case 'Case Report':
            return `/nbs/api/profile/${id}/report/document/${value.id}`;
        case 'Morbidity Report':
            return `/nbs/api/profile/${id}/report/morbidity/${value.id}`;
        default:
            return '';
    }
};

const renderEventDate = (value: string) => {
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

export const PatientDocumentRequiringReview = () => {
    const { id } = usePatient();

    const { documents } = usePatientFileDocumentRequiringReview(id);

    const columns: Column<DocumentRequiringReview>[] = [
        { id: 'id', name: 'Event ID', render: (value: DocumentRequiringReview) => <>{renderEventId(value)}</> },
        { id: 'type', name: 'Document type', render: (value: DocumentRequiringReview) => <>{value.type}</> },
        {
            id: 'dateReceived',
            name: 'Date received',
            render: (value: DocumentRequiringReview) => (
                <>{value.dateReceived && renderDateReceived(value.dateReceived)}</>
            )
        },
        {
            id: 'reporting',
            name: 'Reporting facility/provider',
            render: (value: DocumentRequiringReview) => <>{renderReporting(value)}</>
        },
        {
            id: 'eventDate',
            name: 'Event date',
            render: (value: DocumentRequiringReview) => <>{value.eventDate && renderEventDate(value.eventDate)}</>
        },
        {
            id: 'description',
            name: 'Description',
            render: (value: DocumentRequiringReview) => <>{renderDescription(value)}</>
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

    return (
        <TableCard
            id="document-requiring-review"
            title="Document requiring review"
            columnPreferencesKey="document-requiring-review"
            defaultColumnPreferences={columnPreferences}
            columns={columns}
            data={documents ?? []}
            showSettings={true}
            collapsible
        />
    );
};
