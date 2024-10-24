import { useEffect, useMemo } from 'react';
import { useNavigate } from 'react-router-dom';
import { FormProvider, useForm } from 'react-hook-form';
import { CreatedPatient } from '../api';
import { creator } from './creator';
import { transformer } from './transformer';
import { ExtendedNewPatientEntry } from './entry';
import { AddExtendedPatientInteractionProvider } from './useAddExtendedPatientInteraction';
import { useAddExtendedPatient } from './useAddExtendedPatient';
import { useShowCancelModal } from './useShowCancelModal';
import { useNavigationBlock } from 'navigation/useNavigationBlock';
import { useAddPatientExtendedDefaults } from './useAddPatientExtendedDefaults';
import { Button } from 'components/button';
import { Shown } from 'conditional-render';
import { AddPatientSideNav } from 'apps/patient/add/nav/AddPatientSideNav';
import { PatientCreatedPanel } from 'apps/patient/add/PatientCreatedPanel';
import { AddPatientExtendedForm } from './AddPatientExtendedForm';
import { CancelAddPatientExtendedPanel } from './CancelAddPatientExtendedPanel';
import { AddPatientExtendedInPageNav } from './nav/AddPatientExtendedNav';

import styles from './add-patient-extended.module.scss';
import { useBasicExtendedTransition } from 'apps/patient/add/useBasicExtendedTransition';

export const AddPatientExtended = () => {
    const interaction = useAddExtendedPatient({ transformer, creator });
    const { initialize } = useAddPatientExtendedDefaults();
    const { value: bypassModal } = useShowCancelModal();
    const { toBasic } = useBasicExtendedTransition();
    const navigate = useNavigate();

    const created = useMemo<CreatedPatient | undefined>(
        () => (interaction.status === 'created' ? interaction.created : undefined),
        [interaction.status]
    );

    const form = useForm<ExtendedNewPatientEntry>({
        defaultValues: initialize(),
        mode: 'onBlur',
        reValidateMode: 'onBlur'
    });

    const handleSave = form.handleSubmit(interaction.create);

    const handleCancel = () => navigate('/add-patient');

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
        toBasic();
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
                    <AddPatientSideNav />
                    <div className={styles.content}>
                        <header>
                            <h1>New patient - extended</h1>
                            <div className={styles.buttonGroup}>
                                <Button onClick={handleCancel} outline>
                                    Cancel
                                </Button>
                                <Button onClick={handleSave} disabled={!form.formState.isValid}>
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
