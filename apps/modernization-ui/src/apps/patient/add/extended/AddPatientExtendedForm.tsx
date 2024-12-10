import { Controller, useFormContext } from 'react-hook-form';
import { Card } from '../card/Card';
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
import React from 'react';

type Props = {
    validationErrors?: ValidationErrors;
    setSubFormState: (state: Partial<SubFormDirtyState>) => void;
};
export const AddPatientExtendedForm = ({ validationErrors, setSubFormState }: Props) => {
    const { control } = useFormContext<ExtendedNewPatientEntry>();

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
                Data has been entered in the {linkOrText} section. Please press Add or clear the data and submit again.
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

    return (
        <div className={styles.addPatientForm}>
            <div className={styles.formContent}>
                {validationErrors && (
                    <AlertMessage title="Please fix the following errors:" type="error">
                        {renderErrorMessages()}
                    </AlertMessage>
                )}
                <Card
                    title="Administrative"
                    id="administrative"
                    info={<span className="required-before">All required fields for adding comments</span>}>
                    <AdministrativeEntryFields />
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
                        />
                    )}
                />
                <Card
                    id="ethnicity"
                    title="Ethnicity"
                    info={<span className="required-before">All required fields for adding ethnicity</span>}>
                    <EthnicityEntryFields />
                </Card>
                <Card
                    id="sexAndBirth"
                    title="Sex & birth"
                    info={<span className="required-before">All required fields for adding sex & birth</span>}>
                    <SexAndBirthEntryFields />
                </Card>
                <Card
                    id="mortality"
                    title="Mortality"
                    info={<span className="required-before">All required fields for adding mortality</span>}>
                    <MortalityEntryFields />
                </Card>
                <Card
                    id="generalInformation"
                    title="General patient information"
                    info={
                        <span className="required-before">
                            All required fields for adding general patient information
                        </span>
                    }>
                    <GeneralInformationEntryFields />
                </Card>
            </div>
        </div>
    );
};
