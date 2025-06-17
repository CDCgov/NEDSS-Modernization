import { Sizing } from 'design-system/field';
import { EthnicityCard } from 'libs/patient/demographics/ethnicity/EthnicityCard';
import { useEthnicity } from './useEthnicity';

type PatientFileEthnicityCardType = {
    patient?: number;
    sizing?: Sizing;
};

const PatientFileEthnicityCard = ({ patient, sizing }: PatientFileEthnicityCardType) => {
    const { data } = useEthnicity(patient);

    return <EthnicityCard data={data} sizing={sizing} id={'patient-file-ethnicity'} title={'Ethnicity'} />;
};

export { PatientFileEthnicityCard };
