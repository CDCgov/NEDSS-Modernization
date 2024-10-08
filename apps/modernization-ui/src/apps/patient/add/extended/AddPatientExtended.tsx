import { Button } from 'components/button';
import { Shown } from 'conditional-render';
import { useEffect, useCallback, useMemo, useState } from 'react';
import { FormProvider, useForm } from 'react-hook-form';
import { useNavigate } from 'react-router-dom';
// import { useLocalStorage } from 'storage';
import { AddPatientSideNav } from '../nav/AddPatientSideNav';
import { PatientCreatedPanel } from '../PatientCreatedPanel';
import { AddPatientExtendedForm } from './AddPatientExtendedForm';
import { CreatedPatient } from './api';
import { CancelAddPatientExtendedPanel } from './CancelAddPatientExtendedPanel';
import { creator } from './creator';
import { ExtendedNewPatientEntry, initial } from './entry';
import { AddPatientExtendedInPageNav } from './nav/AddPatientExtendedNav';
import { transformer } from './transformer';
import { useAddExtendedPatient } from './useAddExtendedPatient';
import { AddExtendedPatientInteractionProvider } from './useAddExtendedPatientInteraction';
import styles from './add-patient-extended.module.scss';
import { useNavigationBlock } from 'navigation/useNavigationBlock';
import { useShowCancelModal } from './useShowCancelModal';

export const AddPatientExtended = () => {
    const interaction = useAddExtendedPatient({ transformer, creator });
    const [cancelModal, setCancelModal] = useState<boolean>(false);
    const { value: bypassModal } = useShowCancelModal();
    const navigate = useNavigate();

    const created = useMemo<CreatedPatient | undefined>(
        () => (interaction.status === 'created' ? interaction.created : undefined),
        [interaction.status]
    );

    const defaultValues = initial();
    const form = useForm<ExtendedNewPatientEntry>({
        defaultValues,
        mode: 'onBlur'
    });
    const formIsDirty = form.formState.isDirty;

    const handleSave = form.handleSubmit(interaction.create);

    const handleCancel = useCallback(() => {
        if (bypassModal) {
            handleModalConfirm();
        } else {
            setCancelModal(true);
        }
    }, [bypassModal]);

    // Setup navigation blocking for back button
    const blocker = useNavigationBlock({ shouldBlock: formIsDirty, onBlock: handleCancel });

    const handleModalConfirm = useCallback(() => {
        blocker.unblock();
        navigate('/add-patient');
    }, [blocker, navigate]);

    const handleModalClose = useCallback(() => {
        blocker.reset();
        setCancelModal(false);
    }, [blocker]);

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
                    <div className={styles.contet}>
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
                    {cancelModal && (
                        <CancelAddPatientExtendedPanel onConfirm={handleModalConfirm} onClose={handleModalClose} />
                    )}
                </div>
            </FormProvider>
        </AddExtendedPatientInteractionProvider>
    );
};
