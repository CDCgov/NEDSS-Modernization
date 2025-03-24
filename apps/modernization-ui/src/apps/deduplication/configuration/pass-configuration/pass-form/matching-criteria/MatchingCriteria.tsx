import { Icon } from '@trussworks/react-uswds';
import { MatchingAttribute, Pass } from 'apps/deduplication/api/model/Pass';
import { Shown } from 'conditional-render';
import { Button } from 'design-system/button';
import { Card } from 'design-system/card';
import { useEffect, useState } from 'react';
import { useFormContext, useWatch } from 'react-hook-form';
import { MatchingCriteriaAttribute } from './attribute/MatchingCriteriaAttribute';
import styles from './matching-criteria.module.scss';
import { DataElements } from 'apps/deduplication/data-elements/DataElement';
import { getLogOdds } from './getLogOdds';

type Props = {
    dataElements: DataElements;
    onAddAttributes: () => void;
};
export const MatchingCriteria = ({ dataElements, onAddAttributes }: Props) => {
    const form = useFormContext<Pass>();
    const registeredMatchingCriteria = form.register('matchingCriteria', {
        required: true,
        minLength: { value: 1, message: 'Matching criteria is required.' }
    });
    const { matchingCriteria, blockingCriteria } = useWatch<Pass>(form);
    const [disabled, setDisabled] = useState<boolean>(true);

    useEffect(() => {
        setDisabled(blockingCriteria === undefined || blockingCriteria.length === 0);
    }, [blockingCriteria]);

    const handleRemoveAttribute = (attribute: MatchingAttribute) => {
        const value = [...(matchingCriteria ?? [])].filter((a) => a.attribute !== attribute);
        registeredMatchingCriteria.onChange({
            target: { name: 'matchingCriteria', value: value }
        });
        form.trigger('matchingCriteria');
    };

    const attributeList: { label: string; attribute: MatchingAttribute }[] = [
        { label: 'First name', attribute: MatchingAttribute.FIRST_NAME },
        { label: 'Last name', attribute: MatchingAttribute.LAST_NAME },
        { label: 'Suffix', attribute: MatchingAttribute.SUFFIX },
        {
            label: 'Date of birth',
            attribute: MatchingAttribute.BIRTHDATE
        },
        { label: 'Sex', attribute: MatchingAttribute.SEX },
        { label: 'Race', attribute: MatchingAttribute.RACE },
        { label: 'Address', attribute: MatchingAttribute.ADDRESS },
        { label: 'City', attribute: MatchingAttribute.CITY },
        { label: 'State', attribute: MatchingAttribute.STATE },
        { label: 'Zip', attribute: MatchingAttribute.ZIP },
        { label: 'County', attribute: MatchingAttribute.COUNTY },
        { label: 'Phone', attribute: MatchingAttribute.PHONE },
        { label: 'Email', attribute: MatchingAttribute.EMAIL },
        {
            label: 'Social security number',
            attribute: MatchingAttribute.SOCIAL_SECURITY
        },
        {
            label: "Driver's license",
            attribute: MatchingAttribute.DRIVERS_LICENSE_NUMBER
        },
        {
            label: 'Medicaid number',
            attribute: MatchingAttribute.MEDICAID_NUMBER
        },
        {
            label: 'Medical record number',
            attribute: MatchingAttribute.MEDICAL_RECORD_NUMBER
        },
        {
            label: 'Account number',
            attribute: MatchingAttribute.ACCOUNT_NUMBER
        },
        {
            label: 'National unique idividual identifier',
            attribute: MatchingAttribute.NATIONAL_UNIQUE_INDIVIDUAL_IDENTIFIER
        },
        {
            label: 'Patient external identifier',
            attribute: MatchingAttribute.PATIENT_EXTERNAL_IDENTIFIER
        },
        {
            label: 'Patient internal identifier',
            attribute: MatchingAttribute.PATIENT_INTERNAL_IDENTIFIER
        },
        {
            label: 'Person number',
            attribute: MatchingAttribute.PERSON_NUMBER
        },
        {
            label: 'VISA / Passport number',
            attribute: MatchingAttribute.VISA_PASSPORT
        },
        {
            label: 'WIC Identifier',
            attribute: MatchingAttribute.WIC_IDENTIFIER
        }
    ];

    return (
        <div className={styles.matchingCriteria}>
            <Shown when={disabled}>
                <div className={styles.disabledOverlay}></div>
            </Shown>
            <Card
                id="matchingCriteriaCard"
                title="2. Matching criteria"
                subtext="Include records that meet all these conditions">
                <div className={styles.body}>
                    <Shown
                        when={matchingCriteria && matchingCriteria.length > 0}
                        fallback={
                            <div className={styles.noMatchingCriteriaText}>
                                Please add matching criteria to continue.
                            </div>
                        }>
                        {attributeList.map((a, k) => (
                            <MatchingCriteriaAttribute
                                key={`matchingAttribute-${k}`}
                                label={a.label}
                                attribute={a.attribute}
                                onRemove={handleRemoveAttribute}
                                logOdds={getLogOdds(dataElements, a.attribute)}
                            />
                        ))}
                    </Shown>
                    <div className={styles.buttonContainer}>
                        <Button
                            icon={<Icon.Add size={3} />}
                            labelPosition="right"
                            outline
                            onClick={onAddAttributes}
                            sizing="small"
                            disabled={disabled}
                            className={styles.addButton}>
                            Add matching attribute(s)
                        </Button>
                    </div>
                </div>
            </Card>
        </div>
    );
};
