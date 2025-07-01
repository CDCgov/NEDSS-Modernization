import { Sizing } from 'design-system/field';
import { usePatientFilePhoneEmail } from './usePatientFilePhoneEmail';
import { PhoneEmailDemographicCard } from 'libs/patient/demographics/phoneEmail/PhoneEmailDemographicCard';
import { Shown } from 'conditional-render';
import { LoadingCard } from 'libs/loading';

type PatientFilePhoneEmailProps = {
    patient: number;
    sizing?: Sizing;
};

const PatientFilePhoneEmailCard = ({ patient, sizing }: PatientFilePhoneEmailProps) => {
    const { data, isLoading } = usePatientFilePhoneEmail(patient);

    return (
        <Shown
            when={!isLoading}
            fallback={<LoadingCard id="loading-phone-emails" title="Phone & email" sizing={sizing} />}>
            <PhoneEmailDemographicCard
                data={data}
                sizing={sizing}
                title="Phone & email"
                id="patient-file-phone-email-demographic"
            />
        </Shown>
    );
};

export { PatientFilePhoneEmailCard };
