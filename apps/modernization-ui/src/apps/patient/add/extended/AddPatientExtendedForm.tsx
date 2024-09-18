import { AddressFields } from 'apps/patient/profile/addresses/AddressEntry';
import { NameEntry } from 'apps/patient/profile/names/NameEntry';
import { AdministrativeEntry, RaceEntry, PhoneEmailEntry } from 'apps/patient/data/entry';
import { internalizeDate } from 'date';
import { useState } from 'react';
import { FormProvider, useForm } from 'react-hook-form';
import { AddressMultiEntry } from './inputs/address/AddressMultiEntry';
import { Administrative } from './inputs/administrative/Administrative';
import { PhoneAndEmailMultiEntry } from './inputs/phone/PhoneAndEmailMultiEntry';
import { RaceMultiEntry } from './inputs/race/RaceMultiEntry';
import { AddPatientExtendedNav } from './nav/AddPatientExtendedNav';
import styles from './add-patient-extended-form.module.scss';
import { NameMultiEntry } from './inputs/name/NameMultiEntry';

// Once all sections have been updated with proper types this will be removed
type ExtendedPatientCreationForm = {
    administrative: AdministrativeEntry;
    address: AddressFields[];
    phone: PhoneEmailEntry[];
    race: RaceEntry[];
    name: NameEntry[];
};

// used to track sub-form state to display error on parent form submisson
type DirtyState = {
    address: boolean;
    phone: boolean;
    name: boolean;
    race: boolean;
};
export const AddPatientExtendedForm = () => {
    const form = useForm<ExtendedPatientCreationForm>({
        defaultValues: {
            phone: [],
            administrative: {
                asOf: internalizeDate(new Date()),
                comment: ''
            }
        },
        mode: 'onBlur'
    });
    const [dirtyState, setDirtyState] = useState<DirtyState>({
        address: false,
        phone: false,
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
                                form.setValue('name', nameData);
                            }}
                        />
                        <AddressMultiEntry
                            isDirty={(isDirty) => setDirtyState({ ...dirtyState, address: isDirty })}
                            onChange={(addressData) => {
                                form.setValue('address', addressData);
                            }}
                        />
                        <PhoneAndEmailMultiEntry
                            isDirty={(isDirty) => setDirtyState({ ...dirtyState, phone: isDirty })}
                            onChange={(phoneEmailData) => {
                                form.setValue('phone', phoneEmailData);
                            }}
                        />
                        <RaceMultiEntry
                            isDirty={(isDirty) => setDirtyState({ ...dirtyState, race: isDirty })}
                            onChange={(raceData) => {
                                form.setValue('race', raceData);
                            }}
                        />
                    </div>
                </FormProvider>
            </div>
            <AddPatientExtendedNav />
        </>
    );
};
