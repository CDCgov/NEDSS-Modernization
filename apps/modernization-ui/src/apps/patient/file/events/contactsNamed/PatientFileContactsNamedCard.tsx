import { ContactsCard, ContactsCardProps } from 'libs/events/contacts/ContactsCard';

type PatientFileContactsNamedProps = Omit<ContactsCardProps, 'title'>;

const PatientFileContactsNamedCard = ({ ...remaining }: PatientFileContactsNamedProps) => {
    return <ContactsCard {...remaining} title={'Contacts named by patient'} />;
};

export { PatientFileContactsNamedCard };
