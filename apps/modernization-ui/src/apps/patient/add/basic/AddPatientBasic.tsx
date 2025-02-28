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
import { useBasicExtendedTransition } from 'apps/patient/add/useBasicExtendedTransition';
import { useNavigationBlock } from 'navigation/useNavigationBlock';
import { useEffect } from 'react';
import { useShowCancelModal } from '../cancelAddPatientPanel/useShowCancelModal';
import { CancelAddPatientPanel } from '../cancelAddPatientPanel/CancelAddPatientPanel';

import styles from './add-patient-basic.module.scss';
import { FeatureToggle } from 'feature';

export const AddPatientBasic = () => {
    const { initialize } = useAddPatientBasicDefaults();
    const { value: bypassModal } = useShowCancelModal();
    const blocker = useNavigationBlock({ activated: !bypassModal });

    const interaction = useAddBasicPatient();
    const form = useForm<BasicNewPatientEntry>({
        defaultValues: {
            ...initialize(),
            address: {
                country: {
                    value: '840',
                    name: 'United States'
                }
            }
        },
        mode: 'onBlur'
    });

    const { toExtendedNew } = useBasicExtendedTransition();

    const handleSave = form.handleSubmit(interaction.create);

    const { toSearch } = useSearchFromAddPatient();
    const location = useLocation();

    const handleCancel = () => {
        toSearch(location.state?.criteria ?? '');
    };

    const handleExtended = form.handleSubmit((data) => toExtendedNew(data, location.state?.criteria ?? ''));

    const handleFormIsValid = (valid: boolean) => {
        interaction.setCanSave(valid);
    };

    const working = !form.formState.isValid || !interaction.canSave || interaction.status !== 'waiting';

    const handleModalConfirm = () => {
        blocker.unblock();
        toSearch(location.state?.criteria ?? '');
    };

    const handleModalClose = blocker.reset;

    useEffect(() => {
        if (form.formState.isSubmitted) {
            blocker.allow();
        } else if (form.formState.isDirty) {
            blocker.block();
        } else {
            blocker.allow();
        }
    }, [form.formState.isSubmitted, form.formState.isDirty, blocker.allow, blocker.block]);

    useEffect(() => {
        if (interaction.status === 'created') {
            blocker.reset();
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
                            <Button onClick={handleCancel} outline>
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
