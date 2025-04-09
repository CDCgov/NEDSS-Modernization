import { MatchingAttribute, MatchMethod, Pass } from 'apps/deduplication/api/model/Pass';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { Shown } from 'conditional-render';
import { Button } from 'design-system/button';
import { useEffect, useState } from 'react';
import { Controller, useFormContext, useWatch } from 'react-hook-form';
import styles from './matching-criteria-attribute.module.scss';
import { Icon } from 'design-system/icon';

type AttributeProps = {
    label: string;
    attribute: MatchingAttribute;
    logOdds: number;
    onRemove: (attribute: MatchingAttribute) => void;
};
export const MatchingCriteriaAttribute = ({ label, attribute, logOdds, onRemove }: AttributeProps) => {
    const form = useFormContext<Pass>();
    const { matchingCriteria } = useWatch<Pass>(form);
    const [index, setIndex] = useState(0);
    const [visible, setVisible] = useState(false);

    useEffect(() => {
        if (matchingCriteria && matchingCriteria.length > 0) {
            setIndex(matchingCriteria?.findIndex((m) => m.attribute === attribute));
            setVisible(matchingCriteria.find((m) => m.attribute === attribute) !== undefined);
        } else {
            setVisible(false);
        }
    }, [matchingCriteria]);

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
                </div>
                <div className={styles.deleteButton}>
                    <Button
                        icon={<Icon name="delete" />}
                        sizing="small"
                        outline
                        destructive
                        onClick={() => onRemove(attribute)}
                    />
                </div>
            </div>
        </Shown>
    );
};
