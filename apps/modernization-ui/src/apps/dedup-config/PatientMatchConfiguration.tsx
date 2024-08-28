import styles from './PatientMatchConfig.module.scss';
import { Button } from '@trussworks/react-uswds';

const PatientMatchConfiguration = () => {
    return (
        <div>
            <div className={styles.header}>
                <h2>Patient match configuration</h2>
                <Button unstyled type="button" className={styles.dataConfigButton}>
                    Data elements configuration
                </Button>
            </div>
        </div>
    );
};

export default PatientMatchConfiguration;
