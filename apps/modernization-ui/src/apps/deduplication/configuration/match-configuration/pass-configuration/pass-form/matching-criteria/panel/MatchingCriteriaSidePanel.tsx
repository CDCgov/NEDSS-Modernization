import { MatchingAttribute, Pass } from 'apps/deduplication/api/model/Pass';
import { Button } from 'design-system/button';
import { Icon } from 'design-system/icon';
import { useEffect, useState } from 'react';
import { useFormContext, useWatch } from 'react-hook-form';
import { AttributeEntry } from '../../attribute-entry/AttributeEntry';
import { SidePanel } from '../../side-panel/SidePanel';
import styles from './matching-criteria-panel.module.scss';
import { DataElements } from 'apps/deduplication/api/model/DataElement';

type AttributeInfo = {
    active: boolean;
    attribute: MatchingAttribute;
    label: string;
};
type Props = {
    visible: boolean;
    dataElements: DataElements;
    onAccept: (attributes: MatchingAttribute[]) => void;
    onCancel: () => void;
};
export const MatchingCriteriaSidePanel = ({ visible, dataElements, onAccept, onCancel }: Props) => {
    const form = useFormContext<Pass>();
    const [selectedAttributes, setSelectedAttributes] = useState<MatchingAttribute[]>([]);
    const [attributeList, setAttributeList] = useState<AttributeInfo[]>([]);
    const { matchingCriteria } = useWatch(form);

    useEffect(() => {
        setSelectedAttributes(matchingCriteria?.map((a) => a.attribute).filter((a) => a !== undefined) ?? []);
    }, [JSON.stringify(matchingCriteria), visible]);

    useEffect(() => {
        setAttributeList([
            {
                attribute: MatchingAttribute.FIRST_NAME,
                label: 'First name',
                active: dataElements.firstName?.active ?? false
            },
            {
                attribute: MatchingAttribute.LAST_NAME,
                label: 'Last name',
                active: dataElements.lastName?.active ?? false
            },
            { attribute: MatchingAttribute.SUFFIX, label: 'Suffix', active: dataElements.suffix?.active ?? false },
            {
                attribute: MatchingAttribute.BIRTHDATE,
                label: 'Date of birth',
                active: dataElements.dateOfBirth?.active ?? false
            },
            { attribute: MatchingAttribute.SEX, label: 'Sex', active: dataElements.sex?.active ?? false },
            { attribute: MatchingAttribute.RACE, label: 'Race', active: dataElements.race?.active ?? false },
            { attribute: MatchingAttribute.ADDRESS, label: 'Address', active: dataElements.address?.active ?? false },
            { attribute: MatchingAttribute.CITY, label: 'City', active: dataElements.city?.active ?? false },
            { attribute: MatchingAttribute.STATE, label: 'State', active: dataElements.state?.active ?? false },
            { attribute: MatchingAttribute.ZIP, label: 'Zip', active: dataElements.zip?.active ?? false },
            { attribute: MatchingAttribute.COUNTY, label: 'County', active: dataElements.county?.active ?? false },
            { attribute: MatchingAttribute.PHONE, label: 'Phone', active: dataElements.telephone?.active ?? false },
            { attribute: MatchingAttribute.EMAIL, label: 'Email', active: dataElements.email?.active ?? false },
            {
                attribute: MatchingAttribute.SOCIAL_SECURITY,
                label: 'Social security number',
                active: dataElements.socialSecurity?.active ?? false
            },
            {
                attribute: MatchingAttribute.DRIVERS_LICENSE_NUMBER,
                label: "Driver's license",
                active: dataElements.driversLicenseNumber?.active ?? false
            },
            {
                attribute: MatchingAttribute.MEDICAID_NUMBER,
                label: 'Medicaid number',
                active: dataElements.medicaidNumber?.active ?? false
            },
            {
                attribute: MatchingAttribute.MEDICAL_RECORD_NUMBER,
                label: 'Medical record number',
                active: dataElements.medicalRecordNumber?.active ?? false
            },
            {
                attribute: MatchingAttribute.ACCOUNT_NUMBER,
                label: 'Account number',
                active: dataElements.accountNumber?.active ?? false
            },
            {
                attribute: MatchingAttribute.NATIONAL_UNIQUE_INDIVIDUAL_IDENTIFIER,
                label: 'National unique individual identifier',
                active: dataElements.nationalUniqueIdentifier?.active ?? false
            },
            {
                attribute: MatchingAttribute.PATIENT_EXTERNAL_IDENTIFIER,
                label: 'Patient external identifier',
                active: dataElements.patientExternalIdentifier?.active ?? false
            },
            {
                attribute: MatchingAttribute.PATIENT_INTERNAL_IDENTIFIER,
                label: 'Patient internal identifier',
                active: dataElements.patientInternalIdentifier?.active ?? false
            },
            {
                attribute: MatchingAttribute.PERSON_NUMBER,
                label: 'Person number',
                active: dataElements.personNumber?.active ?? false
            },
            {
                attribute: MatchingAttribute.VISA_PASSPORT,
                label: 'VISA / Passport number',
                active: dataElements.visaPassport?.active ?? false
            },
            {
                attribute: MatchingAttribute.WIC_IDENTIFIER,
                label: 'WIC identifier',
                active: dataElements.wicIdentifier?.active ?? false
            }
        ]);
    }, [dataElements]);

    const handleOnChange = (attribute: MatchingAttribute) => {
        if (selectedAttributes.includes(attribute)) {
            setSelectedAttributes([...selectedAttributes].filter((a) => a !== attribute));
        } else {
            setSelectedAttributes([...selectedAttributes, attribute]);
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
                    <Button
                        icon={<Icon name="add" sizing="small" />}
                        labelPosition="right"
                        onClick={() => onAccept(selectedAttributes)}>
                        Add attribute(s)
                    </Button>
                </>
            }>
            <div className={styles.matchingCriteriaPanel}>
                {attributeList
                    .filter((a) => a.active)
                    .map((e, k) => (
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
