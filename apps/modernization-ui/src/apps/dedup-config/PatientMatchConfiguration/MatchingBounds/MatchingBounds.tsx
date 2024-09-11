import { Input } from 'components/FormInputs/Input';
import styles from '../patient-match-body.module.scss';

export const MatchingBounds = () => {
    return (
        <div className={`${styles.criteria} ${styles.disabled}`}>
            <div className={styles.criteriaHeadingContainer}>
                <h4>Matching bounds</h4>
                <p>Include records that meet all these conditions</p>
            </div>
            <div className={styles.criteriaContentContainer}>
                <div className={styles.progressBar}></div>
                <div className={styles.bounds}>
                    <Input type="number" label="Lower bound" value={0} disabled orientation="horizontal" />
                    <Input type="number" label="Upper bound" value={100} disabled orientation="horizontal" />
                </div>
            </div>
        </div>
    );
};
