import { Icon } from '@trussworks/react-uswds';
import { BlockingAttribute } from 'apps/deduplication/api/model/Pass';
import { Shown } from 'conditional-render';
import { Button } from 'design-system/button';
import { Card } from 'design-system/card';
import { useFormContext, useWatch } from 'react-hook-form';
import { BlockingCriteriaAttribute } from './attribute/BlockingCriteriaAttribute';
import styles from './blocking-criteria.module.scss';
import { BlockingAttributeLabelMap } from 'apps/deduplication/api/model/Labels';

type Props = {
    onAddAttributes: () => void;
};
export const BlockingCriteria = ({ onAddAttributes: onShowAttributes }: Props) => {
    const form = useFormContext<{ blockingCriteria: BlockingAttribute[] }>();
    const registeredBlockingCriteria = form.register('blockingCriteria', { required: true, minLength: 1 });
    const { blockingCriteria } = useWatch(form);
    const attributeList = Array.from(BlockingAttributeLabelMap.entries());

    const handleRemoveAttribute = (attribute: BlockingAttribute) => {
        const value = [...(blockingCriteria ?? [])].filter((a) => a !== attribute);

        registeredBlockingCriteria.onChange({
            target: { name: 'blockingCriteria', value: value }
        });
        form.trigger('blockingCriteria');
    };

    return (
        <Card
            id="blockingCriteriaCard"
            title="1. Blocking criteria"
            subtext="Include records that meet all these conditions">
            <div className={styles.blockingCriteria}>
                <Shown
                    when={blockingCriteria && blockingCriteria.length > 0}
                    fallback={
                        <div className={styles.noBlockingCriteriaText}>
                            Please add blocking criteria to get started.
                        </div>
                    }>
                    {attributeList.map(([attribute, entry], k) => {
                        return (
                            <BlockingCriteriaAttribute
                                key={k}
                                label={entry.label}
                                description={entry.description}
                                attribute={attribute}
                                onRemove={handleRemoveAttribute}
                            />
                        );
                    })}
                </Shown>
                <div className={styles.buttonContainer}>
                    <Button
                        icon={<Icon.Add size={3} />}
                        labelPosition="right"
                        outline
                        onClick={onShowAttributes}
                        sizing="small"
                        className={styles.addButton}>
                        Add blocking attribute(s)
                    </Button>
                </div>
            </div>
        </Card>
    );
};
