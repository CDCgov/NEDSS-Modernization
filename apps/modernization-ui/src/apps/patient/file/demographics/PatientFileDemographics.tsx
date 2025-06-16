import { usePatient } from '../usePatient';
import { PatientFileAdministrativeInformationCard } from './administrative/PatientFileAdministrativeInformationCard';
import { PatientFileNameCard } from './name/PatientFileNameCard';
import { Sizing } from 'design-system/field';

type Props = {
    sizing?: Sizing;
};

const PatientFileDemographics = ({ sizing = 'small' }: Props) => {
    const { id } = usePatient();

    return (
        <>
            <PatientFileAdministrativeInformationCard patient={id} sizing={sizing} />
            <PatientFileNameCard patient={id} sizing={sizing} />
        </>
    );
};

export { PatientFileDemographics };
