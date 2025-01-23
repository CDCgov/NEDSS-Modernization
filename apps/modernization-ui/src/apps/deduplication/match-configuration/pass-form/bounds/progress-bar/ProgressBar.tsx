import styles from './progress-bar.module.scss';

type Props = {
    lower?: number;
    upper?: number;
    max?: number;
};
export const ProgressBar = ({ lower, upper, max }: Props) => {
    return (
        <div className={styles.progressBar}>
            <div className={styles.barContainer}>
                <div className={styles.baseBar} />
                {max !== undefined && (
                    <>
                        {lower !== undefined && lower > 0 && lower < max && (
                            <div
                                className={styles.reviewBar}
                                style={{ width: `${((max - lower) / max) * 100}%` }}
                                data-content={`| ${lower}`}
                            />
                        )}
                        {upper !== undefined && upper > 0 && upper < max && (
                            <div
                                className={styles.autoBar}
                                style={{ width: `${((max - upper) / max) * 100}%` }}
                                data-content={`| ${upper}`}
                            />
                        )}
                    </>
                )}
            </div>
            <div>{max && (Math.round(max * 100) / 100).toFixed(2)}</div>
        </div>
    );
};
