import { Icon } from '@trussworks/react-uswds';
import { matchingAttributeLabelMap, Pass } from 'apps/deduplication/api/model/Pass';
import { DataElements } from 'apps/deduplication/api/model/DataElement';
import { Shown } from 'conditional-render';
import { Button } from 'design-system/button';
import { Card } from 'design-system/card';
import { useEffect, useState } from 'react';
import { useFieldArray, useFormContext, useWatch } from 'react-hook-form';
import { MatchingCriteriaAttribute } from './attribute/MatchingCriteriaAttribute';
import { getLogOdds } from './getLogOdds';
import styles from './matching-criteria.module.scss';

type Props = {
    dataElements: DataElements;
    onAddAttributes: () => void;
};
export const MatchingCriteria = ({ dataElements, onAddAttributes }: Props) => {
    const form = useFormContext<Pass>();
    const { fields, remove } = useFieldArray({ control: form.control, name: 'matchingCriteria' });
    const { blockingCriteria } = useWatch<Pass>(form);
    const [disabled, setDisabled] = useState<boolean>(true);

    useEffect(() => {
        setDisabled(blockingCriteria === undefined || blockingCriteria.length === 0);
    }, [blockingCriteria]);

    const handleRemoveAttribute = (index: number) => {
        remove(index);
    };

    return (
        <div className={styles.matchingCriteria}>
            <Shown when={disabled}>
                <div className={styles.disabledOverlay}></div>
            </Shown>
            <Card
                id="matchingCriteriaCard"
                title="2. Matching criteria"
                subtext="Include records that meet all these conditions">
                <div className={styles.body}>
                    <Shown
                        when={fields && fields.length > 0}
                        fallback={
                            <div className={styles.noMatchingCriteriaText}>
                                Please add matching criteria to continue.
                            </div>
                        }>
                        {fields.map((entry, index) => (
                            <MatchingCriteriaAttribute
                                key={entry.id}
                                label={matchingAttributeLabelMap.get(entry.attribute) ?? ''}
                                attribute={entry.attribute}
                                onRemove={() => handleRemoveAttribute(index)}
                                logOdds={getLogOdds(dataElements, entry.attribute)}
                            />
                        ))}
                    </Shown>
                    <div className={styles.buttonContainer}>
                        <Button
                            icon={<Icon.Add size={3} />}
                            labelPosition="right"
                            outline
                            onClick={onAddAttributes}
                            sizing="small"
                            disabled={disabled}
                            className={styles.addButton}>
                            Add matching attribute(s)
                        </Button>
                    </div>
                </div>
            </Card>
        </div>
    );
};
