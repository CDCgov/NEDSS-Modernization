import { useAlert } from 'alert';
import { Button } from 'components/button';
import { Heading } from 'components/heading';
import { Loading } from 'components/Spinner';
import { Shown } from 'conditional-render';
import { AlertMessage } from 'design-system/message';
import { useEffect, useState } from 'react';
import { FormProvider, useForm } from 'react-hook-form';
import { useNavigate } from 'react-router';
import { DataElements } from '../api/model/DataElement';
import { useDataElements } from '../api/useDataElements';
import { useMatchConfiguration } from '../api/useMatchConfiguration';
import styles from './data-elements.module.scss';
import { DataElementsForm } from './form/DataElementsForm/DataElementsForm';
import { InUseDataElements, validateElementsInUse } from './validation/validateDataElementInUse';

const initial: DataElements = {
    firstName: { active: false, oddsRatio: undefined, logOdds: undefined, threshold: undefined },
    lastName: { active: false, oddsRatio: undefined, logOdds: undefined, threshold: undefined },
    dateOfBirth: { active: false, oddsRatio: undefined, logOdds: undefined, threshold: undefined },
    sex: { active: false, oddsRatio: undefined, logOdds: undefined, threshold: undefined },
    race: { active: false, oddsRatio: undefined, logOdds: undefined, threshold: undefined },
    suffix: { active: false, oddsRatio: undefined, logOdds: undefined, threshold: undefined },
    // Address Details
    address: { active: false, oddsRatio: undefined, logOdds: undefined, threshold: undefined },
    city: { active: false, oddsRatio: undefined, logOdds: undefined, threshold: undefined },
    state: { active: false, oddsRatio: undefined, logOdds: undefined, threshold: undefined },
    zip: { active: false, oddsRatio: undefined, logOdds: undefined, threshold: undefined },
    county: { active: false, oddsRatio: undefined, logOdds: undefined, threshold: undefined },
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

export const DataElementConfig = () => {
    const { showSuccess, showError } = useAlert();
    const { dataElements, save, error, loading } = useDataElements();
    const { passes } = useMatchConfiguration();
    const [validationError, setValidationError] = useState<InUseDataElements | undefined>();
    const form = useForm<DataElements>({ mode: 'onBlur', defaultValues: initial });
    const nav = useNavigate();

    useEffect(() => {
        form.reset(dataElements, { keepDefaultValues: false, keepDirty: false });
    }, [dataElements]);

    useEffect(() => {
        if (error) {
            showError(error);
        }
    }, [error]);

    const handleCancel = () => {
        nav({ pathname: '/deduplication/configuration' });
    };

    const handleSubmit = () => {
        const dataElements = form.getValues();
        const validationError = validateElementsInUse(dataElements, passes);

        if (validationError === undefined) {
            setValidationError(undefined);
            save(form.getValues(), () => showSuccess('You have successfully updated the data elements configuration.'));
        } else {
            setValidationError(validationError);
            console.log('error happened: ', validationError);
        }
    };

    const allValues = form.watch(); // Use watch to get live updates

    const hasInvalidValues = Object.values(allValues).some(
        (element) => element.active && (!element.oddsRatio || !element.threshold)
    );

    const hasActiveElements = Object.values(allValues).some((element) => element.active);

    const isSaveDisabled = hasInvalidValues || !hasActiveElements;

    return (
        <div className={styles.dataElements}>
            <div className={styles.heading}>
                <Heading level={1}>Data elements configuration for match criteria</Heading>
            </div>
            <Shown when={validationError !== undefined}>
                <AlertMessage title="Data element currently in use" type="error">
                    <span>
                        The request to remove the{' '}
                        <span style={{ fontWeight: '700' }}>{validationError?.fields.join(', ')}</span> data element
                        {(validationError?.fields.length ?? 0) > 1 ? 's' : ''} is not possible because{' '}
                        {(validationError?.fields.length ?? 0) > 1 ? 'they are' : 'it is'} currently being used in a
                        pass configuration. If you would like to remove this data element, first remove it from the
                        following pass configurations:
                    </span>
                    <ul>{validationError?.passes.map((p, k) => <li key={k}>{p}</li>)}</ul>
                </AlertMessage>
            </Shown>
            <div className={styles.content}>
                <main>
                    <AlertMessage type="error" title="Some title">
                        Message content
                    </AlertMessage>
                    <Shown when={!loading} fallback={<Loading center />}>
                        <FormProvider {...form}>
                            <DataElementsForm dataElements={dataElements} />
                        </FormProvider>
                    </Shown>
                </main>
            </div>
            <div className={styles.buttonBar}>
                <Button outline onClick={handleCancel}>
                    Cancel
                </Button>
                <Button onClick={handleSubmit} disabled={isSaveDisabled}>
                    Save data elements configuration
                </Button>
            </div>
        </div>
    );
};
