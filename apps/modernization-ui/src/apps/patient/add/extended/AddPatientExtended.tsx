import { Button } from 'components/button';
import { Shown } from 'conditional-render';
import { useMemo, useState } from 'react';
import { FormProvider, useForm } from 'react-hook-form';
import { useLocation, useNavigate } from 'react-router-dom';
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

export const AddPatientExtended = () => {
    const location = useLocation();
    const interaction = useAddExtendedPatient({ transformer, creator });
    const [cancelModal, setCancelModal] = useState<boolean>(false);
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

    const handleSave = form.handleSubmit(interaction.create);

    // useEffect(() => {
    //     console.log(location.state);
    //     console.log(useBasicToExtended(location.state.defaults));
    // }, []);

    // get basic info
    // transfer it into extended type
    // and then pass it into the rest of the things?
    const handleCancel = () => {
        if (value) {
            handleCancelConfirm();
        } else {
            setCancelModal(true);
        }
    };

    const handleCancelConfirm = () => {
        navigate('/add-patient');
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
