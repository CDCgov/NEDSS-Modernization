import { useComponentSizing } from 'design-system/sizing';

import { PatientFileView } from '../PatientFileView';
import { usePatientFileData } from '../usePatientFileData';

import { PatientFileAddressCard } from './address';
import { PatientFileAdministrativeInformationCard } from './administrative';
import { PatientFileEthnicityCard } from './ethnicity';
import { PatientFileGeneralInformationCard } from './general';
import { PatientFileIdentificationCard } from './identification';
import { PatientFileMortalityCard } from './mortality';
import { PatientFileNameCard } from './name/PatientFileNameCard';
import styles from './patient-file-demographics.module.scss';
import { PatientFilePhoneEmailCard } from './phoneEmail';
import { PatientFileRaceCard } from './race';
import { PatientFileSexBirthCard } from './sex-birth';

const PatientFileDemographics = () => {
    const { demographics, patient } = usePatientFileData();
    const sizing = useComponentSizing();

    return (
        <PatientFileView patient={patient} sizing={sizing}>
            <div className={styles.container}>
                <PatientFileAdministrativeInformationCard
                    id="patient-file-administrative"
                    sizing={sizing}
                    provider={demographics.get().administrative}
                />
                <PatientFileNameCard
                    id="patient-file-names"
                    sizing={sizing}
                    provider={demographics.get().names}
                    editable={false}
                />
                <PatientFileAddressCard
                    id="patient-file-address"
                    sizing={sizing}
                    provider={demographics.get().addresses}
                />
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
                <span data-half={true}>
                    <PatientFileEthnicityCard
                        id="patient-file-ethnicity"
                        sizing={sizing}
                        provider={demographics.get().ethnicity}
                    />
                    <PatientFileSexBirthCard
                        id="patient-file-sex-birth"
                        provider={demographics.get().sexBirth}
                        sizing={sizing}
                    />
                </span>
                <span data-half={true}>
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
        </PatientFileView>
    );
};

export { PatientFileDemographics };
