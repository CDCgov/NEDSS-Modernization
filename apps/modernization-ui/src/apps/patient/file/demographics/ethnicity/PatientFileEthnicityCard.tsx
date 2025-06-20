import { useEthnicity } from './useEthnicity';
import { EthnicityCard, EthnicityCardProps } from 'libs/patient/demographics/ethnicity';

type PatientFileEthnicityCardType = {
    patient: number;
} & Omit<EthnicityCardProps, 'id' | 'title'>;

const PatientFileEthnicityCard = ({ patient, ...remaining }: PatientFileEthnicityCardType) => {
    const { data } = useEthnicity(patient);

    return <EthnicityCard id={'patient-file-ethnicity'} data={data} {...remaining} />;
};

export { PatientFileEthnicityCard };
