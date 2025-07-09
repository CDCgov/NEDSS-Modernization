import { useComponentSizing } from 'design-system/sizing';
import { usePatientFileData } from '../usePatientFileData';
import { PatientFileAdministrativeInformationCard } from './administrative';
import { PatientFileNameCard } from './name/PatientFileNameCard';
import { PatientFileAddressCard } from './address';
import { PatientFileIdentificationCard } from './identification';
import { PatientFilePhoneEmailCard } from './phoneEmail';
import { PatientFileEthnicityCard } from './ethnicity';
import { PatientFileRaceCard } from './race';
import { PatientFileSexBirthCard } from './sex-birth';
import { PatientFileGeneralInformationCard } from './general';
import { PatientFileMortalityCard } from './mortality';

import styles from './patient-file-demographics.module.scss';

const PatientFileDemographics = () => {
    const { demographics } = usePatientFileData();
    const sizing = useComponentSizing();

    return (
        <div className={styles.container}>
            <PatientFileAdministrativeInformationCard
                id="patient-file-administrative"
                sizing={sizing}
                provider={demographics.get().administrative}
            />
            <PatientFileNameCard id="patient-file-names" sizing={sizing} provider={demographics.get().names} />
            <PatientFileAddressCard id="patient-file-address" sizing={sizing} provider={demographics.get().addresses} />
            <PatientFilePhoneEmailCard
                id="patient-file-phone-email"
                sizing={sizing}
                provider={demographics.get().phoneEmail}
            />
            <PatientFileIdentificationCard
                id="patient-file-identification"
                sizing={sizing}
                provider={demographics.get().identifications}
            />
            <PatientFileRaceCard id="patient-file-race" provider={demographics.get().race} sizing={sizing} />
            <span data-half>
                <PatientFileEthnicityCard
                    id="patient-file-administrative"
                    sizing={sizing}
                    provider={demographics.get().ethnicity}
                />
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
