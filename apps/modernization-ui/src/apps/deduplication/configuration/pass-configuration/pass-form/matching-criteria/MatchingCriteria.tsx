import { Icon } from '@trussworks/react-uswds';
import { MatchingAttribute, MatchingAttributeEntry, MatchMethod, Pass } from 'apps/deduplication/api/model/Pass';
import { Heading } from 'components/heading';
import { Shown } from 'conditional-render';
import { Button } from 'design-system/button';
import { Control, Controller, useFormContext, useWatch } from 'react-hook-form';
import styles from './matching-criteria.module.scss';
import { SelectInput } from 'components/FormInputs/SelectInput';

type Props = {
    onAddAttributes: () => void;
};
export const MatchingCriteria = ({ onAddAttributes: onShowAttributes }: Props) => {
    const form = useFormContext<Pass>();
    const { matchingCriteria } = useWatch<Pass>(form);

    const handleRemoveAttribute = (attribute: MatchingAttribute) => {
        const current: MatchingAttributeEntry[] = [];
        matchingCriteria?.forEach((c) => {
            if (c.attribute !== undefined && c.method !== undefined) {
                current.push({ attribute: c.attribute, method: c.method });
            }
        });
        form.setValue(
            'matchingCriteria',
            current.filter((a) => attribute !== a.attribute)
        );
    };

    return (
        <div className={styles.matchingCriteria}>
            <div className={styles.heading}>
                <Heading level={2}>2. Matching criteria</Heading>
                <span>Include records that meet all these conditions</span>
            </div>
            <div className={styles.body}>
                <Shown
                    when={matchingCriteria && matchingCriteria.length > 0}
                    fallback={
                        <div className={styles.noMatchingCriteriaText}>Please add matching criteria to continue.</div>
                    }>
                    <MatchingCriteriaAttribute
                        label="First name"
                        logOdds={9.9444}
                        control={form.control}
                        index={matchingCriteria?.findIndex((m) => m.attribute === MatchingAttribute.FIRST_NAME) ?? 0}
                        visible={
                            matchingCriteria?.find((m) => m.attribute === MatchingAttribute.FIRST_NAME) !== undefined
                        }
                        onRemove={() => handleRemoveAttribute(MatchingAttribute.FIRST_NAME)}
                    />
                    <MatchingCriteriaAttribute
                        label="Last name"
                        logOdds={9.988}
                        control={form.control}
                        index={matchingCriteria?.findIndex((m) => m.attribute === MatchingAttribute.LAST_NAME) ?? 0}
                        visible={
                            matchingCriteria?.find((m) => m.attribute === MatchingAttribute.LAST_NAME) !== undefined
                        }
                        onRemove={() => handleRemoveAttribute(MatchingAttribute.LAST_NAME)}
                    />
                    <MatchingCriteriaAttribute
                        label="Suffix"
                        logOdds={9.988}
                        control={form.control}
                        index={matchingCriteria?.findIndex((m) => m.attribute === MatchingAttribute.SUFFIX) ?? 0}
                        visible={matchingCriteria?.find((m) => m.attribute === MatchingAttribute.SUFFIX) !== undefined}
                        onRemove={() => handleRemoveAttribute(MatchingAttribute.SUFFIX)}
                    />
                    <MatchingCriteriaAttribute
                        label="Date of birth"
                        logOdds={9.988}
                        control={form.control}
                        index={matchingCriteria?.findIndex((m) => m.attribute === MatchingAttribute.DATE_OF_BIRTH) ?? 0}
                        visible={
                            matchingCriteria?.find((m) => m.attribute === MatchingAttribute.DATE_OF_BIRTH) !== undefined
                        }
                        onRemove={() => handleRemoveAttribute(MatchingAttribute.DATE_OF_BIRTH)}
                    />
                    <MatchingCriteriaAttribute
                        label="Sex"
                        logOdds={9.988}
                        control={form.control}
                        index={matchingCriteria?.findIndex((m) => m.attribute === MatchingAttribute.SEX) ?? 0}
                        visible={matchingCriteria?.find((m) => m.attribute === MatchingAttribute.SEX) !== undefined}
                        onRemove={() => handleRemoveAttribute(MatchingAttribute.SEX)}
                    />
                    <MatchingCriteriaAttribute
                        label="Race"
                        logOdds={9.988}
                        control={form.control}
                        index={matchingCriteria?.findIndex((m) => m.attribute === MatchingAttribute.RACE) ?? 0}
                        visible={matchingCriteria?.find((m) => m.attribute === MatchingAttribute.RACE) !== undefined}
                        onRemove={() => handleRemoveAttribute(MatchingAttribute.RACE)}
                    />
                    <MatchingCriteriaAttribute
                        label="Address"
                        logOdds={9.988}
                        control={form.control}
                        index={matchingCriteria?.findIndex((m) => m.attribute === MatchingAttribute.ADDRESS) ?? 0}
                        visible={matchingCriteria?.find((m) => m.attribute === MatchingAttribute.ADDRESS) !== undefined}
                        onRemove={() => handleRemoveAttribute(MatchingAttribute.ADDRESS)}
                    />
                    <MatchingCriteriaAttribute
                        label="City"
                        logOdds={9.988}
                        control={form.control}
                        index={matchingCriteria?.findIndex((m) => m.attribute === MatchingAttribute.CITY) ?? 0}
                        visible={matchingCriteria?.find((m) => m.attribute === MatchingAttribute.CITY) !== undefined}
                        onRemove={() => handleRemoveAttribute(MatchingAttribute.CITY)}
                    />
                    <MatchingCriteriaAttribute
                        label="State"
                        logOdds={9.988}
                        control={form.control}
                        index={matchingCriteria?.findIndex((m) => m.attribute === MatchingAttribute.STATE) ?? 0}
                        visible={matchingCriteria?.find((m) => m.attribute === MatchingAttribute.STATE) !== undefined}
                        onRemove={() => handleRemoveAttribute(MatchingAttribute.STATE)}
                    />
                    <MatchingCriteriaAttribute
                        label="Zip"
                        logOdds={9.988}
                        control={form.control}
                        index={matchingCriteria?.findIndex((m) => m.attribute === MatchingAttribute.ZIP) ?? 0}
                        visible={matchingCriteria?.find((m) => m.attribute === MatchingAttribute.ZIP) !== undefined}
                        onRemove={() => handleRemoveAttribute(MatchingAttribute.ZIP)}
                    />
                    <MatchingCriteriaAttribute
                        label="County"
                        logOdds={9.988}
                        control={form.control}
                        index={matchingCriteria?.findIndex((m) => m.attribute === MatchingAttribute.COUNTY) ?? 0}
                        visible={matchingCriteria?.find((m) => m.attribute === MatchingAttribute.COUNTY) !== undefined}
                        onRemove={() => handleRemoveAttribute(MatchingAttribute.COUNTY)}
                    />
                    <MatchingCriteriaAttribute
                        label="Phone"
                        logOdds={9.988}
                        control={form.control}
                        index={matchingCriteria?.findIndex((m) => m.attribute === MatchingAttribute.PHONE) ?? 0}
                        visible={matchingCriteria?.find((m) => m.attribute === MatchingAttribute.PHONE) !== undefined}
                        onRemove={() => handleRemoveAttribute(MatchingAttribute.PHONE)}
                    />
                    <MatchingCriteriaAttribute
                        label="Email"
                        logOdds={9.988}
                        control={form.control}
                        index={matchingCriteria?.findIndex((m) => m.attribute === MatchingAttribute.EMAIL) ?? 0}
                        visible={matchingCriteria?.find((m) => m.attribute === MatchingAttribute.EMAIL) !== undefined}
                        onRemove={() => handleRemoveAttribute(MatchingAttribute.EMAIL)}
                    />
                    <MatchingCriteriaAttribute
                        label="Identifier"
                        logOdds={9.988}
                        control={form.control}
                        index={matchingCriteria?.findIndex((m) => m.attribute === MatchingAttribute.IDENTIFIER) ?? 0}
                        visible={
                            matchingCriteria?.find((m) => m.attribute === MatchingAttribute.IDENTIFIER) !== undefined
                        }
                        onRemove={() => handleRemoveAttribute(MatchingAttribute.IDENTIFIER)}
                    />
                    <MatchingCriteriaAttribute
                        label="Social security number"
                        logOdds={9.988}
                        control={form.control}
                        index={
                            matchingCriteria?.findIndex((m) => m.attribute === MatchingAttribute.SOCIAL_SECURITY) ?? 0
                        }
                        visible={
                            matchingCriteria?.find((m) => m.attribute === MatchingAttribute.SOCIAL_SECURITY) !==
                            undefined
                        }
                        onRemove={() => handleRemoveAttribute(MatchingAttribute.SOCIAL_SECURITY)}
                    />
                    <MatchingCriteriaAttribute
                        label="Driver's license"
                        logOdds={9.988}
                        control={form.control}
                        index={
                            matchingCriteria?.findIndex(
                                (m) => m.attribute === MatchingAttribute.DRIVERS_LICENSE_NUMBER
                            ) ?? 0
                        }
                        visible={
                            matchingCriteria?.find((m) => m.attribute === MatchingAttribute.DRIVERS_LICENSE_NUMBER) !==
                            undefined
                        }
                        onRemove={() => handleRemoveAttribute(MatchingAttribute.DRIVERS_LICENSE_NUMBER)}
                    />
                    <MatchingCriteriaAttribute
                        label="Medicaid number"
                        logOdds={9.988}
                        control={form.control}
                        index={
                            matchingCriteria?.findIndex((m) => m.attribute === MatchingAttribute.MEDICAID_NUMBER) ?? 0
                        }
                        visible={
                            matchingCriteria?.find((m) => m.attribute === MatchingAttribute.MEDICAID_NUMBER) !==
                            undefined
                        }
                        onRemove={() => handleRemoveAttribute(MatchingAttribute.MEDICAID_NUMBER)}
                    />
                    <MatchingCriteriaAttribute
                        label="Medical record number"
                        logOdds={9.988}
                        control={form.control}
                        index={
                            matchingCriteria?.findIndex(
                                (m) => m.attribute === MatchingAttribute.MEDICAL_RECORD_NUMBER
                            ) ?? 0
                        }
                        visible={
                            matchingCriteria?.find((m) => m.attribute === MatchingAttribute.MEDICAL_RECORD_NUMBER) !==
                            undefined
                        }
                        onRemove={() => handleRemoveAttribute(MatchingAttribute.MEDICAL_RECORD_NUMBER)}
                    />
                    <MatchingCriteriaAttribute
                        label="Account number"
                        logOdds={9.988}
                        control={form.control}
                        index={
                            matchingCriteria?.findIndex((m) => m.attribute === MatchingAttribute.ACCOUNT_NUMBER) ?? 0
                        }
                        visible={
                            matchingCriteria?.find((m) => m.attribute === MatchingAttribute.ACCOUNT_NUMBER) !==
                            undefined
                        }
                        onRemove={() => handleRemoveAttribute(MatchingAttribute.ACCOUNT_NUMBER)}
                    />
                    <MatchingCriteriaAttribute
                        label="National unique idividual identifier"
                        logOdds={9.988}
                        control={form.control}
                        index={
                            matchingCriteria?.findIndex(
                                (m) => m.attribute === MatchingAttribute.NATIONAL_UNIQUE_INDIVIDUAL_IDENTIFIER
                            ) ?? 0
                        }
                        visible={
                            matchingCriteria?.find(
                                (m) => m.attribute === MatchingAttribute.NATIONAL_UNIQUE_INDIVIDUAL_IDENTIFIER
                            ) !== undefined
                        }
                        onRemove={() => handleRemoveAttribute(MatchingAttribute.NATIONAL_UNIQUE_INDIVIDUAL_IDENTIFIER)}
                    />
                    <MatchingCriteriaAttribute
                        label="Patient external identifier"
                        logOdds={9.988}
                        control={form.control}
                        index={
                            matchingCriteria?.findIndex(
                                (m) => m.attribute === MatchingAttribute.PATIENT_EXTERNAL_IDENTIFIER
                            ) ?? 0
                        }
                        visible={
                            matchingCriteria?.find(
                                (m) => m.attribute === MatchingAttribute.PATIENT_EXTERNAL_IDENTIFIER
                            ) !== undefined
                        }
                        onRemove={() => handleRemoveAttribute(MatchingAttribute.PATIENT_EXTERNAL_IDENTIFIER)}
                    />
                    <MatchingCriteriaAttribute
                        label="Patient internal identifier"
                        logOdds={9.988}
                        control={form.control}
                        index={
                            matchingCriteria?.findIndex(
                                (m) => m.attribute === MatchingAttribute.PATIENT_INTERNAL_IDENTIFIER
                            ) ?? 0
                        }
                        visible={
                            matchingCriteria?.find(
                                (m) => m.attribute === MatchingAttribute.PATIENT_INTERNAL_IDENTIFIER
                            ) !== undefined
                        }
                        onRemove={() => handleRemoveAttribute(MatchingAttribute.PATIENT_INTERNAL_IDENTIFIER)}
                    />
                    <MatchingCriteriaAttribute
                        label="Person number"
                        logOdds={9.988}
                        control={form.control}
                        index={matchingCriteria?.findIndex((m) => m.attribute === MatchingAttribute.PERSON_NUMBER) ?? 0}
                        visible={
                            matchingCriteria?.find((m) => m.attribute === MatchingAttribute.PERSON_NUMBER) !== undefined
                        }
                        onRemove={() => handleRemoveAttribute(MatchingAttribute.PERSON_NUMBER)}
                    />
                    <MatchingCriteriaAttribute
                        label="VISA / Passport number"
                        logOdds={9.988}
                        control={form.control}
                        index={matchingCriteria?.findIndex((m) => m.attribute === MatchingAttribute.VISA_PASSPORT) ?? 0}
                        visible={
                            matchingCriteria?.find((m) => m.attribute === MatchingAttribute.VISA_PASSPORT) !== undefined
                        }
                        onRemove={() => handleRemoveAttribute(MatchingAttribute.VISA_PASSPORT)}
                    />
                    <MatchingCriteriaAttribute
                        label="WIC Identifier"
                        logOdds={9.988}
                        control={form.control}
                        index={
                            matchingCriteria?.findIndex((m) => m.attribute === MatchingAttribute.WIC_IDENTIFIER) ?? 0
                        }
                        visible={
                            matchingCriteria?.find((m) => m.attribute === MatchingAttribute.WIC_IDENTIFIER) !==
                            undefined
                        }
                        onRemove={() => handleRemoveAttribute(MatchingAttribute.WIC_IDENTIFIER)}
                    />
                </Shown>
                <div className={styles.buttonContainer}>
                    <Button
                        icon={<Icon.Add size={3} />}
                        labelPosition="right"
                        outline
                        onClick={onShowAttributes}
                        sizing="small"
                        className={styles.addButton}>
                        Add matching attribute(s)
                    </Button>
                </div>
            </div>
        </div>
    );
};

type AttributeProps = {
    label: string;
    logOdds: number;
    visible: boolean;
    control: Control<Pass>;
    index: number;
    onRemove: () => void;
};
const MatchingCriteriaAttribute = ({ label, logOdds, visible, control, index, onRemove }: AttributeProps) => {
    return (
        <Shown when={visible}>
            <div className={styles.attribute}>
                <div className={styles.info}>
                    <div>
                        <div className={styles.label}>{label}</div>
                        <div className={styles.logOdds}>Log odds: {logOdds}</div>
                    </div>
                    <Controller
                        control={control}
                        name={`matchingCriteria.${index}.method`}
                        rules={{ required: { value: true, message: 'Match method is required.' } }}
                        render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                            <SelectInput
                                defaultValue={value}
                                onBlur={onBlur}
                                onChange={onChange}
                                id={name}
                                name={name}
                                htmlFor={name}
                                options={[
                                    { name: 'Exact match', value: MatchMethod.EXACT },
                                    { name: 'JaroWinkler', value: MatchMethod.JAROWINKLER }
                                ]}
                                error={error?.message}
                            />
                        )}
                    />
                </div>
                <div className={styles.deleteButton}>
                    <Button outline onClick={onRemove}>
                        <Icon.Delete size={3} />
                    </Button>
                </div>
            </div>
        </Shown>
    );
};
