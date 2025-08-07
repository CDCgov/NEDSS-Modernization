import { ContactsCard, ContactsCardProps } from 'libs/events/contacts/ContactsCard';

type PatientFilePatientsNamedCardProps = Omit<ContactsCardProps, 'title'>;

const PatientFilePatientsNamedCard = ({ ...remaining }: PatientFilePatientsNamedCardProps) => {
    return <ContactsCard {...remaining} title={'Patients named by patient'} patientNamed />;
};

export { PatientFilePatientsNamedCard };
