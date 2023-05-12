import { useEffect, useState } from 'react';
import { format } from 'date-fns';
import { TableBody, TableComponent } from 'components/Table/Table';
import { ClassicLink } from 'classic';
import { usePage, goToPage } from 'page';
import { Direction } from 'sorting';
import { AssociatedWith, Tracing, Contact, Headers } from './PatientContacts';
import { SortCriteria, sort } from './PatientContactSorter';
import { Link } from 'react-router-dom';

const displayContact = (contact: Contact) => (
    <Link to={`/patient-profile/${contact.id}`} className={'profile'}>
        {contact.name}
    </Link>
);

const displatAssociation = (patient: string, association?: AssociatedWith | null) =>
    association && (
        <div>
            <ClassicLink url={`/nbs/api/profile/${patient}/investigation/${association.id}`}>
                {association?.local}
            </ClassicLink>
            <p className="margin-0">{association?.condition}</p>
        </div>
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

const asTableBody =
    (patient: string) =>
    (contact: Tracing | null): TableBody => ({
        id: contact?.event,
        checkbox: false,
        tableDetails: [
            {
                id: 1,
                title: contact && (
                    <ClassicLink
                        url={`/nbs/api/profile/${patient}/contact/${contact.contactRecord}?condition=${contact.condition?.id}`}>
                        {format(contact.createdOn, 'MM/dd/yyyy')} <br /> {format(contact.createdOn, 'hh:mm a')}
                    </ClassicLink>
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
                title: displatAssociation(patient, contact?.associatedWith)
            },
            { id: 6, title: contact?.event || null }
        ]
    });

const asTableBodies = (patient: string, tracings: Tracing[]): TableBody[] => tracings?.map(asTableBody(patient)) || [];

type Props = {
    patient?: string;
    tracings: Tracing[];
    title: string;
    headings: { name: Headers; sortable: boolean }[];
};

export const PatientContactTable = ({ patient, tracings, title, headings }: Props) => {
    const { page, dispatch } = usePage();
    const [criteria, setCriteria] = useState<SortCriteria>({});

    const [bodies, setBodies] = useState<TableBody[]>([]);

    useEffect(() => {
        if (patient) {
            const sorted = sort(tracings, criteria);
            setBodies(asTableBodies(patient, sorted));
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
            handleNext={goToPage(dispatch)}
            sortData={handleSort}
        />
    );
};
