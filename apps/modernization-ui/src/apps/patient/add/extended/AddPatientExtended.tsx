import { Button } from 'components/button';
import { useMemo } from 'react';
import { FormProvider, useForm } from 'react-hook-form';
import { AddPatientExtendedForm } from './AddPatientExtendedForm';
import { creator } from './creator';
import { ExtendedNewPatientEntry, initial } from './entry';
import { AddPatientExtendedInPageNav } from './nav/AddPatientExtendedNav';
import { transformer } from './transformer';
import { useAddExtendedPatient } from './useAddExtendedPatient';
import { AddExtendedPatientInteractionProvider } from './useAddExtendedPatientInteraction';

import { Shown } from 'conditional-render';
import { PatientCreatedPanel } from '../PatientCreatedPanel';
import styles from './add-patient-extended.module.scss';
import { CreatedPatient } from './api';
import { AddPatientSideNav } from '../nav/AddPatientSideNav';

export const AddPatientExtended = () => {
    const interaction = useAddExtendedPatient({ transformer, creator });

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
                                <Button outline>Cancel</Button>
                                <Button onClick={handleSave} disabled={!form.formState.isValid}>
                                    Save
                                </Button>
                            </div>
                        </header>
                        <main>
                            <AddPatientExtendedForm />
                            <AddPatientExtendedInPageNav />
                        </main>
                    </div>
                </div>
            </FormProvider>
        </AddExtendedPatientInteractionProvider>
    );
};
