import { ConditionSummary } from 'apps/page-builder/generated';
import { NoData } from 'design-system/data';
import { useState } from 'react';
import styles from './page-library-table.module.scss';

type Props = {
    conditions: ConditionSummary[];
};
export const ConditionsDisplay = ({ conditions }: Props) => {
    const [showAll, setShowAll] = useState<boolean>(false);

    const renderConditions = () => {
        let toShow = conditions;
        if (!showAll) {
            toShow = conditions.slice(0, 5);
        }
        return (
            <ul className={styles.conditionList}>
                {toShow.map((c, k) => (
                    <li key={k}>{`${c.name} (${c.id})`}</li>
                ))}
            </ul>
        );
    };
    return (
        <>
            {conditions.length > 0 ? renderConditions() : <NoData />}
            {conditions.length > 5 && !showAll && (
                <button
                    aria-label="view all conditions"
                    className={styles.expandButton}
                    onClick={() => setShowAll(true)}>{`${conditions.length - 5} more...`}</button>
            )}
            {conditions.length > 5 && showAll && (
                <button
                    aria-label="view fewer conditions"
                    className={styles.expandButton}
                    onClick={() => setShowAll(false)}>{`view less`}</button>
            )}
        </>
    );
};
