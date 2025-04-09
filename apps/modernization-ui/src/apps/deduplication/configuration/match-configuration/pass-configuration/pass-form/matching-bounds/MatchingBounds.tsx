import { Pass } from 'apps/deduplication/api/model/Pass';
import { Shown } from 'conditional-render';
import { Card } from 'design-system/card';
import { useEffect, useState } from 'react';
import { Controller, useFormContext, useWatch } from 'react-hook-form';
import { BoundEntry } from './bound-entry/BoundEntry';
import { DataElements } from 'apps/deduplication/api/model/DataElement';
import styles from './matching-bounds.module.scss';
import { getLogOdds } from '../matching-criteria/getLogOdds';

type Props = {
    dataElements: DataElements;
};
export const MatchingBounds = ({ dataElements }: Props) => {
    const form = useFormContext<Pass>();
    const { lowerBound } = useWatch(form);
    const { upperBound } = useWatch(form);
    const { matchingCriteria, blockingCriteria } = useWatch<Pass>(form);
    const [disabled, setDisabled] = useState<boolean>(true);
    const [totalLogOdds, setTotalLogOdds] = useState<number | undefined>();

    useEffect(() => {
        setDisabled(
            blockingCriteria === undefined ||
                blockingCriteria.length === 0 ||
                matchingCriteria === undefined ||
                matchingCriteria.length === 0
        );
    }, [blockingCriteria, matchingCriteria]);

    useEffect(() => {
        if (disabled) {
            setTotalLogOdds(undefined);
        } else {
            const total = matchingCriteria?.reduce((a, b) => a + getLogOdds(dataElements, b.attribute), 0);
            setTotalLogOdds(total);
        }
    }, [matchingCriteria, disabled]);

    useEffect(() => {
        if (form.formState.dirtyFields.lowerBound || form.formState.dirtyFields.upperBound) {
            form.trigger('lowerBound');
            form.trigger('upperBound');
        }
    }, [totalLogOdds]);

    const validateLowerBound = (value?: number): string | undefined => {
        if (value == undefined) {
            return '';
        } else if (value < 0) {
            return 'Must be between 0 and upper bound.';
        } else if (upperBound !== undefined && value > upperBound) {
            return 'Cannot be greater than upper bound.';
        } else if (totalLogOdds !== undefined && value > totalLogOdds) {
            return 'Cannot be greater than total log odds.';
        }

        return undefined;
    };

    return (
        <div className={styles.matchingBounds}>
            <Shown when={disabled}>
                <div className={styles.disabledOverlay}></div>
            </Shown>
            <Card
                id="matchingBoundsCard"
                title="3. Matching bounds"
                subtext="Records with log odds scores between the lower and upper bounds will present for review and
                    resolution in the potential match queue">
                <div className={styles.body}>
                    <div>
                        <Controller
                            control={form.control}
                            name={'lowerBound'}
                            rules={{
                                required: { value: true, message: 'Lower bound is required.' },
                                validate: validateLowerBound
                            }}
                            render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                                <BoundEntry
                                    label="Lower bound"
                                    name={name}
                                    value={value}
                                    tooltip={
                                        <span>
                                            <b>Lower bound - </b> Records with log odds scores below this number will be
                                            automatically kept separate and new person records created.
                                        </span>
                                    }
                                    onBlur={() => {
                                        onBlur();
                                        if (form.getValues('upperBound') !== undefined) form.trigger('upperBound');
                                    }}
                                    onChange={onChange}
                                    error={error?.message}
                                    disabled={disabled}
                                />
                            )}
                        />
                    </div>
                    <div>
                        <Controller
                            control={form.control}
                            name={'upperBound'}
                            rules={{
                                required: { value: true, message: 'Upper bound is required.' },
                                max: {
                                    value: totalLogOdds ?? 0,
                                    message: 'Cannot be greater than total log odds.'
                                },
                                min: {
                                    value: lowerBound ?? 0,
                                    message: `Must be between ${lowerBound ? 'lower bound' : '0'} and total log odds.`
                                }
                            }}
                            render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                                <BoundEntry
                                    label="Upper bound"
                                    name={name}
                                    value={value}
                                    tooltip={
                                        <span>
                                            <b>Upper bound - </b> Records with log odds scores above this number will be
                                            automatically merged into a single person record
                                        </span>
                                    }
                                    onBlur={() => {
                                        onBlur();
                                        form.trigger('lowerBound');
                                    }}
                                    onChange={onChange}
                                    error={error?.message}
                                    disabled={disabled}
                                />
                            )}
                        />
                    </div>
                    <div>
                        Total log odds: <strong>{totalLogOdds ?? '--'}</strong>
                    </div>
                </div>
            </Card>
        </div>
    );
};
