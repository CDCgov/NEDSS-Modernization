import { useEffect, useState } from 'react';

import { BlockingAttribute } from 'apps/deduplication/api/model/Pass';
import { Shown } from 'conditional-render';
import { Button } from 'design-system/button';
import { useFormContext, useWatch } from 'react-hook-form';

import styles from './blocking-criteria.module.scss';

type AttributeProps = {
    label: string;
    description: string;
    attribute: BlockingAttribute;
    onRemove: (attribute: BlockingAttribute) => void;
};
export const BlockingCriteriaAttribute = ({ label, description, attribute, onRemove }: AttributeProps) => {
    const form = useFormContext<{ blockingCriteria: BlockingAttribute[] }>();
    const { blockingCriteria } = useWatch(form);
    const [visible, setVisible] = useState(false);

    useEffect(() => {
        if (blockingCriteria) {
            setVisible(blockingCriteria.includes(attribute));
        } else {
            setVisible(false);
        }
    }, [JSON.stringify(blockingCriteria)]);
    return (
        <Shown when={visible}>
            <div className={styles.attribute}>
                <div>
                    <div className={styles.label}>{label}</div>
                    <div className={styles.description}>{description}</div>
                </div>
                <div>
                    <Button
                        icon="delete"
                        secondary={true}
                        sizing="small"
                        destructive={true}
                        aria-label="Remove"
                        onClick={() => onRemove(attribute)}
                    />
                </div>
            </div>
        </Shown>
    );
};
