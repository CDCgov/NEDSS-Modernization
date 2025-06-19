import { useComponentSizing } from 'design-system/sizing';
import { usePatient } from '../usePatient';
import { PatientFileAdministrativeInformationCard } from './administrative/PatientFileAdministrativeInformationCard';
import { PatientFileEthnicityCard } from './ethnicity/PatientFileEthnicityCard';
import { PatientFileNameCard } from './name/PatientFileNameCard';
import styles from './patient-file-demographics.module.scss';

const PatientFileDemographics = () => {
    const { id } = usePatient();
    const sizing = useComponentSizing();

    return (
        <div className={styles.container}>
            <div className={styles.administrative}>
                <PatientFileAdministrativeInformationCard patient={id} sizing={sizing} />
            </div>
            <div className={styles.name}>
                <PatientFileNameCard patient={id} sizing={sizing} />
            </div>
            <div className={styles.ethnicity}>
                <PatientFileEthnicityCard patient={id} sizing={sizing} />
            </div>
        </div>
    );
};

export { PatientFileDemographics };
