import { Suspense, useCallback, useEffect } from 'react';
import { Await, useNavigate } from 'react-router';
import { useForm } from 'react-hook-form';
import { useAlert } from 'alert';
import { NavigationGuard } from 'design-system/entry/navigation-guard';
import { Sizing } from 'design-system/field';
import { useComponentSizing } from 'design-system/sizing';
import { Button } from 'design-system/button';
import { TabNavigation, TabNavigationEntry } from 'components/TabNavigation/TabNavigation';
import { maybeDisplayName } from 'name';
import { exists } from 'utils/exists';
import {
    PatientDemographics,
    PatientDemographicsDefaults,
    PatientDemographicsEntry,
    PatientDemographicsForm,
    usePatientDemographicDefaults
} from 'libs/patient/demographics';
import { usePendingFormEntry } from 'design-system/entry/pending';
import { usePatientFileData } from '../usePatientFileData';
import { PatientFileLayout } from '../PatientFileLayout';
import { Patient } from '../patient';
import { evaluated } from './evaluated';
import { useEditPatient } from './useEditPatient';

import styles from './patient-file-edit.module.scss';

const PatientFileEdit = () => {
    const { patient, demographics, refresh } = usePatientFileData();
    const sizing = useComponentSizing();
    const defaults = usePatientDemographicDefaults();

    const navigate = useNavigate();

    const { showSuccess, showError } = useAlert();

    const goBack = () => navigate(-1);

    const handleSuccess = useCallback(() => {
        showSuccess(
            <span>
                You have successfully edited{' '}
                <strong>{`${maybeDisplayName(patient.name)} (Patient ID: ${patient.patientId})`}</strong>.
            </span>
        );
        refresh();
        goBack();
    }, [showSuccess, goBack]);

    const handleError = useCallback((reason: string) => showError(reason), [showError]);

    return (
        <Suspense fallback={<PatientFileLayout patient={patient} navigation={EditNavigation} />}>
            <Await resolve={evaluated(demographics.get())}>
                {(demographics) => (
                    <Internal
                        patient={patient}
                        sizing={sizing}
                        demographics={demographics}
                        defaults={defaults}
                        onCancel={goBack}
                        onSuccess={handleSuccess}
                        onError={handleError}
                    />
                )}
            </Await>
        </Suspense>
    );
};

type InternalProps = {
    patient: Patient;
    demographics: PatientDemographics;
    defaults: PatientDemographicsDefaults;
    sizing?: Sizing;
    onSuccess: () => void;
    onCancel: () => void;
    onError: (reason: string) => void;
};

const Internal = ({ patient, demographics, defaults, sizing, onSuccess, onCancel, onError }: InternalProps) => {
    const form = useForm<PatientDemographicsEntry>({
        defaultValues: demographics,
        mode: 'onBlur',
        reValidateMode: 'onBlur'
    });

    const pending = usePendingFormEntry({ form });

    const interaction = useEditPatient({ onValidate: pending.check });

    useEffect(() => {
        if (interaction.status === 'error') {
            onError(interaction.reason);
        } else if (interaction.status === 'completed') {
            onSuccess();
        }
    }, [interaction.status, interaction.status === 'error' && interaction.reason]);

    const disabled =
        (form.formState.isValid && !exists(form.formState.dirtyFields)) ||
        (!form.formState.isValid && exists(form.formState.dirtyFields) && interaction.status === 'waiting');

    const handleSave = form.handleSubmit((input) => interaction.edit(patient.id, input));

    return (
        <PatientFileLayout
            patient={patient}
            actions={() => (
                <>
                    <Button secondary onClick={onCancel}>
                        Cancel
                    </Button>
                    <Button onClick={handleSave} disabled={disabled}>
                        Save
                    </Button>
                </>
            )}
            navigation={EditNavigation}>
            <PatientDemographicsForm
                form={form}
                defaults={defaults}
                pending={pending}
                sizing={sizing}
                className={styles.demographics}
            />
            <NavigationGuard id="patient.edit.cancel" form={form} activated={interaction.status !== 'completed'} />
        </PatientFileLayout>
    );
};

export { PatientFileEdit };

const EditNavigation = (patient: Patient) => (
    <TabNavigation newTab>
        <TabNavigationEntry path={`/patient/${patient.patientId}/edit`}>Demographics</TabNavigationEntry>
    </TabNavigation>
);
