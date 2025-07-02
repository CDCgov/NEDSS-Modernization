import { Shown } from 'conditional-render';
import { Sizing } from 'design-system/field';
import { LoadingCard } from 'libs/loading';
import { IdentificationDemographicCard } from 'libs/patient/demographics/identifications/IdentificationDemographicCard';
import { usePatientFileIdentification } from './usePatientFileIdentification';

type PatientFileIdentificationCardProps = {
    patient: number;
    sizing?: Sizing;
};

const PatientFileIdentificationsCard = ({ patient, sizing }: PatientFileIdentificationCardProps) => {
    const { data, isLoading } = usePatientFileIdentification(patient);

    return (
        <Shown when={!isLoading} fallback={<LoadingCard id="loading-names" title="Name" sizing={sizing} />}>
            <IdentificationDemographicCard
                id={'identification-demographics'}
                sizing={sizing}
                viewable
                editable={false}
                data={data}
            />
        </Shown>
    );
};

export { PatientFileIdentificationsCard };
