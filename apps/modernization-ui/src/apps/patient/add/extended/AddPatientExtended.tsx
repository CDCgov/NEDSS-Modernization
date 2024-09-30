import { useMemo } from 'react';
import { FormProvider, useForm } from 'react-hook-form';
import { ExtendedNewPatientEntry, initial } from './entry';
import { Button } from 'components/button';
import { DataEntrySideNav } from '../DataEntrySideNav/DataEntrySideNav';
import { transformer } from './transformer';
import { creator } from './creator';
import { useAddExtendedPatient } from './useAddExtendedPatient';
import { AddPatientExtendedForm } from './AddPatientExtendedForm';
import { AddExtendedPatientInteractionProvider } from './useAddExtendedPatientInteraction';
import { AddPatientExtendedNav } from './nav/AddPatientExtendedNav';
import styles from './add-patient-extended.module.scss';
import { useState } from 'react';
import { Shown } from 'conditional-render';
import { PatientCreatedPanel } from '../PatientCreatedPanel';
import { CreatedPatient } from './api';
import { CancelAddPatientExtendedPanel } from './CancelAddPatientExtendedPanel';
import { useLocalStorage } from 'storage';

export const AddPatientExtended = () => {
    const interaction = useAddExtendedPatient({ transformer, creator });
    const [cancelModal, setCancelModal] = useState<boolean>(false);
    const { value } = useLocalStorage({ key: 'patient.create.extended.cancel' });

    const created = useMemo<CreatedPatient | undefined>(
        () => (interaction.status === 'created' ? interaction.created : undefined),
        [interaction.status]
    );

    const defaultValues = initial();

    const form = useForm<ExtendedNewPatientEntry>({
        defaultValues,
        mode: 'onBlur'
    });

    const handleSave = form.handleSubmit(interaction.create);

    const handleCancel = () => {
        if (value) {
            handleCancelConfirm();
        } else {
            setCancelModal(true);
        }
    };

    const handleCancelConfirm = () => {
        form.reset();
        history.go(-1);
    };

    const closeCancel = () => {
        setCancelModal(false);
    };

    return (
        <AddExtendedPatientInteractionProvider interaction={interaction}>
            <Shown when={interaction.status === 'created'}>
                {created && <PatientCreatedPanel created={created} />}
            </Shown>
            <FormProvider {...form}>
                <div className={styles.addPatientExtended}>
                    <DataEntrySideNav />
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
                            <AddPatientExtendedForm />
                            <AddPatientExtendedNav />
                        </main>
                    </div>
                    {cancelModal && (
                        <CancelAddPatientExtendedPanel
                            onConfirm={() => {
                                handleCancelConfirm();
                            }}
                            onClose={() => closeCancel()}
                        />
                    )}
                </div>
            </FormProvider>
        </AddExtendedPatientInteractionProvider>
    );
};
