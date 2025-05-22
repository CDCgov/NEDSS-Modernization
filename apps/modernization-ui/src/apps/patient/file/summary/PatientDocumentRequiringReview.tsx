import { TableCard } from 'design-system/card/table/TableCard';
import { Column } from 'design-system/table';
import { ColumnPreference } from 'design-system/table/preferences';

type Description = {
    title?: string;
    value?: string;
};

type DocumentRequiringReview = {
    id?: number;
    local?: string;
    type?: string;
    eventDate?: Date;
    dateReceived?: Date;
    isElectronic?: boolean;
    isUpdate?: boolean;
    reportingFacility?: string;
    orderingProvider?: string;
    sendingFacility?: string;
    descriptions?: Description[];
};

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

export const PatientDocumentRequiringReview = () => {
    const columns: Column<DocumentRequiringReview>[] = [
        { id: 'id', name: 'Event ID', render: (value: DocumentRequiringReview) => <>{value.id}</> },
        { id: 'type', name: 'Document type', render: (value: DocumentRequiringReview) => <>{value.type}</> },
        {
            id: 'dateReceived',
            name: 'Date received',
            render: (value: DocumentRequiringReview) => <>{value.dateReceived?.toLocaleString()}</>
        },
        {
            id: 'reporting',
            name: 'Reporting facility/provider',
            render: (value: DocumentRequiringReview) => <>{renderReporting(value)}</>
        },
        {
            id: 'eventDate',
            name: 'Event date',
            render: (value: DocumentRequiringReview) => <>{value.eventDate?.toLocaleString()}</>
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
            data={[]}
            showSettings={true}
            collapsible
        />
    );
};
