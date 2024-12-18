import { Button } from 'components/button';
import { AddPatientLayout, DataEntryLayout } from 'apps/patient/add/layout';
import styles from './add-patient-basic.module.scss';
import { sections } from './sections';
import { AddPatientBasicForm } from './AddPatientBasicForm';
import { FormProvider, useForm } from 'react-hook-form';
import { BasicNewPatientEntry, initial } from './entry';
import { useAddBasicPatient } from './useAddBasicPatient';
import { Shown } from 'conditional-render';
import { PatientCreatedPanel } from '../PatientCreatedPanel';
import { useMemo } from 'react';

export const AddPatientBasic = () => {
    const interaction = useAddBasicPatient();
    const form = useForm<BasicNewPatientEntry>({
        defaultValues: initial(),
        mode: 'onBlur'
    });

    const created = useMemo(
        () => (interaction.status === 'created' ? interaction.created : undefined),
        [interaction.status]
    );

    const handleSave = form.handleSubmit(interaction.create);

    return (
        <DataEntryLayout>
            <Shown when={interaction.status === 'created'}>
                {created && <PatientCreatedPanel created={created} />}
            </Shown>
            <FormProvider {...form}>
                <AddPatientLayout
                    headerTitle="New patient"
                    sections={sections}
                    headerActions={() => (
                        <div className={styles.buttonGroup}>
                            <Button outline>Cancel</Button>
                            <Button type="submit" onClick={handleSave} disabled={!form.formState.isValid}>
                                Save
                            </Button>
                        </div>
                    )}>
                    <AddPatientBasicForm />
                </AddPatientLayout>
            </FormProvider>
        </DataEntryLayout>
    );
};
