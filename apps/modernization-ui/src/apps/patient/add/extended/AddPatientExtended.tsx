import { useEffect, useMemo } from 'react';
import { FormProvider, useForm } from 'react-hook-form';
import { Button } from 'components/button';
import { Shown } from 'conditional-render';
import { useNavigationBlock } from 'navigation/useNavigationBlock';
import { CreatedPatient } from 'apps/patient/add/api';
import { DataEntryMenu } from 'apps/patient/add/DataEntryMenu';
import { PatientCreatedPanel } from 'apps/patient/add/PatientCreatedPanel';
import { useBasicExtendedTransition } from 'apps/patient/add/useBasicExtendedTransition';
import { AddPatientExtendedInPageNav } from './nav/AddPatientExtendedNav';
import { ExtendedNewPatientEntry } from './entry';
import { AddPatientExtendedForm } from './AddPatientExtendedForm';
import { CancelAddPatientExtendedPanel } from './CancelAddPatientExtendedPanel';
import { useAddPatientExtendedDefaults } from './useAddPatientExtendedDefaults';
import { useAddExtendedPatient } from './useAddExtendedPatient';
import { AddExtendedPatientInteractionProvider } from './useAddExtendedPatientInteraction';
import { useShowCancelModal } from './useShowCancelModal';

import styles from './add-patient-extended.module.scss';
import { useConfiguration } from 'configuration';

export const AddPatientExtended = () => {
    const interaction = useAddExtendedPatient();
    const { initialize } = useAddPatientExtendedDefaults();
    const { value: bypassModal } = useShowCancelModal();
    const { toNewBasic, toBasic } = useBasicExtendedTransition();

    const created = useMemo<CreatedPatient | undefined>(
        () => (interaction.status === 'created' ? interaction.created : undefined),
        [interaction.status]
    );

    const { features } = useConfiguration();

    const form = useForm<ExtendedNewPatientEntry>({
        defaultValues: initialize(),
        mode: 'onBlur',
        reValidateMode: 'onBlur'
    });

    const working = !form.formState.isValid || interaction.status !== 'waiting';

    const handleSave = form.handleSubmit(interaction.create);

    const handleCancel = () => {
        if (features.patient.add.enabled) {
            toNewBasic();
        } else {
            toBasic();
        }
    };

    // Setup navigation blocking for back button
    const blocker = useNavigationBlock({ activated: !bypassModal });

    useEffect(() => {
        if (form.formState.isSubmitted) {
            //  the form is in a dirty state even after submitting, since we know a submit occurred allow navigation
            blocker.allow();
        } else if (form.formState.isDirty) {
            blocker.block();
        } else {
            blocker.allow();
        }
    }, [form.formState.isSubmitted, form.formState.isDirty, blocker.allow, blocker.block]);

    const handleModalConfirm = () => {
        blocker.unblock();
    };

    const handleModalClose = blocker.reset;

    // Reset the blocker after a successful submission
    useEffect(() => {
        if (interaction.status === 'created') {
            blocker.reset();
        }
    }, [interaction.status]);

    return (
        <AddExtendedPatientInteractionProvider interaction={interaction}>
            <Shown when={interaction.status === 'created'}>
                {created && <PatientCreatedPanel created={created} />}
            </Shown>
            <FormProvider {...form}>
                <div className={styles.addPatientExtended}>
                    <DataEntryMenu />
                    <div className={styles.content}>
                        <header>
                            <h1>New patient - extended</h1>
                            <div className={styles.buttonGroup}>
                                <Button onClick={handleCancel} outline>
                                    Cancel
                                </Button>
                                <Button onClick={handleSave} disabled={working}>
                                    Save
                                </Button>
                            </div>
                        </header>
                        <main>
                            <AddPatientExtendedForm
                                validationErrors={
                                    interaction.status === 'invalid' ? interaction.validationErrors : undefined
                                }
                                setSubFormState={interaction.setSubFormState}
                            />
                            <AddPatientExtendedInPageNav />
                        </main>
                    </div>
                    <Shown when={blocker.blocked}>
                        <CancelAddPatientExtendedPanel onConfirm={handleModalConfirm} onClose={handleModalClose} />
                    </Shown>
                </div>
            </FormProvider>
        </AddExtendedPatientInteractionProvider>
    );
};
