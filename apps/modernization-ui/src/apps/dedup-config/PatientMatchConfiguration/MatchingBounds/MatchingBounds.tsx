import React, { useEffect } from 'react';
import { useFormContext, Controller } from 'react-hook-form';
import { Input } from 'components/FormInputs/Input';
import { usePatientMatchContext } from '../../context/PatientMatchContext';
import styles from '../PatientMatchConfigurationPage/patient-match-form.module.scss';
import { ProgressBar } from './ProgressBar';

export const MatchingBounds = () => {
    const { control, setValue, watch } = useFormContext();
    const { lowerBound, upperBound, totalLogOdds, matchingCriteria } = usePatientMatchContext();

    useEffect(() => {
        if (lowerBound !== undefined) setValue('lowerBound', lowerBound);
        if (upperBound !== undefined) setValue('upperBound', upperBound);
    }, [lowerBound, upperBound, setValue]);

    const watchedLowerBound = watch('lowerBound', lowerBound);
    const watchedUpperBound = watch('upperBound', upperBound);

    return (
        <div className={`${styles.criteria} ${matchingCriteria.length < 1 ? styles.disabled : ''}`}>
            <div className={styles.criteriaHeadingContainer}>
                <h4>Matching bounds</h4>
                <div className={styles.labels}>
                    <div className={styles.grey}>No match</div>
                    <div className={styles.yellow}>Requires human review</div>
                    <div className={styles.green}>Automatic match</div>
                </div>
            </div>
            <div className={styles.criteriaContentContainer}>
                <div className={styles.progressBar}>
                    <ProgressBar lowerBound={watchedLowerBound} upperBound={watchedUpperBound} />
                </div>
                <div className={styles.bounds}>
                    <Controller
                        name="lowerBound"
                        control={control}
                        render={({ field: { onChange, value, name } }) => (
                            <Input
                                type="number"
                                name={name}
                                label="Lower bound"
                                defaultValue={value?.toString()}
                                value={value}
                                orientation="horizontal"
                                onChange={(e: any) => {
                                    const val = parseFloat(e.target.value);
                                    onChange(isNaN(val) ? undefined : val);
                                }}
                                disabled={!totalLogOdds}
                            />
                        )}
                    />
                    <Controller
                        name="upperBound"
                        control={control}
                        render={({ field: { onChange, value, name } }) => (
                            <Input
                                type="number"
                                name={name}
                                label="Upper bound"
                                defaultValue={value?.toString()}
                                value={value}
                                orientation="horizontal"
                                onChange={(e: any) => {
                                    const val = parseFloat(e.target.value);
                                    onChange(isNaN(val) ? undefined : val);
                                }}
                                disabled={!totalLogOdds}
                            />
                        )}
                    />
                </div>
            </div>
        </div>
    );
};