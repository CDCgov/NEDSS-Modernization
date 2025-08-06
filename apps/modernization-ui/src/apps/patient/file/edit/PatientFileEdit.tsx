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
import { PatientDemographics, PatientDemographicsForm } from 'libs/patient/demographics';
import { usePatientFileData } from '../usePatientFileData';
import { PatientFileLayout } from '../PatientFileLayout';
import { Patient } from '../patient';
import { evaluated } from './evaluated';
import { useEditPatient } from './useEditPatient';

import styles from './patient-file-edit.module.scss';

const PatientFileEdit = () => {
    const { patient, demographics } = usePatientFileData();
    const sizing = useComponentSizing();

    const navigate = useNavigate();

    const { showSuccess, showError } = useAlert();

    const handleSuccess = useCallback(() => {
        showSuccess(
            <span>
                You have successfully edited{' '}
                <strong>{`${maybeDisplayName(patient.name)} (Patient ID: ${patient.patientId})`}</strong>.
            </span>
        );
        demographics.reset();
        navigate(-1);
    }, [showSuccess, navigate]);

    const handleCancel = useCallback(() => navigate(-1), [navigate]);

    const handleError = useCallback((reason: string) => showError(reason), [showError]);

    return (
        <Suspense fallback={<PatientFileLayout patient={patient} navigation={EditNavigation} />}>
            <Await resolve={evaluated(demographics.get())}>
                {(defaultValues) => (
                    <Internal
                        patient={patient}
                        sizing={sizing}
                        defaultValues={defaultValues}
                        onCancel={handleCancel}
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
    defaultValues: PatientDemographics;
    sizing?: Sizing;
    onSuccess: () => void;
    onCancel: () => void;
    onError: (reason: string) => void;
};

const Internal = ({ patient, defaultValues, sizing, onSuccess, onCancel, onError }: InternalProps) => {
    const interaction = useEditPatient();

    const form = useForm<PatientDemographics>({
        defaultValues,
        mode: 'onBlur',
        reValidateMode: 'onBlur'
    });

    useEffect(() => {
        if (interaction.status === 'error') {
            onError(interaction.reason);
        } else if (interaction.status === 'completed') {
            onSuccess();
        }
    }, [interaction.status, interaction.status === 'error' && interaction.reason]);

    const working = !form.formState.isValid || interaction.status !== 'waiting';

    const handleSave = form.handleSubmit((input) => interaction.edit(patient.id, input));

    return (
        <PatientFileLayout
            patient={patient}
            actions={() => (
                <>
                    <Button secondary onClick={onCancel}>
                        Cancel
                    </Button>
                    <Button onClick={handleSave} disabled={working}>
                        Save
                    </Button>
                </>
            )}
            navigation={EditNavigation}>
            <PatientDemographicsForm form={form} sizing={sizing} className={styles.demographics} />
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
