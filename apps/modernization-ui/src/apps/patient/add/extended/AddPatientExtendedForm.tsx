import { Controller, useFormContext } from 'react-hook-form';
import { Card } from 'design-system/card';
import { ExtendedNewPatientEntry } from './entry';
import { AdministrativeEntryFields } from 'apps/patient/data/administrative/AdministrativeEntryFields';
import { EthnicityEntryFields } from 'apps/patient/data/ethnicity/EthnicityEntryFields';
import { GeneralInformationEntryFields } from 'apps/patient/data/general/GeneralInformationEntryFields';
import { MortalityEntryFields } from 'apps/patient/data/mortality/MortalityEntryFields';
import { SexAndBirthEntryFields } from 'apps/patient/data/sexAndBirth/SexAndBirthEntryFields';
import { AddressRepeatingBlock } from './inputs/address/AddressRepeatingBlock';
import { IdentificationRepeatingBlock } from './inputs/identification/IdentificationRepeatingBlock';
import { NameRepeatingBlock } from './inputs/name/NameRepeatingBlock';
import { PhoneAndEmailRepeatingBlock } from './inputs/phone/PhoneAndEmailRepeatingBlock';
import { RaceRepeatingBlock } from './inputs/race/RaceRepeatingBlock';

import { AlertMessage } from 'design-system/message';
import styles from './add-patient-extended-form.module.scss';
import { SubFormDirtyState, ValidationErrors } from './useAddExtendedPatientInteraction';
import React, { useEffect, useRef } from 'react';
import { useComponentSizing } from 'design-system/sizing';

type Props = {
    validationErrors?: ValidationErrors;
    setSubFormState: (state: Partial<SubFormDirtyState>) => void;
};
export const AddPatientExtendedForm = ({ validationErrors, setSubFormState }: Props) => {
    const { control } = useFormContext<ExtendedNewPatientEntry>();
    const sizing = useComponentSizing();

    const formRef = useRef<HTMLDivElement>(null);

    // Generates an error message that will contain a link to the section if an id is provided
    const generateErrorMessage = (section: string, id?: string) => {
        const linkOrText = id ? (
            <a id={`link-to-${id}`} href={`#${id}`}>
                {section}
            </a>
        ) : (
            section
        );
        return (
            <React.Fragment key={section}>
                Data have been entered in the {linkOrText} section. Please press Add or clear the data and submit again.
            </React.Fragment>
        );
    };

    const renderErrorMessages = () => {
        return (
            <ul className={styles.errorList}>
                {validationErrors?.dirtySections.name && <li>{generateErrorMessage('Name', 'names')}</li>}
                {validationErrors?.dirtySections.address && <li>{generateErrorMessage('Address', 'addresses')}</li>}
                {validationErrors?.dirtySections.phone && (
                    <li>{generateErrorMessage('Phone & Email', 'phoneEmails')}</li>
                )}
                {validationErrors?.dirtySections.identification && (
                    <li>{generateErrorMessage('Identification', 'identifications')}</li>
                )}
                {validationErrors?.dirtySections.race && <li>{generateErrorMessage('Race', 'races')}</li>}
            </ul>
        );
    };
    useEffect(() => {
        if (validationErrors) {
            formRef.current?.scrollIntoView({ behavior: 'smooth', block: 'start' });
        }
    }, [validationErrors]);

    return (
        <div className={styles.addPatientForm}>
            <div className={styles.formContent} ref={formRef}>
                {validationErrors && (
                    <AlertMessage title="Please fix the following errors:" type="error">
                        {renderErrorMessages()}
                    </AlertMessage>
                )}
                <Card
                    title="Administrative"
                    id="administrative"
                    info={<span className="required-before">Required</span>}>
                    <AdministrativeEntryFields sizing={sizing} />
                </Card>
                <Controller
                    control={control}
                    name="names"
                    render={({ field: { onChange, value, name } }) => (
                        <NameRepeatingBlock
                            id={name}
                            values={value}
                            isDirty={(isDirty) => setSubFormState({ name: isDirty })}
                            onChange={onChange}
                            errors={validationErrors?.dirtySections.name ? [generateErrorMessage('Name')] : undefined}
                            sizing={sizing}
                        />
                    )}
                />
                <Controller
                    control={control}
                    name="addresses"
                    render={({ field: { onChange, value, name } }) => (
                        <AddressRepeatingBlock
                            id={name}
                            values={value}
                            isDirty={(isDirty) => setSubFormState({ address: isDirty })}
                            onChange={onChange}
                            errors={
                                validationErrors?.dirtySections.address ? [generateErrorMessage('Address')] : undefined
                            }
                            sizing={sizing}
                        />
                    )}
                />
                <Controller
                    control={control}
                    name="phoneEmails"
                    render={({ field: { onChange, value, name } }) => (
                        <PhoneAndEmailRepeatingBlock
                            id={name}
                            values={value}
                            isDirty={(isDirty) => setSubFormState({ phone: isDirty })}
                            onChange={onChange}
                            errors={
                                validationErrors?.dirtySections.phone
                                    ? [generateErrorMessage('Phone & Email')]
                                    : undefined
                            }
                            sizing={sizing}
                        />
                    )}
                />
                <Controller
                    control={control}
                    name="identifications"
                    render={({ field: { onChange, value, name } }) => (
                        <IdentificationRepeatingBlock
                            id={name}
                            values={value}
                            isDirty={(isDirty) => setSubFormState({ identification: isDirty })}
                            onChange={onChange}
                            errors={
                                validationErrors?.dirtySections.identification
                                    ? [generateErrorMessage('Identification')]
                                    : undefined
                            }
                            sizing={sizing}
                        />
                    )}
                />

                <Controller
                    control={control}
                    name="races"
                    render={({ field: { onChange, value, name } }) => (
                        <RaceRepeatingBlock
                            id={name}
                            values={value}
                            isDirty={(isDirty) => setSubFormState({ race: isDirty })}
                            onChange={onChange}
                            errors={validationErrors?.dirtySections.race ? [generateErrorMessage('Race')] : undefined}
                            sizing={sizing}
                        />
                    )}
                />
                <Card id="ethnicity" title="Ethnicity" info={<span className="required-before">Required</span>}>
                    <EthnicityEntryFields sizing={sizing} />
                </Card>
                <Card id="sexAndBirth" title="Sex & birth" info={<span className="required-before">Required</span>}>
                    <SexAndBirthEntryFields sizing={sizing} />
                </Card>
                <Card id="mortality" title="Mortality" info={<span className="required-before">Required</span>}>
                    <MortalityEntryFields sizing={sizing} />
                </Card>
                <Card
                    id="generalInformation"
                    title="General patient information"
                    info={<span className="required-before">Required</span>}>
                    <GeneralInformationEntryFields sizing={sizing} />
                </Card>
            </div>
        </div>
    );
};
