import { usePatient } from '../usePatient';
import { PatientFileAdministrativeInformationCard } from './administrative/PatientFileAdministrativeInformationCard';

export const PatientFileDemographics = () => {
    const { id } = usePatient();

    return (
        <>
            <PatientFileAdministrativeInformationCard patient={id} />
        </>
    );
};
