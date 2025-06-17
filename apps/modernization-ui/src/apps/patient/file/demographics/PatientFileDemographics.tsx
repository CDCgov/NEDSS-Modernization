import { useComponentSizing } from 'design-system/sizing';
import { usePatient } from '../usePatient';
import { PatientFileAdministrativeInformationCard } from './administrative/PatientFileAdministrativeInformationCard';
import { PatientFileNameCard } from './name/PatientFileNameCard';

const PatientFileDemographics = () => {
    const { id } = usePatient();
    const sizing = useComponentSizing();

    return (
        <>
            <PatientFileAdministrativeInformationCard patient={id} sizing={sizing} />
            <PatientFileNameCard patient={id} sizing={sizing} />
        </>
    );
};

export { PatientFileDemographics };
