import { FormProvider, useForm } from 'react-hook-form';
import styles from './add-patient-extended-form.module.scss';
import { PhoneAndEmailMultiEntry } from './inputs/phone/PhoneAndEmailMultiEntry';
import { PhoneEmailFields } from 'apps/patient/profile/phoneEmail/PhoneEmailEntry';
import { useState } from 'react';
import { AddPatientExtendedNav } from './nav/AddPatientExtendedNav';
import { RaceMultiEntry } from './inputs/race/RaceMultiEntry';
import { RaceEntry } from 'apps/patient/profile/race/RaceEntry';

type ExtendedPatientCreationForm = {
    phone: PhoneEmailFields[];
    race: RaceEntry[];
};

type DirtyState = {
    phone: boolean;
    race: boolean;
};
export const AddPatientExtendedForm = () => {
    const form = useForm<ExtendedPatientCreationForm>({ defaultValues: { phone: [] } });
    const [dirtyState, setDirtyState] = useState<DirtyState>({ phone: false, race: false });

    return (
        <>
            <div className={styles.addPatientForm}>
                <FormProvider {...form}>
                    <div className={styles.formContent}>
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
