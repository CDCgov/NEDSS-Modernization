import { NoData } from 'components/NoData';
import { ContactsCard, ContactsCardProps } from 'libs/events/contacts/ContactsCard';
import { PatientFileContacts } from 'libs/events/contacts/contactsNamed';
import { DisplayableName, displayName } from 'name';
import { mapOr } from 'utils/mapping';

type PatientFilePatientsNamedCardProps = Omit<ContactsCardProps, 'title' | 'titleResolver'>;

const titleResolver = (patient?: DisplayableName, contact?: PatientFileContacts): string => {
    const name = mapOr(displayName('short'), <NoData />);

    return `${patient && name} was named as a contact in the following ${contact?.condition}`;
};

const PatientFilePatientsNamedCard = ({ ...remaining }: PatientFilePatientsNamedCardProps) => {
    return <ContactsCard {...remaining} title={'Patients named by patient'} titleResolver={titleResolver} />;
};

export { PatientFilePatientsNamedCard };
