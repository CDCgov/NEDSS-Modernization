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
import { Administrative } from './inputs/administrative/Administrative';

type ExtendedPatientCreationForm = {
    address: AddressFields[];
    phone: PhoneEmailFields[];
    race: RaceEntry[];
    name: NameEntry[];
    asOf: string | null | undefined;
    comments?: string | null;
};

type DirtyState = {
    address: boolean;
    phone: boolean;
    name: boolean;
    race: boolean;
};
export const AddPatientExtendedForm = () => {
    const form = useForm<ExtendedPatientCreationForm>({ defaultValues: { phone: [] } });
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
                        <Administrative
                            onChange={(data) => {
                                form.setValue('asOf', data.asOf);
                                form.setValue('comments', data.comment);
                            }}
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
