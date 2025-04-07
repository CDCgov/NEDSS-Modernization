import { useCallback, useEffect } from 'react';
import { useLocation } from 'react-router';
import { FormProvider, useForm } from 'react-hook-form';
import { useFormNavigationBlock } from 'navigation';
import { FeatureToggle } from 'feature';
import { Shown } from 'conditional-render';
import { Button } from 'components/button';
import { AddPatientLayout, DataEntryLayout } from 'apps/patient/add/layout';
import { sections } from './sections';
import { AddPatientBasicForm } from './AddPatientBasicForm';
import { BasicNewPatientEntry } from './entry';
import { useAddBasicPatient } from './useAddBasicPatient';
import { PatientCreatedPanel } from '../PatientCreatedPanel';
import { useAddPatientBasicDefaults } from './useAddPatientBasicDefaults';
import { useSearchFromAddPatient } from 'apps/patient/add/useSearchFromAddPatient';
import { useBasicExtendedTransition } from 'apps/patient/add/useBasicExtendedTransition';
import { useShowCancelModal, CancelAddPatientPanel } from '../cancelAddPatientPanel';

import styles from './add-patient-basic.module.scss';

export const AddPatientBasic = () => {
    const { initialize } = useAddPatientBasicDefaults();
    const { value: bypassBlocker } = useShowCancelModal();

    const interaction = useAddBasicPatient();
    const form = useForm<BasicNewPatientEntry>({
        defaultValues: initialize(),
        mode: 'onBlur'
    });
    const blocker = useFormNavigationBlock({ activated: !bypassBlocker, form, allowed: '/patient/add/extended' });

    const { toExtendedNew } = useBasicExtendedTransition();

    const handleSave = form.handleSubmit(interaction.create);

    const { toSearch } = useSearchFromAddPatient();
    const location = useLocation();

    const backToSearch = useCallback(
        () => toSearch(location.state?.criteria?.encrypted),
        [toSearch, location.state?.criteria?.encrypted]
    );

    const handleExtended = form.handleSubmit((data) => toExtendedNew(data, location.state?.criteria));

    const handleFormIsValid = (valid: boolean) => {
        interaction.setCanSave(valid);
    };

    const working = !form.formState.isValid || !interaction.canSave || interaction.status !== 'waiting';

    const handleModalConfirm = () => {
        blocker.unblock();
        backToSearch();
    };

    const handleModalClose = blocker.reset;

    useEffect(() => {
        if (interaction.status === 'created') {
            blocker.allow();
        }
    }, [interaction.status]);

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
                            <FeatureToggle guard={(features) => features.patient?.add?.extended?.enabled}>
                                <Button
                                    type="button"
                                    onClick={handleExtended}
                                    outline
                                    className="add-patient-button"
                                    disabled={working}>
                                    Add extended data
                                </Button>
                            </FeatureToggle>
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
            <Shown when={blocker.blocked}>
                <CancelAddPatientPanel onConfirm={handleModalConfirm} onClose={handleModalClose} />
            </Shown>
        </DataEntryLayout>
    );
};
