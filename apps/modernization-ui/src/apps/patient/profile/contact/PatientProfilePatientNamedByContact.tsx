import { PaginationProvider } from 'pagination';
import { Headers } from './PatientContacts';
import { PatientContactsContainer } from './PatientContactContainer';
import { usePatientProfilePatientNamedByContactAPI } from './usePatientProfilePatientNamedByContactAPI';

const title = 'Contact records (patient named by contacts)';
const headings = [
    { name: Headers.DateCreated, sortable: true },
    { name: Headers.NamedBy, sortable: true },
    { name: Headers.DateNamed, sortable: true },
    { name: Headers.Description, sortable: true },
    { name: Headers.AssociatedWith, sortable: true },
    { name: Headers.Event, sortable: true }
];

type Props = {
    patient?: string;
    pageSize: number;
};

export const PatientProfilePatientNamedByContact = ({ patient, pageSize }: Props) => {
    return (
        <div className="margin-top-6 margin-bottom-2 flex-row common-card">
            <PaginationProvider pageSize={pageSize}>
                <PatientContactsContainer
                    source={usePatientProfilePatientNamedByContactAPI}
                    patient={patient}
                    title={title}
                    headings={headings}
                />
            </PaginationProvider>
        </div>
    );
};
