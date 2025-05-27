import { TableCard } from 'design-system/card/table/TableCard';
import { Column } from 'design-system/table';
import { ColumnPreference } from 'design-system/table/preferences';
import { usePatientFileDocumentRequiringReview } from './usePatientFileDocumentRequiringReview';
import { usePatient } from '../usePatient';
import { DocumentRequiringReview } from 'generated';
import { ClassicLink } from 'classic';

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
                    {value.orderingProvider}
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
            {value.descriptions?.map((description) => (
                <>
                    <strong>{description.title}</strong>
                    <br />
                    {description.value}
                    <br />
                </>
            ))}
        </>
    );
};

const dateTransform = (value: string) => {
    const date = new Date(value);
    return date.toLocaleString('en-US', {
        year: 'numeric',
        month: 'numeric',
        day: 'numeric',
        hour: 'numeric',
        minute: 'numeric',
        hour12: true
    });
};

const renderEventId = (value: DocumentRequiringReview) => {
    var classicUrl = '';
    if (value.type == 'Lab report') {
        classicUrl = '/nbs/api/profile/{patient}/document/{identifier}';
    }
    return <ClassicLink url=""></ClassicLink>;
};

export const PatientDocumentRequiringReview = () => {
    const { id } = usePatient();

    const { documents } = usePatientFileDocumentRequiringReview(id);

    const columns: Column<DocumentRequiringReview>[] = [
        { id: 'id', name: 'Event ID', render: (value: DocumentRequiringReview) => <>{value.local}</> },
        { id: 'type', name: 'Document type', render: (value: DocumentRequiringReview) => <>{value.type}</> },
        {
            id: 'dateReceived',
            name: 'Date received',
            render: (value: DocumentRequiringReview) => <>{value.dateReceived && dateTransform(value.dateReceived)}</>
        },
        {
            id: 'reporting',
            name: 'Reporting facility/provider',
            render: (value: DocumentRequiringReview) => <>{renderReporting(value)}</>
        },
        {
            id: 'eventDate',
            name: 'Event date',
            render: (value: DocumentRequiringReview) => <>{value.eventDate && dateTransform(value.eventDate)}</>
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
