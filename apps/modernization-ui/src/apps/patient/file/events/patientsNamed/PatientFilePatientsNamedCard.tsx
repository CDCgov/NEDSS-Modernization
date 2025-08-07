import { displayNoData } from 'design-system/data';
import { ContactsCard, ContactsCardProps } from 'libs/events/contacts/ContactsCard';
import { PatientFileContacts } from 'libs/events/contacts/contactsNamed';
import { DisplayableName, displayName } from 'name';

type PatientFilePatientsNamedCardProps = Omit<ContactsCardProps, 'title' | 'titleResolver'>;

const titleResolver = (patient?: DisplayableName, contact?: PatientFileContacts): string => {
    const name = patient ? displayName('short')(patient) : displayNoData();

    return `${patient && name} was named as a contact in the following ${contact?.condition}`;
};

const PatientFilePatientsNamedCard = ({ ...remaining }: PatientFilePatientsNamedCardProps) => {
    return <ContactsCard {...remaining} title={'Patients named by patient'} titleResolver={titleResolver} />;
};

export { PatientFilePatientsNamedCard };
