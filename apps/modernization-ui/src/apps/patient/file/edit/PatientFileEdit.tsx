import { useCallback, useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router';
import { defaultTo } from 'libs/supplying';
import { useForm } from 'react-hook-form';
import { useAlert } from 'alert';
import { NavigationGuard } from 'design-system/entry/navigation-guard';

import { useComponentSizing } from 'design-system/sizing';
import { Button } from 'design-system/button';
import { TabNavigation, TabNavigationEntry } from 'components/TabNavigation/TabNavigation';
import { maybeDisplayName } from 'name';
import { exists } from 'utils/exists';
import {
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
    const { state } = useLocation();
    const navigate = useNavigate();

    const goBack = useCallback(() => {
        const path = defaultTo('..', state?.return);
        navigate(path);
    }, [navigate, state?.return]);

    const { patient, demographics, refresh } = usePatientFileData();
    const sizing = useComponentSizing();
    const defaults = usePatientDemographicDefaults();

    const { showSuccess, showError } = useAlert();

    const handleSuccess = useCallback(() => {
        showSuccess(
            <span>
                You have successfully edited{' '}
                <strong>{`${maybeDisplayName(patient.name)} (Patient ID: ${patient.patientId})`}</strong>.
            </span>
        );
        refresh();
        goBack();
    }, [showSuccess, refresh, goBack]);

    const handleError = useCallback((reason: string) => showError(reason), [showError]);

    const form = useForm<PatientDemographicsEntry>({
        mode: 'onBlur',
        reValidateMode: 'onBlur'
    });

    useEffect(() => {
        evaluated(demographics.get()).then(form.reset);
    }, []);

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
