import { Icon } from '@trussworks/react-uswds';
import { Button } from 'components/button';
import { SingleSelect } from 'design-system/select';
import { Controller, useFormContext } from 'react-hook-form';
import { MATCHING_METHOD_OPTIONS } from '../../../model/Matching';
import { MatchingConfiguration } from '../../../model/Pass';
import styles from './matching-criteria-row.module.scss';
type Props = {
    label: string;
    activePass: number;
    index: number;
    logOdds: number;
    onRemove: () => void;
};
export const MatchingCriteriaRow = ({ label, activePass, logOdds, index, onRemove }: Props) => {
    const form = useFormContext<MatchingConfiguration>();

    return (
        <div className={styles.matchingCriteriaRow}>
            <div className={styles.underlined}>
                <div className={styles.information}>
                    <div className={styles.label}>{label}</div>
                    <div className={styles.input}>
                        <Controller
                            control={form.control}
                            name={`passes.${activePass}.matchingCriteria.${index}.method`}
                            rules={{ required: { value: true, message: 'Method is required' } }}
                            render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                                <SingleSelect
                                    id={`matchingMethod-${index}`}
                                    orientation="horizontal"
                                    label="Method"
                                    onBlur={onBlur}
                                    onChange={(e) => {
                                        onChange(e), onBlur();
                                    }}
                                    name={name}
                                    value={value}
                                    options={MATCHING_METHOD_OPTIONS}
                                    error={error?.message}
                                />
                            )}
                        />
                    </div>
                    <div>Log odds {logOdds}</div>
                </div>
                <Button unstyled onClick={onRemove}>
                    <Icon.Delete size={3} />
                    Remove
                </Button>
            </div>
        </div>
    );
};
