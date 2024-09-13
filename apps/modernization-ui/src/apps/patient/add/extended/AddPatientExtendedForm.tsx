import { FormProvider, useForm } from 'react-hook-form';
import styles from './add-patient-extended-form.module.scss';
import { PhoneAndEmailMultiEntry } from './inputs/phone/PhoneAndEmailMultiEntry';
import { PhoneEmailFields } from 'apps/patient/profile/phoneEmail/PhoneEmailEntry';
import { useState } from 'react';
import { AddPatientExtendedNav } from './nav/AddPatientExtendedNav';
import { AddressMultiEntry } from './inputs/address/AddressMultiEntry';
import { AddressFields } from 'apps/patient/profile/addresses/AddressEntry';
import { RaceMultiEntry } from './inputs/race/RaceMultiEntry';
import { RaceEntry } from 'apps/patient/profile/race/RaceEntry';
import { NameEntry } from 'apps/patient/profile/names/NameEntry';
import { NameMultiEntry } from './inputs/Name/NameMultiEntry';
import { AdministrativeSingleEntry } from './inputs/administrative/AdministrativeSingleEntry';
import { AdministrativeEntry } from 'apps/patient/profile/administrative/AdministrativeEntry';

type ExtendedPatientCreationForm = {
    address: AddressFields[];
    phone: PhoneEmailFields[];
    race: RaceEntry[];
    name: NameEntry[];
    administrative: AdministrativeEntry;
};

type DirtyState = {
    address: boolean;
    phone: boolean;
    name: boolean;
    race: boolean;
    administrative: boolean;
};
export const AddPatientExtendedForm = () => {
    const form = useForm<ExtendedPatientCreationForm>({ defaultValues: { phone: [] } });
    const [dirtyState, setDirtyState] = useState<DirtyState>({
        address: false,

        phone: false,

        race: false,
        name: false,
        administrative: false
    });

    return (
        <>
            <div className={styles.addPatientForm}>
                <FormProvider {...form}>
                    <div className={styles.formContent}>
                        <AdministrativeSingleEntry
                            onChange={(data) => {
                                form.setValue('administrative', data);
                            }}
                            isDirty={(isDirty) => setDirtyState({ ...dirtyState, administrative: isDirty })}
                        />
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
