import { useAlert } from 'alert';
import { Button } from 'components/button';
import { Heading } from 'components/heading';
import { Loading } from 'components/Spinner';
import { Shown } from 'conditional-render';
import { useEffect, useState } from 'react';
import { FormProvider, useForm, useFormState, useWatch } from 'react-hook-form';
import { useNavigate } from 'react-router';
import { exists } from 'utils';
import { DataElements } from '../api/model/DataElement';
import { useDataElements } from '../api/useDataElements';
import { useMatchConfiguration } from '../api/useMatchConfiguration';
import styles from './data-elements.module.scss';
import { DataElementsForm } from './form/DataElementsForm/DataElementsForm';
import { DataElementValidationError, InUseDataElements } from './validation/DataElementValidationError';
import { validateElementsInUse } from './validation/validateDataElementInUse';

const initial: DataElements = {
    firstName: { active: false, oddsRatio: undefined, logOdds: undefined },
    lastName: { active: false, oddsRatio: undefined, logOdds: undefined },
    dateOfBirth: { active: false, oddsRatio: undefined, logOdds: undefined },
    sex: { active: false, oddsRatio: undefined, logOdds: undefined },
    race: { active: false, oddsRatio: undefined, logOdds: undefined },
    suffix: { active: false, oddsRatio: undefined, logOdds: undefined },
    // Address Details
    address: { active: false, oddsRatio: undefined, logOdds: undefined },
    city: { active: false, oddsRatio: undefined, logOdds: undefined },
    state: { active: false, oddsRatio: undefined, logOdds: undefined },
    zip: { active: false, oddsRatio: undefined, logOdds: undefined },
    county: { active: false, oddsRatio: undefined, logOdds: undefined },
    telephone: { active: false, oddsRatio: undefined, logOdds: undefined },
    email: { active: false, oddsRatio: undefined, logOdds: undefined },
    // Identification Details
    accountNumber: { active: false, oddsRatio: undefined, logOdds: undefined },
    driversLicenseNumber: { active: false, oddsRatio: undefined, logOdds: undefined },
    medicaidNumber: { active: false, oddsRatio: undefined, logOdds: undefined },
    medicalRecordNumber: { active: false, oddsRatio: undefined, logOdds: undefined },
    medicareNumber: { active: false, oddsRatio: undefined, logOdds: undefined },
    nationalUniqueIdentifier: { active: false, oddsRatio: undefined, logOdds: undefined },
    patientExternalIdentifier: { active: false, oddsRatio: undefined, logOdds: undefined },
    patientInternalIdentifier: { active: false, oddsRatio: undefined, logOdds: undefined },
    personNumber: { active: false, oddsRatio: undefined, logOdds: undefined },
    socialSecurity: { active: false, oddsRatio: undefined, logOdds: undefined },
    visaPassport: { active: false, oddsRatio: undefined, logOdds: undefined },
    wicIdentifier: { active: false, oddsRatio: undefined, logOdds: undefined }
};

export const DataElementConfig = () => {
    const { showSuccess, showError } = useAlert();
    const { dataElements, save, error, loading } = useDataElements();
    const { passes } = useMatchConfiguration();
    const [validationError, setValidationError] = useState<InUseDataElements | undefined>();

    const form = useForm<DataElements>({ mode: 'onBlur', defaultValues: initial });
    const { isValid, isDirty, dirtyFields } = useFormState(form);
    const [isSaveDisabled, setIsSaveDisabled] = useState(true);
    const allValues = useWatch(form);
    const nav = useNavigate();

    useEffect(() => {
        form.reset(dataElements, { keepDefaultValues: false, keepDirty: false });
    }, [dataElements]);

    useEffect(() => {
        if (error) {
            showError(error);
        }
    }, [error]);

    useEffect(() => {
        const hasActiveElements = Object.values(allValues).some((element) => element.active);
        setIsSaveDisabled(!(isDirty && exists(dirtyFields)) || !isValid || !hasActiveElements);
    }, [JSON.stringify(allValues), isDirty, dirtyFields, isValid]);

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
        }
    };

    return (
        <div className={styles.dataElements}>
            <div className={styles.heading}>
                <Heading level={1}>Data elements configuration for match criteria</Heading>
            </div>

            <div className={styles.content}>
                <DataElementValidationError validationError={validationError} />
                <main>
                    <Shown when={!loading} fallback={<Loading center />}>
                        <FormProvider {...form}>
                            <DataElementsForm dataElements={dataElements} />
                        </FormProvider>
                    </Shown>
                </main>
            </div>
            <div className={styles.buttonBar}>
                <Button secondary onClick={handleCancel}>
                    Cancel
                </Button>
                <Button onClick={handleSubmit} disabled={isSaveDisabled}>
                    Save data elements configuration
                </Button>
            </div>
        </div>
    );
};
