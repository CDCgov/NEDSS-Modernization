import { Button } from 'components/button';
import { AddPatientLayout, DataEntryLayout } from 'apps/patient/add/layout';
import styles from './add-patient-basic.module.scss';
import { sections } from './sections';
import { AddPatientBasicForm } from './AddPatientBasicForm';
import { FormProvider, useForm, useWatch } from 'react-hook-form';
import { BasicNewPatientEntry, initial } from './entry';
import { useEffect } from 'react';

export const AddPatientBasic = () => {
    const form = useForm<BasicNewPatientEntry>({
        defaultValues: initial(),
        mode: 'onBlur'
    });

    const values = useWatch({ control: form.control });

    useEffect(() => {
        console.log('values', values);
    }, [values]);

    return (
        <DataEntryLayout>
            <FormProvider {...form}>
                <AddPatientLayout
                    headerTitle="New patient"
                    sections={sections}
                    headerActions={() => (
                        <div className={styles.buttonGroup}>
                            <Button outline>Cancel</Button>
                            <Button type="submit">Save</Button>
                        </div>
                    )}>
                    <AddPatientBasicForm />
                </AddPatientLayout>
            </FormProvider>
        </DataEntryLayout>
    );
};
