import { AlertProvider, useAlert } from 'alert';
import { Button } from 'components/button';
import { Heading } from 'components/heading';
import { useEffect } from 'react';
import { FormProvider, useForm } from 'react-hook-form';
import { useNavigate } from 'react-router-dom';
import { useDataElements } from '../api/useDataElements';
import { DataElements } from './DataElement';
import { DataElementsForm } from './form/DataElementsForm';
import { HowTo } from './HowTo';
import styles from './data-elements.module.scss';

export const DataElementConfig = () => {
    return (
        <AlertProvider>
            <DataElementConfigContent />
        </AlertProvider>
    );
};
const initial: DataElements = {
    firstName: { active: false, oddsRatio: undefined, logOdds: undefined, threshold: undefined },
    lastName: { active: false, oddsRatio: undefined, logOdds: undefined, threshold: undefined },
    dateOfBirth: { active: false, oddsRatio: undefined, logOdds: undefined, threshold: undefined },
    currentSex: { active: false, oddsRatio: undefined, logOdds: undefined, threshold: undefined },
    race: { active: false, oddsRatio: undefined, logOdds: undefined, threshold: undefined },
    suffix: { active: false, oddsRatio: undefined, logOdds: undefined, threshold: undefined },
    // Address Details
    streetAddress1: { active: false, oddsRatio: undefined, logOdds: undefined, threshold: undefined },
    city: { active: false, oddsRatio: undefined, logOdds: undefined, threshold: undefined },
    state: { active: false, oddsRatio: undefined, logOdds: undefined, threshold: undefined },
    zip: { active: false, oddsRatio: undefined, logOdds: undefined, threshold: undefined },
    county: { active: false, oddsRatio: undefined, logOdds: undefined, threshold: undefined },
    telecom: { active: false, oddsRatio: undefined, logOdds: undefined, threshold: undefined },
    telephone: { active: false, oddsRatio: undefined, logOdds: undefined, threshold: undefined },
    email: { active: false, oddsRatio: undefined, logOdds: undefined, threshold: undefined },
    // Identification Details
    accountNumber: { active: false, oddsRatio: undefined, logOdds: undefined, threshold: undefined },
    driversLicenseNumber: { active: false, oddsRatio: undefined, logOdds: undefined, threshold: undefined },
    medicaidNumber: { active: false, oddsRatio: undefined, logOdds: undefined, threshold: undefined },
    medicalRecordNumber: { active: false, oddsRatio: undefined, logOdds: undefined, threshold: undefined },
    medicareNumber: { active: false, oddsRatio: undefined, logOdds: undefined, threshold: undefined },
    nationalUniqueIdentifier: { active: false, oddsRatio: undefined, logOdds: undefined, threshold: undefined },
    patientExternalIdentifier: { active: false, oddsRatio: undefined, logOdds: undefined, threshold: undefined },
    patientInternalIdentifier: { active: false, oddsRatio: undefined, logOdds: undefined, threshold: undefined },
    personNumber: { active: false, oddsRatio: undefined, logOdds: undefined, threshold: undefined },
    socialSecurity: { active: false, oddsRatio: undefined, logOdds: undefined, threshold: undefined },
    visaPassport: { active: false, oddsRatio: undefined, logOdds: undefined, threshold: undefined },
    wicIdentifier: { active: false, oddsRatio: undefined, logOdds: undefined, threshold: undefined }
};

const DataElementConfigContent = () => {
    const { showSuccess, showError } = useAlert();
    const { configuration, save, error } = useDataElements();
    const form = useForm<DataElements>({ mode: 'onBlur', defaultValues: initial });
    const nav = useNavigate();

    useEffect(() => {
        form.reset(configuration, { keepDefaultValues: false, keepDirty: false });
    }, [configuration]);

    useEffect(() => {
        if (error) {
            showError({ message: error });
        }
    }, [error]);

    const handleCancel = () => {
        nav({ pathname: '/deduplication/match-configuration' });
    };
    const handleSubmit = () => {
        save(form.getValues(), () =>
            showSuccess({ message: 'You have successfully updated the data elements configuration.' })
        );
    };

    return (
        <div className={styles.dataElements}>
            <div className={styles.heading}>
                <Heading level={1}>Data elements configuration for match criteria</Heading>
            </div>
            <div className={styles.content}>
                <main>
                    <FormProvider {...form}>
                        <DataElementsForm />
                    </FormProvider>
                </main>
                <HowTo />
            </div>
            <div className={styles.buttonBar}>
                <Button outline onClick={handleCancel}>
                    Cancel
                </Button>
                <Button onClick={handleSubmit} disabled={!form.formState.isDirty || !form.formState.isValid}>
                    Save data elements configuration
                </Button>
            </div>
        </div>
    );
};
