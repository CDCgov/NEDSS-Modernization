import { useCallback, useEffect } from 'react';
import { useLocation } from 'react-router';
import { FormProvider, useForm } from 'react-hook-form';
import { Shown } from 'conditional-render';
import { Button } from 'components/button';
import { NavigationGuard } from 'design-system/entry/navigation-guard';
import { sections } from './sections';
import { AddPatientBasicForm } from './AddPatientBasicForm';
import { BasicNewPatientEntry, initial } from './entry';
import { useAddBasicPatient } from './useAddBasicPatient';
import { AddPatientLayout, DataEntryLayout } from '../layout';
import { PatientCreatedPanel } from '../PatientCreatedPanel';
import { usePatientDataEntryMethod } from '../usePatientDataEntryMethod';
import { useAddPatientBasicDefaults } from './useAddPatientBasicDefaults';
import { useSearchFromAddPatient } from '../useSearchFromAddPatient';

import styles from './add-patient-basic.module.scss';

export const AddPatientBasic = () => {
    const { defaults } = useAddPatientBasicDefaults();

    const interaction = useAddBasicPatient();

    const form = useForm<BasicNewPatientEntry>({
        defaultValues: initial(),
        mode: 'onBlur'
    });

    useEffect(() => {
        if (defaults) {
            form.reset(defaults, { keepDefaultValues: true });
        }
    }, [defaults]);
    defaults;

    const { toExtended } = usePatientDataEntryMethod();

    const handleSave = form.handleSubmit(interaction.create);

    const { toSearch } = useSearchFromAddPatient();
    const location = useLocation();

    const backToSearch = useCallback(
        () => toSearch(location.state?.criteria?.encrypted),
        [toSearch, location.state?.criteria?.encrypted]
    );

    const handleExtended = useCallback(
        form.handleSubmit((data) => toExtended(data, location.state?.criteria)),
        [form.handleSubmit, toExtended, location.state?.criteria]
    );

    const handleFormIsValid = (valid: boolean) => {
        interaction.setCanSave(valid);
    };

    const working = !form.formState.isValid || !interaction.canSave || interaction.status !== 'waiting';

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
                            <Button
                                type="button"
                                onClick={handleExtended}
                                outline
                                className="add-patient-button"
                                disabled={working}>
                                Add extended data
                            </Button>
                            <Button onClick={backToSearch} outline>
                                Cancel
                            </Button>
                            <Button type="submit" onClick={handleSave} disabled={working}>
                                Save
                            </Button>
                        </div>
                    )}>
                    <AddPatientBasicForm isValid={handleFormIsValid} />
                </AddPatientLayout>
            </FormProvider>
            <NavigationGuard
                id="patient.create.basic.cancel"
                form={form}
                allowed={'/patient/add/extended'}
                activated={interaction.status !== 'created'}
            />
        </DataEntryLayout>
    );
};
