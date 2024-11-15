import { Heading } from 'components/heading';
import { Controller, useFormContext, useWatch } from 'react-hook-form';
import { MatchingConfiguration } from '../../model/Pass';
import styles from './matching-bounds-form.module.scss';
import { ProgressBar } from './progress-bar/ProgressBar';

type Props = {
    activePass: number;
    logOddsTotal?: number;
};
export const MatchingBoundsForm = ({ activePass, logOddsTotal }: Props) => {
    const form = useFormContext<MatchingConfiguration>();
    const watch = useWatch({ control: form.control });

    return (
        <section className={styles.matchingBoundsForm}>
            <header>
                <Heading level={2}>Matching bounds</Heading>
                <div className={styles.badges}>
                    <div className={styles.noMatch}>No match</div>
                    <div className={styles.reviewMatch}>Requires human review</div>
                    <div className={styles.automaticMatch}>Automatic match</div>
                </div>
            </header>
            <div className={styles.formContent}>
                <ProgressBar
                    max={logOddsTotal}
                    lower={watch.passes?.[activePass].lowerBound}
                    upper={watch.passes?.[activePass].upperBound}
                />
                <div className={styles.inputs}>
                    <Controller
                        name={`passes.${activePass}.lowerBound`}
                        control={form.control}
                        render={({ field: { onChange, onBlur, value, name } }) => (
                            <>
                                <label htmlFor={name}>Lower bound</label>
                                <input
                                    type="number"
                                    onChange={onChange}
                                    onBlur={onBlur}
                                    value={value ?? ''}
                                    id={name}
                                    name={name}
                                    max={logOddsTotal}
                                    min={0}
                                    step={0.1}
                                />
                            </>
                        )}
                    />
                    <Controller
                        name={`passes.${activePass}.upperBound`}
                        control={form.control}
                        render={({ field: { onChange, onBlur, value, name } }) => (
                            <>
                                <label htmlFor={name}>Upper bound</label>
                                <input
                                    type="number"
                                    onChange={onChange}
                                    onBlur={onBlur}
                                    value={value ?? ''}
                                    id={name}
                                    name={name}
                                    max={logOddsTotal}
                                    min={0}
                                    step={0.1}
                                />
                            </>
                        )}
                    />
                </div>
            </div>
        </section>
    );
};
