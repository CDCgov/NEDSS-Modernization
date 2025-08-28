import { displayNoData } from 'design-system/data';
import { mapOr } from 'utils/mapping';
import { DisplayableName, displayName } from 'name';
import { Patient } from '../../patient';
import { ContactsCard, ContactsCardProps } from '../contacts';

type PatientFileContactsNamedProps = { patient: Patient } & Omit<ContactsCardProps, 'title' | 'titleResolver'>;

const maybeDisplayPatient = mapOr(displayName('short'), displayNoData);

const titleResolver =
    (patient?: DisplayableName) =>
    (condition: string): string => {
        return `The following contacts were named in ${maybeDisplayPatient(patient)}'s investigation of ${condition}`;
    };

const PatientFileContactsNamedCard = ({ patient, ...remaining }: PatientFileContactsNamedProps) => {
    return (
        <ContactsCard {...remaining} title={'Contacts named by patient'} titleResolver={titleResolver(patient.name)} />
    );
};

export { PatientFileContactsNamedCard };
