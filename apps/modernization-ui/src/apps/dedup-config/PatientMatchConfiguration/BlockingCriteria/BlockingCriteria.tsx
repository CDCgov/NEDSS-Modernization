import { Button, Icon } from '@trussworks/react-uswds';
import styles from '../patient-match-body.module.scss';

export const BlockingCriteria = () => {
    return (
        <div className={styles.criteria}>
            <div className={styles.criteriaHeadingContainer}>
                <h4>Blocking criteria</h4>
                <p>Include records that meet all these conditions</p>
            </div>
            <div className={styles.criteriaContentContainer}>
                <p className={styles.criteriaRequest}>Please add blocking criteria to get started</p>
                <Button unstyled type={'button'}>
                    <Icon.Add />
                    Add blocking criteria
                </Button>
            </div>
        </div>
    );
};
