import { BlockingAttribute, Pass } from 'apps/deduplication/api/model/Pass';
import { Button } from 'design-system/button';
import { useEffect, useState } from 'react';
import { useFormContext, useWatch } from 'react-hook-form';
import { AttributeEntry } from '../../attribute-entry/AttributeEntry';
import { SidePanel } from '../../side-panel/SidePanel';
import styles from './blocking-criteria-panel.module.scss';
import { BlockingAttributeLabelsList } from 'apps/deduplication/api/model/Labels';
import { DataElements } from 'apps/deduplication/api/model/DataElement';

type Props = {
    visible: boolean;
    dataElements: DataElements;
    onAccept: (attributes: BlockingAttribute[]) => void;
    onCancel: () => void;
};
export const BlockingCriteriaSidePanel = ({ visible, dataElements, onAccept, onCancel }: Props) => {
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

    return (
        <SidePanel
            heading="Add blocking attribute(s)"
            visible={visible}
            onClose={onCancel}
            footer={
                <>
                    <Button secondary onClick={handleCancel}>
                        Cancel
                    </Button>
                    <Button icon="add" labelPosition="right" onClick={() => onAccept(selectedAttributes)}>
                        Add attribute(s)
                    </Button>
                </>
            }>
            <div className={styles.blockingCriteriaPanel}>
                {BlockingAttributeLabelsList.filter((a) => a[1].isActive(dataElements)).map(([attribute, entry]) => (
                    <AttributeEntry
                        key={`blockingAttribute-${attribute}`}
                        label={entry.label}
                        description={entry.description}
                        onChange={() => {
                            handleOnChange(attribute);
                        }}
                        selected={selectedAttributes.includes(attribute)}
                    />
                ))}
            </div>
        </SidePanel>
    );
};
