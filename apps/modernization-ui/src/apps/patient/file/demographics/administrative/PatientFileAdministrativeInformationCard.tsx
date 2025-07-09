import { AdministrativeInformationCard } from 'libs/patient/demographics/administrative/AdministrativeInformationCard';
import { useAdministrativeInformation } from './useAdministrativeInformation';
import { Sizing } from 'design-system/field';

type PatientFileAdministrativeInformationCardType = {
    patient: number;
    sizing?: Sizing;
};

export const PatientFileAdministrativeInformationCard = ({
    patient,
    sizing
}: PatientFileAdministrativeInformationCardType) => {
    const { data, isLoading } = useAdministrativeInformation(patient);

    return (
        <AdministrativeInformationCard
            key={`${isLoading}`}
            collapsible
            data={data}
            sizing={sizing}
            id="patient-file-administrative"
            title={'Administrative'}
        />
    );
};
