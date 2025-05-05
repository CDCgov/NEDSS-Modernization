import { MatchingAttribute, MatchMethod, Pass } from 'apps/deduplication/api/model/Pass';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { Shown } from 'conditional-render';
import { Button } from 'design-system/button';
import { Icon } from 'design-system/icon';
import { useEffect, useState } from 'react';
import { Controller, useFormContext, useWatch } from 'react-hook-form';
import { NumericHintInput } from '../../matching-bounds/numeric-hint-input/NumericHintInput';
import styles from './matching-criteria-attribute.module.scss';

type AttributeProps = {
    label: string;
    attribute: MatchingAttribute;
    index: number;
    logOdds: number;
    onRemove: (index: number) => void;
};
export const MatchingCriteriaAttribute = ({ label, attribute, index, logOdds, onRemove }: AttributeProps) => {
    const form = useFormContext<Pass>();
    const { matchingCriteria } = useWatch<Pass>(form);
    const [visible, setVisible] = useState(false);
    const method = useWatch({ control: form.control, name: `matchingCriteria.${index}.method` });

    useEffect(() => {
        if (method === MatchMethod.EXACT) {
            form.setValue(`matchingCriteria.${index}.threshold`, 1);
        } else {
            form.resetField(`matchingCriteria.${index}.threshold`);
        }
    }, [method]);

    useEffect(() => {
        if (matchingCriteria && matchingCriteria.length > 0) {
            setVisible(matchingCriteria.find((m) => m.attribute === attribute) !== undefined);
        } else {
            setVisible(false);
        }
    }, [JSON.stringify(matchingCriteria)]);

    return (
        <Shown when={visible}>
            <div className={styles.matchingAttributeRow}>
                <div className={styles.attributeInfoWrapper}>
                    <div className={styles.attributeInfo}>
                        <div className={styles.label}>{label}</div>
                        <div className={styles.logOdds}>
                            <span>Log odds:</span>
                            <span>{logOdds}</span>
                        </div>
                    </div>
                    <Controller
                        control={form.control}
                        name={`matchingCriteria.${index}.method`}
                        rules={{ required: { value: true, message: 'Matching method is required.' } }}
                        render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                            <SelectInput
                                defaultValue={value}
                                label="Method"
                                onBlur={onBlur}
                                onChange={(e) => {
                                    onChange(e);
                                    onBlur();
                                }}
                                id={name}
                                name={name}
                                htmlFor={name}
                                options={[
                                    { name: 'Exact match', value: MatchMethod.EXACT },
                                    { name: 'JaroWinkler', value: MatchMethod.JAROWINKLER }
                                ]}
                                error={error?.message}
                            />
                        )}
                    />
                    <Controller
                        control={form.control}
                        name={`matchingCriteria.${index}.threshold`}
                        rules={{
                            required: { value: true, message: 'Threshold is required.' },
                            max: {
                                value: 1,
                                message: 'Cannot be greater 1.'
                            },
                            min: {
                                value: 0,
                                message: 'Cannot be a negative number.'
                            }
                        }}
                        render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                            <NumericHintInput
                                label="Threshold"
                                name={name}
                                value={value}
                                min={0}
                                max={1}
                                step={0.01}
                                className={styles.thresholdInput}
                                tooltip={
                                    <span className={styles.thresholdLabel}>
                                        <b>Threshold -</b> Values between 0 and 1, above which two strings are said to
                                        be "similar enough" that they are probably the same thing. Values that are less
                                        than the threshold will be calculated as 0. <br /> <br />
                                        Attributes that use “Exact Match” will automatically have a threshold of 1 and
                                        cannot be adjusted.
                                    </span>
                                }
                                onBlur={() => {
                                    onBlur();
                                    if (form.getValues('upperBound') !== undefined) form.trigger('upperBound');
                                }}
                                onChange={onChange}
                                error={error?.message}
                                disabled={method === MatchMethod.EXACT}
                            />
                        )}
                    />
                </div>
                <div className={styles.deleteButton}>
                    <Button
                        icon={<Icon name="delete" />}
                        sizing="small"
                        outline
                        destructive
                        onClick={() => onRemove(index)}
                    />
                </div>
            </div>
        </Shown>
    );
};
