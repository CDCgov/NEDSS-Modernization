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
    }, [blockingCriteria, visible]);

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
                <AttributeEntry
                    label="First name"
                    description="The first 4 characters of the person's first name."
                    onChange={() => {
                        handleOnChange(BlockingAttribute.FIRST_NAME);
                    }}
                    selected={selectedAttributes.includes(BlockingAttribute.FIRST_NAME)}
                />
                <AttributeEntry
                    label="Last name"
                    description="The first 4 characters of the person's last name."
                    onChange={() => {
                        handleOnChange(BlockingAttribute.LAST_NAME);
                    }}
                    selected={selectedAttributes.includes(BlockingAttribute.LAST_NAME)}
                />
                <AttributeEntry
                    label="Date of birth"
                    description="The person's birthdate in the format YYYY-MM-DD."
                    onChange={() => {
                        handleOnChange(BlockingAttribute.DATE_OF_BIRTH);
                    }}
                    selected={selectedAttributes.includes(BlockingAttribute.DATE_OF_BIRTH)}
                />
                <AttributeEntry
                    label="Sex"
                    description="The person's sex in the format of M or F."
                    onChange={() => {
                        handleOnChange(BlockingAttribute.SEX);
                    }}
                    selected={selectedAttributes.includes(BlockingAttribute.SEX)}
                />
                <AttributeEntry
                    label="Street address 1"
                    description="The first 4 characters of the person's address."
                    onChange={() => {
                        handleOnChange(BlockingAttribute.STREET_ADDRESS);
                    }}
                    selected={selectedAttributes.includes(BlockingAttribute.STREET_ADDRESS)}
                />
                <AttributeEntry
                    label="Zip"
                    description="The person's 5 digit zip code."
                    onChange={() => {
                        handleOnChange(BlockingAttribute.ZIP);
                    }}
                    selected={selectedAttributes.includes(BlockingAttribute.ZIP)}
                />
                <AttributeEntry
                    label="Email"
                    description="The first 4 characters of the person's email address."
                    onChange={() => {
                        handleOnChange(BlockingAttribute.EMAIL);
                    }}
                    selected={selectedAttributes.includes(BlockingAttribute.EMAIL)}
                />
                <AttributeEntry
                    label="Phone"
                    description="The last 4 digits of the person's phone number."
                    onChange={() => {
                        handleOnChange(BlockingAttribute.PHONE);
                    }}
                    selected={selectedAttributes.includes(BlockingAttribute.PHONE)}
                />
            </div>
        </SidePanel>
    );
};
