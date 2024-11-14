import { SingleSelect } from 'design-system/select';
import { Controller, useFormContext } from 'react-hook-form';
import styles from './blocking-criteria-row.module.scss';
import { MatchingConfiguration } from '../../model/Pass';
import { BLOCKING_METHOD_OPTIONS } from '../../model/Blocking';
import { Icon } from '@trussworks/react-uswds';
import { Button } from 'components/button';

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
                <Button unstyled onClick={onRemove}>
                    <Icon.Delete size={3} />
                    Remove
                </Button>
            </div>
        </div>
    );
};
