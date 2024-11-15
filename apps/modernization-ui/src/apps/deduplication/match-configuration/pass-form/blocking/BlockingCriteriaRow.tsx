import { Icon } from '@trussworks/react-uswds';
import { Button } from 'components/button';
import { SingleSelect } from 'design-system/select';
import { Controller, useFormContext } from 'react-hook-form';
import { BLOCKING_METHOD_OPTIONS } from '../../model/Blocking';
import { MatchingConfiguration } from '../../model/Pass';
import styles from './blocking-criteria-row.module.scss';

type Props = {
    label: string;
    activePass: number;
    index: number;
    onRemove: () => void;
};
export const BlockingCriteriaRow = ({ activePass, label, index, onRemove }: Props) => {
    const form = useFormContext<MatchingConfiguration>();

    return (
        <div className={styles.blockingCriteriaRow}>
            <div className={styles.underlined}>
                <div className={styles.information}>
                    <div className={styles.label}>{label}</div>
                    <div className={styles.input}>
                        <Controller
                            control={form.control}
                            name={`passes.${activePass}.blockingCriteria.${index}.method`}
                            rules={{ required: { value: true, message: 'Method is required' } }}
                            render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                                <SingleSelect
                                    id={`blockingMethod-${index}`}
                                    orientation="horizontal"
                                    label="Method"
                                    onBlur={onBlur}
                                    onChange={(e) => {
                                        onChange(e), onBlur();
                                    }}
                                    name={name}
                                    value={value}
                                    options={BLOCKING_METHOD_OPTIONS}
                                    error={error?.message}
                                />
                            )}
                        />
                    </div>
                </div>
                <Button unstyled onClick={onRemove}>
                    <Icon.Delete size={3} />
                    Remove
                </Button>
            </div>
        </div>
    );
};
