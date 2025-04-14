import { useAlert } from 'alert';
import { Button } from 'components/button';
import { Heading } from 'components/heading';
import { useEffect, useState } from 'react';
import { FormProvider, useForm } from 'react-hook-form';
import { useNavigate } from 'react-router';
import { useDataElements } from '../api/useDataElements';
import { DataElements } from '../api/model/DataElement';
import { DataElementsForm } from './form/DataElementsForm/DataElementsForm';
import styles from './data-elements.module.scss';
import { Shown } from 'conditional-render';
import { Loading } from 'components/Spinner';
import { useMatchConfiguration } from '../api/useMatchConfiguration';
import { MatchingAttribute } from '../api/model/Pass';
import { DataElementToMatchingAttribute } from '../api/model/Conversion';
import { AlertMessage } from 'design-system/message';
import { MatchingAttributeLabelMap } from '../api/model/Labels';

type ValidationError = { passes: string[]; fields: string[] };

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

export const DataElementConfig = () => {
    const { showSuccess, showError } = useAlert();
    const { dataElements, save, error, loading } = useDataElements();
    const { passes } = useMatchConfiguration();
    const [inUseDataElements, setInUseDataElements] = useState<MatchingAttribute[]>([]);
    const [validationError, setValidationError] = useState<ValidationError | undefined>();
    const form = useForm<DataElements>({ mode: 'onBlur', defaultValues: initial });
    const nav = useNavigate();

    useEffect(() => {
        form.reset(dataElements, { keepDefaultValues: false, keepDirty: false });
    }, [dataElements]);

    useEffect(() => {
        console.log('passes', passes);
        if (passes && passes.length > 0) {
            const inUse = [...new Set(passes.flatMap((p) => p.matchingCriteria.flatMap((m) => m.attribute)))];
            setInUseDataElements(inUse);
        }
    }, [JSON.stringify(passes)]);

    useEffect(() => {
        if (error) {
            showError({ message: error });
        }
    }, [error]);

    const handleCancel = () => {
        nav({ pathname: '/deduplication/configuration' });
    };

    // TODO -- make this more betters, move it somewhere, create new component
    const validateElementsInUse = (toValidate: DataElements): ValidationError | undefined => {
        const invalidPasses: string[] = [];
        const invalidElements: string[] = [];
        const disabledElements = Object.entries(toValidate)
            .filter((value) => !value[1].active) // get all entries that are set to 'active: false'
            .map((value) => DataElementToMatchingAttribute.get(value[0] as keyof DataElements)) // map to MatchingAttribute
            .filter((m) => m !== undefined);

        // Check each pass to see if its matchingCriteria includes any of the disabled elements
        passes.forEach((p) => {
            // get a list of in use matching criteria for the pass
            const passMatchCriteria = p.matchingCriteria.map((m) => m.attribute);

            // check if any of the disabled data elements are in use in the pass
            const conflictingCriteria = passMatchCriteria.filter((c) => disabledElements.includes(c));
            // if so, add pass to invalid pass list
            if (conflictingCriteria.length > 0) {
                invalidPasses.push(p.name);
                invalidElements.push(...conflictingCriteria.map((c) => MatchingAttributeLabelMap.get(c)?.label ?? ''));
            }
        });
        if (invalidPasses.length === 0) {
            return;
        }

        return { passes: invalidPasses, fields: [...new Set(invalidElements)] };
    };
    const handleSubmit = () => {
        const dataElements = form.getValues();
        const validationError = validateElementsInUse(dataElements);

        // need to display Pass names
        // as well as fields
        console.log('inUseCriteria', inUseDataElements);
        if (validationError === undefined) {
            setValidationError(undefined);
            save(form.getValues(), () =>
                showSuccess({ message: 'You have successfully updated the data elements configuration.' })
            );
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
