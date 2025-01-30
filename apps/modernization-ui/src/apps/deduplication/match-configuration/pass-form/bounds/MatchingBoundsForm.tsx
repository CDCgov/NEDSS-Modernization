import { Heading } from 'components/heading';
import { Controller, useFormContext, useWatch } from 'react-hook-form';
import { MatchingConfiguration } from '../../model/Pass';
import styles from './matching-bounds-form.module.scss';
import { ProgressBar } from './progress-bar/ProgressBar';

type Props = {
    activePass: number;
};
export const MatchingBoundsForm = ({ activePass }: Props) => {
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
                    max={1}
                    lower={watch.passes?.[activePass]?.lowerBound}
                    upper={watch.passes?.[activePass]?.upperBound}
                />
                <div className={styles.inputs}>
                    <Controller
                        name={`passes.${activePass}.lowerBound`}
                        control={form.control}
                        rules={{ required: { value: true, message: 'Lower bound is required' } }}
                        render={({ field: { onChange, onBlur, name } }) => (
                            <>
                                <label htmlFor={name}>Lower belongingness ratio</label>
                                <input
                                    type="number"
                                    onChange={onChange}
                                    onBlur={onBlur}
                                    value={watch.passes?.[activePass].lowerBound ?? 0}
                                    id={name}
                                    name={name}
                                    max={1}
                                    min={0}
                                    step={0.01}
                                />
                            </>
                        )}
                    />
                    <Controller
                        name={`passes.${activePass}.upperBound`}
                        control={form.control}
                        rules={{ required: { value: true, message: 'Upper bound is required' } }}
                        render={({ field: { onChange, onBlur, name } }) => (
                            <>
                                <label htmlFor={name}>Upper belongingness ratio</label>
                                <input
                                    type="number"
                                    onChange={onChange}
                                    onBlur={onBlur}
                                    value={watch.passes?.[activePass].upperBound ?? 0}
                                    id={name}
                                    name={name}
                                    max={1}
                                    min={0}
                                    step={0.01}
                                />
                            </>
                        )}
                    />
                </div>
            </div>
        </section>
    );
};
