import { Sizing } from 'design-system/field';
import { usePatientFileNames } from './usePatientFileNames';
import { NameDemographicCard } from 'libs/patient/demographics/name';

type PatientFileNameCardProps = {
    patient: number;
    sizing?: Sizing;
};

const PatientFileNameCard = ({ patient, sizing }: PatientFileNameCardProps) => {
    const { data, isLoading } = usePatientFileNames(patient);

    return (
        <NameDemographicCard
            key={`${isLoading}`}
            id={'name-demographics'}
            sizing={sizing}
            viewable
            editable={false}
            data={data}
        />
    );
};

export { PatientFileNameCard };
