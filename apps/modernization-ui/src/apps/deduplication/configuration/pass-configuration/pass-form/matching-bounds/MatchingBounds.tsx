import { Pass } from 'apps/deduplication/api/model/Pass';
import { Heading } from 'components/heading';
import { Shown } from 'conditional-render';
import { InlineErrorMessage } from 'design-system/field/InlineErrorMessage';
import { Icon } from 'design-system/icon';
import { Numeric } from 'design-system/input/numeric/Numeric';
import { useEffect, useState } from 'react';
import { Controller, useFormContext, useWatch } from 'react-hook-form';
import styles from './matching-bounds.module.scss';

export const MatchingBounds = () => {
    const form = useFormContext<Pass>();
    const { matchingCriteria, blockingCriteria } = useWatch<Pass>(form);
    const [disabled, setDisabled] = useState<boolean>(true);

    useEffect(() => {
        setDisabled(
            blockingCriteria === undefined ||
                blockingCriteria.length === 0 ||
                matchingCriteria === undefined ||
                matchingCriteria.length === 0
        );
    }, [blockingCriteria, matchingCriteria]);

    return (
        <div className={styles.matchingBounds}>
            <Shown when={disabled}>
                <div className={styles.disabledOverlay}></div>
            </Shown>
            <div className={styles.heading}>
                <Heading level={2}>3. Matching criteria</Heading>
                <span>
                    Records with log odds scores between the lower and upper bounds will present for review and
                    resolution in the potential match queue
                </span>
            </div>
            <div className={styles.body}>
                <div>
                    <Controller
                        control={form.control}
                        name={'lowerBound'}
                        rules={{ required: { value: true, message: 'Lower bound is required.' } }}
                        render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                            <>
                                <label htmlFor={name}>
                                    Lower bound <Icon name="info_outline" sizing="small" />
                                </label>
                                <Numeric onBlur={onBlur} onChange={onChange} id={name} value={value} />
                                {error?.message && (
                                    <InlineErrorMessage id={`${name}-error`}>{error.message}</InlineErrorMessage>
                                )}
                            </>
                        )}
                    />
                </div>
                <div>
                    <Controller
                        control={form.control}
                        name={'upperBound'}
                        rules={{ required: { value: true, message: 'Upper bound is required.' } }}
                        render={({ field: { onBlur, onChange, value, name }, fieldState: { error } }) => (
                            <>
                                <label htmlFor={name}>
                                    Upper bound <Icon name="info_outline" sizing="small" />
                                </label>
                                <Numeric onBlur={onBlur} onChange={onChange} id={name} value={value} />
                                {error?.message && (
                                    <InlineErrorMessage id={`${name}-error`}>{error.message}</InlineErrorMessage>
                                )}
                            </>
                        )}
                    />
                </div>
                <div>
                    Total log odds: <strong>NYI - TODO</strong>
                </div>
            </div>
        </div>
    );
};
