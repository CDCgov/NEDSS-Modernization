import { AdministrativeEntryFields } from 'apps/patient/data/administrative/AdministrativeEntryFields';
import { EthnicityEntryFields } from 'apps/patient/data/ethnicity/EthnicityEntryFields';
import { GeneralInformationEntryFields } from 'apps/patient/data/general/GeneralInformationEntryFields';
import { MortalityEntryFields } from 'apps/patient/data/mortality/MortalityEntryFields';
import { SexAndBirthEntryFields } from 'apps/patient/data/sexAndBirth/SexAndBirthEntryFields';
import { today } from 'date';
import { useState } from 'react';
import { FormProvider, useForm } from 'react-hook-form';
import styles from './add-patient-extended-form.module.scss';
import { Card } from './card/Card';
import { ExtendedNewPatientEntry } from './entry';
import { AddressRepeatingBlock } from './inputs/address/AddressRepeatingBlock';
import { IdentificationRepeatingBlock } from './inputs/identification/IdentificationRepeatingBlock';
import { NameRepeatingBlock } from './inputs/name/NameRepeatingBlock';
import { PhoneAndEmailRepeatingBlock } from './inputs/phone/PhoneAndEmailRepeatingBlock';
import { RaceRepeatingBlock } from './inputs/race/RaceRepeatingBlock';

// used to track sub-form state to display error on parent form submisson
type DirtyState = {
    address: boolean;
    phone: boolean;
    identification: boolean;
    name: boolean;
    race: boolean;
};
export const AddPatientExtendedForm = () => {
    const defaultDate = today();
    const form = useForm<ExtendedNewPatientEntry>({
        defaultValues: {
            phoneEmails: [],
            administrative: {
                asOf: defaultDate,
                comment: ''
            },
            birthAndSex: {
                asOf: defaultDate
            },
            ethnicity: {
                asOf: defaultDate
            },
            mortality: {
                asOf: defaultDate
            },
            general: {
                asOf: defaultDate
            }
        },
        mode: 'onBlur'
    });
    const [dirtyState, setDirtyState] = useState<DirtyState>({
        address: false,
        phone: false,
        identification: false,
        race: false,
        name: false
    });

    return (
        <>
            <div className={styles.addPatientForm}>
                <FormProvider {...form}>
                    <div className={styles.formContent}>
                        <Card
                            title="Administrative"
                            id="administrative"
                            info={<span className="required-before">All required fields for adding comments</span>}>
                            <AdministrativeEntryFields />
                        </Card>
                        <NameRepeatingBlock
                            isDirty={(isDirty) => setDirtyState({ ...dirtyState, name: isDirty })}
                            onChange={(nameData) => {
                                form.setValue('names', nameData);
                            }}
                        />
                        <AddressRepeatingBlock
                            isDirty={(isDirty) => setDirtyState({ ...dirtyState, address: isDirty })}
                            onChange={(addressData) => {
                                form.setValue('addresses', addressData);
                            }}
                        />
                        <PhoneAndEmailRepeatingBlock
                            isDirty={(isDirty) => setDirtyState({ ...dirtyState, phone: isDirty })}
                            onChange={(phoneEmailData) => {
                                form.setValue('phoneEmails', phoneEmailData);
                            }}
                        />
                        <IdentificationRepeatingBlock
                            isDirty={(isDirty) => setDirtyState({ ...dirtyState, identification: isDirty })}
                            onChange={(identificationData) => {
                                form.setValue('identifications', identificationData);
                            }}
                        />
                        <RaceRepeatingBlock
                            isDirty={(isDirty) => setDirtyState({ ...dirtyState, race: isDirty })}
                            onChange={(raceData) => {
                                form.setValue('races', raceData);
                            }}
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
                </FormProvider>
            </div>
        </>
    );
};
