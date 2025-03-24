import { MatchingAttribute, Pass } from 'apps/deduplication/api/model/Pass';
import { Button } from 'design-system/button';
import { Icon } from 'design-system/icon';
import { useEffect, useState } from 'react';
import { useFormContext, useWatch } from 'react-hook-form';
import { AttributeEntry } from '../../attribute-entry/AttributeEntry';
import { SidePanel } from '../../side-panel/SidePanel';
import styles from './matching-criteria-panel.module.scss';

type Props = {
    visible: boolean;
    onAccept: (attributes: MatchingAttribute[]) => void;
    onCancel: () => void;
};
export const MatchingCriteriaSidePanel = ({ visible, onAccept, onCancel }: Props) => {
    const form = useFormContext<Pass>();
    const [selectedAttributes, setSelectedAttributes] = useState<MatchingAttribute[]>([]);
    const { matchingCriteria } = useWatch(form);

    useEffect(() => {
        setSelectedAttributes(matchingCriteria?.map((a) => a.attribute).filter((a) => a !== undefined) ?? []);
    }, [matchingCriteria, visible]);

    const handleOnChange = (attribute: MatchingAttribute) => {
        if (selectedAttributes.includes(attribute)) {
            setSelectedAttributes([...selectedAttributes].filter((a) => a !== attribute));
        } else {
            setSelectedAttributes([...selectedAttributes, attribute]);
        }
    };
    const attributeList: { attribute: MatchingAttribute; label: string }[] = [
        { attribute: MatchingAttribute.FIRST_NAME, label: 'First name' },
        { attribute: MatchingAttribute.LAST_NAME, label: 'Last name' },
        { attribute: MatchingAttribute.SUFFIX, label: 'Suffix' },
        { attribute: MatchingAttribute.DATE_OF_BIRTH, label: 'Date of birth' },
        { attribute: MatchingAttribute.SEX, label: 'Sex' },
        { attribute: MatchingAttribute.RACE, label: 'Race' },
        { attribute: MatchingAttribute.ADDRESS, label: 'Address' },
        { attribute: MatchingAttribute.CITY, label: 'City' },
        { attribute: MatchingAttribute.STATE, label: 'State' },
        { attribute: MatchingAttribute.ZIP, label: 'Zip' },
        { attribute: MatchingAttribute.COUNTY, label: 'County' },
        { attribute: MatchingAttribute.PHONE, label: 'Phone' },
        { attribute: MatchingAttribute.EMAIL, label: 'Email' },
        { attribute: MatchingAttribute.IDENTIFIER, label: 'Identifier' },
        { attribute: MatchingAttribute.SOCIAL_SECURITY, label: 'Social security number' },
        { attribute: MatchingAttribute.DRIVERS_LICENSE_NUMBER, label: "Driver's license" },
        { attribute: MatchingAttribute.MEDICAID_NUMBER, label: 'Medicaid number' },
        { attribute: MatchingAttribute.MEDICAL_RECORD_NUMBER, label: 'Medical record number' },
        { attribute: MatchingAttribute.ACCOUNT_NUMBER, label: 'Account number' },
        {
            attribute: MatchingAttribute.NATIONAL_UNIQUE_INDIVIDUAL_IDENTIFIER,
            label: 'National unique individual identifier'
        },
        { attribute: MatchingAttribute.PATIENT_EXTERNAL_IDENTIFIER, label: 'Patient external identifier' },
        { attribute: MatchingAttribute.PATIENT_INTERNAL_IDENTIFIER, label: 'Patient internal identifier' },
        { attribute: MatchingAttribute.PERSON_NUMBER, label: 'Person number' },
        { attribute: MatchingAttribute.VISA_PASSPORT, label: 'VISA / Passport number' },
        { attribute: MatchingAttribute.WIC_IDENTIFIER, label: 'WIC identifier' }
    ];

    return (
        <SidePanel
            heading="Add matching attribute(s)"
            visible={visible}
            onClose={onCancel}
            footer={
                <>
                    <Button outline onClick={onCancel}>
                        Cancel
                    </Button>
                    <Button
                        icon={<Icon name="add" sizing="small" />}
                        labelPosition="right"
                        onClick={() => onAccept(selectedAttributes)}>
                        Add attribute(s)
                    </Button>
                </>
            }>
            <div className={styles.matchingCriteriaPanel}>
                {attributeList.map((e, k) => (
                    <AttributeEntry
                        key={`matchingAttribute-${k}`}
                        label={e.label}
                        onChange={() => {
                            handleOnChange(e.attribute);
                        }}
                        selected={selectedAttributes.includes(e.attribute)}
                    />
                ))}
            </div>
        </SidePanel>
    );
};
