import { useEffect, useState } from 'react';
import { useFormContext } from 'react-hook-form';
import { Card } from './card/Card';
import { ExtendedNewPatientEntry } from './entry';
import { AdministrativeEntryFields } from 'apps/patient/data/administrative/AdministrativeEntryFields';
import { EthnicityEntryFields } from 'apps/patient/data/ethnicity/EthnicityEntryFields';
import { GeneralInformationEntryFields } from 'apps/patient/data/general/GeneralInformationEntryFields';
import { MortalityEntryFields } from 'apps/patient/data/mortality/MortalityEntryFields';
import { SexAndBirthEntryFields } from 'apps/patient/data/sexAndBirth/SexAndBirthEntryFields';
import { AddressRepeatingBlock } from './inputs/address/AddressRepeatingBlock';
import { IdentificationRepeatingBlock } from './inputs/identification/IdentificationRepeatingBlock';
import { NameRepeatingBlock } from './inputs/name/NameRepeatingBlock';
import { PhoneAndEmailRepeatingBlock } from './inputs/phone/PhoneAndEmailRepeatingBlock';
import { RaceRepeatingBlock } from './inputs/race/RaceRepeatingBlock';

import styles from './add-patient-extended-form.module.scss';
import { useLocation } from 'react-router-dom';

// used to track sub-form state to display error on parent form submisson
type DirtyState = {
    address: boolean;
    phone: boolean;
    identification: boolean;
    name: boolean;
    race: boolean;
};
export const AddPatientExtendedForm = () => {
    const { setValue } = useFormContext<ExtendedNewPatientEntry>();
    
    const location = useLocation();
    const [dirtyState, setDirtyState] = useState<DirtyState>({
        address: false,
        phone: false,
        identification: false,
        race: false,
        name: false
    });

    useEffect(() => {
        console.log(location.state.defaults);
    });

    return (
        <>
            <div className={styles.addPatientForm}>
                <div className={styles.formContent}>
                    <Card
                        title="Administrative"
                        id="administrative"
                        info={<span className="required-before">All required fields for adding comments</span>}>
                        <AdministrativeEntryFields />
                    </Card>
                    <NameRepeatingBlock
                        isDirty={(isDirty) => setDirtyState({ ...dirtyState, name: isDirty })}
                        onChange={(nameData) => setValue('names', nameData)}
                    />
                    <AddressRepeatingBlock
                        isDirty={(isDirty) => setDirtyState({ ...dirtyState, address: isDirty })}
                        onChange={(addressData) => setValue('addresses', addressData)}
                    />
                    <PhoneAndEmailRepeatingBlock
                        isDirty={(isDirty) => setDirtyState({ ...dirtyState, phone: isDirty })}
                        onChange={(phoneEmailData) => setValue('phoneEmails', phoneEmailData)}
                    />
                    <IdentificationRepeatingBlock
                        isDirty={(isDirty) => setDirtyState({ ...dirtyState, identification: isDirty })}
                        onChange={(identificationData) => setValue('identifications', identificationData)}
                    />
                    <RaceRepeatingBlock
                        isDirty={(isDirty) => setDirtyState({ ...dirtyState, race: isDirty })}
                        onChange={(raceData) => setValue('races', raceData)}
                    />
                    <Card
                        id="ethnicity"
                        title="Ethnicity"
                        info={<span className="required-before">All required fields for adding ethnicity</span>}>
                        <EthnicityEntryFields />
                    </Card>
                    <Card
                        id="sexAndBirth"
                        title="Sex & birth"
                        info={<span className="required-before">All required fields for adding sex & birth</span>}>
                        <SexAndBirthEntryFields />
                    </Card>
                    <Card
                        id="mortality"
                        title="Mortality"
                        info={<span className="required-before">All required fields for adding mortality</span>}>
                        <MortalityEntryFields />
                    </Card>
                    <Card
                        id="generalInformation"
                        title="General patient information"
                        info={
                            <span className="required-before">
                                All required fields for adding general patient information
                            </span>
                        }>
                        <GeneralInformationEntryFields />
                    </Card>
                </div>
            </div>
        </>
    );
};
