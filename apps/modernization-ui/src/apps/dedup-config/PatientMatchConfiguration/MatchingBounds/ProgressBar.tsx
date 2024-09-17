import styles from './progress-bar.module.scss';

type ProgressBarProps = {
    lowerBound: number | undefined;
    upperBound: number | undefined;
    totalLogOdds: number;
};

export const ProgressBar = ({ lowerBound, upperBound, totalLogOdds }: ProgressBarProps) => {
    const requiresReviewPercentage =
        upperBound !== undefined && lowerBound !== undefined ? ((upperBound - lowerBound) / totalLogOdds) * 100 : 0;

    const automaticMatchPercentage = upperBound !== undefined ? ((totalLogOdds - upperBound) / totalLogOdds) * 100 : 0;

    return (
        <div className={styles.progressBarContainer}>
            <div className={`${styles.progressBar} ${!lowerBound && !upperBound ? styles.disabled : ''}`}>
                <div
                    className={`${styles.progressBarSection} ${styles.requiresReview}`}
                    style={{
                        width: `${requiresReviewPercentage}%`,
                        left: `${lowerBound !== undefined ? (lowerBound / totalLogOdds) * 100 : 0}%`
                    }}></div>
                <div
                    className={`${styles.progressBarSection} ${styles.automaticMatch}`}
                    style={{
                        width: `${automaticMatchPercentage}%`,
                        left: `${upperBound !== undefined ? (upperBound / totalLogOdds) * 100 : 0}%`
                    }}></div>

                <div
                    className={`${styles.boundValue} ${styles.lowerBoundLabel}`}
                    style={{ left: `${lowerBound !== undefined ? (lowerBound / totalLogOdds) * 100 : 0}%` }}>
                    {lowerBound !== undefined ? lowerBound : ''}
                </div>

                <div
                    className={`${styles.boundValue} ${styles.upperBoundLabel}`}
                    style={{ left: `${upperBound !== undefined ? (upperBound / totalLogOdds) * 100 : 0}%` }}>
                    {upperBound !== undefined ? upperBound : ''}
                </div>
            </div>

            <div className={styles.totalLogOddsLabel}>{totalLogOdds}</div>
        </div>
    );
};
