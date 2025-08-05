import { Suspense } from 'react';
import { Await, useNavigate } from 'react-router';
import { useForm } from 'react-hook-form';
import { NavigationGuard } from 'design-system/entry/navigation-guard';
import { Sizing } from 'design-system/field';
import { useComponentSizing } from 'design-system/sizing';
import { Button } from 'design-system/button';
import { TabNavigation, TabNavigationEntry } from 'components/TabNavigation/TabNavigation';
import { PatientDemographics, PatientDemographicsForm } from 'libs/patient/demographics';
import { usePatientFileData } from '../usePatientFileData';
import { PatientFileLayout } from '../PatientFileLayout';
import { Patient } from '../patient';
import { evaluated } from './evaluated';

import styles from './patient-file-edit.module.scss';

const PatientFileEdit = () => {
    const { patient, demographics } = usePatientFileData();
    const sizing = useComponentSizing();

    return (
        <PatientFileLayout patient={patient} actions={EditActions} navigation={EditNavigation}>
            <Suspense>
                <Await resolve={evaluated(demographics.get())}>
                    {(defaultValues) => <Internal sizing={sizing} defaultValues={defaultValues} />}
                </Await>
            </Suspense>
        </PatientFileLayout>
    );
};

type InternalProps = {
    sizing?: Sizing;
    defaultValues: PatientDemographics;
};

const Internal = ({ sizing, defaultValues }: InternalProps) => {
    const form = useForm<PatientDemographics>({
        defaultValues,
        mode: 'onBlur',
        reValidateMode: 'onBlur'
    });

    return (
        <>
            <PatientDemographicsForm form={form} sizing={sizing} className={styles.demographics} />
            <NavigationGuard id="patient.edit.cancel" form={form} activated={!form.formState.isSubmitSuccessful} />
        </>
    );
};

export { PatientFileEdit };

const EditActions = () => {
    const navigate = useNavigate();
    return (
        <>
            <Button secondary onClick={() => navigate(-1)}>
                Cancel
            </Button>
            <Button>Save</Button>
        </>
    );
};

const EditNavigation = (patient: Patient) => (
    <TabNavigation newTab>
        <TabNavigationEntry path={`/patient/${patient.patientId}/edit`}>Demographics</TabNavigationEntry>
    </TabNavigation>
);
