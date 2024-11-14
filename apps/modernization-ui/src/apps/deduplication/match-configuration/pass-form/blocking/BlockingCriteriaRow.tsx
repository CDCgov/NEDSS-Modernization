import { SingleSelect } from 'design-system/select';
import { Controller, useFormContext } from 'react-hook-form';
import styles from './blocking-criteria-row.module.scss';
import { MatchingConfiguration } from '../../model/Pass';
import { BLOCKING_METHOD_OPTIONS } from '../../model/Blocking';

type Props = {
    label: string;
    index: number;
    onRemove: () => void;
};
export const BlockingCriteriaRow = ({ label, index, onRemove }: Props) => {
    const form = useFormContext<MatchingConfiguration>();

    return (
        <div className={styles.blockingCriteriaRow}>
            <div>
                <div>{label}</div>
                <div>
                    <Controller
                        control={form.control}
                        name={`passes.${1}.blockingCriteria.${index}.method`}
                        render={({ field: { onBlur, onChange, value, name } }) => (
                            <SingleSelect
                                id={`blockingMethod-${index}`}
                                orientation="horizontal"
                                label="Method"
                                onBlur={onBlur}
                                onChange={onChange}
                                name={name}
                                value={value}
                                options={BLOCKING_METHOD_OPTIONS}
                            />
                        )}
                    />
                </div>
            </div>
            <div onClick={onRemove}>Remove</div>
        </div>
    );
};
