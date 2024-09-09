import { FormProvider, useForm } from 'react-hook-form';
import styles from './add-patient-extended-form.module.scss';
import { PhoneAndEmailMultiEntry } from './inputs/PhoneAndEmailMultiEntry';
import { PhoneEmailFields } from 'apps/patient/profile/phoneEmail/PhoneEmailEntry';
import { useState } from 'react';

type ExtendedPatientCreationForm = {
    phone: PhoneEmailFields[];
};

type DirtyState = {
    phone: boolean;
};
export const AddPatientExtendedForm = () => {
    const form = useForm<ExtendedPatientCreationForm>({ defaultValues: { phone: [] } });
    const [dirtyState, setDirtyState] = useState<DirtyState>({ phone: false });

    return (
        <div className={styles.addPatientForm}>
            <FormProvider {...form}>
                <div className={styles.formContent}>
                    <PhoneAndEmailMultiEntry
                        isDirty={(isDirty) => setDirtyState({ ...dirtyState, phone: isDirty })}
                        onChange={(phoneEmailData) => {
                            form.setValue('phone', phoneEmailData);
                        }}
                    />
                </div>
            </FormProvider>
        </div>
    );
};
