import { displayNoData } from 'design-system/data';
import { ContactsCard, ContactsCardProps } from 'libs/events/contacts/ContactsCard';
import { PatientFileContacts } from 'libs/events/contacts/contactsNamed';
import { DisplayableName, displayName } from 'name';

type PatientFileContactsNamedProps = Omit<ContactsCardProps, 'title' | 'titleResolver'>;

const titleResolver = (patient?: DisplayableName, contact?: PatientFileContacts): string => {
    const name = patient ? displayName('short')(patient) : displayNoData();

    return `The following contacts were named by ${name}'s investigation of ${contact?.condition}`;
};

const PatientFileContactsNamedCard = ({ ...remaining }: PatientFileContactsNamedProps) => {
    return <ContactsCard {...remaining} title={'Contacts named by patient'} titleResolver={titleResolver} />;
};

export { PatientFileContactsNamedCard };
