import { useComponentSizing } from 'design-system/sizing';
import { usePatient } from '../usePatient';
import { PatientFileAdministrativeInformationCard } from './administrative/PatientFileAdministrativeInformationCard';
import { PatientFileEthnicityCard } from './ethnicity/PatientFileEthnicityCard';
import { PatientFileNameCard } from './name/PatientFileNameCard';
import styles from './patient-file-demographics.module.scss';
import { PatientFileAddressCard } from './address/PatientFileAddressCard';
import { PatientFileIdentificationsCard } from './identifications/PatientFileIdentificationsCard';

const PatientFileDemographics = () => {
    const { id } = usePatient();
    const sizing = useComponentSizing();

    return (
        <div className={styles.container}>
            <PatientFileAdministrativeInformationCard patient={id} sizing={sizing} />
            <PatientFileNameCard patient={id} sizing={sizing} />
            <PatientFileAddressCard patient={id} sizing={sizing} />
            <PatientFileIdentificationsCard patient={id} sizing={sizing} />
            <PatientFileEthnicityCard patient={id} sizing={sizing} data-half />
        </div>
    );
};

export { PatientFileDemographics };
