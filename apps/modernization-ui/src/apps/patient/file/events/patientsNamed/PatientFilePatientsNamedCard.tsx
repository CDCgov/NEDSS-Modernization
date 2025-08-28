import { displayNoData } from 'design-system/data';
import { mapOr } from 'utils/mapping';
import { DisplayableName, displayName } from 'name';
import { Patient } from '../../patient';
import { ContactsCard, ContactsCardProps } from '../contacts';

type PatientFilePatientsNamedCardProps = { patient: Patient } & Omit<ContactsCardProps, 'title' | 'titleResolver'>;

const maybeDisplayPatient = mapOr(displayName('short'), displayNoData);

const titleResolver =
    (patient?: DisplayableName) =>
    (condition: string): string => {
        return `${maybeDisplayPatient(patient)} was named as a contact in the following ${condition} investigation(s)`;
    };

const PatientFilePatientsNamedCard = ({ patient, ...remaining }: PatientFilePatientsNamedCardProps) => {
    return (
        <ContactsCard {...remaining} title={'Patient named by contacts'} titleResolver={titleResolver(patient.name)} />
    );
};

export { PatientFilePatientsNamedCard };
