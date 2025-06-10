import { MatchingAttribute, Pass } from 'apps/deduplication/api/model/Pass';
import { Button } from 'design-system/button';
import { useEffect, useState } from 'react';
import { useFormContext, useWatch } from 'react-hook-form';
import { AttributeEntry } from '../../attribute-entry/AttributeEntry';
import { SidePanel } from '../../side-panel/SidePanel';
import styles from './matching-criteria-panel.module.scss';
import { DataElements } from 'apps/deduplication/api/model/DataElement';
import { MatchingAttributeLabelsList } from 'apps/deduplication/api/model/Labels';

type Props = {
    visible: boolean;
    dataElements: DataElements;
    onAccept: (attributes: MatchingAttribute[]) => void;
    onCancel: () => void;
};
export const MatchingCriteriaSidePanel = ({ visible, dataElements, onAccept, onCancel }: Props) => {
    const form = useFormContext<Pass>();
    const [selectedAttributes, setSelectedAttributes] = useState<MatchingAttribute[]>([]);
    const { matchingCriteria } = useWatch(form);

    useEffect(() => {
        setSelectedAttributes(matchingCriteria?.map((a) => a.attribute).filter((a) => a !== undefined) ?? []);
    }, [JSON.stringify(matchingCriteria), visible]);

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
                    <Button secondary onClick={onCancel}>
                        Cancel
                    </Button>
                    <Button icon="add" labelPosition="right" onClick={() => onAccept(selectedAttributes)}>
                        Add attribute(s)
                    </Button>
                </>
            }>
            <div className={styles.matchingCriteriaPanel}>
                {MatchingAttributeLabelsList.filter((a) => a[1].isActive(dataElements)).map(([attribute, entry], k) => (
                    <AttributeEntry
                        key={`matchingAttribute-${k}`}
                        label={entry.label}
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
