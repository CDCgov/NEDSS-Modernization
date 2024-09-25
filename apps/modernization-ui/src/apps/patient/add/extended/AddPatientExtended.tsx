import { Button } from 'components/button';
import { today } from 'date';
import { FormProvider, useForm } from 'react-hook-form';
import { DataEntrySideNav } from '../DataEntrySideNav/DataEntrySideNav';
import { AddPatientExtendedForm, DirtyState } from './AddPatientExtendedForm';
import styles from './add-patient-extended.module.scss';
import { ExtendedNewPatientEntry } from './entry';
import { AddPatientExtendedNav } from './nav/AddPatientExtendedNav';
import { useState } from 'react';

export const AddPatientExtended = () => {
    const defaultDate = today();
    const [dirtyState, setDirtyState] = useState<DirtyState>();
    const form = useForm<ExtendedNewPatientEntry>({
        defaultValues: {
            phoneEmails: [],
            administrative: {
                asOf: defaultDate,
                comment: ''
            },
            birthAndSex: {
                asOf: defaultDate
            },
            ethnicity: {
                asOf: defaultDate
            },
            mortality: {
                asOf: defaultDate
            },
            general: {
                asOf: defaultDate
            }
        },
        mode: 'onBlur'
    });

    return (
        <div className={styles.addPatientExtended}>
            <DataEntrySideNav />
            <div className={styles.contet}>
                <header>
                    <h1>New patient - extended</h1>
                    <div className={styles.buttonGroup}>
                        <Button outline>Cancel</Button>
                        <Button onClick={() => console.log('state', dirtyState)}>Save</Button>
                    </div>
                </header>
                <main>
                    <FormProvider {...form}>
                        <AddPatientExtendedForm onInternalDirtyStateChange={setDirtyState} />
                    </FormProvider>
                    <AddPatientExtendedNav />
                </main>
            </div>
        </div>
    );
};
