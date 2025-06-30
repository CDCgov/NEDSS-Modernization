import { Sizing } from 'design-system/field';
import { usePatientFilePhoneEmail } from './usePatientFilePhoneEmail';

type PatientFilePhoneEmailProps = {
    patient: number;
    sizing?: Sizing;
};

const PatientFilePhoneEmailCard = ({ patient, sizing }: PatientFilePhoneEmailProps) => {
    const { data, isLoading } = usePatientFilePhoneEmail(patient);

    return <PhoneEmailCard />;
};

export { PatientFilePhoneEmailCard };
