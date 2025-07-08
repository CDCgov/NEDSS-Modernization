import { useComponentSizing } from 'design-system/sizing';
import { usePatientFileData } from '../usePatientFileData';
import { PatientFileAdministrativeInformationCard } from './administrative/PatientFileAdministrativeInformationCard';
import { PatientFileNameCard } from './name/PatientFileNameCard';
import { PatientFileAddressCard } from './address/PatientFileAddressCard';
import { PatientFileIdentificationsCard } from './identifications/PatientFileIdentificationsCard';
import { PatientFilePhoneEmailCard } from './phoneEmail/PatientFilePhoneEmailCard';
import { PatientFileEthnicityCard } from './ethnicity/PatientFileEthnicityCard';
import { PatientFileSexBirthCard } from './sex-birth';
import { PatientFileGeneralInformationCard } from './general';
import { PatientFileMortalityCard } from './mortality';

import styles from './patient-file-demographics.module.scss';

const PatientFileDemographics = () => {
    const { id, demographics } = usePatientFileData();
    const sizing = useComponentSizing();

    return (
        <div className={styles.container}>
            <PatientFileAdministrativeInformationCard patient={id} sizing={sizing} />
            <PatientFileNameCard patient={id} sizing={sizing} />
            <PatientFilePhoneEmailCard
                id="patient-file-phone-email"
                sizing={sizing}
                provider={demographics.get().phoneEmail}
            />
            <PatientFileAddressCard patient={id} sizing={sizing} />
            <PatientFileIdentificationsCard patient={id} sizing={sizing} />
            <span data-half>
                <PatientFileEthnicityCard patient={id} sizing={sizing} />
                <PatientFileSexBirthCard
                    id="patient-file-sex-birth"
                    provider={demographics.get().sexBirth}
                    sizing={sizing}
                />
            </span>
            <span data-half>
                <PatientFileMortalityCard
                    id="patient-file-mortality"
                    provider={demographics.get().mortality}
                    sizing={sizing}
                />
                <PatientFileGeneralInformationCard
                    id="patient-file-general"
                    provider={demographics.get().general}
                    sizing={sizing}
                />
            </span>
        </div>
    );
};

export { PatientFileDemographics };
