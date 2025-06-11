import { AdministrativeInformationCard } from 'libs/patient/demographics/AdministrativeInformationCard';
import { useAdministrativeInformation } from './useAdministrativeInformation';

type PatientFileAdministrativeInformationCardType = {
    patient: number;
};

export const PatientFileAdministrativeInformationCard = ({ patient }: PatientFileAdministrativeInformationCardType) => {
    const data = useAdministrativeInformation(patient);

    return <AdministrativeInformationCard collapsible data={data.data} />;
};
