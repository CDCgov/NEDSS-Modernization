import { NoData } from 'components/NoData';
import { ContactsCard, ContactsCardProps } from 'libs/events/contacts/ContactsCard';
import { PatientFileContacts } from 'libs/events/contacts/contactsNamed';
import { DisplayableName, displayName } from 'name';
import { mapOr } from 'utils/mapping';

type PatientFileContactsNamedProps = Omit<ContactsCardProps, 'title' | 'titleResolver'>;

const titleResolver = (patient?: DisplayableName, contact?: PatientFileContacts): string => {
    const name = mapOr(displayName('short'), <NoData />);

    return `The following contacts were named by ${patient && name}'s investigation of ${contact?.condition}`;
};

const PatientFileContactsNamedCard = ({ ...remaining }: PatientFileContactsNamedProps) => {
    return <ContactsCard {...remaining} title={'Contacts named by patient'} titleResolver={titleResolver} />;
};

export { PatientFileContactsNamedCard };
