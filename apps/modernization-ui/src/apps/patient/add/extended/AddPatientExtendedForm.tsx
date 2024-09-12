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

type ExtendedPatientCreationForm = {
    address: AddressFields[];
    phone: PhoneEmailFields[];
    race: RaceEntry[];
};

type DirtyState = {
    address: boolean;
    phone: boolean;
    race: boolean;
};
export const AddPatientExtendedForm = () => {
    const form = useForm<ExtendedPatientCreationForm>({ defaultValues: { phone: [] } });
    const [dirtyState, setDirtyState] = useState<DirtyState>({ address: false, phone: false, race: false });

    return (
        <>
            <div className={styles.addPatientForm}>
                <FormProvider {...form}>
                    <div className={styles.formContent}>
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
