import { Button } from 'components/button';
import { Shown } from 'conditional-render';
import { useEffect, useCallback, useMemo, useState } from 'react';
import { FormProvider, useForm } from 'react-hook-form';
import { useNavigate } from 'react-router-dom';
import { useLocalStorage } from 'storage';
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

export const AddPatientExtended = () => {
    const interaction = useAddExtendedPatient({ transformer, creator });
    const [cancelModal, setCancelModal] = useState<boolean>(false);
    const [bypassBlocker, setBypassBlocker] = useState<boolean>(false);
    const { value } = useLocalStorage({ key: 'patient.create.extended.cancel' });
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

    // setup navigation blocking for back button
    const shouldBlockNavigation = formIsDirty && !bypassBlocker;
    // Fires when user attempts to navigate away from the page, i.e. via router link or back button
    const onBlock = useCallback(() => {
        // open modal to confirm cancel
        setCancelModal(true);
    }, []);
    const blocker = useNavigationBlock({ shouldBlock: shouldBlockNavigation, onBlock });

    const handleSaveClick = form.handleSubmit(interaction.create);

    const handleCancelClick = () => {
        // if user clicked cancel button, we don't want to block navigation
        setBypassBlocker(true);
        handleCancelForm();
    };

    const handleCancelForm = useCallback(() => {
        if (value) {
            handleModalConfirm();
        } else {
            setCancelModal(true);
        }
    }, [bypassBlocker]);

    const handleModalConfirm = () => {
        blocker.proceed();
        navigate('/add-patient');
    };

    const handleModalClose = () => {
        blocker.reset();
        setCancelModal(false);
        setBypassBlocker(false);
    };

    // Reset the blocker after a successful submission
    useEffect(() => {
        if (interaction.status === 'created') {
            blocker.reset();
            setBypassBlocker(false);
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
                                <Button onClick={handleCancelClick} outline>
                                    Cancel
                                </Button>
                                <Button onClick={handleSaveClick} disabled={!form.formState.isValid}>
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
