import styles from './progress-bar.module.scss';
import { usePatientMatchContext } from '../../context/PatientMatchContext';

type ProgressBarProps = {
    lowerBound: number | undefined;
    upperBound: number | undefined;
};

export const ProgressBar = ({ lowerBound, upperBound }: ProgressBarProps) => {
    const { totalLogOdds } = usePatientMatchContext();
    const safeTotalLogOdds = totalLogOdds ?? 1;

    const requiresReviewPercentage =
        upperBound !== undefined && lowerBound !== undefined && lowerBound < safeTotalLogOdds
            ? ((upperBound - lowerBound) / safeTotalLogOdds) * 100
            : 0;
    const automaticMatchPercentage =
        upperBound !== undefined &&
        upperBound !== 0 &&
        lowerBound !== undefined &&
        upperBound > lowerBound &&
        upperBound < lowerBound + safeTotalLogOdds
            ? ((safeTotalLogOdds - upperBound) / safeTotalLogOdds) * 100
            : 0;

    return (
        <div className={styles.progressBarContainer}>
            <div className={`${styles.progressBar} ${!lowerBound && !upperBound ? styles.disabled : ''}`}>
                <div
                    className={`${styles.progressBarSection} ${styles.requiresReview}`}
                    style={{
                        width: `${requiresReviewPercentage}%`,
                        left: `${lowerBound !== undefined ? (lowerBound / safeTotalLogOdds) * 100 : 0}%`
                    }}></div>

                <div
                    className={`${styles.progressBarSection} ${styles.automaticMatch}`}
                    style={{
                        width: `${automaticMatchPercentage}%`,
                        left: `${upperBound !== undefined ? (upperBound / safeTotalLogOdds) * 100 : 0}%`
                    }}></div>

                <div
                    className={`${styles.boundValue} ${styles.lowerBoundLabel}`}
                    style={{
                        left: `${lowerBound !== undefined && lowerBound < safeTotalLogOdds ? (lowerBound / safeTotalLogOdds) * 100 : 0}%`
                    }}>
                    {lowerBound !== undefined ? lowerBound : ''}
                </div>

                <div
                    className={`${styles.boundValue} ${styles.upperBoundLabel}`}
                    style={{ left: `${upperBound !== undefined ? (upperBound / safeTotalLogOdds) * 100 : 0}%` }}>
                    {upperBound !== undefined ? upperBound : ''}
                </div>
            </div>

            <div className={styles.totalLogOddsLabel}>{safeTotalLogOdds}</div>
        </div>
    );
};
