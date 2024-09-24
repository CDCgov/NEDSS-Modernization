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

export const AddPatientExtended = () => {
    const interaction = useAddExtendedPatient({ transformer, creator });

    const defaultValues = initial();

    const form = useForm<ExtendedNewPatientEntry>({
        defaultValues,
        mode: 'onBlur'
    });

    const handleSave = form.handleSubmit(interaction.create);

    return (
        <AddExtendedPatientInteractionProvider interaction={interaction}>
            <FormProvider {...form}>
                <div className={styles.addPatientExtended}>
                    <DataEntrySideNav />
                    <div className={styles.contet}>
                        <header>
                            <h1>New patient - extended</h1>
                            <div className={styles.buttonGroup}>
                                <Button outline>Cancel</Button>
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
                </div>
            </FormProvider>
        </AddExtendedPatientInteractionProvider>
    );
};
