import { useComponentSizing } from 'design-system/sizing';
import { usePatient } from '../usePatient';
import { PatientFileAdministrativeInformationCard } from './administrative/PatientFileAdministrativeInformationCard';
import { PatientFileEthnicityCard } from './ethnicity/PatientFileEthnicityCard';

export const PatientFileDemographics = () => {
    const { id } = usePatient();
    const sizing = useComponentSizing();

    return (
        <>
            <PatientFileAdministrativeInformationCard patient={id} sizing={sizing} />
            <PatientFileEthnicityCard patient={id} sizing={sizing} />
        </>
    );
};
