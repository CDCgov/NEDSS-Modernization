import { Button, Icon } from '@trussworks/react-uswds';
import styles from '../patient-match-body.module.scss';

export const MatchingCriteria = () => {
    return (
        <div className={`${styles.criteria} ${styles.disabled}`}>
            <div className={styles.criteriaHeadingContainer}>
                <h4>Matching criteria</h4>
                <p>Include records that meet all these conditions</p>
            </div>
            <div className={styles.criteriaContentContainer}>
                <p className={styles.criteriaRequest}>Please add a matching attribute to continue</p>
                <Button unstyled type={'button'} disabled>
                    <Icon.Add />
                    Add matching criteria
                </Button>
            </div>
        </div>
    );
};
