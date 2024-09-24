import { GeneralInformationEntryFields } from 'apps/patient/data/general/GeneralInformationEntryFields';
import { today } from 'date';
import { useState } from 'react';
import { FormProvider, useForm } from 'react-hook-form';
import { Card } from './card/Card';
import { ExtendedNewPatientEntry } from './entry';
import { AddressMultiEntry } from './inputs/address/AddressMultiEntry';
import { Administrative } from './inputs/administrative/Administrative';
import { EthnicityEntryCard } from './inputs/ethnicity/EthnicityEntryCard';
import { IdentificationMultiEntry } from './inputs/identification/IdentificationMultiEntry';
import { MortalityCard } from './inputs/mortality/MortalityCard';
import { NameMultiEntry } from './inputs/name/NameMultiEntry';
import { PhoneAndEmailMultiEntry } from './inputs/phone/PhoneAndEmailMultiEntry';
import { RaceMultiEntry } from './inputs/race/RaceMultiEntry';
import { SexAndBirthCard } from './inputs/sexAndBirth/SexAndBirthCard';
import { AddPatientExtendedNav } from './nav/AddPatientExtendedNav';
import styles from './add-patient-extended-form.module.scss';

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
                        <Administrative />
                        <NameMultiEntry
                            isDirty={(isDirty) => setDirtyState({ ...dirtyState, name: isDirty })}
                            onChange={(nameData) => {
                                form.setValue('names', nameData);
                            }}
                        />
                        <AddressMultiEntry
                            isDirty={(isDirty) => setDirtyState({ ...dirtyState, address: isDirty })}
                            onChange={(addressData) => {
                                form.setValue('addresses', addressData);
                            }}
                        />
                        <PhoneAndEmailMultiEntry
                            isDirty={(isDirty) => setDirtyState({ ...dirtyState, phone: isDirty })}
                            onChange={(phoneEmailData) => {
                                form.setValue('phoneEmails', phoneEmailData);
                            }}
                        />
                        <IdentificationMultiEntry
                            isDirty={(isDirty) => setDirtyState({ ...dirtyState, identification: isDirty })}
                            onChange={(identificationData) => {
                                form.setValue('identifications', identificationData);
                            }}
                        />
                        <RaceMultiEntry
                            isDirty={(isDirty) => setDirtyState({ ...dirtyState, race: isDirty })}
                            onChange={(raceData) => {
                                form.setValue('races', raceData);
                            }}
                        />
                        <EthnicityEntryCard />
                        <SexAndBirthCard />
                        <MortalityCard />
                        <Card id="generalInformation" title="General patient information">
                            <GeneralInformationEntryFields />
                        </Card>
                    </div>
                </FormProvider>
            </div>
            <AddPatientExtendedNav />
        </>
    );
};
