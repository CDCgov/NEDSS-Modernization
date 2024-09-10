import { Button, Icon } from '@trussworks/react-uswds';
import styles from './PassConfiguration.module.scss';

const PassConfiguration = () => {
    return (
        <div className={styles.configurationDetails}>
            <div className={styles.criteria}>
                <div className={styles.criteriaHeadingContainer}>
                    <h4>Blocking criteria</h4>
                    <span>Include records that meet all these conditions</span>
                </div>
                <div className={styles.criteriaContentContainer}>
                    <p className={styles.criteriaRequest}>Please add blocking criteria to get started</p>
                    <Button unstyled type={'button'}>
                        <Icon.Add />
                        Add blocking criteria
                    </Button>
                </div>
            </div>
            <div className={`${styles.criteria} ${styles.disabled}`}>
                <div className={styles.criteriaHeadingContainer}>
                    <h4>Matching criteria</h4>
                    <span>Include records that meet all these conditions</span>
                </div>
                <div className={styles.criteriaContentContainer}>
                    <p className={styles.criteriaRequest}>Please add a matching attribute to continue</p>
                    <Button unstyled type={'button'} disabled>
                        <Icon.Add />
                        Add matching criteria
                    </Button>
                </div>
            </div>
            <div className={styles.matchingBounds}>
                <h4>Matching bounds</h4>
            </div>
        </div>
    );
};

export default PassConfiguration;
