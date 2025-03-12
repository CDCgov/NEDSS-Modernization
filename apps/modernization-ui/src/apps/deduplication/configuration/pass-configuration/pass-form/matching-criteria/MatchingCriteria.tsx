import { Icon } from '@trussworks/react-uswds';
import { MatchingAttribute, MatchingAttributeEntry, Pass } from 'apps/deduplication/api/model/Pass';
import { Heading } from 'components/heading';
import { Shown } from 'conditional-render';
import { Button } from 'design-system/button';
import { useEffect, useState } from 'react';
import { useFormContext, useWatch } from 'react-hook-form';
import { MatchingCriteriaAttribute } from './MatchingCriteriaAttribute';
import styles from './matching-criteria.module.scss';

type Props = {
    onAddAttributes: () => void;
};
export const MatchingCriteria = ({ onAddAttributes: onShowAttributes }: Props) => {
    const form = useFormContext<Pass>();
    const { matchingCriteria, blockingCriteria } = useWatch<Pass>(form);
    const [criteria, setCriteria] = useState<MatchingAttributeEntry[]>([]);
    const [disabled, setDisabled] = useState<boolean>(true);

    useEffect(() => {
        setDisabled(blockingCriteria === undefined || blockingCriteria.length === 0);
    }, [blockingCriteria]);

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
            <Shown when={disabled}>
                <div className={styles.disabledOverlay}></div>
            </Shown>
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
                        attribute={MatchingAttribute.FIRST_NAME}
                        onRemove={handleRemoveAttribute}
                    />
                    <MatchingCriteriaAttribute
                        label="Last name"
                        attribute={MatchingAttribute.LAST_NAME}
                        onRemove={handleRemoveAttribute}
                    />
                    <MatchingCriteriaAttribute
                        label="Suffix"
                        attribute={MatchingAttribute.SUFFIX}
                        onRemove={handleRemoveAttribute}
                    />
                    <MatchingCriteriaAttribute
                        label="Date of birth"
                        attribute={MatchingAttribute.DATE_OF_BIRTH}
                        onRemove={handleRemoveAttribute}
                    />
                    <MatchingCriteriaAttribute
                        label="Sex"
                        attribute={MatchingAttribute.SEX}
                        onRemove={handleRemoveAttribute}
                    />
                    <MatchingCriteriaAttribute
                        label="Race"
                        attribute={MatchingAttribute.RACE}
                        onRemove={handleRemoveAttribute}
                    />
                    <MatchingCriteriaAttribute
                        label="Address"
                        attribute={MatchingAttribute.ADDRESS}
                        onRemove={handleRemoveAttribute}
                    />
                    <MatchingCriteriaAttribute
                        label="City"
                        attribute={MatchingAttribute.CITY}
                        onRemove={handleRemoveAttribute}
                    />
                    <MatchingCriteriaAttribute
                        label="State"
                        attribute={MatchingAttribute.STATE}
                        onRemove={handleRemoveAttribute}
                    />
                    <MatchingCriteriaAttribute
                        label="Zip"
                        attribute={MatchingAttribute.ZIP}
                        onRemove={handleRemoveAttribute}
                    />
                    <MatchingCriteriaAttribute
                        label="County"
                        attribute={MatchingAttribute.COUNTY}
                        onRemove={handleRemoveAttribute}
                    />
                    <MatchingCriteriaAttribute
                        label="Phone"
                        attribute={MatchingAttribute.PHONE}
                        onRemove={handleRemoveAttribute}
                    />
                    <MatchingCriteriaAttribute
                        label="Email"
                        attribute={MatchingAttribute.EMAIL}
                        onRemove={handleRemoveAttribute}
                    />
                    <MatchingCriteriaAttribute
                        label="Identifier"
                        attribute={MatchingAttribute.IDENTIFIER}
                        onRemove={handleRemoveAttribute}
                    />
                    <MatchingCriteriaAttribute
                        label="Social security number"
                        attribute={MatchingAttribute.SOCIAL_SECURITY}
                        onRemove={handleRemoveAttribute}
                    />
                    <MatchingCriteriaAttribute
                        label="Driver's license"
                        attribute={MatchingAttribute.DRIVERS_LICENSE_NUMBER}
                        onRemove={handleRemoveAttribute}
                    />
                    <MatchingCriteriaAttribute
                        label="Medicaid number"
                        attribute={MatchingAttribute.MEDICAID_NUMBER}
                        onRemove={handleRemoveAttribute}
                    />
                    <MatchingCriteriaAttribute
                        label="Medical record number"
                        attribute={MatchingAttribute.MEDICAL_RECORD_NUMBER}
                        onRemove={handleRemoveAttribute}
                    />
                    <MatchingCriteriaAttribute
                        label="Account number"
                        attribute={MatchingAttribute.ACCOUNT_NUMBER}
                        onRemove={handleRemoveAttribute}
                    />
                    <MatchingCriteriaAttribute
                        label="National unique idividual identifier"
                        attribute={MatchingAttribute.NATIONAL_UNIQUE_INDIVIDUAL_IDENTIFIER}
                        onRemove={handleRemoveAttribute}
                    />
                    <MatchingCriteriaAttribute
                        label="Patient external identifier"
                        attribute={MatchingAttribute.PATIENT_EXTERNAL_IDENTIFIER}
                        onRemove={handleRemoveAttribute}
                    />
                    <MatchingCriteriaAttribute
                        label="Patient internal identifier"
                        attribute={MatchingAttribute.PATIENT_INTERNAL_IDENTIFIER}
                        onRemove={handleRemoveAttribute}
                    />
                    <MatchingCriteriaAttribute
                        label="Person number"
                        attribute={MatchingAttribute.PERSON_NUMBER}
                        onRemove={handleRemoveAttribute}
                    />
                    <MatchingCriteriaAttribute
                        label="VISA / Passport number"
                        attribute={MatchingAttribute.VISA_PASSPORT}
                        onRemove={handleRemoveAttribute}
                    />
                    <MatchingCriteriaAttribute
                        label="WIC Identifier"
                        attribute={MatchingAttribute.WIC_IDENTIFIER}
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
                        disabled={disabled}
                        className={styles.addButton}>
                        Add matching attribute(s)
                    </Button>
                </div>
            </div>
        </div>
    );
};
