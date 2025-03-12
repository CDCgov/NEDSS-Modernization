import { MatchingAttribute } from 'apps/deduplication/api/model/Pass';
import { Button } from 'design-system/button';
import { Icon } from 'design-system/icon';
import { AttributeEntry } from '../../attribute-entry/AttributeEntry';
import { SidePanel } from '../../side-panel/SidePanel';
import styles from './matching-criteria-panel.module.scss';

type Props = {
    selectedAttributes: MatchingAttribute[];
    visible: boolean;
    onAccept: () => void;
    onChange: (attributes: MatchingAttribute[]) => void;
    onCancel: () => void;
};
export const MatchingCriteriaSidePanel = ({ selectedAttributes, visible, onAccept, onChange, onCancel }: Props) => {
    const handleOnChange = (attribute: MatchingAttribute) => {
        if (selectedAttributes.includes(attribute)) {
            onChange([...selectedAttributes].filter((a) => a !== attribute));
        } else {
            onChange([...selectedAttributes, attribute]);
        }
    };

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
                    <Button icon={<Icon name="add" size="small" />} labelPosition="right" onClick={onAccept}>
                        Add attribute(s)
                    </Button>
                </>
            }>
            <div className={styles.matchingCriteriaPanel}>
                <AttributeEntry
                    label="First name"
                    onChange={() => {
                        handleOnChange(MatchingAttribute.FIRST_NAME);
                    }}
                    selected={selectedAttributes.includes(MatchingAttribute.FIRST_NAME)}
                />
                <AttributeEntry
                    label="Last name"
                    onChange={() => {
                        handleOnChange(MatchingAttribute.LAST_NAME);
                    }}
                    selected={selectedAttributes.includes(MatchingAttribute.LAST_NAME)}
                />
                <AttributeEntry
                    label="Suffix"
                    onChange={() => {
                        handleOnChange(MatchingAttribute.SUFFIX);
                    }}
                    selected={selectedAttributes.includes(MatchingAttribute.SUFFIX)}
                />
                <AttributeEntry
                    label="Date of birth"
                    onChange={() => {
                        handleOnChange(MatchingAttribute.DATE_OF_BIRTH);
                    }}
                    selected={selectedAttributes.includes(MatchingAttribute.DATE_OF_BIRTH)}
                />
                <AttributeEntry
                    label="Sex"
                    onChange={() => {
                        handleOnChange(MatchingAttribute.SEX);
                    }}
                    selected={selectedAttributes.includes(MatchingAttribute.SEX)}
                />
                <AttributeEntry
                    label="Race"
                    onChange={() => {
                        handleOnChange(MatchingAttribute.RACE);
                    }}
                    selected={selectedAttributes.includes(MatchingAttribute.RACE)}
                />
                <AttributeEntry
                    label="Address"
                    onChange={() => {
                        handleOnChange(MatchingAttribute.ADDRESS);
                    }}
                    selected={selectedAttributes.includes(MatchingAttribute.ADDRESS)}
                />
                <AttributeEntry
                    label="City"
                    onChange={() => {
                        handleOnChange(MatchingAttribute.CITY);
                    }}
                    selected={selectedAttributes.includes(MatchingAttribute.CITY)}
                />
                <AttributeEntry
                    label="State"
                    onChange={() => {
                        handleOnChange(MatchingAttribute.STATE);
                    }}
                    selected={selectedAttributes.includes(MatchingAttribute.STATE)}
                />
                <AttributeEntry
                    label="Zip"
                    onChange={() => {
                        handleOnChange(MatchingAttribute.ZIP);
                    }}
                    selected={selectedAttributes.includes(MatchingAttribute.ZIP)}
                />
                <AttributeEntry
                    label="County"
                    onChange={() => {
                        handleOnChange(MatchingAttribute.COUNTY);
                    }}
                    selected={selectedAttributes.includes(MatchingAttribute.COUNTY)}
                />
                <AttributeEntry
                    label="Phone"
                    onChange={() => {
                        handleOnChange(MatchingAttribute.PHONE);
                    }}
                    selected={selectedAttributes.includes(MatchingAttribute.PHONE)}
                />
                <AttributeEntry
                    label="Email"
                    onChange={() => {
                        handleOnChange(MatchingAttribute.EMAIL);
                    }}
                    selected={selectedAttributes.includes(MatchingAttribute.EMAIL)}
                />
                <AttributeEntry
                    label="Identifier"
                    onChange={() => {
                        handleOnChange(MatchingAttribute.IDENTIFIER);
                    }}
                    selected={selectedAttributes.includes(MatchingAttribute.IDENTIFIER)}
                />
                <AttributeEntry
                    label="Social security number"
                    onChange={() => {
                        handleOnChange(MatchingAttribute.SOCIAL_SECURITY);
                    }}
                    selected={selectedAttributes.includes(MatchingAttribute.SOCIAL_SECURITY)}
                />
                <AttributeEntry
                    label="Driver's license"
                    onChange={() => {
                        handleOnChange(MatchingAttribute.DRIVERS_LICENSE_NUMBER);
                    }}
                    selected={selectedAttributes.includes(MatchingAttribute.DRIVERS_LICENSE_NUMBER)}
                />
                <AttributeEntry
                    label="Medicaid number"
                    onChange={() => {
                        handleOnChange(MatchingAttribute.MEDICAID_NUMBER);
                    }}
                    selected={selectedAttributes.includes(MatchingAttribute.MEDICAID_NUMBER)}
                />
                <AttributeEntry
                    label="Medical record number"
                    onChange={() => {
                        handleOnChange(MatchingAttribute.MEDICAL_RECORD_NUMBER);
                    }}
                    selected={selectedAttributes.includes(MatchingAttribute.MEDICAL_RECORD_NUMBER)}
                />
                <AttributeEntry
                    label="Account number"
                    onChange={() => {
                        handleOnChange(MatchingAttribute.ACCOUNT_NUMBER);
                    }}
                    selected={selectedAttributes.includes(MatchingAttribute.ACCOUNT_NUMBER)}
                />
                <AttributeEntry
                    label="National unique individual identifier"
                    onChange={() => {
                        handleOnChange(MatchingAttribute.NATIONAL_UNIQUE_INDIVIDUAL_IDENTIFIER);
                    }}
                    selected={selectedAttributes.includes(MatchingAttribute.NATIONAL_UNIQUE_INDIVIDUAL_IDENTIFIER)}
                />
                <AttributeEntry
                    label="Patient external identifier"
                    onChange={() => {
                        handleOnChange(MatchingAttribute.PATIENT_EXTERNAL_IDENTIFIER);
                    }}
                    selected={selectedAttributes.includes(MatchingAttribute.PATIENT_EXTERNAL_IDENTIFIER)}
                />
                <AttributeEntry
                    label="Patient internal identifier"
                    onChange={() => {
                        handleOnChange(MatchingAttribute.PATIENT_INTERNAL_IDENTIFIER);
                    }}
                    selected={selectedAttributes.includes(MatchingAttribute.PATIENT_INTERNAL_IDENTIFIER)}
                />
                <AttributeEntry
                    label="Person number"
                    onChange={() => {
                        handleOnChange(MatchingAttribute.PERSON_NUMBER);
                    }}
                    selected={selectedAttributes.includes(MatchingAttribute.PERSON_NUMBER)}
                />
                <AttributeEntry
                    label="VISA / Passport number"
                    onChange={() => {
                        handleOnChange(MatchingAttribute.VISA_PASSPORT);
                    }}
                    selected={selectedAttributes.includes(MatchingAttribute.VISA_PASSPORT)}
                />
                <AttributeEntry
                    label="WIC identifier"
                    onChange={() => {
                        handleOnChange(MatchingAttribute.WIC_IDENTIFIER);
                    }}
                    selected={selectedAttributes.includes(MatchingAttribute.WIC_IDENTIFIER)}
                />
            </div>
        </SidePanel>
    );
};
