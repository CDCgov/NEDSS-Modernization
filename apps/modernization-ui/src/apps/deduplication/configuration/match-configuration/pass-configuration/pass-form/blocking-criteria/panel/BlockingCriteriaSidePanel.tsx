import { BlockingAttribute, Pass } from 'apps/deduplication/api/model/Pass';
import { Button } from 'design-system/button';
import { Icon } from 'design-system/icon';
import { useEffect, useState } from 'react';
import { useFormContext, useWatch } from 'react-hook-form';
import { AttributeEntry } from '../../attribute-entry/AttributeEntry';
import { SidePanel } from '../../side-panel/SidePanel';
import styles from './blocking-criteria-panel.module.scss';

type Props = {
    visible: boolean;
    onAccept: (attributes: BlockingAttribute[]) => void;
    onCancel: () => void;
};
export const BlockingCriteriaSidePanel = ({ visible, onAccept, onCancel }: Props) => {
    const form = useFormContext<Pass>();
    const [selectedAttributes, setSelectedAttributes] = useState<BlockingAttribute[]>([]);
    const { blockingCriteria } = useWatch(form);

    useEffect(() => {
        setSelectedAttributes(blockingCriteria ?? []);
    }, [JSON.stringify(blockingCriteria), visible]);

    const handleOnChange = (attribute: BlockingAttribute) => {
        if (selectedAttributes.includes(attribute)) {
            setSelectedAttributes([...selectedAttributes].filter((a) => a !== attribute));
        } else {
            setSelectedAttributes([...selectedAttributes, attribute]);
        }
    };

    const handleCancel = () => {
        onCancel();
        setSelectedAttributes(blockingCriteria ?? []);
    };
    const attributeList: { attribute: BlockingAttribute; label: string; description: string }[] = [
        {
            attribute: BlockingAttribute.FIRST_NAME,
            label: 'First name',
            description: "The first 4 characters of the person's first name."
        },
        {
            attribute: BlockingAttribute.LAST_NAME,
            label: 'Last name',
            description: "The first 4 characters of the person's last name."
        },
        {
            attribute: BlockingAttribute.BIRTHDATE,
            label: 'Date of birth',
            description: "The person's birthdate in the format YYYY-MM-DD."
        },
        { attribute: BlockingAttribute.SEX, label: 'Sex', description: "The person's sex in the format of M or F." },
        {
            attribute: BlockingAttribute.ADDRESS,
            label: 'Street address 1',
            description: "The first 4 characters of the person's address."
        },
        { attribute: BlockingAttribute.ZIP, label: 'Zip', description: "The person's 5 digit zip code." },
        {
            attribute: BlockingAttribute.EMAIL,
            label: 'Email',
            description: "The first 4 characters of the person's email address."
        },
        {
            attribute: BlockingAttribute.PHONE,
            label: 'Phone',
            description: "The last 4 digits of the person's phone number."
        }
    ];

    return (
        <SidePanel
            heading="Add blocking attribute(s)"
            visible={visible}
            onClose={onCancel}
            footer={
                <>
                    <Button outline onClick={handleCancel}>
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
            <div className={styles.blockingCriteriaPanel}>
                {attributeList.map((a, k) => (
                    <AttributeEntry
                        key={`blockingAttribute-${k}`}
                        label={a.label}
                        description={a.description}
                        onChange={() => {
                            handleOnChange(a.attribute);
                        }}
                        selected={selectedAttributes.includes(a.attribute)}
                    />
                ))}
            </div>
        </SidePanel>
    );
};
