import { Shown } from 'conditional-render';
import styles from './blocking-criteria.module.scss';
import { Button } from 'design-system/button';
import { BlockingAttribute } from 'apps/deduplication/api/model/Pass';
import { useFormContext, useWatch } from 'react-hook-form';
import { useEffect, useState } from 'react';

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
                    <Button icon="delete" outline sizing="small" destructive onClick={() => onRemove(attribute)} />
                </div>
            </div>
        </Shown>
    );
};
