import { useEffect, useMemo } from 'react';
import { FormProvider, useForm } from 'react-hook-form';
import { Button } from 'components/button';
import { Shown } from 'conditional-render';
import { useFormNavigationBlock } from 'navigation';
import { CreatedPatient } from 'apps/patient/add/api';
import { DataEntryMenu } from 'apps/patient/add/DataEntryMenu';
import { PatientCreatedPanel } from 'apps/patient/add/PatientCreatedPanel';
import { usePatientDataEntryMethod } from 'apps/patient/add/usePatientDataEntryMethod';
import { AddPatientExtendedInPageNav } from './nav/AddPatientExtendedNav';
import { ExtendedNewPatientEntry } from './entry';
import { AddPatientExtendedForm } from './AddPatientExtendedForm';
import { CancelAddPatientPanel, useShowCancelModal } from '../cancelAddPatientPanel';
import { useAddPatientExtendedDefaults } from './useAddPatientExtendedDefaults';
import { useAddExtendedPatient } from './useAddExtendedPatient';
import { AddExtendedPatientInteractionProvider } from './useAddExtendedPatientInteraction';
import { SkipLink } from 'SkipLink';

import styles from './add-patient-extended.module.scss';

export const AddPatientExtended = () => {
    const interaction = useAddExtendedPatient();
    const { initialize } = useAddPatientExtendedDefaults();
    const { value: bypassBlocker } = useShowCancelModal();
    const { toBasic } = usePatientDataEntryMethod();

    const created = useMemo<CreatedPatient | undefined>(
        () => (interaction.status === 'created' ? interaction.created : undefined),
        [interaction.status]
    );

    const form = useForm<ExtendedNewPatientEntry>({
        defaultValues: initialize(),
        mode: 'onBlur',
        reValidateMode: 'onBlur'
    });

    const working = !form.formState.isValid || interaction.status !== 'waiting';

    const handleSave = form.handleSubmit(interaction.create);

    const handleCancel = () => {
        toBasic();
    };

    // Setup navigation blocking for back button
    const blocker = useFormNavigationBlock({ activated: !bypassBlocker, form });

    const handleModalConfirm = () => {
        blocker.unblock();
    };

    const handleModalClose = blocker.reset;

    // Reset the blocker after a successful submission
    useEffect(() => {
        if (interaction.status === 'created') {
            blocker.allow();
        }
    }, [interaction.status]);

    return (
        <AddExtendedPatientInteractionProvider interaction={interaction}>
            <SkipLink id="administrative.asOf" />
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
                        <CancelAddPatientPanel onConfirm={handleModalConfirm} onClose={handleModalClose} />
                    </Shown>
                </div>
            </FormProvider>
        </AddExtendedPatientInteractionProvider>
    );
};
