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
import { ChangeEvent, useState } from 'react';
import { Confirmation } from 'design-system/modal';
import { Checkbox } from '@trussworks/react-uswds';
import { useLocalStorage } from 'storage';

export const AddPatientExtended = () => {
    const interaction = useAddExtendedPatient({ transformer, creator });
    const [cancelModal, setCancelModal] = useState<boolean>(false);
    const [visibilityCheckBox, setVisibilityCheckBox] = useState<boolean>(false);
    const { value, save } = useLocalStorage({ key: 'patient.create.extended.cancel', initial: false });

    const defaultValues = initial();

    const form = useForm<ExtendedNewPatientEntry>({
        defaultValues,
        mode: 'onBlur'
    });

    const handleSave = form.handleSubmit(interaction.create);

    const handleCancel = () => {
        if (value) {
            closeForm();
        } else {
            setCancelModal(true);
        }
    };

    const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
        setVisibilityCheckBox(e.target.checked);
    };

    const handleConfirmCancel = () => {
        save(visibilityCheckBox);
        closeForm();
    };

    const closeForm = () => {
        form.reset();
        history.go(-1);
    };

    return (
        <AddExtendedPatientInteractionProvider interaction={interaction}>
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
                        <Confirmation
                            onCancel={() => {
                                setCancelModal(false);
                            }}
                            title="Warning"
                            confirmText="Yes, cancel"
                            cancelText="No, back to form"
                            onConfirm={() => {
                                handleConfirmCancel();
                            }}>
                            Canceling the form will result in the loss of all additional data entered. Are you sure you
                            want to cancel?
                            <Checkbox
                                label="Don't show again"
                                id={'visbilityCheckbox'}
                                name={'visbilityCheckbox'}
                                onChange={(e) => handleChange(e)}
                            />
                        </Confirmation>
                    )}
                </div>
            </FormProvider>
        </AddExtendedPatientInteractionProvider>
    );
};
