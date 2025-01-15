import { DataElements } from 'apps/deduplication/data-elements/DataElement';
import { Toggle } from 'design-system/toggle/Toggle';
import { Controller, useFormContext, useWatch } from 'react-hook-form';
import { MatchingConfiguration } from '../model/Pass';
import { BlockingCriteriaForm } from './blocking/BlockingCriteriaForm';
import { MatchingBoundsForm } from './bounds/MatchingBoundsForm';
import { MatchingCriteriaForm } from './matching/MatchingCriteriaForm';
import styles from './pass-form.module.scss';

type Props = {
    activePass?: number;
    dataElements: DataElements;
};
export const PassForm = ({ activePass, dataElements }: Props) => {
    const form = useFormContext<MatchingConfiguration>();
    const watch = useWatch({ control: form.control });

    return (
        <div className={styles.passForm}>
            {activePass !== undefined && (
                <div className={styles.cardContainer}>
                    <BlockingCriteriaForm activePass={activePass} />
                    <MatchingCriteriaForm activePass={activePass} dataElements={dataElements} />
                    <MatchingBoundsForm activePass={activePass} />
                    <div className={styles.activateButton}>
                        <Controller
                            control={form.control}
                            name={`passes.${activePass}.active`}
                            render={({ field: { onChange, onBlur, name } }) => (
                                <Toggle
                                    value={watch.passes?.[activePass].active}
                                    label={'Activate this pass configuration'}
                                    name={name}
                                    onChange={onChange}
                                    onBlur={onBlur}
                                    sizing="compact"
                                />
                            )}
                        />
                    </div>
                </div>
            )}
        </div>
    );
};
