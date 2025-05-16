import { TableCard } from 'design-system/card/table/TableCard';
import { Column } from 'design-system/table';
import { ColumnPreference } from 'design-system/table/preferences';

type Test = {
    id: number;
    type: string;
    received: number;
    reporting: string;
    eventDate: number;
    description: string;
};

export const PatientDocumentRequiringReview = () => {
    const columns: Column<Test>[] = [
        { id: 'id', name: 'Event ID', render: (value: Test) => <>{value.id}</> },
        { id: 'type', name: 'Document type', render: (value: Test) => <>{value.type}</> },
        { id: 'received', name: 'Date received', render: (value: Test) => <>{value.received}</> },
        { id: 'reporting', name: 'Reporting facility/provider', render: (value: Test) => <>{value.reporting}</> },
        { id: 'eventDate', name: 'Event date', render: (value: Test) => <>{value.eventDate}</> },
        { id: 'description', name: 'Description', render: (value: Test) => <>{value.description}</> }
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

    const data: Test[] = [
        {
            id: 1234,
            type: 'Lab report',
            received: Date.now(),
            reporting: 'Reporting facility',
            eventDate: Date.now(),
            description: 'Test description'
        }
    ];

    return (
        <TableCard
            id="document-requiring-review"
            title="Document requiring review"
            columnPreferencesKey="document-requiring-review"
            defaultColumnPreferences={columnPreferences}
            columns={columns}
            data={data}
            showSettings={true}
            collapsible
        />
    );
};
