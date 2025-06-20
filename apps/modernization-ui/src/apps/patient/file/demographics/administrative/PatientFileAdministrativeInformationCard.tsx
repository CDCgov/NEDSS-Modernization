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
    const data = useAdministrativeInformation(patient);

    return <AdministrativeInformationCard collapsible data={data.data} sizing={sizing} />;
};
