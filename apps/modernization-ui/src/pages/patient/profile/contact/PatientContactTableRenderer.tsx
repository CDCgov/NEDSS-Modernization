import { TableBody } from 'components/Table/Table';
import { format } from 'date-fns';
import { AssociatedWith, Tracing } from './PatientContacts';

export const headings = [
    { name: 'Date created', sortable: true },
    { name: 'Named by', sortable: true },
    { name: 'Date named', sortable: true },
    { name: 'Description', sortable: true },
    { name: 'Associated with', sortable: true },
    { name: 'Event #', sortable: true }
];

const association = (association?: AssociatedWith | null) =>
    association && (
        <>
            <div>
                <p className="margin-0 text-primary text-bold link" style={{ wordBreak: 'break-word' }}>
                    {association.local}
                </p>
                <p className="margin-0">{association.condition}</p>
            </div>
        </>
    );

const description = (contact: Tracing) => (
    <>
        {contact.condition && (
            <>
                <b>{contact.condition}</b>
                <br />
            </>
        )}
        {contact.priority && (
            <>
                <b>Priority:</b> {contact.priority}
                <br />
            </>
        )}
        {contact.disposition && (
            <>
                <b>Disposition:</b> {contact.disposition}
                <br />
            </>
        )}
    </>
);

export const asTableBody = (contact: Tracing | null): TableBody => ({
    id: contact?.event,
    checkbox: false,
    tableDetails: [
        {
            id: 1,
            title: (
                <>
                    {format(new Date(contact?.createdOn), 'MM/dd/yyyy')} <br />{' '}
                    {format(new Date(contact?.createdOn), 'hh:mm a')}
                </>
            ),
            class: 'link',
            link: ''
        },
        {
            id: 2,
            title: contact?.contact?.name
        },
        { id: 3, title: format(new Date(contact?.namedOn), 'MM/dd/yyyy') },
        {
            id: 4,
            title: contact && description(contact)
        },
        {
            id: 5,
            title: association(contact?.associatedWith)
        },
        { id: 6, title: contact?.event || null }
    ]
});
