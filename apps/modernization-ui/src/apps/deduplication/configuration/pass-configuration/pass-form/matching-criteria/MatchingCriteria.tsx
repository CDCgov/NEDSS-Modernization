import { Icon } from '@trussworks/react-uswds';
import { MatchingAttribute, MatchingAttributeEntry, MatchMethod, Pass } from 'apps/deduplication/api/model/Pass';
import { Heading } from 'components/heading';
import { Shown } from 'conditional-render';
import { Button } from 'design-system/button';
import { Control, Controller, useFormContext, useWatch } from 'react-hook-form';
import styles from './matching-criteria.module.scss';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { useEffect, useState } from 'react';

type Props = {
    onAddAttributes: () => void;
};
export const MatchingCriteria = ({ onAddAttributes: onShowAttributes }: Props) => {
    const form = useFormContext<Pass>();
    const { matchingCriteria } = useWatch<Pass>(form);
    const [criteria, setCriteria] = useState<MatchingAttributeEntry[]>([]);

    useEffect(() => {
        // create local copy to avoid form issues with undefined
        const cleanCriteria: MatchingAttributeEntry[] = [];
        matchingCriteria?.forEach((c) => {
            if (c.attribute !== undefined && c.method !== undefined) {
                cleanCriteria.push({ attribute: c.attribute, method: c.method });
            }
        });
        setCriteria(cleanCriteria);
    }, [matchingCriteria]);

    const handleRemoveAttribute = (attribute: MatchingAttribute) => {
        form.setValue(
            'matchingCriteria',
            criteria.filter((a) => attribute !== a.attribute)
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
                        attribute={MatchingAttribute.FIRST_NAME}
                        matchingCriteria={criteria}
                        onRemove={handleRemoveAttribute}
                    />
                    <MatchingCriteriaAttribute
                        label="Last name"
                        logOdds={9.988}
                        control={form.control}
                        attribute={MatchingAttribute.LAST_NAME}
                        matchingCriteria={criteria}
                        onRemove={handleRemoveAttribute}
                    />
                    <MatchingCriteriaAttribute
                        label="Suffix"
                        logOdds={9.988}
                        control={form.control}
                        attribute={MatchingAttribute.SUFFIX}
                        matchingCriteria={criteria}
                        onRemove={handleRemoveAttribute}
                    />
                    <MatchingCriteriaAttribute
                        label="Date of birth"
                        logOdds={9.988}
                        control={form.control}
                        attribute={MatchingAttribute.DATE_OF_BIRTH}
                        matchingCriteria={criteria}
                        onRemove={handleRemoveAttribute}
                    />
                    <MatchingCriteriaAttribute
                        label="Sex"
                        logOdds={9.988}
                        control={form.control}
                        attribute={MatchingAttribute.SEX}
                        matchingCriteria={criteria}
                        onRemove={handleRemoveAttribute}
                    />
                    <MatchingCriteriaAttribute
                        label="Race"
                        logOdds={9.988}
                        control={form.control}
                        attribute={MatchingAttribute.RACE}
                        matchingCriteria={criteria}
                        onRemove={handleRemoveAttribute}
                    />
                    <MatchingCriteriaAttribute
                        label="Address"
                        logOdds={9.988}
                        control={form.control}
                        attribute={MatchingAttribute.ADDRESS}
                        matchingCriteria={criteria}
                        onRemove={handleRemoveAttribute}
                    />
                    <MatchingCriteriaAttribute
                        label="City"
                        logOdds={9.988}
                        control={form.control}
                        attribute={MatchingAttribute.CITY}
                        matchingCriteria={criteria}
                        onRemove={handleRemoveAttribute}
                    />
                    <MatchingCriteriaAttribute
                        label="State"
                        logOdds={9.988}
                        control={form.control}
                        attribute={MatchingAttribute.STATE}
                        matchingCriteria={criteria}
                        onRemove={handleRemoveAttribute}
                    />
                    <MatchingCriteriaAttribute
                        label="Zip"
                        logOdds={9.988}
                        control={form.control}
                        attribute={MatchingAttribute.ZIP}
                        matchingCriteria={criteria}
                        onRemove={handleRemoveAttribute}
                    />
                    <MatchingCriteriaAttribute
                        label="County"
                        logOdds={9.988}
                        control={form.control}
                        attribute={MatchingAttribute.COUNTY}
                        matchingCriteria={criteria}
                        onRemove={handleRemoveAttribute}
                    />
                    <MatchingCriteriaAttribute
                        label="Phone"
                        logOdds={9.988}
                        control={form.control}
                        attribute={MatchingAttribute.PHONE}
                        matchingCriteria={criteria}
                        onRemove={handleRemoveAttribute}
                    />
                    <MatchingCriteriaAttribute
                        label="Email"
                        logOdds={9.988}
                        control={form.control}
                        attribute={MatchingAttribute.EMAIL}
                        matchingCriteria={criteria}
                        onRemove={handleRemoveAttribute}
                    />
                    <MatchingCriteriaAttribute
                        label="Identifier"
                        logOdds={9.988}
                        control={form.control}
                        attribute={MatchingAttribute.IDENTIFIER}
                        matchingCriteria={criteria}
                        onRemove={handleRemoveAttribute}
                    />
                    <MatchingCriteriaAttribute
                        label="Social security number"
                        logOdds={9.988}
                        control={form.control}
                        attribute={MatchingAttribute.SOCIAL_SECURITY}
                        matchingCriteria={criteria}
                        onRemove={handleRemoveAttribute}
                    />
                    <MatchingCriteriaAttribute
                        label="Driver's license"
                        logOdds={9.988}
                        control={form.control}
                        attribute={MatchingAttribute.DRIVERS_LICENSE_NUMBER}
                        matchingCriteria={criteria}
                        onRemove={handleRemoveAttribute}
                    />
                    <MatchingCriteriaAttribute
                        label="Medicaid number"
                        logOdds={9.988}
                        control={form.control}
                        attribute={MatchingAttribute.MEDICAID_NUMBER}
                        matchingCriteria={criteria}
                        onRemove={handleRemoveAttribute}
                    />
                    <MatchingCriteriaAttribute
                        label="Medical record number"
                        logOdds={9.988}
                        control={form.control}
                        attribute={MatchingAttribute.MEDICAL_RECORD_NUMBER}
                        matchingCriteria={criteria}
                        onRemove={handleRemoveAttribute}
                    />
                    <MatchingCriteriaAttribute
                        label="Account number"
                        logOdds={9.988}
                        control={form.control}
                        attribute={MatchingAttribute.ACCOUNT_NUMBER}
                        matchingCriteria={criteria}
                        onRemove={handleRemoveAttribute}
                    />
                    <MatchingCriteriaAttribute
                        label="National unique idividual identifier"
                        logOdds={9.988}
                        control={form.control}
                        attribute={MatchingAttribute.NATIONAL_UNIQUE_INDIVIDUAL_IDENTIFIER}
                        matchingCriteria={criteria}
                        onRemove={handleRemoveAttribute}
                    />
                    <MatchingCriteriaAttribute
                        label="Patient external identifier"
                        logOdds={9.988}
                        control={form.control}
                        attribute={MatchingAttribute.PATIENT_EXTERNAL_IDENTIFIER}
                        matchingCriteria={criteria}
                        onRemove={handleRemoveAttribute}
                    />
                    <MatchingCriteriaAttribute
                        label="Patient internal identifier"
                        logOdds={9.988}
                        control={form.control}
                        attribute={MatchingAttribute.PATIENT_INTERNAL_IDENTIFIER}
                        matchingCriteria={criteria}
                        onRemove={handleRemoveAttribute}
                    />
                    <MatchingCriteriaAttribute
                        label="Person number"
                        logOdds={9.988}
                        control={form.control}
                        attribute={MatchingAttribute.PERSON_NUMBER}
                        matchingCriteria={criteria}
                        onRemove={handleRemoveAttribute}
                    />
                    <MatchingCriteriaAttribute
                        label="VISA / Passport number"
                        logOdds={9.988}
                        control={form.control}
                        attribute={MatchingAttribute.VISA_PASSPORT}
                        matchingCriteria={criteria}
                        onRemove={handleRemoveAttribute}
                    />
                    <MatchingCriteriaAttribute
                        label="WIC Identifier"
                        logOdds={9.988}
                        control={form.control}
                        attribute={MatchingAttribute.WIC_IDENTIFIER}
                        matchingCriteria={criteria}
                        onRemove={handleRemoveAttribute}
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
    control: Control<Pass>;
    matchingCriteria: MatchingAttributeEntry[];
    attribute: MatchingAttribute;
    onRemove: (attribute: MatchingAttribute) => void;
};
const MatchingCriteriaAttribute = ({
    label,
    logOdds,
    control,
    attribute,
    matchingCriteria,
    onRemove
}: AttributeProps) => {
    const [index, setIndex] = useState(0);

    useEffect(() => {
        setIndex(matchingCriteria.findIndex((m) => m.attribute === attribute));
    }, [matchingCriteria]);
    return (
        <Shown when={matchingCriteria.find((m) => m.attribute === attribute) !== undefined}>
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
                    <Button outline onClick={() => onRemove(attribute)}>
                        <Icon.Delete size={3} />
                    </Button>
                </div>
            </div>
        </Shown>
    );
};
