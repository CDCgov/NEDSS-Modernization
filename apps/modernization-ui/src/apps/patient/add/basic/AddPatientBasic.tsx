import { Button } from 'components/button';
import { AddPatientLayout, DataEntryLayout } from 'apps/patient/add/layout';
import styles from './add-patient-basic.module.scss';
import { sections } from './sections';
import { AddPatientBasicForm } from './AddPatientBasicForm';
import { FormProvider, useForm } from 'react-hook-form';
import { BasicNewPatientEntry } from './entry';
import { useAddBasicPatient } from './useAddBasicPatient';
import { Shown } from 'conditional-render';
import { PatientCreatedPanel } from '../PatientCreatedPanel';
import { useMemo } from 'react';
import { useAddPatientBasicDefaults } from './useAddPatientBasicDefaults';
import { useLocation } from 'react-router-dom';
import { useSearchFromAddPatient } from 'apps/search/patient/add/useSearchFromAddPatient';
import { useConfiguration } from 'configuration';
import { useBasicExtendedTransition } from '../useBasicExtendedTransition';

export const AddPatientBasic = () => {
    const { initialize } = useAddPatientBasicDefaults();

    const interaction = useAddBasicPatient();
    const { features } = useConfiguration();
    const form = useForm<BasicNewPatientEntry>({
        defaultValues: initialize(),
        mode: 'onBlur'
    });

    const { toExtendedNew } = useBasicExtendedTransition();

    const created = useMemo(
        () => (interaction.status === 'created' ? interaction.created : undefined),
        [interaction.status]
    );

    const handleSave = form.handleSubmit(interaction.create);

    const { toSearch } = useSearchFromAddPatient();
    const location = useLocation();
    const handleCancel = () => {
        toSearch(location.state.criteria);
    };
    const handleExtended = form.handleSubmit(toExtendedNew);

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
                            {features.patient?.add?.extended?.enabled && (
                                <Button
                                    type="button"
                                    onClick={handleExtended}
                                    outline
                                    className="add-patient-button"
                                    disabled={!form.formState.isValid}>
                                    Add extended data
                                </Button>
                            )}
                            <Button onClick={handleCancel} outline>
                                Cancel
                            </Button>
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
