import { useCallback, useEffect, useState } from 'react';
import { useLocation, useNavigate } from 'react-router';
import { defaultTo } from 'libs/supplying';
import { useForm } from 'react-hook-form';
import { useAlert } from 'alert';
import { maybeDisplayName } from 'name';
import { NavigationGuard } from 'design-system/entry/navigation-guard';
import { Shown } from 'conditional-render';
import { TabNavigation, TabNavigationEntry } from 'components/TabNavigation/TabNavigation';
import { useComponentSizing } from 'design-system/sizing';
import { Button } from 'design-system/button';
import { exists } from 'utils/exists';
import {
    PatientDemographicsEntry,
    PatientDemographicsForm,
    usePatientDemographicDefaults
} from 'libs/patient/demographics';
import { usePendingFormEntry } from 'design-system/entry/pending';
import { Patient } from '../patient';
import { PatientFileLayout } from '../PatientFileLayout';
import { usePatientFileData } from '../usePatientFileData';
import { evaluated } from './evaluated';
import { useEditPatient } from './useEditPatient';

import styles from './patient-file-edit.module.scss';
import { LoadingOverlay } from 'libs/loading';

const PatientFileEdit = () => {
    const { patient, demographics } = usePatientFileData();

    const [entry, setEntry] = useState<PatientDemographicsEntry>();

    useEffect(() => {
        evaluated(demographics.get()).then(setEntry);
    }, []);

    // Suspense and Await are not used here because it causes the blocker within useBlocker to detach
    // from the router resulting in it's state not updating.  This successfully blocks navigation
    // however, the confirmation modal is not displayed.
    return (
        <Shown when={exists(entry)} fallback={<LoadingPatientFileEdit patient={patient} />}>
            {entry && <ReadyPatientFileEdit entry={entry} patient={patient} />}
        </Shown>
    );
};

type LoadingPatientFileEditProps = {
    patient: Patient;
};

const LoadingPatientFileEdit = ({ patient }: LoadingPatientFileEditProps) => (
    <LoadingOverlay>
        <PatientFileLayout patient={patient} navigation={EditNavigation} />
    </LoadingOverlay>
);

const resolveBackPath = defaultTo('..');

type ReadyPatientFileEditProps = {
    patient: Patient;
    entry: PatientDemographicsEntry;
};

const ReadyPatientFileEdit = ({ patient, entry }: ReadyPatientFileEditProps) => {
    const { state } = useLocation();
    const navigate = useNavigate();

    const goBack = useCallback(() => {
        const path = resolveBackPath(state?.return);
        console.log('back path', state.return);
        navigate(path);
    }, [navigate, state?.return]);

    const { refresh } = usePatientFileData();
    const sizing = useComponentSizing();
    const defaults = usePatientDemographicDefaults();

    const { showSuccess, showError } = useAlert();

    const handleSuccess = useCallback(() => {
        showSuccess(
            <>
                You have successfully edited{' '}
                <strong>{`${maybeDisplayName(patient.name)} (Patient ID: ${patient.patientId})`}</strong>.
            </>
        );
        refresh();
        goBack();
    }, [showSuccess, refresh, goBack]);

    const handleError = useCallback((reason: string) => showError(reason), [showError]);

    const form = useForm<PatientDemographicsEntry>({
        mode: 'onBlur',
        reValidateMode: 'onBlur',
        defaultValues: entry
    });

    const pending = usePendingFormEntry({ form });

    const interaction = useEditPatient({ onValidate: pending.check });

    useEffect(() => {
        if (interaction.status === 'error') {
            handleError(interaction.reason);
        } else if (interaction.status === 'completed') {
            handleSuccess();
        }
    }, [interaction.status, interaction.status === 'error' && interaction.reason, handleError, handleSuccess]);

    const disabled =
        (form.formState.isValid && !exists(form.formState.dirtyFields)) ||
        (!form.formState.isValid && exists(form.formState.dirtyFields) && interaction.status === 'waiting');

    const handleSave = form.handleSubmit((input) => interaction.edit(patient.id, input));

    return (
        <PatientFileLayout
            patient={patient}
            actions={() => (
                <>
                    <Button secondary onClick={goBack}>
                        Cancel
                    </Button>
                    <Button onClick={handleSave} disabled={disabled}>
                        Save
                    </Button>
                </>
            )}
            navigation={EditNavigation}>
            <PatientDemographicsForm
                pending={pending}
                defaults={defaults}
                form={form}
                entry={entry}
                sizing={sizing}
                className={styles.demographics}
            />
            <NavigationGuard id="patient.edit.cancel" form={form} activated={interaction.status !== 'completed'} />
        </PatientFileLayout>
    );
};

export { PatientFileEdit };

const EditNavigation = (patient: Patient) => (
    <TabNavigation sizing="medium">
        <TabNavigationEntry path={`/patient/${patient.patientId}/edit`}>Demographics</TabNavigationEntry>
    </TabNavigation>
);
