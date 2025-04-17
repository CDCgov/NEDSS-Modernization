import { PaginationProvider } from 'pagination';
import { Headers } from './PatientContacts';
import { PatientContactsContainer } from './PatientContactContainer';
import { usePatientProfileContactNamedByPatientAPI } from './usePatientProfileContactNamedByPatientAPI';

const title = 'Contact records (contacts named by patient)';
const headings = [
    { name: Headers.DateCreated, sortable: true },
    { name: Headers.ContactsNamed, sortable: true },
    { name: Headers.DateNamed, sortable: true },
    { name: Headers.Description, sortable: true },
    { name: Headers.AssociatedWith, sortable: true },
    { name: Headers.Event, sortable: true }
];

type Props = {
    patient?: string;
    pageSize: number;
};

export const PatientProfileContactsNamedByPatient = ({ patient, pageSize }: Props) => {
    return (
        <div className="margin-top-6 margin-bottom-2 flex-row common-card">
            <PaginationProvider pageSize={pageSize}>
                <PatientContactsContainer
                    source={usePatientProfileContactNamedByPatientAPI}
                    patient={patient}
                    title={title}
                    headings={headings}
                />
            </PaginationProvider>
        </div>
    );
};
