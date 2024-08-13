import { useEffect, useState } from 'react';
import { Icon } from '@trussworks/react-uswds';
import { TableBody, TableComponent } from 'components/Table/Table';
import { format } from 'date-fns';
import { usePage } from 'page';
import { Direction } from 'sorting';
import { ClassicLink, ClassicModalButton, ClassicModalLink } from 'classic';
import { Vaccination, AssociatedWith, Headers } from './PatientVaccination';
import { SortCriteria, sort } from './PatientVaccinationSorter';

const headings = [
    { name: Headers.DateCreated, sortable: true },
    { name: Headers.Provider, sortable: true },
    { name: Headers.DateAdministered, sortable: true },
    { name: Headers.VaccineAdministered, sortable: true },
    { name: Headers.AssociatedWith, sortable: true },
    { name: Headers.Event, sortable: true }
];

const displayAssociation = (patient: string, association?: AssociatedWith | null) =>
    association && (
        <>
            <ClassicLink url={`/nbs/api/profile/${patient}/investigation/${association.id}`}>
                {association?.local}
            </ClassicLink>
            <p className="margin-0">{association?.condition}</p>
        </>
    );

type Props = {
    patient?: string;
    vaccinations: Vaccination[];
    allowAdd?: boolean;
};

export const PatientVaccinationTable = ({ patient, vaccinations, allowAdd = false }: Props) => {
    const { page, request, reload } = usePage();
    const [criteria, setCriteria] = useState<SortCriteria>({});

    const [bodies, setBodies] = useState<TableBody[]>([]);

    const handleModalClose = () => {
        reload();
    };

    const buttons = (
        <ClassicModalButton url={`/nbs/api/profile/${patient}/vaccination`} onClose={handleModalClose}>
            <Icon.Add className="margin-right-05" />
            Add vaccination
        </ClassicModalButton>
    );

    const asTableBody = (vaccination: Vaccination): TableBody => ({
        id: vaccination?.event,
        tableDetails: [
            {
                id: 1,
                title: vaccination && (
                    <ClassicModalLink
                        url={`/nbs/api/profile/${patient}/vaccination/${vaccination.vaccination}`}
                        destination="none"
                        onClose={handleModalClose}>
                        {format(vaccination.createdOn, 'MM/dd/yyyy')} <br /> {format(vaccination.createdOn, 'hh:mm a')}
                    </ClassicModalLink>
                )
            },
            {
                id: 2,
                title: vaccination?.provider
            },
            { id: 3, title: vaccination.administeredOn && format(new Date(vaccination.administeredOn), 'MM/dd/yyyy') },
            {
                id: 4,
                title: vaccination.administered
            },
            {
                id: 5,
                title: patient && displayAssociation(patient, vaccination?.associatedWith)
            },
            { id: 6, title: vaccination?.event || null }
        ]
    });

    useEffect(() => {
        if (patient) {
            const sorted = sort(vaccinations, criteria);

            setBodies(sorted.map(asTableBody));
        }
    }, [vaccinations, criteria]);

    const handleSort = (name: string, direction: string): void => {
        setCriteria({ name: name as Headers, type: direction as Direction });
    };

    return (
        <TableComponent
            tableHeader={'Vaccinations'}
            buttons={allowAdd && buttons}
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
