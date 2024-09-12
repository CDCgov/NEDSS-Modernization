import { FormProvider, useForm } from 'react-hook-form';
import styles from './add-patient-extended-form.module.scss';
import { PhoneAndEmailMultiEntry } from './inputs/PhoneAndEmailMultiEntry';
import { PhoneEmailFields } from 'apps/patient/profile/phoneEmail/PhoneEmailEntry';
import { useState } from 'react';
import { AddPatientExtendedNav } from './nav/AddPatientExtendedNav';
import { NameEntry } from 'apps/patient/profile/names/NameEntry';
import { NameMultiEntry } from './inputs/Name/NameMultiEntry';

type ExtendedPatientCreationForm = {
    phone: PhoneEmailFields[];
    name: NameEntry[];
};

type DirtyState = {
    phone: boolean;
    name: boolean;
};
export const AddPatientExtendedForm = () => {
    const form = useForm<ExtendedPatientCreationForm>({ defaultValues: { phone: [], name: [] } });
    const [dirtyState, setDirtyState] = useState<DirtyState>({ phone: false, name: false });

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
                    </div>
                </FormProvider>
            </div>
            <AddPatientExtendedNav />
        </>
    );
};
