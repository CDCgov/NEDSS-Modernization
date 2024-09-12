import { FormProvider, useForm } from 'react-hook-form';
import styles from './add-patient-extended-form.module.scss';
import { PhoneAndEmailMultiEntry } from './inputs/phone/PhoneAndEmailMultiEntry';
import { PhoneEmailFields } from 'apps/patient/profile/phoneEmail/PhoneEmailEntry';
import { useState } from 'react';
import { AddPatientExtendedNav } from './nav/AddPatientExtendedNav';
import { RaceMultiEntry } from './inputs/race/RaceMultiEntry';
import { RaceEntry } from 'apps/patient/profile/race/RaceEntry';
import { NameEntry } from 'apps/patient/profile/names/NameEntry';
import { NameMultiEntry } from './inputs/Name/NameMultiEntry';

type ExtendedPatientCreationForm = {
    phone: PhoneEmailFields[];
    race: RaceEntry[];
    name: NameEntry[];
};

type DirtyState = {
    phone: boolean;
    name: boolean;
    race: boolean;
};
export const AddPatientExtendedForm = () => {
    const form = useForm<ExtendedPatientCreationForm>({ defaultValues: { phone: [], name: [] } });
    const [dirtyState, setDirtyState] = useState<DirtyState>({ phone: false, race: false, name: false });

    return (
        <>
            <div className={styles.addPatientForm}>
                <FormProvider {...form}>
                    <div className={styles.formContent}>
                        <NameMultiEntry
                            isDirty={(isDirty) => setDirtyState({ ...dirtyState, name: isDirty })}
                            onChange={(nameData) => {
                                form.setValue('name', nameData);
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
