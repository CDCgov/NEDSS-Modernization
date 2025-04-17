import { useEffect, useState } from 'react';
import { format } from 'date-fns';
import { TableBody, TableComponent } from 'components/Table/Table';
import { ClassicLink, ClassicModalLink } from 'classic';
import { usePagination } from 'pagination';
import { Direction } from 'sorting';
import { AssociatedWith, Tracing, Contact, Headers } from './PatientContacts';
import { SortCriteria, sort } from './PatientContactSorter';
import { Link } from 'react-router';

const displayContact = (contact: Contact) => (
    <Link to={`/patient-profile/${contact.id}`} className={'profile'}>
        {contact.name}
    </Link>
);

const displayAssociation = (patient: string, association?: AssociatedWith | null) =>
    association && (
        <>
            <ClassicLink url={`/nbs/api/profile/${patient}/investigation/${association.id}`}>
                {association?.local}
            </ClassicLink>
            <p className="margin-0">{association?.condition}</p>
        </>
    );

const displayDescription = (contact: Tracing) => (
    <>
        {contact.condition && (
            <>
                <b>{contact.condition.description}</b>
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

type Props = {
    patient?: string;
    tracings: Tracing[];
    title: string;
    headings: { name: Headers; sortable: boolean }[];
};

export const PatientContactTable = ({ patient, tracings, title, headings }: Props) => {
    const { page, request, reload } = usePagination();
    const [criteria, setCriteria] = useState<SortCriteria>({});

    const [bodies, setBodies] = useState<TableBody[]>([]);

    const handleModalClose = () => {
        reload();
    };

    const asTableBody = (contact: Tracing): TableBody => ({
        id: contact?.event,
        tableDetails: [
            {
                id: 1,
                title: contact && (
                    <ClassicModalLink
                        url={`/nbs/api/profile/${patient}/contact/${contact.contactRecord}?condition=${contact.condition?.id}`}
                        onClose={handleModalClose}>
                        {format(contact.createdOn, 'MM/dd/yyyy')} <br /> {format(contact.createdOn, 'hh:mm a')}
                    </ClassicModalLink>
                )
            },
            {
                id: 2,
                title: contact?.contact && displayContact(contact.contact)
            },
            { id: 3, title: contact && format(new Date(contact.namedOn), 'MM/dd/yyyy') },
            {
                id: 4,
                title: contact && displayDescription(contact)
            },
            {
                id: 5,
                title: patient && displayAssociation(patient, contact?.associatedWith)
            },
            { id: 6, title: contact?.event || null }
        ]
    });

    useEffect(() => {
        if (patient) {
            const sorted = sort(tracings, criteria);

            setBodies(sorted.map(asTableBody));
        }
    }, [tracings, criteria]);

    const handleSort = (name: string, direction: string): void => {
        setCriteria({ name: name as Headers, type: direction as Direction });
    };

    return (
        <TableComponent
            tableHeader={title}
            tableHead={headings}
            tableBody={bodies}
            isPagination={true}
            pageSize={page.pageSize}
            totalResults={page.total}
            currentPage={page.current}
            handleNext={request}
            sortData={handleSort}
        />
    );
};
