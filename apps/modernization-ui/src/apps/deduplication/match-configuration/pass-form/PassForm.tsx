import { DataElementsConfiguration } from 'apps/deduplication/data-elements/DataElement';
import { useEffect, useState } from 'react';
import { useFormContext, useWatch } from 'react-hook-form';
import { MatchingConfiguration } from '../model/Pass';
import { BlockingCriteriaForm } from './blocking/BlockingCriteriaForm';
import { MatchingBoundsForm } from './bounds/MatchingBoundsForm';
import { MatchingCriteriaForm } from './matching/MatchingCriteriaForm';
import styles from './pass-form.module.scss';

type Props = {
    activePass?: number;
    dataElementConfiguration: DataElementsConfiguration;
};
export const PassForm = ({ activePass, dataElementConfiguration }: Props) => {
    const form = useFormContext<MatchingConfiguration>();
    const watch = useWatch({ control: form.control });
    const [logOddsTotal, setLogOddsTodal] = useState<number | undefined>();

    useEffect(() => {
        if (activePass !== undefined) {
            const total = form
                .getValues(`passes.${activePass}.matchingCriteria`)
                ?.map((m) => dataElementConfiguration[m.field.value]?.logOdds)
                .filter((f) => f !== undefined)
                .reduce((a, b) => a + b, 0);
            setLogOddsTodal(total > 0 ? total : undefined);
        } else {
            setLogOddsTodal(undefined);
        }
    }, [JSON.stringify(watch.passes?.[activePass ?? 0].matchingCriteria)]);

    return (
        <div className={styles.passForm}>
            {activePass !== undefined && (
                <>
                    <BlockingCriteriaForm activePass={activePass} />
                    <MatchingCriteriaForm
                        activePass={activePass}
                        dataElementConfiguration={dataElementConfiguration}
                        logOddsTotal={logOddsTotal}
                    />
                    <MatchingBoundsForm activePass={activePass} logOddsTotal={logOddsTotal} />
                </>
            )}
        </div>
    );
};
