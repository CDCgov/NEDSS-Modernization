import { useLocation } from 'react-router-dom';
import { FormProvider, useForm } from 'react-hook-form';
import { Button } from 'components/button';
import { AddPatientLayout, DataEntryLayout } from 'apps/patient/add/layout';
import { sections } from './sections';
import { AddPatientBasicForm } from './AddPatientBasicForm';
import { BasicNewPatientEntry } from './entry';
import { useAddBasicPatient } from './useAddBasicPatient';
import { Shown } from 'conditional-render';
import { PatientCreatedPanel } from '../PatientCreatedPanel';
import { useAddPatientBasicDefaults } from './useAddPatientBasicDefaults';
import { useSearchFromAddPatient } from 'apps/search/patient/add/useSearchFromAddPatient';
import { useConfiguration } from 'configuration';
import { useBasicExtendedTransition } from 'apps/patient/add/useBasicExtendedTransition';

import styles from './add-patient-basic.module.scss';

export const AddPatientBasic = () => {
    const { initialize } = useAddPatientBasicDefaults();

    const interaction = useAddBasicPatient();
    const { features } = useConfiguration();
    const form = useForm<BasicNewPatientEntry>({
        defaultValues: initialize(),
        mode: 'onBlur'
    });

    const { toExtendedNew } = useBasicExtendedTransition();

    const handleSave = form.handleSubmit(interaction.create);

    const { toSearch } = useSearchFromAddPatient();
    const location = useLocation();
    const handleCancel = () => {
        toSearch(location.state.criteria);
    };
    const handleExtended = form.handleSubmit(toExtendedNew);

    const working = !form.formState.isValid || interaction.status !== 'waiting';

    return (
        <DataEntryLayout>
            <Shown when={interaction.status === 'created'}>
                {interaction.status === 'created' && <PatientCreatedPanel created={interaction.created} />}
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
                                    disabled={working}>
                                    Add extended data
                                </Button>
                            )}
                            <Button onClick={handleCancel} outline>
                                Cancel
                            </Button>
                            <Button type="submit" onClick={handleSave} disabled={working}>
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
