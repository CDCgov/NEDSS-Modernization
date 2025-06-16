import { Sizing } from 'design-system/field';
import { Shown } from 'conditional-render';
import { usePatientFileNames } from './usePatientFileNames';
import { NameDemographicCard } from 'libs/patient/demographics/name';
import { LoadingCard } from 'libs/loading';

type PatientFileNameCardProps = {
    patient: number;
    sizing?: Sizing;
};

const PatientFileNameCard = ({ patient, sizing }: PatientFileNameCardProps) => {
    const { data, isLoading } = usePatientFileNames(patient);

    return (
        <Shown when={!isLoading} fallback={<LoadingCard id="loading-names" title="Name" sizing={sizing} />}>
            <NameDemographicCard id={'name-demographics'} sizing={sizing} viewable editable={false} data={data} />
        </Shown>
    );
};

export { PatientFileNameCard };
