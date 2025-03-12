import { Icon } from '@trussworks/react-uswds';
import { MatchingAttribute, MatchMethod, Pass } from 'apps/deduplication/api/model/Pass';
import { SelectInput } from 'components/FormInputs/SelectInput';
import { Shown } from 'conditional-render';
import { Button } from 'design-system/button';
import { useEffect, useState } from 'react';
import { Controller, useFormContext, useWatch } from 'react-hook-form';
import styles from './matching-criteria.module.scss';

type AttributeProps = {
    label: string;
    attribute: MatchingAttribute;
    onRemove: (attribute: MatchingAttribute) => void;
};
export const MatchingCriteriaAttribute = ({ label, attribute, onRemove }: AttributeProps) => {
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
            <div className={styles.attribute}>
                <div className={styles.info}>
                    <div>
                        <div className={styles.label}>{label}</div>
                        <div className={styles.logOdds}>Log odds: TODO</div>
                    </div>
                    <Controller
                        control={form.control}
                        name={`matchingCriteria.${index}.method`}
                        rules={{ required: { value: true, message: 'Match method is required.' } }}
                        render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                            <SelectInput
                                defaultValue={value}
                                onBlur={onBlur}
                                onChange={onChange}
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
                    <Button outline onClick={() => onRemove(attribute)}>
                        <Icon.Delete size={3} />
                    </Button>
                </div>
            </div>
        </Shown>
    );
};
